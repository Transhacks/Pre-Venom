package me.stevemmmmm.thepitremake.game.killstreaks;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.permissions.core.Rank;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import me.stevemmmmm.thepitremake.managers.other.PitScoreboardManager;

public class Killstreak implements Listener {
    private static Killstreak instance;
    public Map<UUID, Integer> killCounts;
    public Map<Player, Boolean> isOverdrive;

    public Killstreak() {
        this.killCounts = new HashMap<>();
        this.isOverdrive = new HashMap<>();
    }

    public static Killstreak getInstance() {
        if (instance == null) {
            instance = new Killstreak();
        }
        return instance;
    }
    
    public void incrementKillCount(Player player, Player victim) {
        UUID playerId = player.getUniqueId();
        int currentKills = killCounts.getOrDefault(playerId, 0);
        killCounts.put(playerId, currentKills + 1);
        PitScoreboardManager.getInstance().updateScoreboard(player, currentKills + 1);
        handleKillMessages(player, victim, currentKills);
        playKillSound(player, currentKills);
        checkStreak(player);
    }

    private void handleKillMessages(Player player, Player victim, int currentKills) {	
		String killMessage;
		DecimalFormat df = new DecimalFormat("##0.00");

		GrindingSystem grindingSystem = GrindingSystem.getInstance();

		Rank playerRank = PermissionsManager.getInstance().getPlayerRank(victim);
		String displayName = grindingSystem.getFormattedPlayerLevelWithoutPrestige(victim) + playerRank.getNameColor()
				+ " " + victim.getName();

		int randomXP = grindingSystem.giveRandomXP(player);
		double randomGold = grindingSystem.giveRandomGold(player);

		String xpMessage = ChatColor.AQUA + " +" + randomXP + "XP";
		String goldMessage = ChatColor.GOLD + " +" + df.format(randomGold) + "g";

        switch (currentKills) {
            case 1:
                killMessage = ChatColor.translateAlternateColorCodes('&',
                        "&a&lDOUBLE KILL! &r&7on &c" + displayName + xpMessage + goldMessage);
                break;
            case 2:
                killMessage = ChatColor.translateAlternateColorCodes('&',
                        "&a&lTRIPLE KILL! &r&7on &c" + displayName + xpMessage + goldMessage);
                break;
            case 3:
                killMessage = ChatColor.translateAlternateColorCodes('&',
                        "&a&lQUADRA KILL! &r&7on &c" + displayName + xpMessage + goldMessage);
                break;
            case 4:
                killMessage = ChatColor.translateAlternateColorCodes('&',
                        "&a&lPENTA KILL! &r&7on &c" + displayName + xpMessage + goldMessage);
                break;
            default:
                if (currentKills > 4) {
                    killMessage = ChatColor.translateAlternateColorCodes('&',
                            "&a&lMULTI KILL! &r&7on &c" + displayName + xpMessage + goldMessage);
                } else {
                    killMessage = ChatColor.translateAlternateColorCodes('&',
                            "&a&lKILL! &r&7on &c" + displayName + xpMessage + goldMessage);
                }
                break;
        }

        player.sendMessage(killMessage);
    }

	private void playKillSound(Player player, int currentKills) {

		float[] pitches;

		switch (currentKills) {
		case 1:
			pitches = new float[] { 1.7936507f };
			break;
		case 2:
			pitches = new float[] { 1.7936507f, 1.8253968f };
			break;
		case 3:
			pitches = new float[] { 1.7936507f, 1.8253968f, 1.8730159f };
			break;
		case 4:
			pitches = new float[] { 1.7936507f, 1.8253968f, 1.8730159f, 1.9047619f };
			break;
		default:
			if (currentKills > 4) {
				pitches = new float[] { 1.7936507f, 1.8253968f, 1.8730159f, 1.9047619f, 1.95232009f };
			} else {
				pitches = new float[] { 1.7936507f };
			}
			break;
		}

		int delay = 0;

		for (float pitch : pitches) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
				player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, pitch);
			}, delay);

			delay += 2;
		}
	}

	public void checkStreak(Player player) {
		UUID playerId = player.getUniqueId();
		int currentKills = killCounts.getOrDefault(playerId, 0);

		Rank playerRank = PermissionsManager.getInstance().getPlayerRank(player);
		String displayName = GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player)
				+ playerRank.getNameColor() + " " + player.getName();

		if (currentKills > 0) {
			if (currentKills <= 10) {
				if (currentKills % 5 == 0) {
					String message = ChatColor.translateAlternateColorCodes('&',
							"&c&lSTREAK! &r&7of &c" + currentKills + "&r&7 kills by " + displayName);
					Bukkit.broadcastMessage(message);
				}
			} else {
				if (currentKills % 10 == 0) {
					String message = ChatColor.translateAlternateColorCodes('&',
							"&c&lSTREAK! &r&7of &c" + currentKills + "&r&7 kills by " + displayName);
					Bukkit.broadcastMessage(message);
				}
			}
		}
	}

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerId = player.getUniqueId();
        removeKillCount(playerId);
        
        if (player.getKiller() instanceof Player) {
            Player killer = player.getKiller();
            incrementKillCount(killer, player);
        }
    }
    
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID playerId = player.getUniqueId();
		removeKillCount(playerId);
	}
    
	public void removeKillCount(UUID playerId) {
		if (killCounts.containsKey(playerId)) {
			killCounts.remove(playerId);
		}
	}

	public Map<UUID, Integer> currentStreak() {
		return this.killCounts;
	}
}