package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Crush extends CustomEnchant {

	 private final EnchantProperty<Integer> weaknessAmplifier = new EnchantProperty<>(4, 5, 6);
	    private final EnchantProperty<Integer> weaknessDuration = new EnchantProperty<>(4, 8, 10);

	    @EventHandler
	    public void onHit(EntityDamageByEntityEvent event) {
	        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
	            attemptEnchantExecution(((Player) event.getDamager()).getItemInHand(), event.getDamager(), event.getEntity(), event);
	        }
	    }

	    @Override
	    public void applyEnchant(int level, Object... args) {
	        Player damager = (Player) args[0];
	        Player damaged = (Player) args[1];
	        
	        if(isNotOnCooldown(damager)) {
	            damager.getWorld().playSound(damager.getLocation(), Sound.GLASS, 1, 1);
	            damaged.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, weaknessDuration.getValueAtLevel(level), weaknessAmplifier.getValueAtLevel(level)));

	        	startCooldown(damager, 30, false);
	        }
	        
	    }

	    @Override
	    public String getName() {
	        return "Crush";
	    }

	    @Override
	    public String getEnchantReferenceName() {
	        return "Crush";
	    }

	    @Override
	    public ArrayList<String> getDescription(int level) {
	        return new LoreBuilder()
	                .declareVariable("Weakness V", "Weakness VI", "Weakness VII")
	                .declareVariable("0.2s", "0.4s", "0.5s")
	                .write("Strikes apply ").setColor(ChatColor.RED).writeVariable(0, level).next()
	                .write("(lasts ").writeVariable(1, level).write(", 1.5s cooldown)")
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
	        return false;
	    }

	    @Override
	    public Material[] getEnchantItemTypes() {
	        return new Material[] { Material.GOLD_SWORD };
	    }
	}
