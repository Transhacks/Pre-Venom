package me.stevemmmmm.thepitremake.commands;

import me.stevemmmmm.permissions.core.PermissionsManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    public NickCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (!cmd.getName().equalsIgnoreCase("nick")) {
            return true;
        }

        if (args.length == 0) {
            resetNickname(player);
        } else {
            setNickname(player, args[0]);
        }

        return true;
    }

    private void resetNickname(Player player) {
        player.setCustomName(player.getName());
        player.sendMessage(ChatColor.GREEN + "Your nickname has been reset successfully!");
    }

    private void setNickname(Player player, String nickname) {
        if (!StringUtils.isAlphanumeric(nickname)) {
            player.sendMessage(ChatColor.RED + "Nickname must be alphanumeric!");
            return;
        }

        if (nickname.length() > 16) {
            player.sendMessage(ChatColor.RED + "Nickname must be less than 16 characters long!");
            return;
        }

        player.setCustomName(nickname);
        player.sendMessage(ChatColor.GREEN + "Your nickname has been set to " + PermissionsManager.getInstance().getPlayerRank(player).getNameColor() + nickname + ChatColor.GREEN + "!");
    }
}
