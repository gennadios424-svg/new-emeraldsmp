package net.emeraldsmp;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public final class WorthService {
    private final EmeraldSMP plugin;
    private final Map<Material, Double> prices = new EnumMap<>(Material.class);
    private final List<Material> items = new ArrayList<>();

    private static final Set<Material> NEVER_OBTAINABLE = EnumSet.of(
        Material.AIR, Material.CAVE_AIR, Material.VOID_AIR,
        Material.BEDROCK, Material.BARRIER, Material.LIGHT,
        Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK,
        Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.JIGSAW,
        Material.DEBUG_STICK, Material.KNOWLEDGE_BOOK,
        Material.END_PORTAL, Material.END_GATEWAY, Material.END_PORTAL_FRAME,
        Material.FIRE, Material.SOUL_FIRE, Material.PETRIFIED_OAK_SLAB,
        Material.BUDDING_AMETHYST, Material.REINFORCED_DEEPSLATE,
        Material.SPAWNER, Material.TRIAL_SPAWNER, Material.VAULT,
        Material.COMMAND_BLOCK_MINECART
    );

    public WorthService(EmeraldSMP plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        for (Material material : Material.values()) {
            if (!isPlayerObtainable(material)) continue;
            prices.put(material, calculateDefault(material));
            items.add(material);
        }

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("prices");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Material material = Material.matchMaterial(key);
                if (material != null && items.contains(material)) {
                    prices.put(material, Math.max(0.01, section.getDouble(key)));
                }
            }
        }

        items.sort(Comparator.comparing(m -> pretty(m.name())));
    }

    public boolean isPlayerObtainable(Material m) {
        if (!m.isItem() || NEVER_OBTAINABLE.contains(m)) return false;
        String n = m.name();
        if (n.endsWith("_SPAWN_EGG")) return false;
        if (n.startsWith("INFESTED_")) return false;
        if (n.contains("COMMAND_BLOCK") || n.contains("STRUCTURE")
            || n.equals("JIGSAW") || n.equals("BARRIER") || n.equals("LIGHT")
            || n.equals("DEBUG_STICK") || n.equals("KNOWLEDGE_BOOK")
            || n.equals("BEDROCK")) return false;
        return true;
    }

    public double getPrice(Material material) {
        return prices.getOrDefault(material, calculateDefault(material));
    }

    public List<Material> getItems() {
        return Collections.unmodifiableList(items);
    }

    private double calculateDefault(Material m) {
        String n = m.name();
        double value = 25.0;
        if (n.contains("WOOD") || n.contains("LOG") || n.contains("PLANKS")
            || n.contains("LEAVES") || n.contains("SAPLING") || n.contains("CARPET")
            || n.contains("WOOL")) value = 40;
        if (n.contains("COPPER")) value = 75;
        if (n.contains("IRON")) value = 100;
        if (n.contains("GOLD")) value = 150;
        if (n.contains("REDSTONE") || n.contains("QUARTZ")) value = 175;
        if (n.contains("DIAMOND")) value = 300;
        if (n.contains("EMERALD")) value = 350;
        if (n.contains("NETHERITE")) value = 2500;
        if (n.contains("ANCIENT_DEBRIS")) value = 10000;
        if (n.contains("SHULKER")) value = 200;
        if (n.contains("ELYTRA")) value = 50000;
        if (n.contains("DRAGON")) value = 500000;
        if (Set.of("WITHER_SKELETON_SKULL","NETHER_STAR","TOTEM_OF_UNDYING",
                   "HEART_OF_THE_SEA","NAUTILUS_SHELL","TRIDENT",
                   "ENCHANTED_GOLDEN_APPLE","ECHO_SHARD").contains(n)) value = Math.max(value, 15000);
        if (n.equals("CHEST")) value = 50;
        if (n.equals("TRAPPED_CHEST")) value = 250;
        if (n.endsWith("_CHEST_BOAT")) value = 250;
        if (n.equals("CHEST_MINECART")) value = 500;
        if (n.equals("BARREL")) value = 75;
        if (n.equals("ENDER_CHEST")) value = 1000;
        return value;
    }

    public static String pretty(String raw) {
        String[] parts = raw.toLowerCase(Locale.ROOT).split("_");
        StringBuilder out = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            if (!out.isEmpty()) out.append(' ');
            out.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
        }
        return out.toString();
    }

    public static String money(double amount) {
        if (amount >= 1_000_000_000) return trim(amount / 1_000_000_000) + "B";
        if (amount >= 1_000_000) return trim(amount / 1_000_000) + "M";
        if (amount >= 1_000) return trim(amount / 1_000) + "K";
        if (amount == Math.rint(amount)) return String.format(Locale.US, "%.0f", amount);
        return String.format(Locale.US, "%.2f", amount);
    }

    private static String trim(double d) {
        if (d == Math.rint(d)) return String.format(Locale.US, "%.0f", d);
        if (d * 10 == Math.rint(d * 10)) return String.format(Locale.US, "%.1f", d);
        return String.format(Locale.US, "%.2f", d);
    }
}
