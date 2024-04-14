package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Pebble extends CustomEnchant {

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }

    @Override
    public void applyEnchant(int level, Object... args) {
    }

	@Override
	public String getName() {
		return "Pebble";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Pebble";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
	    LoreBuilder builder = new LoreBuilder();

	    builder.declareVariable("+10g", "+20g", "+30g");  

	    if (level < 3) {
	        builder.write("Picked up gold rewards ").writeVariable(ChatColor.GOLD, 0, level); 
	    } else if (level == 3) {
	        builder.write("Picked up gold grants ").setColor(ChatColor.GOLD).write("1❤").resetColor().write(" and").next()
	        .write("rewards ").writeVariable(ChatColor.GOLD, 0, level); 
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
