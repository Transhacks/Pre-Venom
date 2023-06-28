//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import me.stevemmmmm.animationapi.core.Sequence;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MysticWellInventory {
    public static final String INVENTORY_NAME;
    private Player player;
    private Inventory gui;
    private MysticWellState state;
    private Sequence sequence;
    private int enchantButtonSlot = 24;

    private MysticWellInventory() {
    }

    public static MysticWellInventory newInventory(Player player) {
        Inventory gui = Bukkit.createInventory((InventoryHolder)null, 45, INVENTORY_NAME);
        ItemStack grayGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta gpMeta = grayGlassPane.getItemMeta();
        gpMeta.setDisplayName(ChatColor.GRAY + "Click an item in your inventory!");
        grayGlassPane.setItemMeta(gpMeta);
        gui.setItem(10, grayGlassPane);
        gui.setItem(11, grayGlassPane);
        gui.setItem(12, grayGlassPane);
        gui.setItem(19, grayGlassPane);
        gui.setItem(21, grayGlassPane);
        gui.setItem(28, grayGlassPane);
        gui.setItem(29, grayGlassPane);
        gui.setItem(30, grayGlassPane);
        MysticWellInventory mysticWellInventory = new MysticWellInventory();
        mysticWellInventory.gui = gui;
        mysticWellInventory.player = player;
        return mysticWellInventory;
    }

    public static boolean pressedEnchantButton(InventoryClickEvent event) {
        return event.getRawSlot() == 24 && event.getClickedInventory().getName().equals(INVENTORY_NAME);
    }

    public boolean canEnchant() {
        if (this.getItemInEnchantmentSlot() == null) {
            return false;
        } else {
            int goldAmount = 0;
            switch (CustomEnchantManager.getInstance().getItemTier(this.getItemInEnchantmentSlot())) {
                case 0:
                    goldAmount = 1000;
                    break;
                case 1:
                    goldAmount = 4000;
                    break;
                case 2:
                    goldAmount = 8000;
            }

            if (GrindingSystem.getInstance().getPlayerGold(this.player) >= (double)goldAmount) {
                GrindingSystem.getInstance().setPlayerGold(this.player, Math.max(0.0, GrindingSystem.getInstance().getPlayerGold(this.player) - (double)goldAmount));
            }

            return true;
        }
    }

    public void setEnchantmentButtonIcon(ItemStack icon) {
        this.gui.setItem(24, icon);
    }

    public Inventory getRawInventory() {
        return this.gui;
    }

    public ItemStack getItemInEnchantmentSlot() {
        return this.gui.getItem(20);
    }

    public MysticWellState getState() {
        return this.state;
    }

    public void setState(MysticWellState state) {
        this.state = state;
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    static {
        INVENTORY_NAME = ChatColor.GRAY + "Mystic Well";
    }

    static enum MysticWellState {
        IDLE,
        ENCHANTING;

        private MysticWellState() {
        }
    }
}
