package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class EscapePod extends CustomEnchant {
	
	private EnchantProperty<Integer> regenAmplifier = new EnchantProperty<>(1, 2, 3);
	private EnchantProperty<Integer> regenDuration = new EnchantProperty<>(400, 500, 600);
	private EnchantProperty<Integer> aoeDamage = new EnchantProperty<>(2, 4, 6);

	@Override
	public void applyEnchant(int paramInt, Object... paramVarArgs) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Escape Pod";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Escapepod";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder()
				.declareVariable("1❤", "2❤", "3❤")
				.declareVariable("Regen II", "Regen III", "Regen IV")
				.declareVariable("20s", "25s", "30s")
				.write("When hit below ").setColor(ChatColor.RED).write("2❤").resetColor().write(", launch").next()
				.write("into the air dealing ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" damage").next()
				.write("to nearby enemies and gaining").next()
				.setColor(ChatColor.GREEN).writeVariable(1, level).resetColor().write(" (").writeVariable(2, level).write("). Can launch").next()
				.write("once per life.")
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
		return new Material[] { Material.LEATHER_LEGGINGS };
	}

}
