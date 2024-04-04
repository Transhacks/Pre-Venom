package me.stevemmmmm.thepitremake.enchants.bow;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class SprintDrain extends CustomEnchant {
    private final EnchantProperty<Integer> speedDuration = new EnchantProperty<>(5, 5, 7);
    private final EnchantProperty<Integer> speedAmplifier = new EnchantProperty<>(0, 0, 1);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
            if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
                attemptEnchantExecution(((Player) ((Arrow) event.getDamager()).getShooter()).getInventory().getItemInHand(), ((Arrow) event.getDamager()).getShooter(), event.getEntity());
            }
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
    	Player damager = (Player) args[0];
        Player damaged = (Player) args[1];

        damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration.getValueAtLevel(level) * 20, speedAmplifier.getValueAtLevel(level)), true);
        damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 0), true);
        
        if (level == 1) return;

        damaged.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 0, 0), true);
        damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 0), true);
    }

    @Override
    public String getName() {
        return "Sprint Drain";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Sprintdrain";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("I", "I", "II")
                .declareVariable("", "5", "7")
                .write("Arrow shots grant you ").write(ChatColor.YELLOW, "Speed ").writeVariable(ChatColor.YELLOW, 0, level).next()
                .write("(").writeVariable(1, level).write("s)")
                .setWriteCondition(level != 1)
                .write(" and apply ").write(ChatColor.BLUE, "Slowness I").next()
                .write("(3s)")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        //TODO Correct group
        return EnchantGroup.C;
    }

    @Override
    public boolean isRareEnchant() {
        return false;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.BOW };
    }
}
