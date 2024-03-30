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

public class GiveEggCommand implements CommandExecutor {
    public GiveEggCommand() {
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("giveegg")) {
            ItemStack egg = new ItemStack(Material.MONSTER_EGG, 1, (short) 96);
            ItemMeta meta = egg.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "First-Aid Egg");
            meta.setLore(new LoreBuilder()
                    .write(ChatColor.YELLOW + "Special item")
                    .next()
                    .write(ChatColor.GRAY + "Heals " + ChatColor.RED + "2.5❤")
                    .next()
                    .write(ChatColor.GRAY + "10 seconds cooldown.")
                    .build());
            egg.setItemMeta(meta);
            player.getInventory().addItem(egg);
            player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "First-Aid Egg");
        }
        return true;
    }
}
