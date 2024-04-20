//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game.duels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameUtility implements Listener {
    public GameUtility() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        player.setMaxHealth(24.0);
        player.setFoodLevel(19);     
        player.performCommand("setgold 10000000");
        
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.performCommand("setgold 10000000");
    }
}
//public class GameUtility implements Listener {
//public GameUtility() {
//}

//@EventHandler
//public void onJoin(PlayerJoinEvent event) {
//    Player player = event.getPlayer();
//    this.giveStuff(player);
//    player.setMaxHealth(20.0);
//    player.setFoodLevel(19);     
//}
//@EventHandler
//public void onHunger(FoodLevelChangeEvent event) {
//    event.setCancelled(true);
//}
//
//private void giveStuff(Player player) {
//    ItemStack[] armor = new ItemStack[4];
//
//    Material[] armorTypes = {Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, null};
//    for (int i = 0; i < armorTypes.length; i++) {
//        if (armorTypes[i] != null) {
//            ItemStack armorPiece = new ItemStack(armorTypes[i]);
//            ItemMeta armorMeta = armorPiece.getItemMeta();
//            if (armorMeta != null) {
//                armorMeta.spigot().setUnbreakable(true);
//                armorPiece.setItemMeta(armorMeta);
//            }
//            armor[i] = armorPiece;
//        } else {
//           armor[i] = null;
//        }
//    }
//
//    player.getInventory().setArmorContents(armor);
//
//    boolean hasIronSword = false;
//    for (ItemStack item : player.getInventory().getContents()) {
//        if (item != null && item.getType() == Material.IRON_SWORD) {
//            hasIronSword = true;
//            break;
//        }
//    }
//
//    if (!hasIronSword) {
//        ItemStack ironSword = new ItemStack(Material.IRON_SWORD, 1);
//        ItemMeta swordMeta = ironSword.getItemMeta();
//        swordMeta.spigot().setUnbreakable(true);
//        ironSword.setItemMeta(swordMeta);
//        player.getInventory().addItem(ironSword);
//    }
//}
//
//@EventHandler
//public void onDeath(PlayerDeathEvent event) {
//    Player player = event.getEntity();
//    this.giveStuff(player);
//}
//}