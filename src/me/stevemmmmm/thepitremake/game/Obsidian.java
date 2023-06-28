//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.HashMap;
import java.util.Iterator;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Obsidian implements Listener {
    private static Obsidian instance;
    private final HashMap<Block, Integer> obsidianToRemovalTasks = new HashMap();
    private final HashMap<Block, Integer> removalTime = new HashMap();

    public Obsidian() {
    }

    public static Obsidian getInstance() {
        if (instance == null) {
            instance = new Obsidian();
        }

        return instance;
    }

    @EventHandler(
        priority = EventPriority.MONITOR
    )
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!RegionManager.getInstance().locationIsInRegion(event.getBlockPlaced().getLocation(), RegionType.SPAWN)) {
            if (event.getBlockPlaced().getType() == Material.OBSIDIAN) {
                this.obsidianToRemovalTasks.put(event.getBlockPlaced(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                    this.removalTime.put(event.getBlockPlaced(), (Integer)this.removalTime.getOrDefault(event.getBlockPlaced(), 120) - 1);
                    if ((Integer)this.removalTime.get(event.getBlockPlaced()) <= 0) {
                        Bukkit.getServer().getScheduler().cancelTask((Integer)this.obsidianToRemovalTasks.get(event.getBlockPlaced()));
                        this.obsidianToRemovalTasks.remove(event.getBlockPlaced());
                        this.removalTime.remove(event.getBlockPlaced());
                        event.getBlockPlaced().setType(Material.AIR);
                    }

                }, 0L, 20L));
            }

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.OBSIDIAN) {
            Iterator var2 = this.obsidianToRemovalTasks.keySet().iterator();

            while(var2.hasNext()) {
                Block obsidianBlocks = (Block)var2.next();
                if (obsidianBlocks.equals(event.getBlock())) {
                    Bukkit.getServer().getScheduler().cancelTask((Integer)this.obsidianToRemovalTasks.get(event.getBlock()));
                    this.obsidianToRemovalTasks.remove(event.getBlock());
                    this.removalTime.remove(event.getBlock());
                    break;
                }
            }
        }

    }

    public void removeObsidian() {
        Iterator var1 = this.obsidianToRemovalTasks.keySet().iterator();

        while(var1.hasNext()) {
            Block block = (Block)var1.next();
            block.setType(Material.AIR);
        }

    }
}
