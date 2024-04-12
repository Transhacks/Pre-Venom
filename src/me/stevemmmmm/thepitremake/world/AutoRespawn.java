package me.stevemmmmm.thepitremake.world;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AutoRespawn implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");

        Player player = event.getEntity();
        triggerRespawnSequence(player);
    }

    public static void triggerRespawnSequence(Player player) {
        Bukkit.getScheduler().runTaskLater(Main.INSTANCE, () -> {
            player.spigot().respawn();
            resetPlayerState(player); 
        }, 1L);
    }

    public static void resetPlayerState(Player player) {
        player.setHealth(player.getMaxHealth());
        player.teleport(RegionManager.getInstance().getSpawnLocation(player));
        player.setFoodLevel(19);
        player.setFireTicks(0);
        player.setVelocity(new Vector(0, 0, 0));

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
    }

}
