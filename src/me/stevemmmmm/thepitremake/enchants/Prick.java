package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

public class Prick extends CustomEnchant {
	private final EnchantProperty<Float> trueDamage = new EnchantProperty<>(0.25f, 0.375f, 0.5f);

    private final List<UUID> queue = new ArrayList<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(((Player) event.getEntity()).getInventory().getLeggings(), event.getDamager(), event);
        }

        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
                attemptEnchantExecution(((Player) event.getEntity()).getInventory().getLeggings(), event.getDamager(), event);
            }
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        Player damager = (Player) args[0];
        DamageManager.getInstance().doTrueDamage(damager, trueDamage.getValueAtLevel(level));
        damager.playSound(damager.getLocation(), Sound.BAT_HURT, 1, 1);
        
    }

    @Override
    public String getName() {
        return "Prick";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Prick";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("0.25❤", "0.375❤", "0.5❤")
                .write("Enemies hitting you receive ").setColor(ChatColor.RED).writeVariable(0, level).next()
                .resetColor().write("true damage")
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