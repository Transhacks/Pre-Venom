//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.HashMap;
import java.util.UUID;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CombatManager implements Listener {
    private static CombatManager instance;
    private final HashMap<UUID, Integer> combatTasks = new HashMap();
    private final HashMap<UUID, Integer> combatTime = new HashMap();

    private CombatManager() {
    }

    public static CombatManager getInstance() {
        if (instance == null) {
            instance = new CombatManager();
        }

        return instance;
    }

    @EventHandler
    public void initOnPlayerJoin(PlayerJoinEvent event) {
        this.combatTime.put(event.getPlayer().getUniqueId(), 0);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            this.combatTag((Player)event.getDamager());
            this.combatTag((Player)event.getEntity());
        }

        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player && ((Arrow)event.getDamager()).getShooter() instanceof Player) {
            this.combatTag((Player)((Arrow)event.getDamager()).getShooter());
            this.combatTag((Player)event.getEntity());
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        this.removePlayerFromCombat(event.getEntity());
    }

    public boolean playerIsInCombat(Player player) {
        return (Integer)this.combatTime.getOrDefault(player.getUniqueId(), 0) != 0;
    }

    public void combatTag(Player player) {
        if (!RegionManager.getInstance().playerIsInRegion(player, RegionType.SPAWN)) {
            this.combatTime.put(player.getUniqueId(), this.calculateCombatTime(player));
            if (!this.combatTasks.containsKey(player.getUniqueId())) {
                this.combatTasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                    if ((Integer)this.combatTime.get(player.getUniqueId()) == 0) {
                        Bukkit.getServer().getScheduler().cancelTask((Integer)this.combatTasks.get(player.getUniqueId()));
                        this.combatTasks.remove(player.getUniqueId());
                    } else {
                        this.combatTime.put(player.getUniqueId(), (Integer)this.combatTime.get(player.getUniqueId()) - 1);
                    }
                }, 0L, 20L));
            }

        }
    }

    private void combatTag(Player playerA, Player playerB) {
        this.combatTag(playerA);
        this.combatTag(playerB);
    }

    public int getCombatTime(Player player) {
        return (Integer)this.combatTime.get(player.getUniqueId());
    }

    private int calculateCombatTime(Player player) {
        int time = 15;
        return time;
    }

    private void removePlayerFromCombat(Player player) {
        if (this.playerIsInCombat(player)) {
            Bukkit.getServer().getScheduler().cancelTask((Integer)this.combatTasks.get(player.getUniqueId()));
            this.combatTime.put(player.getUniqueId(), 0);
            this.combatTasks.remove(player.getUniqueId());
        }

    }
}
