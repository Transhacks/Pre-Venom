package me.stevemmmmm.thepitremake.commands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;

public class SetXPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /setxp <amount>");
            return true;
        }

        Long amount;
        try {
            amount = Long.parseLong(args[0]);
            if (amount <= 0) {
                sender.sendMessage("Amount must be a positive integer.");
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount. Please enter a valid integer.");
            return true;
        }

        Player player = (Player) sender;
        GrindingSystem.getInstance().setXP(player, amount);

        sender.sendMessage(ChatColor.RED + amount.toString() + ChatColor.GOLD + " XP set.");

        return true;
    }
}

