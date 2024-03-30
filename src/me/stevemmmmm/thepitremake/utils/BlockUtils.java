//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockUtils {
    public BlockUtils() {
    }

    public Block getBlockUnderPlayer(Player player) {
        Location loc = player.getLocation().clone().add(0.0, -1.0, 0.0);
        return loc.getBlock();
    }
}
