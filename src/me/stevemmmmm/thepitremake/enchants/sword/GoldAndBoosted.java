package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class GoldAndBoosted extends CustomEnchant {

    @Override
    public void applyEnchant(int paramInt, Object... paramVarArgs) {
        // TODO: Implement TNT enchantment logic
    }

    @Override
    public String getName() {
        return "Gold and Boosted";
    }

    @Override
    public String getEnchantReferenceName() {
        return "GoldAndBoosted";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("5", "9", "15")
                .write("Deal ").setColor(ChatColor.RED).write("+").writeVariable(0, level).write("%").resetColor().write(" damage when you have").next()
                .write("absorption hearts")
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
