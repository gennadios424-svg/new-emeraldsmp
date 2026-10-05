package net.emeraldsmp;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public final class WorthCommand implements CommandExecutor, TabCompleter {
    private final EmeraldSMP plugin;
    private final WorthService worth;

    public WorthCommand(EmeraldSMP plugin, WorthService worth) {
        this.plugin = plugin;
        this.worth = worth;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open /worth.");
            return true;
        }
        if (!player.hasPermission("emeraldsmp.worth")) {
            player.sendMessage("§cYou do not have permission to use /worth.");
            return true;
        }
        if (args.length > 0) {
            String query = String.join(" ", args).trim();
            plugin.getServer().getScheduler().runTask(plugin, () -> WorthGui.open(plugin, worth, player, query));
        } else {
            WorthGui.open(plugin, worth, player, "");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
