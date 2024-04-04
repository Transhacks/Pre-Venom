package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

public class GottaGoFast extends CustomEnchant {

	private final HashMap<UUID, Integer> playerHasGtgf = new HashMap<>();
	private final EnchantProperty<Float> speedAmplifier = new EnchantProperty<>(1.04f, 1.1f, 1.2f);
	private final HashMap<UUID, Float> hasEffect = new HashMap<>();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setWalkSpeed((1f) / 5);
		
		playerHasGtgf.put(player.getUniqueId(),
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {

					if (itemHasEnchant(player.getInventory().getLeggings(), this)) {
						if (!hasEffect.containsKey(player.getUniqueId())){
							player.setWalkSpeed((player.getWalkSpeed() * speedAmplifier.getValueAtLevel(CustomEnchant.getEnchantLevel(player.getInventory().getLeggings(), this))));
							
							hasEffect.put(player.getUniqueId(), speedAmplifier.getValueAtLevel(CustomEnchant.getEnchantLevel(player.getInventory().getLeggings(), this)));
						}
						
						if (hasEffect.get(player.getUniqueId()) != speedAmplifier.getValueAtLevel(CustomEnchant.getEnchantLevel(player.getInventory().getLeggings(), this))) {
							player.setWalkSpeed((1f) / 5);
							hasEffect.remove(player.getUniqueId());
						} // If the pants are switched, then redo the effect.
						
						Location location = player.getLocation();

						PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, // Particle
																													// type
								true, // Long distance visibility
								(float) location.getX(), // X coordinate
								(float) location.getY(), // Y coordinate
								(float) location.getZ(), // Z coordinate
								0.1f, // Offset X
								0.005f, // Offset Y
								0.1f, // Offset Z
								0.001f, // Particle speed
								5, // Particle count
								null // Particle data (not used for CLOUD particle type)
						);

						// Send the packet to the player's client
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
					} else {
						player.setWalkSpeed((1f) / 5);
						hasEffect.remove(player.getUniqueId());
					}
				}, 0L, 1L));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		
		Bukkit.getServer().getScheduler().cancelTask(playerHasGtgf.get(uuid));
		playerHasGtgf.remove(uuid);
		hasEffect.remove(uuid);
	}

	@Override
	public void applyEnchant(int level, Object... args) {
		
	}

	@Override
	public String getName() {
		return "Gotta go fast";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Gtgf";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder().declareVariable("4% faster", "10% faster", "20% faster").write("Move ")
				.writeVariable(ChatColor.YELLOW, 0, level).write(" at all times").build();

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
