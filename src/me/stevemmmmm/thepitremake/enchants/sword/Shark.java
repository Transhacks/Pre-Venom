package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Shark extends CustomEnchant {

    @Override
    public void applyEnchant(int paramInt, Object... paramVarArgs) {
    }

    @Override
    public String getName() {
        return "Shark";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Shark";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("+2%", "+4%", "+7%")
                .write("Deal ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" damage per other").next()
                .write("player below ").setColor(ChatColor.RED).write("6❤").resetColor().write(" within 12").next()
                .write("blocks")
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
