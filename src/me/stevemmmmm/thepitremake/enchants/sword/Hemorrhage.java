package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Hemorrhage extends CustomEnchant {

    @Override
    public void applyEnchant(int paramInt, Object... paramVarArgs) {
    }

    @Override
    public String getName() {
        return "Hemorrhage";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Hemorrhage";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("4", "4", "25")
                .declareVariable("6", "4", "2")
                .write("Strikes bleed enemies for ").setColor(ChatColor.RED).writeVariable(0, level).write("s").resetColor().write(",").next()
                .write("stopping them from gaining").next()
                .setColor(ChatColor.GOLD).write("Absorption ❤").resetColor().write(" and slowing them.").next()
                .write("(").writeVariable(1, level).write("s cooldown)")
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
        return true;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLD_SWORD };
    }
}
