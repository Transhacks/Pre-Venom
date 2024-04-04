//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;
import me.stevemmmmm.thepitremake.managers.enchants.CalculationMode;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Duelist extends CustomEnchant {
    private final EnchantProperty<Integer> healingAmount = new EnchantProperty(new Integer[]{1, 2, 3});
    private final EnchantProperty<Float> damageIncrease = new EnchantProperty(new Float[]{0.2F, 0.4F, 0.75F});
    public static boolean shouldDuelistProc;

    public Duelist() {
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            this.attemptEnchantExecution(((Player)event.getEntity()).getInventory().getItemInHand(), new Object[]{event.getDamager(), event.getEntity(), event});
        }

    }

    public void applyEnchant(int level, Object... args) {
        Player damager = (Player)args[0];
        Player player = (Player)args[1];
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)args[2];
        int hits = 0;
        if (event.getDamager().equals(damager) && player.isBlocking() && hits != 2) {
            ++hits;
        }

        if (hits == 2) {
            shouldDuelistProc = true;
        }

        if (shouldDuelistProc) {
            player.setHealth(Math.min(player.getHealth() + (double)(Integer)this.healingAmount.getValueAtLevel(level), player.getMaxHealth()));
            DamageManager.getInstance().addDamage((EntityDamageByEntityEvent)args[2], (double)(Float)this.damageIncrease.getValueAtLevel(level), CalculationMode.ADDITIVE);
            player.playSound(player.getLocation(), Sound.ENDERMAN_HIT, 2.0F, 2.0F);
            hits = 0;
            shouldDuelistProc = false;
        }

    }

    public String getName() {
        return "Duelist";
    }

    public String getEnchantReferenceName() {
        return "Duelist";
    }

    public ArrayList<String> getDescription(int level) {
        return (new LoreBuilder()).declareVariable(new String[]{"+20%", "+40%", "+75%"}).declareVariable(new String[]{"0.5❤", "1❤", "1.5❤"}).write("Blocking two hits empowers your").next().write("next strike against them for ").writeVariable(ChatColor.RED, 0, level).next().resetColor().write("damage and heals ").writeVariable(ChatColor.RED, 1, level).build();
    }

    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.B;
    }

    public boolean isRareEnchant() {
        return false;
    }

    public Material[] getEnchantItemTypes() {
        return new Material[]{Material.GOLD_SWORD};
    }
}
