//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChatCommand implements CommandExecutor {
    public static boolean chatIsToggledOff;

    public MuteChatCommand() {
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (s.equalsIgnoreCase("mutechat") && player.hasPermission("bhperms.mutechat")) {
                chatIsToggledOff = !chatIsToggledOff;
                player.sendMessage(Main.prefix + ChatColor.RED + "Chat is turned " + (chatIsToggledOff ? "off!" : "on!"));
                Bukkit.broadcastMessage(ChatColor.GREEN + player.getName().toString() + (chatIsToggledOff ? " muted chat!" : " unmuted chat!"));
            }
        }

        return true;
    }
}
