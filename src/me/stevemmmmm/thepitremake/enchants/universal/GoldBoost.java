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

public class GoldBoost extends CustomEnchant {

    @Override
    public void applyEnchant(int level, Object... args) {

    }

    @Override
    public String getName() {
        return "Gold Boost";
    }

    @Override
    public String getEnchantReferenceName() {
        return "GoldBoost";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("+15%", "+30%", "+45%")
                .write("Earn ").setColor(ChatColor.GOLD).writeVariable(0, level).write(" gold (g)").resetColor().write(" from kills")
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
