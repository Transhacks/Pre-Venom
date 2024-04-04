package me.stevemmmmm.thepitremake.enchants.sword;

import me.stevemmmmm.thepitremake.commands.GambleImmunityCommand;
import me.stevemmmmm.thepitremake.managers.enchants.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class Gamble extends CustomEnchant {
	private final EnchantProperty<Integer> damageAmount = new EnchantProperty<>(2, 4, 6);

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			attemptEnchantExecution(((Player) event.getDamager()).getInventory().getItemInHand(), event.getDamager(),
					event.getEntity());
		}
	}

	@Override
	public void applyEnchant(int level, Object... args) {
		Player damager = (Player) args[0];
		Player damaged = (Player) args[1];

		if (percentChance(50) && !GambleImmunityCommand.getInstance().gambleImmunedPlayer.contains(damager)) {
			damager.setHealth(Math.max(0, damager.getHealth() - damageAmount.getValueAtLevel(level)));
			damager.damage(0);
			damager.playSound(damager.getLocation(), Sound.NOTE_PLING, 1000000, (float) 1.5);
		} else {
			DamageManager.getInstance().doTrueDamage(damaged, damageAmount.getValueAtLevel(level), damager);
			damager.playSound(damager.getLocation(), Sound.NOTE_PLING, 1000000, (float) 2);
		}

	}

	@Override
	public String getName() {
		return "Gamble";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Gamble";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder().declareVariable("1❤", "2❤", "3❤")
				.write(ChatColor.LIGHT_PURPLE + "50% chance " + ChatColor.GRAY + "to deal ")
				.writeVariable(ChatColor.RED, 0, level).write(" true").next().write("damage to whoever you hit, or to")
				.next().write("yourself").build();
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
