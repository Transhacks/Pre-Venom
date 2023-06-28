package me.stevemmmmm.thepitremake.enchants;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class Pullbow extends CustomEnchant {
	private final EnchantProperty<Double> pullRange = new EnchantProperty<>(0.5D, 3D, 8D);
    private final EnchantProperty<Integer> cooldownTime = new EnchantProperty<>(8, 8, 8);
    private final EnchantProperty<Float> pullIntensity = new EnchantProperty<>(-1.5f, -2.5f, -4f);
    private final EnchantProperty<Float> aoePullIntensity = new EnchantProperty<>(-2.5f, -3.5f, -5f);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
      
        if (damager instanceof Arrow) {
            Arrow arrow = (Arrow) damager;
      
            if (event.getEntity() instanceof Player) {
                if (arrow.getShooter() instanceof Player) {
                    attemptEnchantExecution((((Player) arrow.getShooter()).getInventory().getItemInHand()), event.getEntity(), (Player) arrow.getShooter(),(Arrow)event.getDamager(), event);
                }
            }
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
    	Player damager = (Player) args[1];
    	Player hit = (Player) args[0];
    	Arrow arrow = (Arrow) args[2];
    	 
    	 if (isNotOnCooldown(damager)) {
    		 Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
             {
    		 public void run() {
    			 for (Entity entity : arrow.getNearbyEntities(pullRange.getValueAtLevel(level), pullRange.getValueAtLevel(level), pullRange.getValueAtLevel(level))) {
                     if (entity instanceof Player) {
                         Player player = (Player) entity;

                         if (RegionManager.getInstance().playerIsInRegion(player, RegionManager.RegionType.SPAWN)) continue;

                         if (player != damager) {
                        	 Vector force = arrow.getVelocity().normalize().multiply(pullIntensity.getValueAtLevel(level) / 2.9);
                             force.setY(0.45f);
                             Vector aoeForce = arrow.getVelocity().normalize().multiply(aoePullIntensity.getValueAtLevel(level) / 2.4);
                             aoeForce.setY(0.6f);
                             hit.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1, 1);

                             player.setVelocity(force);
                             hit.setVelocity(aoeForce);
                         }
                     }
    			 }
    		}
             }
           , 1L);
    	}
                 startCooldown(damager, cooldownTime.getValueAtLevel(level), true);
             }
    	 
    

    @Override
    public String getName() {
        return "Pullbow";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Pullbow";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .setWriteCondition(level == 1)
                .write("Hitting a player pulls them toward").next()
                .write("you (8s cooldown)")
                .setWriteCondition(level != 1)
                .write("Hitting a player pulls them and").next()
                .write("nearby players toward you (8s").next()
                .write("cooldown)")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.B;
    }

    @Override
    public boolean isRareEnchant() {
        return true;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.BOW };
    }
}
