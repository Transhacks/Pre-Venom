package me.stevemmmmm.thepitremake.commands;

import org.apache.commons.lang.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;

public class SetLevelCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		if (!label.equalsIgnoreCase("setlevel")) {
			return false;
		}

		if (args.length != 1 || !StringUtils.isNumeric(args[0])) {
			player.sendMessage(ChatColor.RED + "Usage: /setlevel <level>");
			return true;
		}

		int level = Integer.parseInt(args[0]);
		if (level > 120) {
			level = 120;
		}

		if (GrindingSystem.getInstance().getPlayerLevel(player) == level) {
			player.sendMessage(ChatColor.RED + "You are already at this level.");
			return true;
		}

		updatePlayerLevel(player, level);
		return true;
	}

	private void updatePlayerLevel(Player player, int level) {
		int initialLevel = GrindingSystem.getInstance().getPlayerLevel(player);

		GrindingSystem.getInstance().sendLevelUpMessage(player, initialLevel, level);
		scheduleLevelUpdate(player, level);
	}

	private void scheduleLevelUpdate(Player player, int level) {
		Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
			GrindingSystem.getInstance().setPlayerLevel(player, level);
			GrindingSystem.getInstance().setXP(player, 0L);
			GrindingSystem.getInstance().updateDisplayName(player);
			GrindingSystem.getInstance().sendLevelProgress(player);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
			player.sendMessage(ChatColor.GREEN + "Success!");
		}, 1L);
	}
}
