//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.world;

import java.util.Iterator;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AutoRespawn implements Listener {
    public AutoRespawn() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");
        triggerRespawnSequence(event.getEntity());
    }

    public static void triggerRespawnSequence(Player player) {
        player.setHealth(player.getMaxHealth());
        player.teleport(RegionManager.getInstance().getSpawnLocation(player));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
            Iterator var1 = player.getActivePotionEffects().iterator();

            while(var1.hasNext()) {
                PotionEffect effect = (PotionEffect)var1.next();
                player.removePotionEffect(effect.getType());
            }

            player.setFoodLevel(19);
            player.setHealth(player.getMaxHealth());
            ((CraftPlayer)player).getHandle().setAbsorptionHearts(0.0F);
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
            player.setVelocity(new Vector(0, 0, 0));
            player.setFireTicks(0);
        }, 1L);
    }
}
