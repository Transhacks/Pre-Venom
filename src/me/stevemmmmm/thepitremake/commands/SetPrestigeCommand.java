//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import me.stevemmmmm.thepitremake.utils.RomanUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class SetPrestigeCommand implements CommandExecutor {
    public SetPrestigeCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("setprestige")) {
                if (args.length > 0) {
                    if (args[0] != null) {
                        if (StringUtils.isNumeric(args[0])) {
                            int prestige = Integer.parseInt(args[0]);
                            prestige = Math.min(prestige, 70);

                            if (prestige == 0) {
                                player.sendMessage(ChatColor.RED
                                        + "Only prestiges >1 are allowed.");
                                return true;
                            }

							GrindingSystem.getInstance().setPlayerPrestige(player, prestige);
							GrindingSystem.getInstance().setPlayerLevel(player, 1);
							GrindingSystem.getInstance().writeToConfig();
							IChatBaseComponent chatTitle = ChatSerializer
									.a("{\"text\": \"" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "PRESTIGE"
											+ "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
							PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
							PacketPlayOutTitle length = new PacketPlayOutTitle(20, 20, 20);
							IChatBaseComponent chatSubTitle = ChatSerializer
									.a("{\"text\": \"" + ChatColor.GRAY + "You unlocked prestige " + ChatColor.YELLOW
											+ RomanUtils.getInstance().convertToRomanNumeral(prestige) + "\",color:"
											+ ChatColor.GOLD.name().toLowerCase() + "}");
							PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE,
									chatSubTitle);
							PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(20, 20, 20);
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
							((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitleLength);
							((CraftPlayer) player).getHandle().listName = CraftChatMessage.fromString(
									GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player)
											+ PermissionsManager.getInstance().getPlayerRank(player).getNameColor()
											+ " " + player.getName())[0];
							((CraftPlayer) player).getHandle().playerConnection
									.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME,
											new EntityPlayer[] { ((CraftPlayer) player).getHandle() }));
							((CraftPlayer) player).getHandle().listName = CraftChatMessage.fromString(
									GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player)
											+ PermissionsManager.getInstance().getPlayerRank(player).getNameColor()
											+ " " + player.getName())[0];
							((CraftPlayer) player).getHandle().playerConnection
									.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME,
											new EntityPlayer[] { ((CraftPlayer) player).getHandle() }));
							player.sendMessage(ChatColor.GREEN + "Success!");
							player.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD
									+ "You may need to relog to see the effects.");
							player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
						} else {
							player.sendMessage(ChatColor.RED + "Usage: /setprestige <prestige>");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Usage: /setprestige <prestige>");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Usage: /setprestige <prestige>");
				}
			}
		}

		return true;
	}
}
