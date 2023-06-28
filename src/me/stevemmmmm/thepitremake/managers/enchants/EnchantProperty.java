//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.enchants;

public class EnchantProperty<T> {
    private final T[] values;

    @SafeVarargs
    public EnchantProperty(T... values) {
        this.values = values;
    }

    public T getValueAtLevel(int level) {
        return this.values[level - 1];
    }
}
