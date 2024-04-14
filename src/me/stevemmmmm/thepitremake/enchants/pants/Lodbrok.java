package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Lodbrok extends CustomEnchant {

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }

    @Override
    public void applyEnchant(int level, Object... args) {
    }

	@Override
	public String getName() {
		return "Lodbrok";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Lodbrok";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder()
        		.declareVariable("40", "55", "75")
                .write("Increases the chance for armor").next()
                .write("pieces to drop to ").writeVariable( 0, level).write("% (normally").next()
                .write("30%")
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
