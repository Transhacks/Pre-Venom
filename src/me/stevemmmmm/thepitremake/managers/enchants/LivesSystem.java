package me.stevemmmmm.thepitremake.managers.enchants;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class LivesSystem implements Listener {	
	
	public static void destroyItem(ItemStack item, Player player, PlayerInventory playerInventory, int slot, boolean armor) {
		if (CustomEnchantManager.getInstance().getItemLives(item) <= 0) {
			ItemMeta itemMeta = item.getItemMeta();
			if (itemMeta != null && itemMeta.getDisplayName() != null) {
				String displayName = itemMeta.getDisplayName();
				
				if (armor) {
					playerInventory.setLeggings(null);
				} else {
					playerInventory.setItem(slot, null);
				}
				
				player.sendMessage(ChatColor.RED + "Your mystic item " + ChatColor.RESET + ChatColor.RED + "\"" + ChatColor.RESET + displayName + ChatColor.RESET + "\"" + ChatColor.RED + " has been destroyed because there were no more lives left!");
			}
		}
	}
	
	public static void removeItemLives(Player player) {
		PlayerInventory playerInventory = player.getInventory();
		ItemStack[] armorContents = player.getInventory().getArmorContents();
		
		for (int i = 0; i < playerInventory.getSize(); i++) {
		    ItemStack item = playerInventory.getContents()[i];
		    
		    if (item != null) {
		    	ItemMeta itemMeta = item.getItemMeta();
		    	if (itemMeta != null && itemMeta.getDisplayName() != null) {
		    		ArrayList<String> displayName = new ArrayList<>(Arrays.asList(ChatColor.stripColor(itemMeta.getDisplayName()).split(" ")));
		    		if (displayName.contains("Tier") || displayName.contains("Golden") || displayName.contains("Archangel")) {
	                	CustomEnchantManager.getInstance().setItemLives(item, CustomEnchantManager.getInstance().getItemLives(item) - 1);
	                	
	                	destroyItem(item, player, playerInventory, i, false);
	                }	
		    	}
		    }
		}
		
		for (int i = 0; i < armorContents.length; i++) {
			ItemStack armorPiece = armorContents[i];
			
			if (armorPiece != null) {
		    	ItemMeta armorMeta = armorPiece.getItemMeta();
		    	if (armorMeta != null && armorMeta.getDisplayName() != null) {
		    		ArrayList<String> displayName = new ArrayList<>(Arrays.asList(ChatColor.stripColor(armorMeta.getDisplayName()).split(" ")));
		    		if (displayName.contains("Tier") || displayName.contains("Golden") || displayName.contains("Armageddon") || displayName.contains("Archangel")) {
	                	CustomEnchantManager.getInstance().setItemLives(armorPiece, CustomEnchantManager.getInstance().getItemLives(armorPiece) - 1);
	                	
	                	destroyItem(armorPiece, player, playerInventory, i, true);
	                }	
		    	}
		    }	
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		PlayerInventory playerInventory = player.getInventory();
		
		for (int i = 0; i < 9; i++) { 
			ItemStack item = playerInventory.getItem(i);
			if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Funky Feather")) {
				if (item.getAmount() > 1) {
					item.setAmount(item.getAmount() - 1);
					playerInventory.setItem(i, item);
				} else {
					playerInventory.setItem(i, null);
				}
				
				player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 2, 2F);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lFUNKY FEATHER!&7 Inventory protected!"));
				
				return; 
			}
		}
		
		removeItemLives(player);
	}
}
