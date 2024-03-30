//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private final List<String> levelOptions = List.of("1", "2", "3");
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
        Player player = (Player) sender;
        ArrayList<String> arguments = new ArrayList<>();
        
        List<String> options = new ArrayList<>();
        if (player.getInventory().getItemInHand().getType() == Material.GOLD_SWORD) {
            options.addAll(List.of(
                "Billionaire", "Healer", "Perun", "Combostun", "Lifesteal", "Diamondstomp",
                "Bullettime", "Combodamage", "Painfocus", "Kingbuster", "Punisher", "Comboswift",
                "Bruiser", "Frostbite", "Executioner", "Beatthespammers", "Comboheal", "Sweaty",
                "Sharp", "Gamble", "FancyRaider", "Grasshopper", "Guts", "Crush",
                "Speedyhit", "Punch", "Counter-janitor", "Pitpocket", "Duelist", "Speedyhit"
            ));
        } else if (player.getInventory().getItemInHand().getType() == Material.BOW) {
            options.addAll(List.of(
                "Robinhood", "Telebow", "Megalongbow", "Volley", "LuckyShot", "DevilChicks",
                "Explosive", "Wasp", "Parasite", "Pcts", "Chipping", "Fletching", "SprintDrain",
                "Jumpspammer", "Pullbow", "TrueShot"
            ));
        } 
        else if (player.getInventory().getItemInHand().getType() == Material.LEATHER_LEGGINGS) {
            options.addAll(List.of(
                "Mirror", "Solitude", "Assassin", "Critfunky", "LastStand", "Peroxide",
                "BooBoo", "Frac", "RingArmor", "Protection", "Cricket", "Revitalize", "Prick",
                "EscapePod", "Absorption", "Hearts", "NotGladiator", "Doublejump"
            ));
        }                 
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            arguments.addAll(options.stream()
                    .filter(option -> option.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList()));
        } else if (args.length == 2) {
            if (levelOptions.contains(args[1])) {
                int currentIndex = levelOptions.indexOf(args[1]);
                int nextIndex = (currentIndex + 1) % levelOptions.size();
                return List.of(levelOptions.get(nextIndex));
            } else {
                return levelOptions;
            }
        }

        return arguments.isEmpty() ? options : arguments;
    }
}
