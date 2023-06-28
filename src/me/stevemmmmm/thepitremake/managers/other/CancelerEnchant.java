//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.other;

import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import org.bukkit.event.Event;

public abstract class CancelerEnchant<T extends Event> extends CustomEnchant {
    private CancelerEnchant<T> caller;
    private boolean enchantsWereCancelled = false;

    public CancelerEnchant() {
    }

    public void attemptEnchantCancellation(T event) {
        this.caller = null;
        this.enchantsWereCancelled = false;
    }

    public void cancelEnchants(CancelerEnchant<T> caller) {
        this.enchantsWereCancelled = true;
        this.caller = caller;
    }

    public boolean cancelledEnchants() {
        return this.enchantsWereCancelled;
    }

    public CancelerEnchant<T> getCaller() {
        return this.caller;
    }
}
