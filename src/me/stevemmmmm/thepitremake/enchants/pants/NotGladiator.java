package me.stevemmmmm.thepitremake.enchants.pants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class NotGladiator extends CustomEnchant {
    private final EnchantProperty<Float> damageReduction = new EnchantProperty<>(.01f, .015f, .02f);
    private final int maxPlayers = 10;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(((Player) event.getEntity()).getInventory().getLeggings(), event.getEntity(), event);
        }
    }

    @Override
    public void applyEnchant(int level, Object... args)  {
        Player damaged = (Player) args[0];
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) args[1];
        int totalDamageReduction = 0;

        List<Entity> entities = damaged.getNearbyEntities(12, 12, 12);
        List<Player> players = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Player) {
                if (entity != damaged) {
                    players.add((Player) entity);
                }
            }
        }
        
        for (int i = 0; i < players.size(); i++) {
        	totalDamageReduction += damageReduction.getValueAtLevel(level);

        	if (i >= 10) {
        		break;
        	}
        }
        
        DamageManager.getInstance().reduceDamage(event, totalDamageReduction);
    }

    @Override
    public String getName() {
    	return "\"Not\" Gladiator";
    }

    @Override
    public String getEnchantReferenceName() {
    	return "NotGladiator";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("-1%", "-1.5%", "-2%")
                .write("Recieve ").setColor(ChatColor.BLUE).writeVariable(0, level).resetColor().write(" damage per nearby ").next()
                .write("player (max 10 players)")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.C;
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