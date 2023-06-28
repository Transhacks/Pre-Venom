//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PitHelpCommand implements CommandExecutor {
    public PitHelpCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (label.equalsIgnoreCase("pithelp") && args.length == 0) {
                player.sendMessage("§9§lHOW TO START MAKING MYSTICS");
                player.sendMessage("§7-/givefreshitem [bow / sword / color of pants (yellow, orange, blue, red, green)]");
                player.sendMessage(" ");
                player.sendMessage(" ");
                player.sendMessage("§7-With the fresh item in your hand : /pitenchant [name] [tier] (/mysticenchants for the list of all the enchants)");
                player.sendMessage(" ");
                player.sendMessage(" ");
                player.sendMessage("§9§lUSEFUL COMMANDS");
                player.sendMessage(" ");
                player.sendMessage(" ");
                player.sendMessage("§7/givebread - Gives you a stack of bread");
                player.sendMessage("§7/givearrows - Gives you a stack of arrows");
                player.sendMessage("§7/giveprot - Gives you a full diamond Protection 1 set and a Sharpness 1 sword");
                player.sendMessage("§7/giveobsidian - Gives you a stack of obsidian");
                player.sendMessage("§7/setgold - Sets your gold amount to the given number");
            }
        }

        return true;
    }
}
