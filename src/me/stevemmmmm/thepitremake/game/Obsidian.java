package me.stevemmmmm.thepitremake.game;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.stevemmmmm.permissions.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;

public class Obsidian implements Listener {
    private static Obsidian instance;
    private final HashMap<Block, Integer> obsidianToRemovalTasks = new HashMap<>();
    private final HashMap<Block, Integer> removalTime = new HashMap<>();

    private Obsidian() {
    }

    public static Obsidian getInstance() {
        if (instance == null) {
            instance = new Obsidian();
        }
        return instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!RegionManager.getInstance().locationIsInRegion(event.getBlockPlaced().getLocation(), RegionType.SPAWN)) {
            if (event.getBlockPlaced().getType() == Material.OBSIDIAN) {
                Block obsidian = event.getBlockPlaced();
                this.obsidianToRemovalTasks.put(obsidian, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                    this.removalTime.put(obsidian, this.removalTime.getOrDefault(obsidian, 120) - 1);
                    if (this.removalTime.get(obsidian) <= 0) {
                        Integer taskId = this.obsidianToRemovalTasks.get(obsidian);
                        if (taskId != null) {
                            Bukkit.getServer().getScheduler().cancelTask(taskId);
                            this.obsidianToRemovalTasks.remove(obsidian);
                            this.removalTime.remove(obsidian);
                            if (hasAdjacentWater(obsidian)) {
                                obsidian.setType(Material.WATER);
                            } else {
                                obsidian.setType(Material.AIR);
                            }
                        }
                    }
                }, 0L, 20L));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.OBSIDIAN) {
            Block obsidian = event.getBlock();
            Bukkit.getServer().getScheduler().cancelTask(this.obsidianToRemovalTasks.get(obsidian));
            this.obsidianToRemovalTasks.remove(obsidian);
            this.removalTime.remove(obsidian);
        }
    }

    public void removeObsidian() {
        Iterator<Block> iterator = this.obsidianToRemovalTasks.keySet().iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (hasAdjacentLava(block)) {
                block.setType(Material.LAVA);
            } else if (hasAdjacentWater(block)) {
                block.setType(Material.WATER);
            } else {
                block.setType(Material.AIR);
            }
            iterator.remove();
        }
    }

    private boolean hasAdjacentWater(Block block) {
        Block[] adjacentBlocks = {
            block.getRelative(1, 0, 0), block.getRelative(-1, 0, 0),
            block.getRelative(0, 0, 1), block.getRelative(0, 0, -1)
        };
        for (Block adjacentBlock : adjacentBlocks) {
            if (adjacentBlock.getType() == Material.WATER || adjacentBlock.getType() == Material.STATIONARY_WATER) {
                return true;
            }
        }
        return false;
    }
    private boolean hasAdjacentLava(Block block) {
        Block[] adjacentBlocks = {
            block.getRelative(1, 0, 0), block.getRelative(-1, 0, 0),
            block.getRelative(0, 0, 1), block.getRelative(0, 0, -1)
        };
        for (Block adjacentBlock : adjacentBlocks) {
            if (adjacentBlock.getType() == Material.LAVA || adjacentBlock.getType() == Material.STATIONARY_LAVA) {
                return true;
            }
        }
        return false;
    }
}
