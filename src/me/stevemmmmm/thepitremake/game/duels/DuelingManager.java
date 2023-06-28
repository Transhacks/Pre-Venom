//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game.duels;

import java.util.ArrayList;
import java.util.Iterator;
import me.stevemmmmm.animationapi.core.Sequence;
import me.stevemmmmm.animationapi.core.SequenceAPI;
import me.stevemmmmm.animationapi.core.SequenceActions;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class DuelingManager implements Listener {
    private static DuelingManager instance;
    private final ArrayList<Duel> activeDuels = new ArrayList();

    private DuelingManager() {
        this.init();
    }

    public static DuelingManager getInstance() {
        if (instance == null) {
            instance = new DuelingManager();
        }

        return instance;
    }

    private void init() {
    }

    public void startDuel(Duel duel) {
        this.activeDuels.add(duel);
        this.initializeDuel(duel);
    }

    public void stopDuel(Player player) {
        if (this.getPlayerDuel(player) != null) {
            this.deInitializeDuel(this.getPlayerDuel(player));
        }
    }

    public Duel getPlayerDuel(Player player) {
        Iterator var2 = this.activeDuels.iterator();

        Duel duel;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            duel = (Duel)var2.next();
        } while(duel.getPlayerA() != player);

        return duel;
    }

    private void initializeDuel(final Duel duel) {
        if (duel.getPlayerAPos() != null && duel.getPlayerBPos() == null) {
        }

        duel.getPlayerA().teleport(new Location(duel.getPlayerA().getWorld(), duel.getPlayerAPos().getX(), duel.getPlayerAPos().getY(), duel.getPlayerAPos().getZ()));
        duel.getPlayerB().teleport(new Location(duel.getPlayerB().getWorld(), duel.getPlayerBPos().getX(), duel.getPlayerBPos().getY(), duel.getPlayerBPos().getZ()));
        SequenceAPI.startSequence(new Sequence() {
            {
                this.addKeyFrame(0L, () -> {
                    DuelingManager.this.sendTitle(duel.getPlayerA(), ChatColor.RED + "Get Ready!", ChatColor.AQUA + "The duel is starting in" + ChatColor.YELLOW + " 3 " + ChatColor.AQUA + "seconds!");
                    DuelingManager.this.sendTitle(duel.getPlayerB(), ChatColor.RED + "Get Ready!", ChatColor.AQUA + "The duel is starting in" + ChatColor.YELLOW + " 3 " + ChatColor.AQUA + "seconds!");
                });
                this.addKeyFrame(20L, () -> {
                    DuelingManager.this.sendTitle(duel.getPlayerA(), ChatColor.RED + "Get Ready!", ChatColor.AQUA + "The duel is starting in" + ChatColor.YELLOW + " 2 " + ChatColor.AQUA + "seconds!");
                    DuelingManager.this.sendTitle(duel.getPlayerB(), ChatColor.RED + "Get Ready!", ChatColor.AQUA + "The duel is starting in" + ChatColor.YELLOW + " 2 " + ChatColor.AQUA + "seconds!");
                });
                this.addKeyFrame(40L, () -> {
                    DuelingManager.this.sendTitle(duel.getPlayerA(), ChatColor.RED + "Get Ready!", ChatColor.AQUA + "The duel is starting in" + ChatColor.YELLOW + " 1 " + ChatColor.AQUA + "seconds!");
                    DuelingManager.this.sendTitle(duel.getPlayerB(), ChatColor.RED + "Get Ready!", ChatColor.AQUA + "The duel is starting in" + ChatColor.YELLOW + " 1 " + ChatColor.AQUA + "seconds!");
                });
                this.addKeyFrame(60L, () -> {
                    DuelingManager.this.sendTitle(duel.getPlayerA(), ChatColor.RED + "FIGHT!", ChatColor.AQUA + "Good luck!");
                    DuelingManager.this.sendTitle(duel.getPlayerB(), ChatColor.RED + "FIGHT!", ChatColor.AQUA + "Good luck!");
                });
                this.setAnimationActions(new SequenceActions() {
                    public void onSequenceStart() {
                    }

                    public void onSequenceEnd() {
                    }
                });
            }
        });
    }

    private void deInitializeDuel(Duel duel) {
    }

    private void sendTitle(Player player, String titleValue, String subTitleValue) {
        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + titleValue + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(0, 60, 20);
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + subTitleValue + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(0, 60, 20);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitleLength);
    }
}
