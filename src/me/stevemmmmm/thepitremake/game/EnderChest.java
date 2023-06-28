//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import me.stevemmmmm.configapi.core.ConfigAPI.InventorySerializer;
import me.stevemmmmm.thepitremake.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class EnderChest implements Listener {
    private static EnderChest instance;
    private final HashMap<UUID, Inventory> playerEnderChestsToxicWorld = new HashMap();
    private final HashMap<UUID, Inventory> playerEnderChestsNonToxicWorld = new HashMap();

    public EnderChest() {
    }

    public static EnderChest getInstance() {
        if (instance == null) {
            instance = new EnderChest();
        }

        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.playerEnderChestsToxicWorld.containsKey(event.getPlayer().getUniqueId())) {
            this.playerEnderChestsToxicWorld.put(event.getPlayer().getUniqueId(), Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.GRAY + "Ender Chest"));
            InventorySerializer.loadInventory(Main.INSTANCE, event.getPlayer(), "ToxicWorld", (Inventory)this.playerEnderChestsToxicWorld.get(event.getPlayer().getUniqueId()));
        }

        if (!this.playerEnderChestsNonToxicWorld.containsKey(event.getPlayer().getUniqueId())) {
            this.playerEnderChestsNonToxicWorld.put(event.getPlayer().getUniqueId(), Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.GRAY + "Ender Chest"));
            InventorySerializer.loadInventory(Main.INSTANCE, event.getPlayer(), "NonToxicWorld", (Inventory)this.playerEnderChestsNonToxicWorld.get(event.getPlayer().getUniqueId()));
        }

        for(int i = 0; i < event.getPlayer().getEnderChest().getSize(); ++i) {
            if (event.getPlayer().getEnderChest().getItem(i) != null && event.getPlayer().getEnderChest().getItem(i).getType() != Material.AIR) {
                ((Inventory)this.playerEnderChestsToxicWorld.get(event.getPlayer().getUniqueId())).setItem(i, event.getPlayer().getEnderChest().getItem(i));
            }
        }

        event.getPlayer().getEnderChest().clear();
    }

    @EventHandler
    public void onEnderChestOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            event.setCancelled(true);
            if (event.getPlayer().getWorld().getName().equals("world")) {
                event.getPlayer().openInventory((Inventory)this.playerEnderChestsToxicWorld.get(event.getPlayer().getUniqueId()));
            } else if (event.getPlayer().getWorld().getName().equals("ThePit_0")) {
                event.getPlayer().openInventory((Inventory)this.playerEnderChestsNonToxicWorld.get(event.getPlayer().getUniqueId()));
            }
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            this.storeEnderChests();
        }

    }

    public void storeEnderChests() {
        Iterator var1 = this.playerEnderChestsToxicWorld.entrySet().iterator();

        Map.Entry entry;
        while(var1.hasNext()) {
            entry = (Map.Entry)var1.next();
            InventorySerializer.serializeInventory(Main.INSTANCE, (UUID)entry.getKey(), "ToxicWorld", (Inventory)entry.getValue());
        }

        var1 = this.playerEnderChestsNonToxicWorld.entrySet().iterator();

        while(var1.hasNext()) {
            entry = (Map.Entry)var1.next();
            Player player = Bukkit.getPlayer((UUID)entry.getKey());
            InventorySerializer.serializeInventory(Main.INSTANCE, (UUID)entry.getKey(), "NonToxicWorld", (Inventory)entry.getValue());
        }

    }
}
