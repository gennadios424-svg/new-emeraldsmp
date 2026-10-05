package net.emeraldsmp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public final class WorthListener implements Listener {
    private final EmeraldSMP plugin;
    private final WorthService worth;

    public WorthListener(EmeraldSMP plugin, WorthService worth) {
        this.plugin = plugin;
        this.worth = worth;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!(event.getView().getTopInventory().getHolder() instanceof WorthHolder holder)) return;

        event.setCancelled(true);
        int slot = event.getRawSlot();
        if (slot == 45 && holder.getPage() > 0) {
            WorthGui.show(plugin, worth, player, holder.getQuery(), holder.getPage() - 1);
        } else if (slot == 53) {
            WorthGui.show(plugin, worth, player, holder.getQuery(), holder.getPage() + 1);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof WorthHolder) {
            event.setCancelled(true);
        }
    }
}
