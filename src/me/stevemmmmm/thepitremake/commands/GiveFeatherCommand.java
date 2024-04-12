package me.stevemmmmm.thepitremake.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class GiveFeatherCommand implements CommandExecutor {
    public GiveFeatherCommand() {
    }
	 @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (label.equalsIgnoreCase("givefeather")) {
            	ItemStack arch = new ItemStack(Material.FEATHER, 1);
                 ItemMeta meta = arch.getItemMeta();

                 meta.setDisplayName(ChatColor.DARK_AQUA + "Funky Feather");
                 meta.setLore(new LoreBuilder()
                     .write(ChatColor.YELLOW + "Special item").next()
                     .write(ChatColor.GRAY + "Protects your inventory but").next()
                     .write(ChatColor.GRAY + "gets consumed on death if").next()
                     .write(ChatColor.GRAY + "in your hotbar.")
                     .build());

                arch.setItemMeta(meta);
                player.getInventory().addItem(arch);
                }
            }
      return true;
      }
	 

}