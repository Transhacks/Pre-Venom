package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class PurpleGold extends CustomEnchant {

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }

    @Override
    public void applyEnchant(int level, Object... args) {
    }

	@Override
	public String getName() {
		return "Purple Gold";
	}

	@Override
	public String getEnchantReferenceName() {
		return "PurpleGold";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
	    LoreBuilder builder = new LoreBuilder();

	    builder.declareVariable("+7g", "+11g", "+15g");  
	    builder.declareVariable("", "2", "3");

	    String baseText = "Gain ";

	    if (level == 1) {
	        builder.write(baseText).writeVariable(ChatColor.GOLD, 0, level).resetColor().write(" from breaking").next()
	               .write("obsidian");
	    } else if (level > 1 ) {
	        builder.write(baseText).writeVariable(ChatColor.GOLD, 0, level).write(" and ").setColor(ChatColor.RED).write("Regen III").next()
	        	   .resetColor().write("(").writeVariable(1, level).write("s) from breaking obsidian");
	    }
	    return builder.build();
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
