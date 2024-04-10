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

public class GiveArchCommand implements CommandExecutor {
    public GiveArchCommand() {
    }
	 @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (label.equalsIgnoreCase("givearch")) {
            	ItemStack arch = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                 ItemMeta meta = arch.getItemMeta();

                 meta.setDisplayName(ChatColor.AQUA + "Archangel Chestplate");
                 meta.setLore(new LoreBuilder()
                     .write(ChatColor.GRAY + "Lives: " + ChatColor.GREEN + "100" + ChatColor.GRAY + "/100").next()
                     .write("").next()
                     .write(ChatColor.BLUE + "Guardian ").next()
                     .write(ChatColor.GRAY + "Recieve " + ChatColor.BLUE + "-10% " + ChatColor.GRAY + "damage").next()
                     .write(ChatColor.GRAY + "").next()
                     .write(ChatColor.AQUA + "Angel Faction Reward")
                     .build());
                 meta.spigot().setUnbreakable(true);

                arch.setItemMeta(meta);
                player.getInventory().addItem(arch);
                }
            }
      return true;
      }
	 

}