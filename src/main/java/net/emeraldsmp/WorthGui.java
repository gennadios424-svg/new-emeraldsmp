package net.emeraldsmp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public final class WorthGui {
    public static final String TITLE_PREFIX = "§2§lEmeraldSMP §8» §a§lWorth";
    public static final int ROWS = 6;
    public static final int CONTENT_SLOTS = 45;

    public static void open(EmeraldSMP plugin, WorthService worth, Player player, String query) {
        int page = 0;
        show(plugin, worth, player, query == null ? "" : query, page);
    }

    public static void show(EmeraldSMP plugin, WorthService worth, Player player, String query, int page) {
        List<Material> filtered = worth.getItems().stream()
            .filter(m -> query == null || query.isBlank() || WorthService.pretty(m.name()).toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)))
            .collect(Collectors.toList());

        int maxPage = Math.max(0, (filtered.size() - 1) / CONTENT_SLOTS);
        page = Math.max(0, Math.min(page, maxPage));

        Inventory inv = Bukkit.createInventory(new WorthHolder(query, page), ROWS * 9,
            TITLE_PREFIX + " §7(" + (page + 1) + "/" + (maxPage + 1) + ")");

        for (int i = 0; i < CONTENT_SLOTS; i++) {
            int index = page * CONTENT_SLOTS + i;
            if (index >= filtered.size()) break;
            Material material = filtered.get(index);
            inv.setItem(i, display(material, worth.getPrice(material)));
        }

        ItemStack filler = item(Material.GREEN_STAINED_GLASS_PANE, "§2");
        for (int i = CONTENT_SLOTS; i < 54; i++) inv.setItem(i, filler);

        inv.setItem(45, item(Material.SPECTRAL_ARROW, "§a§lPrevious Page", "§7Click to go back"));
        inv.setItem(49, item(Material.EMERALD, "§a§lWorth", "§7Items: §f" + filtered.size(), "§7Page: §f" + (page + 1) + "§7/§f" + (maxPage + 1)));
        inv.setItem(53, item(Material.SPECTRAL_ARROW, "§a§lNext Page", "§7Click to continue"));

        player.openInventory(inv);
    }

    private static ItemStack display(Material material, double price) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(net.kyori.adventure.text.Component.text("§a" + WorthService.pretty(material.name())));
        meta.lore(List.of(
            net.kyori.adventure.text.Component.text("§7Worth: §2§l$" + WorthService.money(price)),
            net.kyori.adventure.text.Component.text("§8Per item")
        ));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack item(Material material, String name, String... lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(net.kyori.adventure.text.Component.text(name));
        if (lore.length > 0) meta.lore(Arrays.stream(lore).map(net.kyori.adventure.text.Component::text).toList());
        stack.setItemMeta(meta);
        return stack;
    }
}
