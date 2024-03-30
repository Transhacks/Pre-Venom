//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.HashMap;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class FirstAidEgg implements Listener {
    private final Main MAIN;
    private final ItemStack mooshromegg;
    private final ItemStack skeletonegg;
    private final HashMap<Player, Integer> cooldownMap;

    public FirstAidEgg(Main MAIN) {
        this.mooshromegg = new ItemStack(Material.MONSTER_EGG, 1, (short)96);
        this.skeletonegg = new ItemStack(Material.MONSTER_EGG, 1, (short)0);
        this.cooldownMap = new HashMap();
        ItemMeta skeletonmeta = this.skeletonegg.getItemMeta();
        skeletonmeta.setDisplayName(ChatColor.GRAY + "First-Aid Egg");
        skeletonmeta.setLore((new LoreBuilder()).write(ChatColor.YELLOW + "Special item").next().write(ChatColor.GRAY + "Heals " + ChatColor.RED + "2.5❤").next().write(ChatColor.GRAY + "On Cooldown!").build());
        this.skeletonegg.setItemMeta(skeletonmeta);
        ItemMeta mooshrommeta = this.mooshromegg.getItemMeta();
        mooshrommeta.setDisplayName(ChatColor.RED + "First-Aid Egg");
        mooshrommeta.setLore((new LoreBuilder()).write(ChatColor.YELLOW + "Special item").next().write(ChatColor.GRAY + "Heals " + ChatColor.RED + "2.5❤").next().write(ChatColor.GRAY + "10 seconds cooldown.").build());
        this.mooshromegg.setItemMeta(mooshrommeta);
        this.MAIN = MAIN;
    }

    @EventHandler
    public void onAidEggUse(PlayerInteractEvent event) {
        if (event.getMaterial() == this.mooshromegg.getType() || event.getMaterial() == this.skeletonegg.getType()) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            if (this.cooldownMap.containsKey(player)) {
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0F, 1.0F);
                return;
            }

            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 5.0));
            player.playSound(player.getLocation(), Sound.CAT_HISS, 10.0F, 1.6F);
            this.replaceItems(player, this.mooshromegg, this.skeletonegg);
            this.cooldownMap.put(player, Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.MAIN, () -> {
                this.replaceItems(player, this.skeletonegg, this.mooshromegg);
                this.cooldownMap.remove(player);
            }, 200L));
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.cooldownMap.remove(player);
        this.replaceItems(player, this.skeletonegg, this.mooshromegg);
    }

    private void replaceItems(Player player, ItemStack checkedItem, ItemStack replacedItem) {
        PlayerInventory playerInventory = player.getInventory();

        for(int i = 0; i < player.getInventory().getSize() - 1; ++i) {
            if (playerInventory.getItem(i) != null && playerInventory.getItem(i).getType() == checkedItem.getType()) {
                player.getInventory().setItem(i, replacedItem);
            }
        }

    }
}
