package me.stevemmmmm.thepitremake.enchants.pants;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class RGM extends CustomEnchant {
    private final EnchantProperty<Integer> cooldownTime = new EnchantProperty<>(5, 4, 3);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }
    
    @Override
    public void applyEnchant(int level, Object... args) {
    }

    @Override
    public String getName() {
        return "Retro-Gravity Microcosm";
    }

    @Override
    public String getEnchantReferenceName() {
        return "RGM";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        LoreBuilder loreBuilder = new LoreBuilder();

        loreBuilder.declareVariable("1.25❤", "1.5❤", "0.5❤")
                .write("When a player hits you from above ground ").setColor(ChatColor.YELLOW).write("3 times").resetColor().write(" in a row:").next()
                .write("You heal ").setColor(ChatColor.RED).write("1.25❤").resetColor();

        if (level == 2) {
            loreBuilder.next().write("Gain ").setColor(ChatColor.RED).write("1.5❤").resetColor().write(" damage vs them for 30s");
        }

        if (level == 3) {
            loreBuilder.next().write("Gain ").setColor(ChatColor.RED).write("1.5❤").resetColor().write(" damage vs them for 30s").next()
            .write("They take ").setColor(ChatColor.RED).write("0.5❤").resetColor().write(" true damage");
        }

        return loreBuilder.build();
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
        return new Material[] { Material.LEATHER_LEGGINGS };
    }
}
