package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Protection extends CustomEnchant {

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        }

    @Override
    public void applyEnchant(int level, Object... args) {
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Protection";
	}

	@Override
	public String getEnchantReferenceName() {
		// TODO Auto-generated method stub
		return "Protection";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder()
        		.declareVariable("-4%", "-6%", "-10%")
                .write("Recieve ").writeVariable(ChatColor.BLUE, 0, level).write(" damage")
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
