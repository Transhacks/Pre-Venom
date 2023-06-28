package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Jumpspammer extends CustomEnchant {

	@Override
	public void applyEnchant(int paramInt, Object... paramVarArgs) {
		// TODO Auto-generated method stub

	}

	@Override
    public String getName() {
        return "Jumpspammer";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Jumpspammer";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("10%", "16%", "24%")
                .declareVariable("", "10%", "20%")
                .setWriteCondition(level == 1)
                .write("Deal ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" damage while midair").next()
                .write("on arrow hit")
                .resetCondition()
                .setWriteCondition(level != 1)
                .write("While midair, your arrows deal").next()
                .setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" damage. While midair,").next()
                .write("receive ").setColor(ChatColor.BLUE).writeVariable(1, level).resetColor().write(" damage from melee").next()
                .write("and ranged attacks.")
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
        return new Material[] { Material.BOW };
    }
}
