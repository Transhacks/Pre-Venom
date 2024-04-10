package me.stevemmmmm.thepitremake.commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class GiveArmageddonCommand implements CommandExecutor {
    public GiveArmageddonCommand() {
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (label.equalsIgnoreCase("givearmageddon")) {
                ItemStack arma = new ItemStack(Material.LEATHER_BOOTS, 1);
                LeatherArmorMeta meta = (LeatherArmorMeta) arma.getItemMeta();

                Color leatherColor = Color.fromRGB(255, 0, 0); 

                meta.setColor(leatherColor);
                meta.setDisplayName(ChatColor.RED + "Armageddon Boots");
                meta.setLore(new LoreBuilder()
                    .write(ChatColor.GRAY + "Lives: " + ChatColor.GREEN + "66" + ChatColor.GRAY + "/66").next()
                    .write("").next()
                    .write(ChatColor.BLUE + "Evil Within ").next()
                    .write(ChatColor.GRAY + "Your hits ignore Somber").next()
                    .write(ChatColor.GRAY + "").next()
                    .write(ChatColor.RED + "Demon Faction Reward")
                    .build());
                meta.spigot().setUnbreakable(true);

                arma.setItemMeta(meta);
                player.getInventory().addItem(arma);
            }
        }
        return true;
    }
}
