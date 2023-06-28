//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.utils;

import java.util.Comparator;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;

public class SortCustomEnchantByName implements Comparator<CustomEnchant> {
    public SortCustomEnchantByName() {
    }

    public int compare(CustomEnchant a, CustomEnchant b) {
        try {
            return a.getName().compareTo(b.getName());
        } catch (NullPointerException var4) {
            System.out.println("Error! Missing name information for an enchant.");
            return 0;
        }
    }
}
