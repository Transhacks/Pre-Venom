package me.stevemmmmm.thepitremake.managers.other;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.comphenix.protocol.wrappers.WrappedServerPing.CompressedImage;

import lol.pitremake.maintenanceapi.events.HypixelMode;
import lol.pitremake.maintenanceapi.events.MaintenanceMode;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class ServerMOTDInitializer implements Listener {
    public static ServerMOTDInitializer INSTANCE;
    private final JavaPlugin plugin;

    public ServerMOTDInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void handlePingAsync(PacketEvent event, WrappedServerPing ping) {
        if (HypixelMode.isActive) {
            try {
                ping.setMotD(ChatColor.GREEN + "             Hypixel Network  " + ChatColor.RED + "[1.8-1.16]" + "\n" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "        SKYBLOCK" + ChatColor.RED.toString() + ", " + ChatColor.AQUA.toString() + ChatColor.BOLD + "BEDWARS" + ChatColor.RED.toString() + ChatColor.BOLD + " + " + ChatColor.GREEN.toString() + ChatColor.BOLD + "MORE");
                ping.setVersionName(MaintenanceMode.isActive ? ChatColor.DARK_RED + "Maintenance" : ping.getVersionName());
                ping.setVersionProtocol(MaintenanceMode.isActive ? 1 : ping.getVersionProtocol());
                ping.setPlayersOnline(ThreadLocalRandom.current().nextInt(114000, 120000));
                ping.setPlayersMaximum(200000);
                ping.setFavicon(CompressedImage.fromPng(getHypixelIcon()));
            } catch (IOException var5) {
                throw new IllegalArgumentException("Cannot access image.", var5);
            }
        } else {
            try {
                ping.setMotD(ChatColor.AQUA.toString() + ChatColor.BOLD + "Fabian's Pit Remake" + ChatColor.DARK_GRAY.toString() + " ▶" + ChatColor.RED + " [1.8-1.16]\n" + ChatColor.DARK_AQUA + "✪" + ChatColor.AQUA + " God Fight Simulator " + ChatColor.DARK_AQUA + "✪ " + ChatColor.RED + "【 " + ChatColor.BLUE + "traficantes.wtf" + ChatColor.RED + " 】");
                ping.setVersionName(MaintenanceMode.isActive ? ChatColor.DARK_RED + "Maintenance" : ping.getVersionName());
                ping.setVersionProtocol(MaintenanceMode.isActive ? 1 : ping.getVersionProtocol());
                ping.setFavicon(CompressedImage.fromPng(getServerIcon()));
            } catch (IOException var4) {
                throw new IllegalArgumentException("Cannot access image.", var4);
            }
        }
    }

    public static ServerMOTDInitializer getInstance(JavaPlugin plugin) {
        if (INSTANCE == null) {
            INSTANCE = new ServerMOTDInitializer(plugin);
        }

        return INSTANCE;
    }

    private BufferedImage getHypixelIcon() throws IOException {
        File imageFile = new File(plugin.getDataFolder(), "hypixel_icon.png");
        return ImageIO.read(imageFile);
    }

    private BufferedImage getServerIcon() throws IOException {
        File imageFile = new File(plugin.getDataFolder(), "server_icon.png");
        return ImageIO.read(imageFile);
    }
}
