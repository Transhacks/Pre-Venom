//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.other;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.comphenix.protocol.wrappers.WrappedServerPing.CompressedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import lol.pitremake.maintenanceapi.events.HypixelMode;
import lol.pitremake.maintenanceapi.events.MaintenanceMode;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class ServerMOTDInitializer implements Listener {
    public static ServerMOTDInitializer INSTANCE;

    public ServerMOTDInitializer() {
    }

    public void handlePingAsync(PacketEvent event, WrappedServerPing ping) {
        if (HypixelMode.isActive) {
            try {
                ping.setMotD(ChatColor.GREEN + "             Hypixel Network  " + ChatColor.RED + "[1.8-1.16]" + "\n" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "        SKYBLOCK" + ChatColor.RED.toString() + ", " + ChatColor.AQUA.toString() + ChatColor.BOLD + "BEDWARS" + ChatColor.RED.toString() + ChatColor.BOLD + " + " + ChatColor.GREEN.toString() + ChatColor.BOLD + "MORE");
                ping.setVersionName(MaintenanceMode.isActive ? ChatColor.DARK_RED + "Maintenance" : ping.getVersionName());
                ping.setVersionProtocol(MaintenanceMode.isActive ? 1 : ping.getVersionProtocol());
                ping.setPlayersOnline(ThreadLocalRandom.current().nextInt(114000, 120000));
                ping.setPlayersMaximum(200000);
                ping.setFavicon(CompressedImage.fromPng(this.getHypixelIcon()));
            } catch (IOException var5) {
                throw new IllegalArgumentException("Cannot access image.", var5);
            }
        } else {
            try {
                ping.setMotD(ChatColor.AQUA.toString() + ChatColor.BOLD + "Fabian's Pit Remake" + ChatColor.DARK_GRAY.toString() + " ▶" + ChatColor.RED + " [1.8-1.16]\n" + ChatColor.DARK_AQUA + "✪" + ChatColor.AQUA + " God Fight Simulator " + ChatColor.DARK_AQUA + "✪ " + ChatColor.RED + "【 " + ChatColor.BLUE + "traficantes.wtf" + ChatColor.RED + " 】");
                ping.setVersionName(MaintenanceMode.isActive ? ChatColor.DARK_RED + "Maintenance" : ping.getVersionName());
                ping.setVersionProtocol(MaintenanceMode.isActive ? 1 : ping.getVersionProtocol());
                ping.setFavicon(CompressedImage.fromPng(this.getServerIcon()));
            } catch (IOException var4) {
                throw new IllegalArgumentException("Cannot access image.", var4);
            }
        }

    }

    public static ServerMOTDInitializer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerMOTDInitializer();
        }

        return INSTANCE;
    }

    private BufferedImage getHypixelIcon() throws IOException {
        URL asset = new URL("https://i.imgur.com/MZaqdFT.png");
        Image img = ImageIO.read(asset);
        return this.toBufferedImage(img);
    }

    private BufferedImage getServerIcon() throws IOException {
        URL asset = new URL("https://i.imgur.com/NQdzwoC.png");
        Image img = ImageIO.read(asset);
        return this.toBufferedImage(img);
    }

    private BufferedImage toBufferedImage(Image image) {
        BufferedImage buffer = new BufferedImage(image.getWidth((ImageObserver)null), image.getHeight((ImageObserver)null), 2);
        Graphics2D g = buffer.createGraphics();
        g.drawImage(image, (AffineTransform)null, (ImageObserver)null);
        return buffer;
    }
}
