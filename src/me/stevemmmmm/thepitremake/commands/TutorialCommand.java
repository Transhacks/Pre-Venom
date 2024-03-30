package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TutorialCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (!label.equalsIgnoreCase("tutorial")) {
            return false;
        }

        List<CustomEnchant> enchants = CustomEnchantManager.getInstance().getEnchants();
        int pages = (int) Math.ceil((double) enchants.size() / 9);
        int page = 1;

        if (args.length == 1) {
            if (!StringUtils.isNumeric(args[0])) {
                player.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Please specify a correct page number!");
                return true;
            }

            page = Integer.parseInt(args[0]);
        }

        if (page <= 0 || page > pages) {
            player.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Please specify a correct page number!");
            return true;
        }

        player.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "TUTORIAL! (" + page + "/" + pages + ")");

        int start = (page - 1) * 9;
        int end = Math.min(start + 9, enchants.size());

        for (int i = start; i < end; i++) {
            CustomEnchant enchant = enchants.get(i);
            player.sendMessage(ChatColor.GRAY + "■ " + ChatColor.RED + enchant.getName() + ChatColor.GOLD + " ▶ " + ChatColor.YELLOW + enchant.getEnchantReferenceName());
        }

        return true;
    }
}
