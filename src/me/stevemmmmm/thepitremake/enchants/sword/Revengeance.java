package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Revengeance extends CustomEnchant {

    @Override
    public void applyEnchant(int paramInt, Object... paramVarArgs) {
    }

    @Override
    public String getName() {
        return "Revengeance";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Revengeance";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("8", "15", "25")
                .write("Deal ").setColor(ChatColor.RED).write("+").writeVariable(0, level).write("%").resetColor().write(" damage vs. the last").next()
                .write("player who killed you")
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
