//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.thepitremake.game.WorldSelection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectWorldCommand implements CommandExecutor {
    public SelectWorldCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("selectworld")) {
                WorldSelection.getInstance().displaySelectionMenu(player);
            }
        }

        return true;
    }
}
