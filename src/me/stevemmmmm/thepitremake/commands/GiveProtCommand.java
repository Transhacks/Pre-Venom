package me.stevemmmmm.thepitremake.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveProtCommand implements CommandExecutor {
    public GiveProtCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("giveprot")) {
            ItemStack helmet = createItem(Material.DIAMOND_HELMET, 1);
            ItemStack chestplate = createItem(Material.DIAMOND_CHESTPLATE, 1);
            ItemStack leggings = createItem(Material.DIAMOND_LEGGINGS, 1);
            ItemStack boots = createItem(Material.DIAMOND_BOOTS, 1);
            ItemStack sword = createItem(Material.DIAMOND_SWORD, 1);

            player.getInventory().addItem(helmet, chestplate, leggings, boots, sword);
            player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "a set " + ChatColor.GOLD + "of " + ChatColor.RED + "diamond gear");
        }
        return true;
    }

    private ItemStack createItem(Material material, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }
}
