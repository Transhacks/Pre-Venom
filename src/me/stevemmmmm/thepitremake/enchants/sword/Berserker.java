package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Berserker extends CustomEnchant {

    @Override
    public void applyEnchant(int paramInt, Object... paramVarArgs) {
        // TODO: Implement TNT enchantment logic
    }

    @Override
    public String getName() {
        return "Berserker";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Berserker";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("12", "20", "30")
                .write("You can now critical hit on the").next()
                .write("ground. ").setColor(ChatColor.GREEN).write("+").writeVariable(0, level).write("% chance").resetColor().write(" to crit for").next()
                .setColor(ChatColor.RED).write("50% extra").resetColor().write(" damage")
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
