package me.stevemmmmm.thepitremake.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveArrowCommand implements CommandExecutor {
    public GiveArrowCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("givearrows")) {
            ItemStack arrows = new ItemStack(Material.ARROW, 64);
            player.getInventory().addItem(arrows);
            player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "64 " + ChatColor.GOLD + "of " + ChatColor.RED + "arrow");
        }
        return true;
    }
}
