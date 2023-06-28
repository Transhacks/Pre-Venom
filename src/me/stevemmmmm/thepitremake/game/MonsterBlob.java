//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.Iterator;
import me.stevemmmmm.thepitremake.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MonsterBlob implements Listener {
    public MonsterBlob() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
            World world = Bukkit.getServer().getWorld("world");
            Iterator var1 = world.getEntities().iterator();

            while(var1.hasNext()) {
                Entity entity = (Entity)var1.next();
                if (entity instanceof Slime) {
                    entity.remove();
                }
            }

            for(int i = 0; i < 10; ++i) {
                Slime slime = (Slime)world.spawnEntity(new Location(world, 0.0, 46.0, 0.0), EntityType.SLIME);
                slime.setSize(2);
                slime.setHealth(1.0);
                slime.setCustomName(ChatColor.GREEN + "Bhopping Pit Blob");
                slime.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 50), true);
                slime.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 200), true);
            }

        }, 0L, 1200L);
    }

    @EventHandler
    public void onSlimeHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Slime) {
            event.setDamage(30.0);
        }

    }

    @EventHandler
    public void onSlimeSpawn(SlimeSplitEvent event) {
        event.setCancelled(true);
    }
}
