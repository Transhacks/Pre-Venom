package me.stevemmmmm.thepitremake.game;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.commands.MuteChatCommand;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class ChatManagement implements Listener {
    private final HashMap<UUID, Integer> tasks = new HashMap<>();
    private final HashMap<UUID, Integer> time = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (hasChatCooldown(playerId) && !canBypassChatCooldown(event.getPlayer())) {
            cancelChatEvent(event, playerId);
            return;
        }

        if (MuteChatCommand.chatIsToggledOff && !canBypassMute(event.getPlayer())) {
            event.getPlayer().sendMessage(Main.prefix + ChatColor.RED + "The chat is currently muted!");
            event.setCancelled(true);
            return;
        }

        formatChatMessage(event);
        startChatCooldown(event.getPlayer());
    }

    private boolean hasChatCooldown(UUID playerId) {
        return tasks.containsKey(playerId);
    }

    private boolean canBypassChatCooldown(Player player) {
        return player.isOp() || player.hasPermission("bhperms.chatcooldownbypass");
    }

    private boolean canBypassMute(Player player) {
        return player.hasPermission("bhperms.mutebypass");
    }

    private void cancelChatEvent(AsyncPlayerChatEvent event, UUID playerId) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(Main.prefix + " Your chat is on cooldown for " + ChatColor.YELLOW + time.get(playerId) + "s" + ChatColor.RED + "!");
    }

    private void formatChatMessage(AsyncPlayerChatEvent event) {
        String message = event.getMessage().replace("%", "%%");
        String playerName = event.getPlayer().getName();
        String customName = event.getPlayer().getCustomName();
        String formattedName = GrindingSystem.getInstance().getFormattedPlayerLevel(event.getPlayer()) +
                " " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getPrefix() +
                " " + PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getNameColor() +
                (customName != null && !customName.equals(playerName) ? customName + " (" + playerName + ")" : playerName) +
                ChatColor.WHITE + ": " +
                PermissionsManager.getInstance().getPlayerRank(event.getPlayer()).getChatColor() +
                message;
        event.setFormat(formattedName);
    }

    private void startChatCooldown(Player player) {
        if (!tasks.containsKey(player.getUniqueId())) {
            tasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                int remainingTime = time.getOrDefault(player.getUniqueId(), 4) - 1;
                time.put(player.getUniqueId(), remainingTime);
                if (remainingTime <= 0) {
                    cancelChatCooldownTask(player);
                }
            }, 0L, 20L));
        }
    }

    private void cancelChatCooldownTask(Player player) {
        Bukkit.getServer().getScheduler().cancelTask(tasks.get(player.getUniqueId()));
        tasks.remove(player.getUniqueId());
        time.remove(player.getUniqueId());
    }
}
