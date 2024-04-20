package me.stevemmmmm.thepitremake.game;

import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import java.text.DecimalFormat;

public class DamageIndicator implements Listener {
    private static DamageIndicator instance;

    private DamageIndicator() {}

    public static DamageIndicator getInstance() {
        if (instance == null) {
            instance = new DamageIndicator();
        }
        return instance;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    Player attacked = (Player) event.getEntity();

                    if (RegionManager.getInstance().playerIsInRegion(attacked, RegionManager.RegionType.SPAWN) ||
                            !DamageManager.getInstance().isEventNotCancelled(event) ||
                            DamageManager.getInstance().playerIsInCanceledEvent(shooter)) {
                        return;
                    }

                    displayIndicator(shooter, attacked, DamageManager.getInstance().getFinalDamageFromEvent(event));
                }
            }
            return;
        }

        Player damager = (Player) event.getDamager();
        Player attacked = (Player) event.getEntity();

        if (RegionManager.getInstance().playerIsInRegion(attacked, RegionManager.RegionType.SPAWN) ||
                !DamageManager.getInstance().isEventNotCancelled(event) ||
                DamageManager.getInstance().playerIsInCanceledEvent(damager)) {
            return;
        }

        displayIndicator(damager, attacked, DamageManager.getInstance().getFinalDamageFromEvent(event));
    }

    public void displayIndicator(Player damager, Player attacked, double damage) {
        String indicator = new IndicatorBuilder(attacked, attacked.getHealth(), damage, attacked.getMaxHealth()).build();
        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + indicator + "\"}"), (byte) 2);
        ((CraftPlayer) damager).getHandle().playerConnection.sendPacket(packet);
    }

    static class IndicatorBuilder {
        private final StringBuilder output = new StringBuilder();

        public IndicatorBuilder(Player damaged, double originalHealth, double damageTaken, double maxHealth) {
            output.append(PermissionsManager.getInstance().getPlayerRank(damaged).getNameColor()).append(damaged.getName()).append(" ");

            EntityPlayer player = ((CraftPlayer) damaged).getHandle();
            int roundedDamageTaken = (int) damageTaken / 2;
            originalHealth /= 2;
            maxHealth /= 2;
            int result = Math.max((int) originalHealth - roundedDamageTaken, 0);

            if (result == 0) {
                roundedDamageTaken = (int) originalHealth;
            }

            appendHearts(result, ChatColor.DARK_RED);
            appendHearts(roundedDamageTaken - (int) player.getAbsorptionHearts() / 2, ChatColor.RED);
            appendHearts((int) (maxHealth - originalHealth), ChatColor.BLACK);
            appendHearts((int) player.getAbsorptionHearts() / 2, ChatColor.YELLOW);
            output.append(ChatColor.RED).append(" ").append((new DecimalFormat("###0.000")).format(damageTaken / 2.0)).append("HP");
        }

        private void appendHearts(int count, ChatColor color) {
            for (int i = 0; i < count; i++) {
                output.append(color).append("❤");
            }
        }

        public String build() {
            return output.toString();
        }
    }
}
