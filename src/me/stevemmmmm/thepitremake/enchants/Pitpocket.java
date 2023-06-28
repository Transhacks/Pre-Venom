//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Pitpocket extends CustomEnchant {
    private final EnchantProperty<Integer> goldAmount = new EnchantProperty(new Integer[]{15, 20, 25});
    private final EnchantProperty<Integer> pitpocketCooldown = new EnchantProperty(new Integer[]{25, 20, 13});

    public Pitpocket() {
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            this.attemptEnchantExecution(((Player)event.getDamager()).getItemInHand(), new Object[]{event.getDamager(), event.getEntity(), event});
        }

    }

    public void applyEnchant(int level, Object... args) {
        Player damager = (Player)args[0];
        Player damaged = (Player)args[1];
        if (this.isNotOnCooldown(damager)) {
            GrindingSystem.getInstance().setPlayerGold(damaged, GrindingSystem.getInstance().getPlayerGold(damaged) - (double)(Integer)this.goldAmount.getValueAtLevel(level));
            GrindingSystem.getInstance().setPlayerGold(damager, GrindingSystem.getInstance().getPlayerGold(damager) + (double)(Integer)this.goldAmount.getValueAtLevel(level));
            damager.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "PITPOCKET! " + ChatColor.GRAY + "you stole " + ChatColor.GOLD + this.goldAmount.getValueAtLevel(level) + "g " + ChatColor.GRAY + "from " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(damaged) + " " + PermissionsManager.getInstance().getPlayerRank(damaged).getNameColor() + damaged.getName() + ChatColor.GRAY + "!");
            damaged.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "PITPOCKET! " + GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(damaged) + " " + PermissionsManager.getInstance().getPlayerRank(damaged).getNameColor() + damaged.getName() + ChatColor.GRAY + " stole " + ChatColor.GOLD + this.goldAmount.getValueAtLevel(level) + "g " + ChatColor.GRAY + "from you!");
            damager.playSound(damager.getLocation(), Sound.BAT_IDLE, 1.0F, 100.0F);
            damager.playSound(damager.getLocation(), Sound.BAT_IDLE, 1.0F, 100.0F);
            this.startCooldown(damager, (long)(Integer)this.pitpocketCooldown.getValueAtLevel(level), true);
        }

    }

    public String getName() {
        return "Pitpocket";
    }

    public String getEnchantReferenceName() {
        return "Pitpocket";
    }

    public ArrayList<String> getDescription(int level) {
        return (new LoreBuilder()).declareVariable(new String[]{"15g", "20g", "25g"}).declareVariable(new String[]{"25s", "20s", "13s"}).write("Steal ").setColor(ChatColor.GOLD).writeVariable(0, level).resetColor().write(" on melee hit (").writeVariable(1, level).next().write("cooldown)").build();
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
