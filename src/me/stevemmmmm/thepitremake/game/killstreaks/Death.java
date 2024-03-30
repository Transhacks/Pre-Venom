package me.stevemmmmm.thepitremake.game.killstreaks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener{
	
	@EventHandler
    public void onDeath(final PlayerDeathEvent event){
        final Player player = event.getEntity();
 
        Killstreak.getInstance().resetKills(player.getUniqueId());
        
 
        if(player.getKiller() != null) {
 
            Killstreak.getInstance().addKill(player.getKiller().getUniqueId());
 
        }
    }
}

