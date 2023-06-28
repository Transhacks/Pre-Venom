//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.world;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class DeveloperUpdates implements Listener {
    private static final String update = "Fixed some bugs; #update-channel in the discord for more info!";
    private static final String testMessage = "Working on opening the server";
    private static final boolean isTesting = false;

    public DeveloperUpdates() {
    }

    public static void displayUpdate(Player player) {
        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.GOLD + "What's new?" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(20, 100, 20);
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.GREEN + "Fixed some bugs; #update-channel in the discord for more info!" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(20, 100, 20);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitleLength);
    }
}
