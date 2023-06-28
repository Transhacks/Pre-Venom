//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCommand implements TabExecutor {
    public EnchantCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("pitenchant")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage:" + ChatColor.WHITE + " /pitenchant <enchant> <level>");
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

                    if (args.length < 2) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You did not specify an enchantment level!");
                        return true;
                    }

                    if (!StringUtils.isNumeric(args[1])) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " The enchantment level you entered is not a number!");
                        return true;
                    }

                    if (CustomEnchantManager.getInstance().itemContainsEnchant(item, customEnchant)) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " This item already contains this enchantment!");
                        return true;
                    }

                    if (!customEnchant.isCompatibleWith(item.getType())) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You can not enchant this enchant on this item!");
                        return true;
                    }

                    if (Integer.parseInt(args[1]) > 3 || Integer.parseInt(args[1]) < 1) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " The enchant level can only be 1, 2, or 3!");
                        return true;
                    }

                    int level = Integer.parseInt(args[1]);
                    if (player.getWorld().getName().equals("ThePit_0")) {
                        int numberOfEnchants = CustomEnchantManager.getInstance().getItemEnchants(item).size();
                        if (numberOfEnchants >= 3) {
                            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You can only put a maximum of 3 enchants in this world!");
                            return true;
                        }

                        int tokens = CustomEnchantManager.getInstance().getTokensOnItem(item) + level;
                        if (tokens > 8) {
                            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You can only have a maximum of 8 tokens in this world!");
                            return true;
                        }

                        int rareTokens = 0;
                        int rareEnchantCount = 0;
                        Iterator var14 = CustomEnchantManager.getInstance().getItemEnchants(item).entrySet().iterator();

                        while(var14.hasNext()) {
                            Map.Entry<CustomEnchant, Integer> entry = (Map.Entry)var14.next();
                            if (((CustomEnchant)entry.getKey()).isRareEnchant()) {
                                rareTokens += (Integer)entry.getValue();
                                ++rareEnchantCount;
                            }
                        }

                        if (customEnchant.isRareEnchant()) {
                            rareTokens += level;
                            ++rareEnchantCount;
                        }

                        if (rareEnchantCount > 2) {
                            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You can only have 2 rare enchants on an item in this world!");
                            return true;
                        }

                        if (rareTokens > 4) {
                            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " You can only have a maximum of 4 tokens for rare enchants in this world!");
                            return true;
                        }
                    }

                    CustomEnchantManager.getInstance().addEnchants(item, level, new CustomEnchant[]{customEnchant});
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "NICE!" + ChatColor.GRAY + " You applied the enchantment successfully!");
                    player.updateInventory();
                }
            }
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player)sender;
        ArrayList arguments;
        if (args.length == 1 && player.getInventory().getItemInHand().getType() == Material.GOLD_SWORD) {
            arguments = new ArrayList();
            arguments.add("Billionaire".toLowerCase());
            arguments.add("Healer".toLowerCase());
            arguments.add("Perun".toLowerCase());
            arguments.add("Combostun".toLowerCase());
            arguments.add("Lifesteal".toLowerCase());
            arguments.add("Diamondstomp".toLowerCase());
            arguments.add("Bullettime".toLowerCase());
            arguments.add("Combodamage".toLowerCase());
            arguments.add("Painfocus".toLowerCase());
            arguments.add("Kingbuster".toLowerCase());
            arguments.add("Punisher".toLowerCase());
            arguments.add("Comboswift".toLowerCase());
            arguments.add("Bruiser".toLowerCase());
            arguments.add("Frostbite".toLowerCase());
            arguments.add("Executioner".toLowerCase());
            arguments.add("Beatthespammers".toLowerCase());
            arguments.add("Comboheal".toLowerCase());
            arguments.add("Sweaty".toLowerCase());
            arguments.add("Knockback".toLowerCase());
            arguments.add("Sharp".toLowerCase());
            arguments.add("Gamble".toLowerCase());
            arguments.add("FancyRaider".toLowerCase());
            arguments.add("Grasshopper".toLowerCase());
            arguments.add("Guts".toLowerCase());
            arguments.add("Crush".toLowerCase());
            arguments.add("Speedyhit".toLowerCase());
            arguments.add("Punch".toLowerCase());
            arguments.add("Counter-janitor".toLowerCase());
            arguments.add("Pitpocket".toLowerCase());
            arguments.add("Duelist".toLowerCase());
            arguments.add("Speedyhit".toLowerCase());
            return arguments;
        } else if (args.length == 1 && player.getInventory().getItemInHand().getType() == Material.BOW) {
            arguments = new ArrayList();
            arguments.add("Robinhood".toLowerCase());
            arguments.add("Telebow".toLowerCase());
            arguments.add("Megalongbow".toLowerCase());
            arguments.add("Volley".toLowerCase());
            arguments.add("LuckyShot".toLowerCase());
            arguments.add("DevilChicks".toLowerCase());
            arguments.add("Explosive".toLowerCase());
            arguments.add("Wasp".toLowerCase());
            arguments.add("Parasite".toLowerCase());
            arguments.add("Pcts".toLowerCase());
            arguments.add("Chipping".toLowerCase());
            arguments.add("Fletching".toLowerCase());
            arguments.add("SprintDrain".toLowerCase());
            arguments.add("Jumpspammer".toLowerCase());
            arguments.add("Pullbow".toLowerCase());
            arguments.add("TrueShot".toLowerCase());
            return arguments;
        } else if (args.length == 1 && player.getInventory().getItemInHand().getType() == Material.LEATHER_LEGGINGS) {
            arguments = new ArrayList();
            arguments.add("Mirror".toLowerCase());
            arguments.add("Solitude".toLowerCase());
            arguments.add("Assassin".toLowerCase());
            arguments.add("Critfunky".toLowerCase());
            arguments.add("LastStand".toLowerCase());
            arguments.add("Peroxide".toLowerCase());
            arguments.add("BooBoo".toLowerCase());
            arguments.add("Frac".toLowerCase());
            arguments.add("RingArmor".toLowerCase());
            arguments.add("Protection".toLowerCase());
            arguments.add("Cricket".toLowerCase());
            arguments.add("Revitalize".toLowerCase());
            arguments.add("Prick".toLowerCase());
            arguments.add("EscapePod".toLowerCase());
            arguments.add("Absorption".toLowerCase());
            arguments.add("Hearts".toLowerCase());
            arguments.add("NotGladiator".toLowerCase());
            arguments.add("Doublejump".toLowerCase());
            return arguments;
        } else if (args.length == 2) {
            arguments = new ArrayList();
            arguments.add("1");
            arguments.add("2");
            arguments.add("3");
            return arguments;
        } else {
            return null;
        }
    }
}
