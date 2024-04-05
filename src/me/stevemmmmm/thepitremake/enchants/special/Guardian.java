package me.stevemmmmm.thepitremake.enchants.special;

import java.util.ArrayList;

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

public class Guardian extends CustomEnchant {

	private final EnchantProperty<Float> damageReductionAmount = new EnchantProperty<>(0.1f);
	
	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
                attemptEnchantExecution(((Player) event.getEntity()).getInventory().getChestplate(), event);
            }
        }

    @Override
    public void applyEnchant(int level, Object... args) {
        DamageManager.getInstance().reduceDamage(((EntityDamageByEntityEvent) args[0]), damageReductionAmount.getValueAtLevel(level));
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Guardian";
	}

	@Override
	public String getEnchantReferenceName() {
		// TODO Auto-generated method stub
		return "Guardian";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder().build();
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
        return new Material[] { Material.DIAMOND_CHESTPLATE };
    }
}
