//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveProtCommand implements CommandExecutor {
    public GiveProtCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("giveprot")) {
                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
                this.enchantToProt(helmet);
                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                this.enchantToProt(chestplate);
                ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                this.enchantToProt(leggings);
                ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
                this.enchantToProt(boots);
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                swordMeta.spigot().setUnbreakable(true);
                sword.setItemMeta(swordMeta);
                player.getInventory().addItem(new ItemStack[]{helmet, chestplate, leggings, boots, sword});
                player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "diamond helmet");
                player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "diamond chestplate");
                player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "diamond leggings");
                player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "diamond boots");
                player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "diamond sword");
            }
        }

        return true;
    }

    private void enchantToProt(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
    }
}
