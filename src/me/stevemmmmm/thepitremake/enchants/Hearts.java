package me.stevemmmmm.thepitremake.enchants;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class Hearts extends CustomEnchant {

	private final HashMap<UUID, Integer> playerHasHearts = new HashMap<>();
	private final EnchantProperty<Float> heartsAmount = new EnchantProperty<>(0.5f, 1f, 2f);
	private final HashMap<UUID, Boolean> effectGivenMap = new HashMap<>();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		int taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
			boolean hasEnchant = itemHasEnchant(player.getInventory().getLeggings(), this);
			boolean effectGiven = effectGivenMap.getOrDefault(player.getUniqueId(), false);
			int enchantLevel = CustomEnchant.getEnchantLevel(player.getInventory().getLeggings(), this);

			if (hasEnchant && !effectGiven) {
				player.setMaxHealth(player.getMaxHealth() + heartsAmount.getValueAtLevel(enchantLevel));
				effectGivenMap.put(player.getUniqueId(), true);
			} else if (!hasEnchant && effectGiven) {
				player.setMaxHealth(24);
				effectGivenMap.put(player.getUniqueId(), false);
			}
		}, 0L, 0L);

		playerHasHearts.put(player.getUniqueId(), taskId);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID playerId = event.getPlayer().getUniqueId();
		int taskId = playerHasHearts.getOrDefault(playerId, -1);
		if (taskId != -1) {
			Bukkit.getServer().getScheduler().cancelTask(taskId);
		}
		playerHasHearts.remove(playerId);
		effectGivenMap.remove(playerId);
	}

	@Override
	public void applyEnchant(int level, Object... args) {

	}

	@Override
	public String getName() {
		return "Hearts";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Hearts";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder().declareVariable("0.25❤", "0.5❤", "1❤")
				.write(ChatColor.GRAY + "Increase your max health by").next().writeVariable(ChatColor.RED, 0, level)
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
		return new Material[] { Material.LEATHER_LEGGINGS };
	}
}