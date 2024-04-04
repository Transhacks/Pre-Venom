package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.util.Vector;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import net.md_5.bungee.api.ChatColor;

public class ThePunch extends CustomEnchant {

	 private final EnchantProperty<Integer> punchCooldown = new EnchantProperty<>(30, 25, 20);

	    @EventHandler
	    public void onHit(EntityDamageByEntityEvent event) {
	        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
	            attemptEnchantExecution(((Player) event.getDamager()).getItemInHand(), event.getEntity(), event.getDamager(), event);
	        }
	    }

	    @Override
	    public void applyEnchant(int level, Object... args) {
	        Player damaged = (Player) args[0];
	        Player damager = (Player) args[1];
	        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) args[2];
	        if (isNotOnCooldown(damager)) {
	        	Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
                {
                  public void run() {
                	 
                	  Vector vec = new Vector(0, 6, 0);
                	  damaged.setVelocity(vec);
                	  damaged.getWorld().playSound(damaged.getLocation(), Sound.EXPLODE, 0.75f, 1);
                	  damaged.getWorld().playEffect(damaged.getLocation(), Effect.EXPLOSION_LARGE, Effect.EXPLOSION_LARGE.getData(), 100);
                	  for (Player player : event.getEntity().getWorld().getPlayers()) {
                		 player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "PUNCH! " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(damaged) + " " + PermissionsManager.getInstance().getPlayerRank(damaged).getNameColor() + (damaged).getName() + ChatColor.GRAY + " by " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(damager) + " " + PermissionsManager.getInstance().getPlayerRank(damager).getNameColor() + (damager).getName() + ChatColor.GRAY + "!");
                		
                      }
                	  
                	 
                  }
                }
              
                , 1L);
	        	}
	        	 startCooldown(damager, punchCooldown.getValueAtLevel(level), true);
	        
	       
	    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "The Punch";
	}

	@Override
	public String getEnchantReferenceName() {
		// TODO Auto-generated method stub
		return "Punch";
	}

	@Override
	public ArrayList<String> getDescription(int level) {
		// TODO Auto-generated method stub
		return new LoreBuilder()
				.declareVariable("30s", "25s", "20s")
				.write("Hitting a player launches them up").next()
				.write("in the air (").writeVariable(0, level).write(" cooldown)")
				.build();
	}

	@Override
	public boolean isDisabledOnPassiveWorld() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantGroup getEnchantGroup() {
		// TODO Auto-generated method stub
		return EnchantGroup.B;
	}

	@Override
	public boolean isRareEnchant() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Material[] getEnchantItemTypes() {
		// TODO Auto-generated method stub
		 return new Material[] { Material.GOLD_SWORD };
	}

}
