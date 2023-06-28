//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPlayerHealthCommand implements CommandExecutor {
    public SetPlayerHealthCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You are not a player!");
            return true;
        } else {
            Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("setplayerhealth")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Usage: /setplayerhealth <health>");
                }

                if (args.length == 1) {
                    if (StringUtils.isNumeric(args[0])) {
                        if (p.isOp()) {
                            int health = Integer.parseInt(args[0]);
                            if ((double)health > p.getMaxHealth()) {
                                health = (int)p.getMaxHealth();
                            }

                            p.setHealth((double)health);
                        } else {
                            p.sendMessage(ChatColor.RED + "Usage: /setplayerhealth <health>");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Usage: /setplayerhealth <health>");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You need to be opped to use this command!");
                }
            }

            return true;
        }
    }
}
