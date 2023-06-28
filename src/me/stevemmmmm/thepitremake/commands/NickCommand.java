//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.permissions.core.PermissionsManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    public NickCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can get nicknames!");
            return true;
        } else {
            Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("nick")) {
                if (args.length == 0) {
                    p.setCustomName(p.getName());
                    p.sendMessage(ChatColor.GREEN + "Successfully reset your nickname!");
                    return true;
                }

                String nick = args[0];
                if (StringUtils.isAlphanumeric(nick)) {
                    if (StringUtils.length(nick) <= 16) {
                        p.sendMessage(ChatColor.GREEN + "Successfully changed your nickname to " + PermissionsManager.getInstance().getPlayerRank(p).getNameColor() + nick + ChatColor.GREEN + "!");
                        p.setCustomName(nick);
                    } else {
                        p.sendMessage(ChatColor.RED + "Nickname must be less than 16 characters long!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Nickname must be alphanumeric!");
                }
            }

            return true;
        }
    }
}
