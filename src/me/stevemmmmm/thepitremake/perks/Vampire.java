//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.perks;

import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.other.Perk;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Vampire extends Perk {
    public Vampire() {
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player && ((Arrow)event.getDamager()).getShooter() instanceof Player) {
            Arrow arrow = (Arrow)event.getDamager();
            Player player = (Player)((Arrow)event.getDamager()).getShooter();
            if (!DamageManager.getInstance().playerIsInCanceledEvent((Player)event.getEntity())) {
                player.setHealth(Math.min(arrow.isCritical() ? player.getHealth() + 3.0 : player.getHealth() + 1.0, player.getMaxHealth()));
            }
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player)event.getDamager();
            if (!DamageManager.getInstance().playerIsInCanceledEvent((Player)event.getEntity())) {
                player.setHealth(Math.min(player.getHealth() + 1.0, player.getMaxHealth()));
            }
        }

    }
}
