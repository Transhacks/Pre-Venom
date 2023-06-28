//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
    public static boolean pvpIsToggledOff;

    public TogglePvPCommand() {
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

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (s.equalsIgnoreCase("togglepvp") && player.isOp()) {
                pvpIsToggledOff = !pvpIsToggledOff;
                player.sendMessage(ChatColor.RED + "PvP is turned " + (pvpIsToggledOff ? "off!" : "on!"));
            }
        }

        return true;
    }
}
