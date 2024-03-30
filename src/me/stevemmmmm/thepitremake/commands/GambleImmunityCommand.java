//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import java.util.ArrayList;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GambleImmunityCommand implements CommandExecutor {
    public final ArrayList<UUID> gambleImmunedPlayer = new ArrayList();
    public static GambleImmunityCommand INSTANCE;

    public GambleImmunityCommand() {
    }

    public static GambleImmunityCommand getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GambleImmunityCommand();
        }

        return INSTANCE;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("gambleimmunity") && player.isOp()) {
                if (this.gambleImmunedPlayer.contains(player.getUniqueId())) {
                    this.gambleImmunedPlayer.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.RED + "You are no longer immune to Gamble!");
                } else {
                    this.gambleImmunedPlayer.add(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "You are now immune to Gamble!");
                }
            }
        }

        return true;
    }
}
