//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import me.stevemmmmm.thepitremake.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ClearArrows implements Listener {
    private final HashMap<UUID, ArrayList<Arrow>> playerToArrows = new HashMap();
    private static final int ARROW_MAXIMUM = 7;

    public ClearArrows() {
    }

    @EventHandler
    public void onProjectileShoot(EntityShootBowEvent event) {
        Entity projectile = event.getProjectile();
        if (event.getProjectile() instanceof Arrow && ((Arrow)event.getProjectile()).getShooter() instanceof Player) {
            Player player = (Player)((Arrow)event.getProjectile()).getShooter();
            if (!this.playerToArrows.containsKey(player.getUniqueId())) {
                this.playerToArrows.put(player.getUniqueId(), new ArrayList());
            }

            ArrayList<Arrow> data = (ArrayList)this.playerToArrows.get(player.getUniqueId());
            if (data.size() >= 7) {
                ((Arrow)data.get(0)).remove();
                data.remove(0);
            }

            data.add((Arrow)event.getProjectile());
            this.playerToArrows.put(player.getUniqueId(), data);
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
            if (projectile != null) {
                projectile.remove();
            }

        }, 100L);
    }
}
