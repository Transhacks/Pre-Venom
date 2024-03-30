//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class MysticWellIcons {
    private static boolean hasInitialized;
    private static final ItemStack enchantmentTableInfoIdle;
    private static final ItemStack enchantmentTableInfoT1;
    private static final ItemStack enchantmentTableInfoT2;
    private static final ItemStack enchantmentTableInfoT3;
    private static final ItemStack enchantmentTableInfoItsRollin;
    private static final ItemStack enchantmentTableInfoMaxTier;

    public MysticWellIcons() {
    }

    public void getIcon(Icon icon) {
    }

    static {
        enchantmentTableInfoIdle = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoT1 = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoT2 = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoT3 = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoItsRollin = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoMaxTier = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
    }
}
