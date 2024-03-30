//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.commands;

import me.confuser.killstreaks.KillStreaks;
import me.confuser.killstreaks.configs.KillStreak;
import me.confuser.killstreaks.storage.KillStreakPlayer;
import me.confuser.killstreaks.storage.PlayerStorage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StreakCommand implements CommandExecutor {
    public PlayerStorage playerStorage;

    public StreakCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            KillStreakPlayer ksKiller = this.playerStorage.get(player);
            KillStreak streak = ((KillStreaks)KillStreaks.getBukkitPlugin()).getConfiguration().getStreaksConfig().getStreak(ksKiller.getKills().intValue());
            player.sendMessage(ChatColor.GREEN + "Streak: " + "0");
        }

        return true;
    }
}
