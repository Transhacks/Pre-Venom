package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class RespawnAbsorption extends CustomEnchant {
	
	private final HashMap<UUID, Integer> playerHasAbs = new HashMap<>();
	private final EnchantProperty<Integer> absorptionInt = new EnchantProperty<>(10, 20, 30);
	private final HashMap<UUID, Boolean> effectGivenMap = new HashMap<>();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

		int taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
			boolean hasEnchant = itemHasEnchant(player.getInventory().getLeggings(), this);
			boolean effectGiven = effectGivenMap.getOrDefault(player.getUniqueId(), false);

			if (hasEnchant && !effectGiven
					&& RegionManager.getInstance().playerIsInRegion(player, RegionManager.RegionType.SPAWN)) {
				entityPlayer.setAbsorptionHearts(Math.min(entityPlayer.getAbsorptionHearts() + absorptionInt
						.getValueAtLevel(CustomEnchant.getEnchantLevel(player.getInventory().getLeggings(), this)),
						30));
				effectGivenMap.put(player.getUniqueId(), true);
			} else if (!hasEnchant && effectGiven) {
				entityPlayer.setAbsorptionHearts(0);
				effectGivenMap.put(player.getUniqueId(), false);
			}
		}, 0L, 0L);

		playerHasAbs.put(player.getUniqueId(), taskId);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID playerId = event.getPlayer().getUniqueId();
		int taskId = playerHasAbs.getOrDefault(playerId, -1);
		if (taskId != -1) {
			Bukkit.getServer().getScheduler().cancelTask(taskId);
		}
		playerHasAbs.remove(playerId);
		effectGivenMap.remove(playerId);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		if (itemHasEnchant(player.getInventory().getLeggings(), this)) {
			entityPlayer.setAbsorptionHearts(Math.min(
					entityPlayer.getAbsorptionHearts() + absorptionInt
							.getValueAtLevel(CustomEnchant.getEnchantLevel(player.getInventory().getLeggings(), this)),
					30));
			effectGivenMap.put(player.getUniqueId(), true);
		}
	}

	@Override
	public void applyEnchant(int level, Object... args) {

	}

	@Override
	public String getName() {
		return "Respawn: Absorption";
	}

	@Override
	public String getEnchantReferenceName() {
		return "Absorption";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		return new LoreBuilder().declareVariable("5❤", "10❤", "15❤").write("Spawn with ")
				.writeVariable(ChatColor.YELLOW, 0, level).write(" absorption").build();
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
