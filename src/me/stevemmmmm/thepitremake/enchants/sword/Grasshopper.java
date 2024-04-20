package me.stevemmmmm.thepitremake.enchants.sword;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.stevemmmmm.thepitremake.managers.enchants.CalculationMode;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import me.stevemmmmm.thepitremake.utils.BlockUtils;

public class Grasshopper extends CustomEnchant {

	public BlockUtils blockUtils;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
    }

	@Override
    public void applyEnchant(int level, Object... args) {
    }

    @Override
    public String getName() {
        return "Grasshopper";
    }

    @Override
    public String getEnchantReferenceName() {
        return "Grasshopper";
    }

    @Override
    public ArrayList<String> getDescription(int level) {
        return new LoreBuilder()
                .declareVariable("+5%", "+9%", "+15%")
                .write("Deal ").setColor(ChatColor.RED).writeVariable(0, level).resetColor().write(" damage when you or").next()
                .write("your victim are standing on grass")
                .build();
    }

    @Override
    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    @Override
    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.A;
    }

    @Override
    public boolean isRareEnchant() {
        return false;
    }

    @Override
    public Material[] getEnchantItemTypes() {
        return new Material[] { Material.GOLD_SWORD };
    }
}
