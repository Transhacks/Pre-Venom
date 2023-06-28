package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CalculationMode;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Sharp extends CustomEnchant {
	
	private final EnchantProperty<Float> damageIncrease = new EnchantProperty<>(0.04f, 0.07f, 0.12f);

	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(((Player) event.getDamager()).getItemInHand(), event.getDamager(), event);
        }
	}
	
	@Override
	public void applyEnchant(int level, Object... args) {
		 DamageManager.getInstance().addDamage((EntityDamageByEntityEvent) args[1], damageIncrease.getValueAtLevel(level), CalculationMode.ADDITIVE);

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sharp";
	}

	@Override
	public String getEnchantReferenceName() {
		// TODO Auto-generated method stub
		return "Sharp";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		 return new LoreBuilder()
	        		.declareVariable("+4%", "+7%", "+12%")
	        		.setColor(ChatColor.GRAY)
	        		.write("Deal ").writeVariable(ChatColor.RED, 0, level).write(" melee damage")
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
