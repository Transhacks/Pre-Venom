//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import java.util.Iterator;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UnenchantCommand implements CommandExecutor {
    public UnenchantCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("unenchant")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: /unenchant <enchant>");
                } else {
                    CustomEnchant customEnchant = null;
                    Iterator var8 = CustomEnchantManager.getInstance().getEnchants().iterator();

                    while(var8.hasNext()) {
                        CustomEnchant enchant = (CustomEnchant)var8.next();
                        if (enchant.getEnchantReferenceName().equalsIgnoreCase(args[0])) {
                            customEnchant = enchant;
                        }
                    }

                    if (customEnchant == null) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " This enchant does not exist!");
                        return true;
                    }

                    ItemStack item = player.getInventory().getItemInHand();
                    if (item.getType() == Material.AIR) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You are not holding anything!");
                        return true;
                    }

                    if (args.length > 1) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " Too many arguments!");
                        return true;
                    }

                    if (item.getType() != Material.LEATHER_LEGGINGS && item.getType() != Material.GOLD_SWORD && item.getType() != Material.BOW) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You can not enchant this item!");
                        return true;
                    }

                    if (!CustomEnchantManager.getInstance().itemContainsEnchant(item, customEnchant)) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " This item does not have the specified enchant!");
                        return true;
                    }

                    CustomEnchantManager.getInstance().removeEnchant(item, customEnchant);
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "NICE!" + ChatColor.GRAY + " You unenchanted the enchant successfully!");
                    player.updateInventory();
                }
            }
        }

        return true;
    }
}
