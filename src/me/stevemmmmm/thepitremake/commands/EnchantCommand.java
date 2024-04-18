package me.stevemmmmm.thepitremake.commands;

import java.util.ArrayList;
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

    private void sendErrorMessage(Player player, String message) {
        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WHOOPS!" + ChatColor.GRAY + " " + message);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("pitenchant")) {
                if (args.length == 0) {
                    sendErrorMessage(player, "Usage: /pitenchant <enchant> <level>");
                } else {
                    CustomEnchant customEnchant = null;
                    for (CustomEnchant enchant : CustomEnchantManager.getInstance().getEnchants()) {
                        if (enchant.getEnchantReferenceName().equalsIgnoreCase(args[0])) {
                            customEnchant = enchant;
                            break;
                        }
                    }

                    if (customEnchant == null) {
                        sendErrorMessage(player, "This enchant does not exist!");
                        return true;
                    }

                    ItemStack item = player.getInventory().getItemInHand();
                    if (item.getType() == Material.AIR) {
                        sendErrorMessage(player, "You are not holding anything!");
                        return true;
                    }
                    
                    if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                        sendErrorMessage(player, "You cannot enchant this item!");
                        return true;
                    }
                    
                    if (item.getType() == Material.GOLD_HELMET) {
                        sendErrorMessage(player, "You cannot enchant this item!");
                        return true;
                    }
                    
                    if (item.getType() == Material.LEATHER_BOOTS) {
                        sendErrorMessage(player, "You cannot enchant this item!");
                        return true;
                    }

                    if (args.length < 2) {
                        sendErrorMessage(player, "You did not specify an enchantment level!");
                        return true;
                    }

                    if (!StringUtils.isNumeric(args[1])) {
                        sendErrorMessage(player, "The enchantment level you entered is not a number!");
                        return true;
                    }

                    int level = Integer.parseInt(args[1]);
                    if (level > 3 || level < 1) {
                        sendErrorMessage(player, "The enchant level can only be 1, 2, or 3!");
                        return true;
                    }

                    if (CustomEnchantManager.getInstance().itemContainsEnchant(item, customEnchant)) {
                        sendErrorMessage(player, "This item already contains this enchantment!");
                        return true;
                    }

                    if (!customEnchant.isCompatibleWith(item.getType())) {
                        sendErrorMessage(player, "You cannot enchant this enchant on this item!");
                        return true;
                    }

                    if (player.getWorld().getName().equals("ThePit_0")) {
                        int numberOfEnchants = CustomEnchantManager.getInstance().getItemEnchants(item).size();
                        if (numberOfEnchants >= 3) {
                            sendErrorMessage(player, "You can only put a maximum of 3 enchants in this world!");
                            return true;
                        }

                        int tokens = CustomEnchantManager.getInstance().getTokensOnItem(item) + level;
                        if (tokens > 8) {
                            sendErrorMessage(player, "You can only have a maximum of 8 tokens in this world!");
                            return true;
                        }

                        int rareTokens = 0;
                        int rareEnchantCount = 0;
                        for (Map.Entry<CustomEnchant, Integer> entry : CustomEnchantManager.getInstance().getItemEnchants(item).entrySet()) {
                            if (entry.getKey().isRareEnchant()) {
                                rareTokens += entry.getValue();
                                rareEnchantCount++;
                            }
                        }

                        if (customEnchant.isRareEnchant()) {
                            rareTokens += level;
                            rareEnchantCount++;
                        }

                        if (rareEnchantCount > 2) {
                            sendErrorMessage(player, "You can only have 2 rare enchants on an item in this world!");
                            return true;
                        }

                        if (rareTokens > 4) {
                            sendErrorMessage(player, "You can only have a maximum of 4 tokens for rare enchants in this world!");
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
                    "BeatTheSpammers", "Berserker", "Billionaire", "BountyReaper", "Bruiser", "BulletTime", "ComboDamage", "ComboHeal",
                    "ComboStun", "ComboSwift", "CounterJanitor", "Crush", "DiamondStomp", "Duelist", "Executioner",
                    "FancyRaider", "Frostbite", "Gamble", "GoldAndBoosted", "Grasshopper", "Guts", "Healer",
                    "Hemorrhage", "KingBuster", "Knockback", "Lifesteal", "PainFocus", "Perun", "Pitpocket", "Punisher",
                    "Revengeance", "Shark", "Sharp", "SpeedyHit", "Speedyhit", "SpeedyKill", "ThePunch",
                    "CriticallyRich", "GoldBoost", "GoldBump", "Moctezuma", "StrikeGold", "Sweaty", "XPBoost", "XPBump"
            ));
        } else if (player.getInventory().getItemInHand().getType() == Material.BOW) {
            options.addAll(List.of(
                    "Chipping", "DevilChicks", "Explosive", "Fletching", "Jumpspammer", "LuckyShot",
                    "Megalongbow", "Parasite", "Pullbow", "Pcts", "Robinhood", "SprintDrain", "Telebow",
                    "TrueShot", "Volley", "Wasp", 
                    "CriticallyRich", "GoldBoost", "GoldBump", "Moctezuma", "StrikeGold", "Sweaty", "XPBoost", "XPBump"
            ));
        } else if (player.getInventory().getItemInHand().getType() == Material.LEATHER_LEGGINGS) {
            options.addAll(List.of(
                    "Assassin", "Billy", "BooBoo", "Cricket", "CriticallyFunky", "DavidandGoliath", "DiamondAllergy",
                    "DoubleJump", "EscapePod", "FractionalReserve", "GottaGoFast", "Hearts", "LastStand", "Lodbrok",
                    "McSwimmer", "Mirror", "Negotiator", "NotGladiator", "Pebble", "Peroxide", "Prick", "Protection", 
                    "PurpleGold", "RespawnAbsorption", "Revitalize", "RingArmor", "Selfcheckout", "Solitude", "TNT",
                    "CriticallyRich", "GoldBoost", "GoldBump", "Moctezuma", "StrikeGold", "Sweaty", "XPBoost", "XPBump"
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
