//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListSorter implements Listener {
    private static TabListSorter instance;
    private final HashMap<UUID, Integer> tabTasks = new HashMap();

    public TabListSorter() {
    }

    public static TabListSorter getInstance() {
        if (instance == null) {
            instance = new TabListSorter();
        }

        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.tabTasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
            ((CraftPlayer)player).getHandle().listName = CraftChatMessage.fromString(GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(player) + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getNameColor() + " " + player.getName())[0];
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[]{((CraftPlayer)player).getHandle()}));
            Iterator var3 = player.getWorld().getPlayers().iterator();

            while(var3.hasNext()) {
                Player p = (Player)var3.next();
                ((CraftPlayer)p).getHandle().listName = CraftChatMessage.fromString(GrindingSystem.getInstance().getFormattedPlayerLevelWithoutPrestige(p) + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getNameColor() + " " + p.getName())[0];
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[]{((CraftPlayer)p).getHandle()}));
            }

        }, 20L, 20L));
    }
}
