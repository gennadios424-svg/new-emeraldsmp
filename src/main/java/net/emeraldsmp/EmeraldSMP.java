package net.emeraldsmp;

import org.bukkit.plugin.java.JavaPlugin;

public final class EmeraldSMP extends JavaPlugin {
    private WorthService worthService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        worthService = new WorthService(this);
        WorthCommand command = new WorthCommand(this, worthService);
        getCommand("worth").setExecutor(command);
        getCommand("worth").setTabCompleter(command);
        getServer().getPluginManager().registerEvents(new WorthListener(this, worthService), this);
        getLogger().info("EmeraldSMP enabled - /worth loaded with " + worthService.getItems().size() + " survival-obtainable entries.");
    }

    public WorthService getWorthService() {
        return worthService;
    }
}
