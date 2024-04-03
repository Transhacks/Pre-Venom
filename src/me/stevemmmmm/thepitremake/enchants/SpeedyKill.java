package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class SpeedyKill extends CustomEnchant {

	 private final EnchantProperty<Integer> speedDuration = new EnchantProperty<>(80, 140, 240);

	    @EventHandler
	    public void onKill(PlayerDeathEvent event) {
	        if (event.getEntity().getKiller() instanceof Player && event.getEntity() instanceof Player) {
	            attemptEnchantExecution(event.getEntity().getKiller().getItemInHand(), event.getEntity().getKiller(), event);
	        }
	    }

	    @Override
	    public void applyEnchant(int level, Object... args) {
	        Player damager = (Player) args[0];

	        damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration.getValueAtLevel(level), 0));

	        }
	    
	    @Override
	    public String getName() {
	        return "Speedy Kill"; 
	    }

	    @Override
	    public String getEnchantReferenceName() {
	        return "Speedykill";
	    }

	    @Override
	    public ArrayList<String> getDescription(int level) {
	        return new LoreBuilder()
	                .declareVariable("4", "7", "12")
	                .write("Gain ").setColor(ChatColor.YELLOW).write("Speed I ").resetColor().write("(").writeVariable(0, level).write("s) on kill")
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

