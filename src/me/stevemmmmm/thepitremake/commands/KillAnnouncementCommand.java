/*    */ package me.stevemmmmm.thepitremake.commands;
/*    */ 
/*    */ import de.themoep.ShowItem.api.ItemConverter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.logging.Level;
/*    */ import net.md_5.bungee.api.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.PlayerDeathEvent;
/*    */ 
/*    */ public class KillAnnouncementCommand implements CommandExecutor, Listener {
/* 18 */   public final ArrayList<Player> playerWantKillAnnouncements = new ArrayList<>();
/*    */   
/*    */   public static KillAnnouncementCommand INSTANCE;
/*    */   
/*    */   public ItemConverter itemConverter;
/*    */   
/*    */   public static KillAnnouncementCommand getInstance() {
/* 23 */     if (INSTANCE == null)
/* 24 */       INSTANCE = new KillAnnouncementCommand(); 
/* 26 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 32 */     if (sender instanceof Player) {
/* 33 */       Player player = (Player)sender;
/* 34 */       if (label.equalsIgnoreCase("killannouncements") || label.equalsIgnoreCase("ka"))
/* 36 */         if (this.playerWantKillAnnouncements.contains(player)) {
/* 37 */           this.playerWantKillAnnouncements.remove(player);
/* 38 */           player.sendMessage(ChatColor.RED + "You can no longer see kill announcements!");
/*    */         } else {
/* 40 */           this.playerWantKillAnnouncements.add(player);
/* 41 */           player.sendMessage(ChatColor.GREEN + "You can now see kill announcements!");
/*    */         }  
/*    */     } 
/* 48 */     return true;
/*    */   }
/*    */   
			@EventHandler
			public void onKill(PlayerDeathEvent event) {
			    Player victim = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
			    Player killer = victim != null ? victim.getKiller() : null;
			    if (killer != null) {
			        for (Player players : this.playerWantKillAnnouncements) {
			            players.sendMessage(ChatColor.DARK_RED + killer.getName() + ChatColor.RED + " killed " + ChatColor.DARK_RED + victim.getName() + ChatColor.RED + " with their " + this.itemConverter.itemToJson(killer.getInventory().getItemInHand(), Level.ALL)); 
			        }
			    }
			}
		}