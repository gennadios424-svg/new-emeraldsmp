package net.emeraldsmp;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class WorthHolder implements InventoryHolder {
    private final String query;
    private final int page;

    public WorthHolder(String query, int page) {
        this.query = query;
        this.page = page;
    }

    public String getQuery() { return query; }
    public int getPage() { return page; }

    @Override
    public Inventory getInventory() { return null; }
}
