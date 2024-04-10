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

public class GiveGoldenCommand implements CommandExecutor {
    public GiveGoldenCommand() {
    }
	 @Override
	    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	        if (sender instanceof Player) {
	            Player player = (Player) sender;

	            if (label.equalsIgnoreCase("givegolden")) {
	            	ItemStack golden = new ItemStack(Material.GOLD_HELMET, 1);
	                 ItemMeta meta = golden.getItemMeta();

	                meta.setDisplayName(ChatColor.GOLD + "Golden Helmet");
	                meta.setLore(new LoreBuilder()
	                    .write(ChatColor.GRAY + "Lives: " + ChatColor.GREEN + "50" + ChatColor.GRAY + "/50").next()
	                    .write("").next()
	                    .write(ChatColor.BLUE + "Royalty ").next()
	                    .write(ChatColor.GRAY + "Earn " + ChatColor.AQUA + "+10% XP" + ChatColor.GRAY + " from kills").next()
	                    .write(ChatColor.GRAY + "").next()
	                    .write(ChatColor.GOLD + "As strong as diamond")
	                    .build());
	                meta.spigot().setUnbreakable(true);

	                golden.setItemMeta(meta);

	                player.getInventory().addItem(golden);
	            }
	        }
	      return true;
	 }
}