//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GiveFreshItemCommand implements TabExecutor {
    public GiveFreshItemCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("givefreshitem")) {
                if (args.length == 1) {
                    String object = args[0];
                    LeatherArmorMeta freshPantsMeta = null;
                    ItemStack item;
                    ItemMeta meta;
                    if (object.equalsIgnoreCase("Sword")) {
                        item = new ItemStack(Material.GOLD_SWORD, 1);
                        meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.YELLOW + "Mystic Sword");
                        meta.setLore((new LoreBuilder()).write("Kept on death").next().next().write("Used in the mystic well").build());
                        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE});
                        meta.spigot().setUnbreakable(true);
                        item.setItemMeta(meta);
                        player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "mystic sword");
                    } else if (object.equalsIgnoreCase("Bow")) {
                        item = new ItemStack(Material.BOW, 1);
                        meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.AQUA + "Mystic Bow");
                        meta.setLore((new LoreBuilder()).write("Kept on death").next().next().write("Used in the mystic well").build());
                        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE});
                        meta.spigot().setUnbreakable(true);
                        item.setItemMeta(meta);
                        player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "mystic bow");
                    } else {
                        item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                        freshPantsMeta = (LeatherArmorMeta)item.getItemMeta();
                    }

                    if (freshPantsMeta != null) {
                        if (object.equalsIgnoreCase("Red")) {
                            freshPantsMeta.setColor(Color.fromRGB(16733525));
                            this.addPantsLore(freshPantsMeta, "Red", ChatColor.RED);
                        }

                        if (object.equalsIgnoreCase("Green")) {
                            freshPantsMeta.setColor(Color.fromRGB(5635925));
                            this.addPantsLore(freshPantsMeta, "Green", ChatColor.GREEN);
                        }

                        if (object.equalsIgnoreCase("Blue")) {
                            freshPantsMeta.setColor(Color.fromRGB(5592575));
                            this.addPantsLore(freshPantsMeta, "Blue", ChatColor.BLUE);
                        }

                        if (object.equalsIgnoreCase("Yellow")) {
                            freshPantsMeta.setColor(Color.fromRGB(16777045));
                            this.addPantsLore(freshPantsMeta, "Yellow", ChatColor.YELLOW);
                        }

                        if (object.equalsIgnoreCase("Orange")) {
                            freshPantsMeta.setColor(Color.fromRGB(16755200));
                            this.addPantsLore(freshPantsMeta, "Orange", ChatColor.GOLD);
                        }

                        if (object.equalsIgnoreCase("Dark")) {
                            freshPantsMeta.setColor(Color.fromRGB(0));
                            this.addPantsLore(freshPantsMeta, "Dark", ChatColor.DARK_PURPLE);
                        }

                        if (object.equalsIgnoreCase("Sewer")) {
                            freshPantsMeta.setColor(Color.fromRGB(8242051));
                            this.addPantsLore(freshPantsMeta, "Sewer", ChatColor.DARK_AQUA);
                        }

                        if (object.equalsIgnoreCase("Aqua")) {
                            freshPantsMeta.setColor(Color.fromRGB(5636095));
                            this.addPantsLore(freshPantsMeta, "Aqua", ChatColor.AQUA);
                        }

                        if (freshPantsMeta.getLore() == null) {
                            sender.sendMessage(ChatColor.RED.toString() + "Wrong argument!");
                            return true;
                        }

                        item.setItemMeta(freshPantsMeta);
                    }

                    player.getInventory().addItem(new ItemStack[]{item});
                    player.updateInventory();
                    if (object.equalsIgnoreCase("Sword") || object.equalsIgnoreCase("Bow")) {
                        return true;
                    }

                    player.sendMessage(ChatColor.GOLD + "Giving " + ChatColor.RED + "1 " + ChatColor.GOLD + "of " + ChatColor.RED + "fresh " + object.toLowerCase() + " pants");
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /givefreshitem <type>");
                }
            }
        }

        return true;
    }

    private void addPantsLore(LeatherArmorMeta newMeta, String name, ChatColor color) {
        newMeta.setDisplayName(color + "Fresh " + name + " Pants");
        newMeta.spigot().setUnbreakable(true);
        newMeta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.GRAY + "Kept on death");
                this.add(" ");
                this.add(color + "Used in the mystic well");
                this.add(color + "Also, a fashion statement");
            }
        });
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> freshItemTypes = Arrays.asList("Sword", "Bow", "Red", "Orange", "Yellow", "Blue", "Green", "Dark", "Sewer", "Aqua");
            List<String> completions = new ArrayList<>();

            String partial = args[0].toLowerCase();

            for (String freshItemType : freshItemTypes) {
                if (freshItemType.toLowerCase().startsWith(partial)) {
                    completions.add(freshItemType);
                }
            }

            return completions;
        }

        return null;
    }
}
