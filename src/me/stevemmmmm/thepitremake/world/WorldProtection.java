//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.world;

import me.stevemmmmm.thepitremake.commands.TogglePvPCommand;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class WorldProtection implements Listener {
    public WorldProtection() {
    }

    @EventHandler
    public void onBlockBread(BlockBreakEvent event) {
        if (!event.getPlayer().getName().equalsIgnoreCase("Stevemmmmm") && !event.getPlayer().getName().equalsIgnoreCase("OVEREXERTED") && !event.getPlayer().getName().equalsIgnoreCase("Sundews") && !event.getPlayer().isOp()) {
            if (event.getBlock().getType() != Material.OBSIDIAN) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().getName().equalsIgnoreCase("Stevemmmmm") && !event.getPlayer().getName().equalsIgnoreCase("OVEREXERTED") && !event.getPlayer().getName().equalsIgnoreCase("Sundews") && !event.getPlayer().isOp()) {
            if (event.getBlockPlaced().getType() != Material.OBSIDIAN && event.getBlockPlaced().getType() != Material.COBBLESTONE) {
                event.setCancelled(true);
            } else {
                if (RegionManager.getInstance().locationIsInRegion(event.getBlockPlaced().getLocation(), RegionType.SPAWN) || TogglePvPCommand.pvpIsToggledOff) {
                    event.setCancelled(true);
                }

            }
        }
    }

    @EventHandler
    public void onLiquidPlace(PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.RED + "I see what you are doing over there.");
    }
}
