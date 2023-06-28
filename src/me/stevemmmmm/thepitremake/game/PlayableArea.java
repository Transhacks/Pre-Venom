//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import me.stevemmmmm.thepitremake.world.AutoRespawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayableArea implements Listener {
    private final HashMap<UUID, Integer> tasks = new HashMap();

    public PlayableArea() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.tasks.put(event.getPlayer().getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
            if (!RegionManager.getInstance().playerIsInRegion(event.getPlayer(), RegionType.PLAYABLEAREA)) {
                if (event.getPlayer().getLocation().getY() < 0.0) {
                    Main.INSTANCE.getServer().getPluginManager().callEvent(new PlayerDeathEvent(event.getPlayer(), new ArrayList(), 0, ""));
                } else {
                    AutoRespawn.triggerRespawnSequence(event.getPlayer());
                }

                event.getPlayer().sendMessage(ChatColor.RED + "Congratulations! You went out of the map!");
            }

        }, 0L, 1L));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Bukkit.getServer().getScheduler().cancelTask((Integer)this.tasks.get(event.getPlayer().getUniqueId()));
        this.tasks.remove(event.getPlayer().getUniqueId());
    }
}
