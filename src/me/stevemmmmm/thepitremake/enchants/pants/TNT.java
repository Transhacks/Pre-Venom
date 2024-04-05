package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class TNT extends CustomEnchant {

    @Override
    public void applyEnchant(int paramInt, Object... paramVarArgs) {
        // TODO: Implement TNT enchantment logic
    }

    @Override
    public String getName() {
        return "TNT";
    }

    @Override
    public String getEnchantReferenceName() {
        return "TNT";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("1", "2", "3")
                .declareVariable("1", "1.5", "2")
                .declareVariable("1", "1", "2")
                .write("Spawn with ").setColor(ChatColor.RED).writeVariable(0, level).write(" TNT").resetColor().write(".").setColor(ChatColor.RED).write(" TNT").resetColor().next()
                .write("explodes after 1.5 seconds and").next()
                .write("deals ").setColor(ChatColor.RED).writeVariable(1, level).write("❤").resetColor().write(" in a 3 blocks radius.").next()
                .write("Gain ") .setColor(ChatColor.RED).write("+").writeVariable(2, level).write(" TNT").resetColor().write(" on kill")
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
        return new Material[] { Material.LEATHER_LEGGINGS };
    }
}
