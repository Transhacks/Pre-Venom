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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveObsidianCommand implements CommandExecutor {
    public GiveObsidianCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("giveobsidian")) {
                ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 64);
                player.getInventory().addItem(new ItemStack[]{obsidian});
                player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "64 " + ChatColor.GOLD + "of " + ChatColor.RED + "obsidian");
            }
        }

        return true;
    }
}
