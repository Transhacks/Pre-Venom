//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.enchants;

import java.util.ArrayList;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TrueShot extends CustomEnchant {
    private final EnchantProperty<Float> extraDamage = new EnchantProperty(new Float[]{0.25F, 0.35F, 0.45F});
    private final EnchantProperty<Float> extraRawDamage = new EnchantProperty(new Float[]{0.0F, 0.25F, 0.5F});

    public TrueShot() {
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && ((Arrow)event.getDamager()).getShooter() instanceof Player) {
            this.attemptEnchantExecution(((Player)((Arrow)event.getDamager()).getShooter()).getInventory().getItemInHand(), new Object[]{((Arrow)event.getDamager()).getShooter(), event.getEntity(), event});
        }

    }

    public void applyEnchant(int level, Object... args) {
        Player damager = (Player)args[0];
        Player damaged = (Player)args[1];
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)args[2];
        DamageManager.getInstance().doTrueDamage(damaged, DamageManager.getInstance().getFinalDamageFromEvent(event) / (double)(Float)this.extraDamage.getValueAtLevel(level) + (double)(Float)this.extraRawDamage.getValueAtLevel(level), damager);
    }

    public String getName() {
        return "True Shot";
    }

    public String getEnchantReferenceName() {
        return "TrueShot";
    }

    public ArrayList<String> getDescription(int level) {
        return (new LoreBuilder()).declareVariable(new String[]{"25%", "35% + 0.25❤", "45% + 0.5❤"}).setWriteCondition(level == 1).write("Deal ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" of arrow damage as").next().write("true damage (ignores armor)").setWriteCondition(level != 1).write("Deal ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" of arrow").next().write("damage as true damage (ignores").next().write("armor)").build();
    }

    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.C;
    }

    public boolean isRareEnchant() {
        return true;
    }

    public Material[] getEnchantItemTypes() {
        return new Material[]{Material.BOW};
    }
}
