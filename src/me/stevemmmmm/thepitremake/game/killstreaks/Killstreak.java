package me.stevemmmmm.thepitremake.game.killstreaks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Killstreak implements Listener {

    private Map<UUID, Integer> killCounts;

    public Killstreak() {
        this.killCounts = new HashMap<>();
    }

    public void incrementKillCount(Player player) {
        UUID playerId = player.getUniqueId();
        int currentKills = killCounts.getOrDefault(playerId, 0);
        killCounts.put(playerId, currentKills + 1);

        checkStreak(player);
    }

    private void checkStreak(Player player) {
        UUID playerId = player.getUniqueId();
        int currentKills = killCounts.getOrDefault(playerId, 0);

        if (currentKills % 5 == 0) {
            String message = ChatColor.translateAlternateColorCodes('&',
                    "&c&lSTREAK! &r&7of &c" + currentKills + "&r&7 kills by " + player.getName());

            Bukkit.broadcastMessage(message);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerId = player.getUniqueId();

        if (killCounts.containsKey(playerId)) {
            killCounts.remove(playerId);
        }
    }
}
