//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MysticEnchantsCommand implements CommandExecutor {
    public MysticEnchantsCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("mysticenchants")) {
                int pages = CustomEnchantManager.getInstance().getEnchants().size() / 9;
                int page = 1;
                if (args.length == 1) {
                    if (!StringUtils.isNumeric(args[0])) {
                        player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Please specify a correct page number!");
                        return true;
                    }

                    page = Integer.parseInt(args[0]);
                }

                if (page <= 0 || page > pages + 1) {
                    player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Please specify a correct page number!");
                    return true;
                }

                player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Mystic Enchants (" + page + "/" + (pages + 1) + ")");

                for(int i = 0; i < 9; ++i) {
                    int index = i + (page - 1) * 9;
                    if (index > CustomEnchantManager.getInstance().getEnchants().size() - 1) {
                        player.sendMessage(" ");
                    } else {
                        player.sendMessage(ChatColor.GRAY + "■ " + ChatColor.RED + ((CustomEnchant)CustomEnchantManager.getInstance().getEnchants().get(index)).getName() + ChatColor.GOLD + " ▶ " + ChatColor.YELLOW + ((CustomEnchant)CustomEnchantManager.getInstance().getEnchants().get(index)).getEnchantReferenceName());
                    }
                }
            }
        }

        return true;
    }
}
