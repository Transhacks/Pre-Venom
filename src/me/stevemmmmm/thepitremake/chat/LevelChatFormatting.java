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
    public LevelChatFormatting() {}

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        Rank playerRank = PermissionsManager.getInstance().getPlayerRank(player);
        String displayName = GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player) + playerRank.getNameColor() + " " + player.getName();

        craftPlayer.getHandle().listName = CraftChatMessage.fromString(displayName)[0];
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[]{entityPlayer}));

        int prestige = GrindingSystem.getInstance().getPlayerPrestige(player);
        GrindingSystem.getInstance().setPlayerPrestige(player, prestige == 0 ? 1 : prestige);
        GrindingSystem.getInstance().setPlayerLevel(player, GrindingSystem.getInstance().getPlayerLevel(player));
    }
}
