//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.ArrayList;
import java.util.UUID;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WorldSelection implements Listener {
    private static WorldSelection instance;
    private final String inventoryName;
    private final Inventory gui;
    private final ArrayList<UUID> mayExitGuiSelection;

    public WorldSelection() {
        this.inventoryName = ChatColor.LIGHT_PURPLE + "World Selection";
        this.gui = Bukkit.createInventory((InventoryHolder)null, 9, this.inventoryName);
        this.mayExitGuiSelection = new ArrayList();
    }

    public static WorldSelection getInstance() {
        if (instance == null) {
            instance = new WorldSelection();
        }

        return instance;
    }

    public void displaySelectionMenu(Player player) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
            player.teleport(new Location(player.getWorld(), -90.5, 60.0, 0.5, 0.0F, 90.0F));
            this.transportToWorld(player, "ThePit_0");
        }, 1L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.displaySelectionMenu(event.getPlayer());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(this.inventoryName)) {
            if (event.getRawSlot() == 3) {
                this.mayExitGuiSelection.add(event.getWhoClicked().getUniqueId());
                event.getWhoClicked().sendMessage(ChatColor.RED + "Toxic world is now unavailable! You will be transported to the Peaceful world.");
                this.transportToWorld(event.getWhoClicked(), "ThePit_0");
                event.getWhoClicked().closeInventory();
            }

            if (event.getRawSlot() == 5) {
                this.mayExitGuiSelection.add(event.getWhoClicked().getUniqueId());
                this.transportToWorld(event.getWhoClicked(), "ThePit_0");
                event.getWhoClicked().closeInventory();
            }

            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getName().equals(this.inventoryName)) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
                if (!this.mayExitGuiSelection.contains(event.getPlayer().getUniqueId())) {
                    event.getPlayer().openInventory(this.gui);
                } else {
                    this.mayExitGuiSelection.remove(event.getPlayer().getUniqueId());
                }

            }, 1L);
        }

    }

    private void transportToWorld(HumanEntity player, String worldName) {
        World world = Main.INSTANCE.getServer().createWorld(new WorldCreator(worldName));
        Location location = RegionManager.getInstance().getSpawnLocation((Player)player);
        Location spawnLocation = new Location(world, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        Chunk centerChunk = location.getChunk();
        centerChunk.load(true);
        player.sendMessage(ChatColor.GREEN + "Loading world...");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> {
            player.teleport(spawnLocation);
        }, 20L);
    }

    private void generateGui() {
        this.gui.setItem(3, new ItemStack(Material.BLAZE_POWDER));
        ItemMeta meta = this.gui.getItem(3).getItemMeta();
        meta.setDisplayName(ChatColor.RED + "The Toxic World");
        meta.setLore((new LoreBuilder()).setColor(ChatColor.YELLOW).write("A world where any").next().write("enchants are allowed").next().resetColor().write(ChatColor.GRAY + "No token limit on items").next().next().write("" + ChatColor.DARK_GRAY + ChatColor.ITALIC + "CURRENTLY BEING REWORKED").build());
        this.gui.getItem(3).setItemMeta(meta);
        this.gui.setItem(5, new ItemStack(Material.QUARTZ));
        meta = this.gui.getItem(5).getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "The Peaceful World");
        meta.setLore((new LoreBuilder()).setColor(ChatColor.WHITE).write("A world where the most toxic").next().write("enchants are removed from").next().write("existance for peaceful").next().write("gameplay and fair fights").next().next().resetColor().write(ChatColor.ITALIC + "8 tokens maximum on items").build());
        this.gui.getItem(5).setItemMeta(meta);
    }
}
