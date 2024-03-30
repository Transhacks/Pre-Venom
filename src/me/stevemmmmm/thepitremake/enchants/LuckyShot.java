package me.stevemmmmm.thepitremake.enchants;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.enchants.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class LuckyShot extends CustomEnchant {
    private final EnchantProperty<Integer> percentChance = new EnchantProperty<>(2, 3, 10);

	private final ArrayList<Arrow> hitLuckyShotArrows = new ArrayList<>();

    private final List<UUID> canLuckyShot = new ArrayList<>();

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getDamager();
            hitLuckyShotArrows.add(arrow);

            if (arrow.getShooter() instanceof Player) {
                attemptEnchantExecution(BowManager.getInstance().getBowFromArrow(arrow), event);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
            if (event.getEntity() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getEntity();

                if (event.getEntity().getShooter() instanceof Player) {
                    if (getAttemptedEnchantExecutionFeedback(BowManager.getInstance().getBowFromArrow((Arrow) event.getEntity()))) {
                        if (!hitLuckyShotArrows.contains(arrow)) {
                            canLuckyShot.remove(((Player) (event.getEntity()).getShooter()).getUniqueId());
                        }
                    }
                }
            }
        }, 1L);
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getProjectile() instanceof Arrow) {
            if (((Arrow) event.getProjectile()).getShooter() instanceof Player) {
                if (CustomEnchant.itemHasEnchant(((Player) ((Arrow) event.getProjectile()).getShooter()).getItemInHand(), this)) {
                    int level = CustomEnchant.getEnchantLevel(((Player) ((Arrow) event.getProjectile()).getShooter()).getItemInHand(), this);

                    if (percentChance(percentChance.getValueAtLevel(level))) {
                        canLuckyShot.add(((Player) ((Arrow) event.getProjectile()).getShooter()).getUniqueId());
                        ((Player) ((Arrow) event.getProjectile()).getShooter()).sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "LUCKY SHOT!" + ChatColor.LIGHT_PURPLE + " Quadruple damage!");
                    }
                }
            }
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) args[0];
        Arrow arrow = (Arrow) event.getDamager();

        if (canLuckyShot.contains(((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId())) {
            hitLuckyShotArrows.removeAll(hitLuckyShotArrows);
            canLuckyShot.removeAll(canLuckyShot);
            DamageManager.getInstance().addDamage(event, 4, CalculationMode.MULTIPLICATIVE);
        }
    }

    @Override
    public String getName() {
        return "Lucky Shot";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Luckyshot";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("2%", "5%", "10%")
                .setColor(ChatColor.YELLOW).writeVariable(0, level).resetColor().write(" chance for a shot to deal").next()
                .write("quadruple damage")
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
