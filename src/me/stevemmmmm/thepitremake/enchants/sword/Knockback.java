package me.stevemmmmm.thepitremake.enchants.sword;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Knockback extends CustomEnchant {
    private final EnchantProperty<Integer> blockAmount = new EnchantProperty<>(3, 4, 6);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            attemptEnchantExecution(((Player) event.getDamager()).getItemInHand(), event.getEntity());
        }
    }

    @Override
    public void applyEnchant(int level, Object... args) {
        Player player = ((Player) args[0]);

        player.setVelocity(new Vector(player.getVelocity().getX() + blockAmount.getValueAtLevel(level), player.getVelocity().getY() + blockAmount.getValueAtLevel(level), player.getVelocity().getZ() + blockAmount.getValueAtLevel(level)));
    }

    @Override
    public String getName() {
        return "Knockback";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Knockback";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("3", "6", "9")
                .write("Increases knockback taken by").next()
                .write("enemies by ").setColor(ChatColor.WHITE).writeVariable(0, level).write(" blocks")
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
        return true;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLD_SWORD };
    }
}