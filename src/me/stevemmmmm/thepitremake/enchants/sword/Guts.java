package me.stevemmmmm.thepitremake.enchants.sword;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class Guts extends CustomEnchant {
    private final EnchantProperty<Float> healthRegen = new EnchantProperty<>(0.5F, 1F, 2F);

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(event.getEntity().getKiller().getItemInHand(), event.getEntity().getKiller(), event);
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        Player killer = (Player) args[0];

        killer.setHealth(Math.min(killer.getHealth() + healthRegen.getValueAtLevel(level), killer.getMaxHealth()));
        
    }

    @Override
    public String getName() {
        return "Guts";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Guts";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("0.25❤", "0.5❤", "1❤")
                .write("Heal ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" on kill")
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
        return false;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLD_SWORD };
    }
}

