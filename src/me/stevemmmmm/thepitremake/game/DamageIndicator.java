//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.text.DecimalFormat;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageIndicator implements Listener {
    private static DamageIndicator instance;

    public DamageIndicator() {
    }

    public static DamageIndicator getInstance() {
        if (instance == null) {
            instance = new DamageIndicator();
        }

        return instance;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Player damager;
        Player attacked;
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            damager = (Player)event.getDamager();
            attacked = (Player)event.getEntity();
            if (!RegionManager.getInstance().playerIsInRegion(attacked, RegionType.SPAWN) && DamageManager.getInstance().isEventNotCancelled(event) && !DamageManager.getInstance().playerIsInCanceledEvent(damager)) {
                this.displayIndicator(damager, attacked, DamageManager.getInstance().getFinalDamageFromEvent(event));
            }
        }

        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player && ((Arrow)event.getDamager()).getShooter() instanceof Player) {
            damager = (Player)((Arrow)event.getDamager()).getShooter();
            attacked = (Player)event.getEntity();
            if (!RegionManager.getInstance().playerIsInRegion(attacked, RegionType.SPAWN) && DamageManager.getInstance().isEventNotCancelled(event) && !DamageManager.getInstance().playerIsInCanceledEvent(damager)) {
                this.displayIndicator(damager, attacked, DamageManager.getInstance().getFinalDamageFromEvent(event));
            }
        }

    }

    public void displayIndicator(Player damager, Player attacked, double damage) {
        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + (new IndicatorBuilder(attacked, (int)attacked.getHealth(), damage, (int)attacked.getMaxHealth())).build() + "\"}"), (byte)2);
        ((CraftPlayer)damager).getHandle().playerConnection.sendPacket(packet);
    }

    static class IndicatorBuilder {
        private final StringBuilder output = new StringBuilder();

        public IndicatorBuilder(Player damaged, int originalHealth, double damageTaken, int maxHealth) {
            this.output.append(PermissionsManager.getInstance().getPlayerRank(damaged).getNameColor()).append(damaged.getName()).append(" ");
            EntityPlayer player = ((CraftPlayer)damaged).getHandle();
            int roundedDamageTaken = (int)damageTaken / 2;
            originalHealth /= 2;
            maxHealth /= 2;
            int result = Math.max(originalHealth - roundedDamageTaken, 0);
            int i;
            if (result == 0) {
                roundedDamageTaken = 0;

                for(i = 0; i < originalHealth; ++i) {
                    ++roundedDamageTaken;
                }
            }

            for(i = 0; i < Math.max(originalHealth - roundedDamageTaken, 0); ++i) {
                this.output.append(ChatColor.DARK_RED.toString()).append("❤");
            }

            for(i = 0; i < roundedDamageTaken - (int)player.getAbsorptionHearts() / 2; ++i) {
                this.output.append(ChatColor.RED.toString()).append("❤");
            }

            for(i = originalHealth; i < maxHealth; ++i) {
                this.output.append(ChatColor.BLACK.toString()).append("❤");
            }

            for(i = 0; i < (int)player.getAbsorptionHearts() / 2; ++i) {
                this.output.append(ChatColor.YELLOW.toString()).append("❤");
            }

            this.output.append(ChatColor.RED.toString()).append(" ").append((new DecimalFormat("###0.000")).format(damageTaken / 2.0)).append("HP");
        }

        public String build() {
            return this.output.toString();
        }
    }
}
