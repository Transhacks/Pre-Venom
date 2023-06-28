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

public class Revitalize extends CustomEnchant {

	private final EnchantProperty<Integer> speedAmplifier = new EnchantProperty<>(0, 1, 1);
	private final EnchantProperty<Integer> regenAmplifier = new EnchantProperty<>(1, 2, 2);
	private final EnchantProperty<Integer> speedDuration = new EnchantProperty<>(100, 100, 140);
	private final EnchantProperty<Integer> regenDuration = new EnchantProperty<>(60, 60, 100);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            attemptEnchantExecution(((Player) event.getEntity()).getInventory().getLeggings(), event.getEntity());
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        Player damaged = (Player) args[0];

        if (isNotOnCooldown(damaged) && damaged.getHealth() < 6) {
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration.getValueAtLevel(level), speedAmplifier.getValueAtLevel(level)));
            damaged.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenDuration.getValueAtLevel(level), regenAmplifier.getValueAtLevel(level)));
            startCooldown(damaged, 45, true);
        }
    }

    @Override
    public String getName() {
        return "Revitalize";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Revitalize";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("Speed I", "Speed II", "Speed II")
                .declareVariable("II", "II", "III")
                .declareVariable("5s", "5s", "7s")
                .declareVariable("3s", "3s", "5s")
                .write("Gain ").writeVariable(0, level).write(" (").writeVariable(2, level).write(") and ").setColor(ChatColor.RED).write("Regen").next()
                .setColor(ChatColor.RED).writeVariable(1, level).resetColor().write(" (").writeVariable(3, level).write(") when reaching ").setColor(ChatColor.RED).write("3❤").next()
                .resetColor().write("(45s cooldown)")
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

