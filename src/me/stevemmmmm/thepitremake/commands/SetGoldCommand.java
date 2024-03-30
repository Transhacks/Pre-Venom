//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGoldCommand implements CommandExecutor {
    public SetGoldCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("setgold")) {
                if (args.length > 0) {
                    if (args[0] != null) {
                        if (StringUtils.isNumeric(args[0])) {
                            double gold = Double.parseDouble(args[0]);

                            if (gold > 2147483647.00) gold = 2147483647.00;

                            GrindingSystem.getInstance().setPlayerGold(player, gold);
                            player.sendMessage(ChatColor.GREEN + "Success!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Usage: /setgold <amount>");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Usage: /setgold <amount>");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /setgold <amount>");
                }
            }
        }

        return true;
    }
}
