//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.chat;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.permissions.core.Rank;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LevelChatFormatting implements Listener {
    public LevelChatFormatting() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Rank playerRank = PermissionsManager.getInstance().getPlayerRank(event.getPlayer());
        ((CraftPlayer)player).getHandle().listName = CraftChatMessage.fromString(GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player) + playerRank.getNameColor() + " " + player.getName())[0];
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[]{((CraftPlayer)player).getHandle()}));
        GrindingSystem.getInstance().setPlayerPrestige(player, GrindingSystem.getInstance().getPlayerPrestige(player));
        GrindingSystem.getInstance().setPlayerLevel(player, GrindingSystem.getInstance().getPlayerLevel(player));
        if (GrindingSystem.getInstance().getPlayerPrestige(player) == 0) {
            GrindingSystem.getInstance().setPlayerPrestige(player, 1);
        }

    }
}
