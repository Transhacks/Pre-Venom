package me.stevemmmmm.thepitremake.enchants;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.managers.enchants.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class Robinhood extends CustomEnchant {
    private final EnchantProperty<Float> damageReduction = new EnchantProperty<>(.4f, .5f, .6f);
    private final HashMap<Arrow, Integer> arrowTasks = new HashMap<>();

    private final double range = 8;

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getProjectile();
            Player player = (Player) event.getEntity();

            attemptEnchantExecution(event.getBow(), arrow, player, event.getForce());
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow removal = null;

            for (Map.Entry<Arrow, Integer> entry : arrowTasks.entrySet()) {
                if (entry.getKey() == event.getDamager()) {
                    Arrow arrow = (Arrow) event.getDamager();

                    if (arrow.getShooter() instanceof Player) {
                        Player player = (Player) arrow.getShooter();

                        if (itemHasEnchant(player.getInventory().getItemInHand(), this)) {
                            DamageManager.getInstance().addDamage(event, damageReduction.getValueAtLevel(getEnchantLevel(player.getInventory().getItemInHand(), this)), CalculationMode.ADDITIVE);
                        }
                    }

                    if (!arrow.isValid()) {
                        Bukkit.getServer().getScheduler().cancelTask(arrowTasks.get(arrow));
                        removal = arrow;
                    }
                }
            }

            if (removal != null) arrowTasks.remove(removal);
        }
    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow removal = null;

            for (Map.Entry<Arrow, Integer> entry : arrowTasks.entrySet()) {
                if (entry.getKey() == event.getEntity()) {
                    Arrow arrow = (Arrow) event.getEntity();

                    if (!arrow.isValid()) {
                        Bukkit.getServer().getScheduler().cancelTask(arrowTasks.get(arrow));
                        removal = arrow;
                    }
                }
            }

            if (removal != null) arrowTasks.remove(removal);
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        Arrow arrow = (Arrow) args[0];
        Player player = (Player) args[1];
        float force = (float) args[2];

        if (level == 1) {
            if (force < 1) return;
        }

        arrowTasks.put(arrow, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
            List<Entity> closestEntities = player.getNearbyEntities(range, range, range);
            List<Player> closestPlayers = new ArrayList<>();

            for (Entity entity : closestEntities) {
                if (entity instanceof Player) {                    closestPlayers.add((Player) entity);
                }
            }

            if (closestPlayers.isEmpty()) closestEntities = arrow.getNearbyEntities(range, range ,range);

            Player closestPlayer = null;

            for (Entity entity : closestEntities) {
                if (entity instanceof Player) {
                    if (entity != player) {
                        if (closestPlayer == null) {
                            closestPlayer = (Player) entity;
                            continue;
                        }

                        if (player.getLocation().toVector().distance(entity.getLocation().toVector()) < player.getLocation().toVector().distance(closestPlayer.getLocation().toVector())) {
                            closestPlayer = (Player) entity;
                        }
                    }
                }
            }

            if (closestPlayer == null) return;

            Vector arrowVector = arrow.getLocation().toVector();
            Vector closestPlayerVector = closestPlayer.getLocation().toVector();
            closestPlayerVector.setY(closestPlayerVector.getY() + 2);

            Vector direction = closestPlayerVector.subtract(arrowVector).normalize();

            arrow.setVelocity(direction);
        }, 0L, 3L));
    }

    @Override
    public String getName() {
        return "Robinhood";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Robinhood";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("40%", "50%", "60%")
                .setWriteCondition(level == 1)
                .write("Your charged shots are homing but").next()
                .write("deal ").writeVariable(ChatColor.RED, 0, level).write(" damage")
                .resetCondition()
                .setWriteCondition(level != 1)
                .write("All your shots are homing but deal").next()
                .setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" damage")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return true;
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
