package me.stevemmmmm.thepitremake.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PitHelpCommand implements CommandExecutor {
    public PitHelpCommand() {
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("pithelp") && args.length == 0) {
            sendPlayerMessage(player, "§9§lHOW TO START MAKING MYSTICS",
                    "§7-/givefreshitem [bow / sword / color of pants (yellow, orange, blue, red, green)]",
                    "",
                    "§7-With the fresh item in your hand: /pitenchant [name] [tier] (/mysticenchants for the list of all the enchants)",
                    "",
                    "§9§lUSEFUL COMMANDS",
                    "",
                    "§7/givebread - Gives you a stack of bread",
                    "§7/givearrows - Gives you a stack of arrows",
                    "§7/giveprot - Gives you a full diamond Protection 1 set and a Sharpness 1 sword",
                    "§7/giveobsidian - Gives you a stack of obsidian",
                    "§7/setgold - Sets your gold amount to the given number");
        }
        return true;
    }

    private void sendPlayerMessage(Player player, String... messages) {
        for (String message : messages) {
            player.sendMessage(message);
        }
    }
}
