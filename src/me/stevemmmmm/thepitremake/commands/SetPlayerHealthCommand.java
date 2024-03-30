package me.stevemmmmm.thepitremake.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPlayerHealthCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("setplayerhealth")) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /setplayerhealth <health>");
            return true;
        }

        if (!StringUtils.isNumeric(args[0])) {
            player.sendMessage(ChatColor.RED + "Usage: /setplayerhealth <health>");
            return true;
        }

        int health = Integer.parseInt(args[0]);
        double maxHealth = player.getMaxHealth();

        if (health > maxHealth) {
            health = (int) maxHealth;
        }

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You need to be opped to use this command!");
            return true;
        }

        player.setHealth(health);
        return true;
    }
}
