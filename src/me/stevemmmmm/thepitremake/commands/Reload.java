package me.stevemmmmm.thepitremake.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Reload implements CommandExecutor {
    private final JavaPlugin plugin;

    public Reload(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("reloadcommand")) {
            if (sender.isOp()) {
                sender.sendMessage("Reloading TheBlueHatsPit...");
                reloadPlugin(sender);
                return true;
            } else {
                sender.sendMessage("You must be an op to execute this command.");
                return true;
            }
        }
        return false;
    }

    private void reloadPlugin(CommandSender sender) {
        try {
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            plugin.getServer().getPluginManager().enablePlugin(plugin);
            sender.sendMessage("TheBlueHatsPit reloaded successfully!");
        } catch (Exception e) {
            sender.sendMessage("Failed to reload TheBlueHatsPit: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
