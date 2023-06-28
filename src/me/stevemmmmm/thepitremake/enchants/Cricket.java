package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Cricket extends CustomEnchant {

	@Override
	public void applyEnchant(int paramInt, Object... paramVarArgs) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Cricket";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Cricket";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder()
				.declareVariable("-5%", "-7%", "-15%")
				.write("Recieve ").setColor(ChatColor.BLUE).writeVariable(0, level).resetColor().write(" damage when you or").next()
				.write("your victims are standing on grass").next()
				.setColor(ChatColor.GREEN).write("Perma ").setColor(ChatColor.RED).write("Regen I ").setColor(ChatColor.GREEN).write("on grass!")
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
		return new Material[] { Material.LEATHER_LEGGINGS };
		}

}
