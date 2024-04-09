package me.stevemmmmm.thepitremake.enchants.universal;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class StrikeGold extends CustomEnchant {

    @Override
    public void applyEnchant(int level, Object... args) {

    }

    @Override
    public String getName() {
        return "Strike Gold";
    }

    @Override
    public String getEnchantReferenceName() {
        return "StrikeGold";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("+1g", "+2g", "+3g")
                .write("Earn ").writeVariable(ChatColor.GOLD, 0, level).write(" per hit (1s cooldown)")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.C;
    }

    @Override
    public boolean isRareEnchant() {
        return false;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLD_SWORD, Material.LEATHER_LEGGINGS, Material.BOW };
    }
}
