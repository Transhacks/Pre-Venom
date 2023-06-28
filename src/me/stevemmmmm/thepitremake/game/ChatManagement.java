//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.HashMap;
import java.util.UUID;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.commands.MuteChatCommand;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManagement implements Listener {
    private final HashMap<UUID, Integer> tasks = new HashMap();
    private final HashMap<UUID, Integer> time = new HashMap();

    public ChatManagement() {
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (this.tasks.containsKey(event.getPlayer().getUniqueId()) && !event.getPlayer().isOp() && !event.getPlayer().hasPermission("bhperms.chatcooldownbypass")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Main.prefix + " Your chat is on cooldown for " + ChatColor.YELLOW + this.time.get(event.getPlayer().getUniqueId()) + "s" + ChatColor.RED + "!");
        } else if (MuteChatCommand.chatIsToggledOff && !event.getPlayer().hasPermission("bhperms.mutebypass")) {
            event.getPlayer().sendMessage(Main.prefix + ChatColor.RED + "The chat is currently muted!");
            event.setCancelled(true);
        } else {
            String message = event.getMessage();
            message = message.replace("%", "%%");
            if (event.getPlayer().getCustomName() != null && !event.getPlayer().getCustomName().equals(event.getPlayer().getName())) {
                event.setFormat(GrindingSystem.getInstance().getFormattedPlayerLevel(event.getPlayer()) + " " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getPrefix() + " " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getNameColor() + event.getPlayer().getCustomName() + " (" + event.getPlayer().getName() + ")" + ChatColor.WHITE + ": " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getChatColor() + message);
            } else {
                event.setFormat(GrindingSystem.getInstance().getFormattedPlayerLevel(event.getPlayer()) + " " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getPrefix() + " " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getNameColor() + event.getPlayer().getName() + ChatColor.WHITE + ": " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getChatColor() + message);
            }

            if (!this.tasks.containsKey(event.getPlayer().getUniqueId())) {
                this.tasks.put(event.getPlayer().getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                    this.time.put(event.getPlayer().getUniqueId(), (Integer)this.time.getOrDefault(event.getPlayer().getUniqueId(), 4) - 1);
                    if ((Integer)this.time.get(event.getPlayer().getUniqueId()) <= 0) {
                        Bukkit.getServer().getScheduler().cancelTask((Integer)this.tasks.get(event.getPlayer().getUniqueId()));
                        this.tasks.remove(event.getPlayer().getUniqueId());
                        this.time.remove(event.getPlayer().getUniqueId());
                    }

                }, 0L, 20L));
            }

        }
    }
}
