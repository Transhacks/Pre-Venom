package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class DavidandGoliath extends CustomEnchant {

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }

    @Override
    public void applyEnchant(int level, Object... args) {
    }

	@Override
	public String getName() {
		return "David and Goliath";
	}

	@Override
	public String getEnchantReferenceName() {
		return "DavidandGoliath";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder()
        		.declareVariable("-15%", "-25%", "-40%")
                .write("Receive ").writeVariable(ChatColor.BLUE, 0, level).write(" damage from").next()
                .write("players with a bounty")
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
