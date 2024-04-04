package me.stevemmmmm.thepitremake.enchants.sword;

import me.stevemmmmm.thepitremake.managers.enchants.*;
import net.minecraft.server.v1_8_R3.MobSpawnerAbstract.a;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class FancyRaider extends CustomEnchant {
    private final EnchantProperty<Double> percentDamageIncrease = new EnchantProperty<>(.05, .09, .15);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(((Player) event.getDamager()).getItemInHand(), event);
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) args[0];

        if (playerHasLeatherPiece((Player) event.getEntity())) {
            DamageManager.getInstance().addDamage(event, percentDamageIncrease.getValueAtLevel(level), CalculationMode.ADDITIVE);
                                       
        }
    }

    private boolean playerHasLeatherPiece(Player player) {
        if (player.getInventory().getBoots() != null) {
            if (player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS) {
                return true;
            }
        }

        if (player.getInventory().getLeggings() != null) {
            if (player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS) {
                return true;
            }
        }

        if (player.getInventory().getChestplate() != null) {
            if (player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
                return true;
            }
        }

        if (player.getInventory().getHelmet() != null) {
            return player.getInventory().getHelmet().getType() == Material.LEATHER_HELMET;
        }

        return false;
    }


    @Override
    public String getName() {
        return "Fancy Raider";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Fancyraider";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("5%", "9%", "15%")
                .write("Deal ").setColor(ChatColor.RED).write("+").writeVariable(0, level).resetColor().write(" damage vs. players").next()
                .write("wearing leather armor")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.A;
    }

    @Override
    public boolean isRareEnchant() {
        return false;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLD_SWORD };
    }
}
