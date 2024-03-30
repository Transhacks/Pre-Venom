package me.stevemmmmm.thepitremake.enchants;

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

	private final EnchantProperty<Float> damageReductionAmount = new EnchantProperty<>(0.04f, 0.06f, 0.1f);
	
	@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
                attemptEnchantExecution(((Player) event.getEntity()).getInventory().getLeggings(), event);
            }
        }

    @Override
    public void applyEnchant(int level, Object... args) {
        DamageManager.getInstance().reduceDamage(((EntityDamageByEntityEvent) args[0]), damageReductionAmount.getValueAtLevel(level));
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
