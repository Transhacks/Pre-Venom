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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class CounterJanitor extends CustomEnchant {
    private final EnchantProperty<Integer> resDuration = new EnchantProperty<>(40, 60, 100);

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(event.getEntity().getKiller().getItemInHand(), event.getEntity().getKiller(), event);
        }
    }
    
    @Override
    public void applyEnchant(int level, Object... args) {
        Player killer = (Player) args[0];

        killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, resDuration.getValueAtLevel(level), 0), true);
        
    }

    @Override
    public String getName() {
        return "Counter-Janitor";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Counter-Janitor";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("2s", "3s", "5s")
                .write("Gain ").setColor(ChatColor.YELLOW).write("Resistance I ").resetColor().write("(").writeVariable(0, level).write(") on").next()
                .write("kill")
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

