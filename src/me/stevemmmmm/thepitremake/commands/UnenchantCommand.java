package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnenchantCommand implements CommandExecutor, TabCompleter {
	
    private void sendErrorMessage(Player player, String message) {
        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " " + message);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (!label.equalsIgnoreCase("unenchant")) {
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /unenchant <enchant>");
            return true;
        }

        String enchantName = args[0];
        CustomEnchant customEnchant = getCustomEnchant(enchantName);
        if (customEnchant == null) {
        	sendErrorMessage(player, " This enchant does not exist!");
            return true;
        }

        ItemStack item = player.getInventory().getItemInHand();
        if (item.getType() == Material.AIR) {
        	sendErrorMessage(player, " You are not holding anything!");
            return true;
        }

        if (!isValidItemForEnchant(item.getType())) {
        	sendErrorMessage(player, " You cannot unenchant this item!");
            return true;
        }

        if (!CustomEnchantManager.getInstance().itemContainsEnchant(item, customEnchant)) {
        	sendErrorMessage(player, " This item does not have the specified enchant!");
            return true;
        }

        CustomEnchantManager.getInstance().removeEnchant(item, customEnchant);
        sendErrorMessage(player," You unenchanted the enchant successfully!");
        player.updateInventory();
        return true;
    }

    private CustomEnchant getCustomEnchant(String enchantName) {
        List<CustomEnchant> enchants = CustomEnchantManager.getInstance().getEnchants().stream()
                .filter(enchant -> enchant.getEnchantReferenceName().equalsIgnoreCase(enchantName))
                .collect(Collectors.toList());
        return !enchants.isEmpty() ? enchants.get(0) : null;
    }

    private boolean isValidItemForEnchant(Material itemType) {
        return itemType == Material.LEATHER_LEGGINGS || itemType == Material.GOLD_SWORD || itemType == Material.BOW;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            ItemStack item = player.getInventory().getItemInHand();
            if (item == null || item.getType() == Material.AIR) {
                return null;
            }

            for (CustomEnchant enchant : CustomEnchantManager.getInstance().getEnchants()) {
                if (CustomEnchantManager.getInstance().itemContainsEnchant(item, enchant)) {
                    suggestions.add(enchant.getEnchantReferenceName());
                }
            }

            return suggestions;
        }

        return null;
    }

}