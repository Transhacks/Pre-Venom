package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveBreadCommand implements CommandExecutor {
    public GiveBreadCommand() {
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("givebread")) {
            ItemStack bread = new ItemStack(Material.BREAD, 64);
            ItemMeta meta = bread.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Yummy Bread");
            meta.setLore(new LoreBuilder()
                    .write("Heals ")
                    .write(ChatColor.RED, "4❤")
                    .next()
                    .write("Grants ")
                    .write(ChatColor.GOLD, "2❤")
                    .build());
            bread.setItemMeta(meta);
            player.getInventory().addItem(bread);
            player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "64 " + ChatColor.GOLD + "of " + ChatColor.RED + "bread");
        }
        return true;
    }
}
