//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.utils;

import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;

public class TelebowData {
    private final Arrow arrow;
    private final ItemStack bow;
    private final boolean isSneaking;

    public TelebowData(Arrow arrow, ItemStack bow, boolean isSneaking) {
        this.arrow = arrow;
        this.bow = bow;
        this.isSneaking = isSneaking;
    }

    public Arrow getArrow() {
        return this.arrow;
    }

    public ItemStack getBow() {
        return this.bow;
    }

    public boolean isSneaking() {
        return this.isSneaking;
    }
}
