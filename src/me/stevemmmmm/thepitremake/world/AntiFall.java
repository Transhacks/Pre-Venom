//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class AntiFall implements Listener {
    public AntiFall() {
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        event.setCancelled(event.getCause() == DamageCause.FALL);
    }
}
