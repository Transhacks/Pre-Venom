package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class TogglePvPCommand implements CommandExecutor, Listener {

    public static boolean pvpIsToggledOff = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (label.equalsIgnoreCase("togglepvp")) {
            pvpIsToggledOff = !pvpIsToggledOff;
            player.sendMessage(ChatColor.RED + "PvP is turned " + (pvpIsToggledOff ? "off!" : "on!"));
        }

        return true;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (pvpIsToggledOff) {
            DamageManager.getInstance().setEventAsCanceled(event);
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (pvpIsToggledOff) {
            event.setCancelled(true);
        }
    }
}
