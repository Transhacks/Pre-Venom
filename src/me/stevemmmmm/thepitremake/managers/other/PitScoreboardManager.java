package me.stevemmmmm.thepitremake.managers.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import me.confuser.killstreaks.storage.PlayerStorage;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.CombatManager;
import me.stevemmmmm.thepitremake.game.killstreaks.Killstreak;
import me.stevemmmmm.thepitremake.utils.RomanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PitScoreboardManager implements Listener {
    private static PitScoreboardManager instance;
    public PlayerStorage playerStorage;
    private final HashMap<UUID, Integer> scoreboardTasks = new HashMap();
    private final HashMap<UUID, Scoreboard> playerToScoreboard = new HashMap();

    public PitScoreboardManager() {
    }

    public static PitScoreboardManager getInstance() {
        if (instance == null) {
            instance = new PitScoreboardManager();
        }

        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!this.scoreboardTasks.containsKey(player.getUniqueId())) {
            this.playerToScoreboard.put(player.getUniqueId(), Bukkit.getScoreboardManager().getNewScoreboard());
            player.setScoreboard((Scoreboard)this.playerToScoreboard.get(player.getUniqueId()));
            this.scoreboardTasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                int currentKills = Killstreak.getInstance().killCounts.getOrDefault(player.getUniqueId(), 0);
                this.updateScoreboard(player, currentKills);
            }, 20L, 20L));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (this.scoreboardTasks.containsKey(event.getPlayer().getUniqueId())) {
            Bukkit.getServer().getScheduler().cancelTask((Integer)this.scoreboardTasks.get(event.getPlayer().getUniqueId()));
            this.scoreboardTasks.remove(event.getPlayer().getUniqueId());
        }

    }

    public void createScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = this.getScoreboardObjective(board, player);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }
    public void updateScoreboard(Player player, int Streak) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "FABIAN'S PIT SANDBOX");
        
        int index = 11;
        boolean hasPrestige = !RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)).equalsIgnoreCase("None");
        boolean isOver = Killstreak.getInstance().isOverdrive.getOrDefault(player, false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();

        objective.getScore(ChatColor.GRAY + dateFormat.format(date) + ChatColor.DARK_GRAY + " mega17A").setScore(index--);
        objective.getScore(" ").setScore(index--);

        if (hasPrestige) {
            objective.getScore(ChatColor.WHITE + "Prestige: " + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player))).setScore(index--);
        }

        objective.getScore(ChatColor.WHITE + "Level: " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player)).setScore(index--);
        objective.getScore(GrindingSystem.getInstance().getPlayerLevel(player) >= 120 ? ChatColor.WHITE + "XP: " + ChatColor.AQUA + "MAXED!" : ChatColor.WHITE + "Needed XP: " + ChatColor.AQUA + GrindingSystem.getInstance().getPlayerNeededXP(player)).setScore(index--);
        objective.getScore("  ").setScore(index--);
        objective.getScore(ChatColor.WHITE + "Gold: " + ChatColor.GOLD + GrindingSystem.getInstance().getFormattedPlayerGold(player)).setScore(index--);
        objective.getScore("   ").setScore(index--);

        String text;
        if (isOver) {
            text = ChatColor.RED + "Overdrive";
        } else if (CombatManager.getInstance().playerIsInCombat(player)) {
            text = ChatColor.RED + "Fighting " + ChatColor.GRAY + "(" + CombatManager.getInstance().getCombatTime(player) + ")";
        } else {
            text = ChatColor.GREEN + "Idling";
        }
        Score status = objective.getScore(ChatColor.WHITE + "Status: " + text);
        status.setScore(index);
        --index;

        if (Streak > 0) {
            Score streak = objective.getScore(ChatColor.WHITE + "Streak: "  + ChatColor.GREEN + Streak);
            streak.setScore(index);
            index--;
                  	
        }
        objective.getScore("    ").setScore(index--);
        objective.getScore(ChatColor.YELLOW + "traficantes.wtf").setScore(index);

        updateNametag(player, board);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    public Objective getScoreboardObjective(Scoreboard board, Player player) {

        Objective existingObjective = board.getObjective("test");
        if (existingObjective != null) {
            existingObjective.unregister();
        }

        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "FABIAN'S PIT SANDBOX");
        
        int index = 11;
        boolean hasPrestige = !RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)).equalsIgnoreCase("None");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();

        objective.getScore(ChatColor.GRAY + dateFormat.format(date) + ChatColor.DARK_GRAY + " mega17A").setScore(index--);
        objective.getScore(" ").setScore(index--);

        if (hasPrestige) {
            objective.getScore(ChatColor.WHITE + "Prestige: " + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player))).setScore(index--);
        }

        objective.getScore(ChatColor.WHITE + "Level: " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player)).setScore(index--);
        objective.getScore(GrindingSystem.getInstance().getPlayerLevel(player) >= 120 ? ChatColor.WHITE + "XP: " + ChatColor.AQUA + "MAXED!" : ChatColor.WHITE + "Needed XP: " + ChatColor.AQUA + GrindingSystem.getInstance().getPlayerNeededXP(player)).setScore(index--);
        objective.getScore("  ").setScore(index--);
        objective.getScore(ChatColor.WHITE + "Gold: " + ChatColor.GOLD + GrindingSystem.getInstance().getFormattedPlayerGold(player)).setScore(index--);
        objective.getScore("   ").setScore(index--);
        objective.getScore(ChatColor.WHITE + "Status: " + (!CombatManager.getInstance().playerIsInCombat(player) ? ChatColor.GREEN + "Idling" : ChatColor.RED + "Fighting " + ChatColor.GRAY + "(" + CombatManager.getInstance().getCombatTime(player) + ")")).setScore(index--);
        objective.getScore("    ").setScore(index--);
        objective.getScore(ChatColor.YELLOW + "traficantes.wtf").setScore(index);

        return objective;
    }

    private void updateNametag(Player p, Scoreboard board) {
        Iterator var4 = p.getWorld().getPlayers().iterator();

        while(var4.hasNext()) {
            Player player = (Player)var4.next();
            if (board.getTeam(player.getName()) != null) {
                board.getTeam(player.getName()).unregister();
            }

            Team team = board.registerNewTeam(player.getName());
            team.setPrefix(GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player) + " " + PermissionsManager.getInstance().getPlayerRank(player).getNameColor());
            team.addPlayer(player);
        }

    }
}
