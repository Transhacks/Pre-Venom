//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import me.confuser.killstreaks.KillStreaks;
import me.confuser.killstreaks.storage.KillStreakPlayer;
import me.confuser.killstreaks.storage.PlayerStorage;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.CombatManager;
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
                this.updateScoreboard(player);
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

    private void createScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = this.getScoreboardObjective(board, player);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    private void updateScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "FABIAN'S PIT SANDBOX");
        int index = 11;
        if (RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)).equalsIgnoreCase("None")) {
            --index;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();
        Score dataAndInstance = objective.getScore(ChatColor.GRAY + simpleDateFormat.format(date) + ChatColor.DARK_GRAY + " mega17A");
        dataAndInstance.setScore(index);
        --index;
        Score space1 = objective.getScore(" ");
        space1.setScore(index);
        --index;
        Score level;
        if (!RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)).equalsIgnoreCase("None")) {
            level = objective.getScore(ChatColor.WHITE + "Prestige: " + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)));
            level.setScore(index);
            --index;
        }

        level = objective.getScore(ChatColor.WHITE + "Level: " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player));
        level.setScore(index);
        --index;
        Score xp = objective.getScore(GrindingSystem.getInstance().getPlayerLevel(player) >= 120 ? ChatColor.WHITE + "XP: " + ChatColor.AQUA + "MAXED!" : ChatColor.WHITE + "Needed XP: " + ChatColor.AQUA + GrindingSystem.getInstance().getPlayerNeededXP(player));
        xp.setScore(index);
        --index;
        Score space2 = objective.getScore("  ");
        space2.setScore(index);
        --index;
        Score gold = objective.getScore(ChatColor.WHITE + "Gold: " + ChatColor.GOLD + GrindingSystem.getInstance().getFormattedPlayerGold(player));
        gold.setScore(index);
        --index;
        Score space3 = objective.getScore("   ");
        space3.setScore(index);
        --index;
        Score status = objective.getScore(ChatColor.WHITE + "Status: " + (!CombatManager.getInstance().playerIsInCombat(player) ? ChatColor.GREEN + "Idling" : ChatColor.RED + "Fighting " + ChatColor.GRAY + "(" + CombatManager.getInstance().getCombatTime(player) + ")"));
        status.setScore(index);
        --index;
        Score space4 = objective.getScore("    ");
        space4.setScore(index);
        --index;
        Score serverinfo = objective.getScore(ChatColor.YELLOW + "traficantes.wtf");
        serverinfo.setScore(index);
        this.updateNametag(player, board);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    public Objective getScoreboardObjective(Scoreboard board, Player player) {
        KillStreakPlayer streaker = ((KillStreaks)KillStreaks.getBukkitPlugin()).getPlayerStorage().get(player);
        if (board.getObjective("test") != null) {
            board.getObjective("test").unregister();
        }

        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "FABIAN'S PIT SANDBOX");
        int index = 11;
        if (RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)).equalsIgnoreCase("None")) {
            --index;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();
        Score dataAndInstance = objective.getScore(ChatColor.GRAY + simpleDateFormat.format(date) + ChatColor.DARK_GRAY + " mega17A");
        dataAndInstance.setScore(index);
        --index;
        Score space1 = objective.getScore(" ");
        space1.setScore(index);
        --index;
        Score level;
        if (!RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)).equalsIgnoreCase("None")) {
            level = objective.getScore(ChatColor.WHITE + "Prestige: " + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(GrindingSystem.getInstance().getPlayerPrestige(player)));
            level.setScore(index);
            --index;
        }

        level = objective.getScore(ChatColor.WHITE + "Level: " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player));
        level.setScore(index);
        --index;
        Score xp = objective.getScore(GrindingSystem.getInstance().getPlayerLevel(player) >= 120 ? ChatColor.WHITE + "XP: " + ChatColor.AQUA + "MAXED!" : ChatColor.WHITE + "Needed XP: " + ChatColor.AQUA + GrindingSystem.getInstance().getPlayerNeededXP(player));
        xp.setScore(index);
        --index;
        Score space2 = objective.getScore("  ");
        space2.setScore(index);
        --index;
        Score gold = objective.getScore(ChatColor.WHITE + "Gold: " + ChatColor.GOLD + GrindingSystem.getInstance().getFormattedPlayerGold(player));
        gold.setScore(index);
        --index;
        Score space3 = objective.getScore("   ");
        space3.setScore(index);
        --index;
        Score status = objective.getScore(ChatColor.WHITE + "Status: " + (!CombatManager.getInstance().playerIsInCombat(player) ? ChatColor.GREEN + "Idling" : ChatColor.RED + "Fighting " + ChatColor.GRAY + "(" + CombatManager.getInstance().getCombatTime(player) + ")"));
        status.setScore(index);
        --index;
        Score space4 = objective.getScore("    ");
        space4.setScore(index);
        --index;
        Score serverinfo = objective.getScore(ChatColor.YELLOW + "traficantes.wtf");
        serverinfo.setScore(index);
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
