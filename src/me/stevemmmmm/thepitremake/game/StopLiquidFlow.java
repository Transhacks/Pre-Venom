//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class StopLiquidFlow implements Listener {
    public StopLiquidFlow() {
    }

    @EventHandler
    public void onFlow(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.STATIONARY_WATER || event.getBlock().getType() == Material.STATIONARY_LAVA) {
            event.setCancelled(true);
        }

    }
}
