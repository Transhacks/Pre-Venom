package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Selfcheckout extends CustomEnchant {

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }

    @Override
    public void applyEnchant(int level, Object... args) {
    }

	@Override
	public String getName() {
		return "Self-checkout";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Self-checkout";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder()
        		.declareVariable("+2,000g", "+3,000g", "+5,000g")
                .write("Upon reaching a ").setColor(ChatColor.GOLD).write("5,000g").next()
                .resetColor().write("bounty, clear it and gain").next()
                .writeVariable(ChatColor.GOLD, 0, level).resetColor().write(". Consumes 1 life of").next()
                .write("this item")
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
