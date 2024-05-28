package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPrestigeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (!label.equalsIgnoreCase("setprestige")) {
            return false;
        }

        if (args.length == 0 || !StringUtils.isNumeric(args[0])) {
            player.sendMessage(ChatColor.RED + "Usage: /setprestige <prestige>");
            return true;
        }

        int prestige = Integer.parseInt(args[0]);
        prestige = Math.min(prestige, 70);

        if (prestige <= 1) {
            player.sendMessage(ChatColor.RED + "Only prestiges >1 are allowed.");
            return true;
        }
        
        GrindingSystem.getInstance().setXP(player, 0L);
        GrindingSystem.getInstance().setPlayerPrestige(player, prestige);
        GrindingSystem.getInstance().setPlayerLevel(player, 1);
        GrindingSystem.getInstance().writeToConfig();
        GrindingSystem.getInstance().sendPrestigeMessage(player, prestige);
		GrindingSystem.getInstance().updateDisplayName(player);
		GrindingSystem.getInstance().sendLevelProgress(player);
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
        player.sendMessage(ChatColor.GREEN + "Success!");

        return true;
    }
}