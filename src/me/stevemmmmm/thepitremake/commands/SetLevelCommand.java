package me.stevemmmmm.thepitremake.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class SetLevelCommand implements CommandExecutor {

	private void updateDisplayName(Player player) {
		((CraftPlayer) player).getHandle().listName = CraftChatMessage
				.fromString(GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player)
						+ PermissionsManager.getInstance().getPlayerRank(player).getNameColor() + " "
						+ player.getName())[0];
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(
				PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) player).getHandle()));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (label.equalsIgnoreCase("setlevel")) {
			if (args.length > 0) {
				if (args[0] != null) {
					if (StringUtils.isNumeric(args[0])) {
						int level = Integer.parseInt(args[0]);
						level = Math.min(level, 120);
						

						if (!(GrindingSystem.getInstance().getPlayerLevel(player) == level)) {

							IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer
									.a("{\"text\": \"" + ChatColor.AQUA.toString() + ChatColor.BOLD + "LEVEL UP!"
											+ "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");

							PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
									chatTitle);
							PacketPlayOutTitle length = new PacketPlayOutTitle(20, 20, 20);

							IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer
									.a("{\"text\": \"" + ChatColor.YELLOW
											+ GrindingSystem.getInstance()
													.getFormattedPlayerLevelWithoutPrestige(player)
											+ ChatColor.GRAY + " ➟ "
											+ GrindingSystem.getInstance().getPlayerPrestigeColor(player) + "["
											+ GrindingSystem.getInstance().getLevelColorFromInt(level) + level
											+ GrindingSystem.getInstance().getPlayerPrestigeColor(player) + "]"
											+ "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");

							PacketPlayOutTitle subTitle = new PacketPlayOutTitle(
									PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
							PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(20, 20, 20);

							((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);

							((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitleLength);

							player.sendMessage(
									ChatColor.AQUA.toString() + ChatColor.BOLD + "LEVEL UP! " + ChatColor.YELLOW
											+ GrindingSystem.getInstance()
													.getFormattedPlayerLevelWithoutPrestige(player)
											+ ChatColor.GRAY + " ➟ "
											+ GrindingSystem.getInstance().getPlayerPrestigeColor(player) + "["
											+ GrindingSystem.getInstance().getLevelColorFromInt(level) + level
											+ GrindingSystem.getInstance().getPlayerPrestigeColor(player) + "]");
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

							Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
									new Runnable() {
										public void run() {
											int level = Integer.parseInt(args[0]);
											if (level > 120)
												level = 120;
											((CraftPlayer) player).getHandle().listName = CraftChatMessage
													.fromString(GrindingSystem.getInstance()
															.getFormattedPlayerLevelWithoutPrestige(player)
															+ PermissionsManager.getInstance().getPlayerRank(player)
																	.getNameColor()
															+ " " + player.getName())[0];
											((CraftPlayer) player).getHandle().playerConnection
													.sendPacket(new PacketPlayOutPlayerInfo(
															PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME,
															((CraftPlayer) player).getHandle()));

											((CraftPlayer) player).getHandle().listName = CraftChatMessage
													.fromString(GrindingSystem.getInstance()
															.getFormattedPlayerLevelWithoutPrestige(player)
															+ PermissionsManager.getInstance().getPlayerRank(player)
																	.getNameColor()
															+ " " + player.getName())[0];
											((CraftPlayer) player).getHandle().playerConnection
													.sendPacket(new PacketPlayOutPlayerInfo(
															PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME,
															((CraftPlayer) player).getHandle()));

											GrindingSystem.getInstance().setPlayerLevel(player, level);
											player.sendMessage(ChatColor.GREEN + "Success!");
											player.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD
													+ "You may need to relog to see the effects.");
											updateDisplayName(player);
											Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(
													Main.getInstance(), () -> updateDisplayName(player), 1);

										}
									}, 1L);

						}

					} else {
						player.sendMessage(ChatColor.RED + "Usage: /setlevel <level>");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Usage: /setlevel <level>");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Usage: /setlevel <level>");
			}
		}

		return true;
	}

}
