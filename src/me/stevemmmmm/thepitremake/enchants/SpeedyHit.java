package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class SpeedyHit extends CustomEnchant {

	 private final EnchantProperty<Integer> speedDuration = new EnchantProperty<>(100, 140, 180);
	 private final EnchantProperty<Integer> speedCooldown = new EnchantProperty<>(3, 2, 1);

	    @EventHandler
	    public void onHit(EntityDamageByEntityEvent event) {
	        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
	            attemptEnchantExecution(((Player) event.getDamager()).getItemInHand(), event.getDamager(), event);
	        }
	    }

	    @Override
	    public void applyEnchant(int level, Object... args) {
	        Player damager = (Player) args[0];

	        if (isNotOnCooldown(damager)) {
	        damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration.getValueAtLevel(level), 0));
	        startCooldown(damager, speedCooldown.getValueAtLevel(level), true);
	        }
	    }

	    @Override
	    public String getName() {
	        return "Speedy Hit"; 
	    }

	    @Override
	    public String getEnchantReferenceName() {
	        return "Speedyhit";
	    }

	    @Override
	    public ArrayList<String> getDescription(int level) {
	        return new LoreBuilder()
	                .declareVariable("5s", "7s", "9s")
	                .declareVariable("3s", "2s", "1s")
	                .write("Gain Speed I for ").setColor(ChatColor.YELLOW).writeVariable(0, level).resetColor().write(" on hit (").writeVariable(1, level).next()
	                .write("cooldown)")
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
	        return new Material[] { Material.GOLD_SWORD };
	    }
	}

