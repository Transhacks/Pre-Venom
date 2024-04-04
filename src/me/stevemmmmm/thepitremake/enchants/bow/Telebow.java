//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.enchants.bow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import me.stevemmmmm.thepitremake.managers.enchants.BowManager;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantProperty;
import me.stevemmmmm.thepitremake.managers.enchants.LoreBuilder;
import me.stevemmmmm.thepitremake.utils.TelebowData;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Telebow extends CustomEnchant {
    private final EnchantProperty<Integer> cooldownTimes = new EnchantProperty(new Integer[]{90, 45, 20});
    private final HashMap<UUID, TelebowData> telebowData = new HashMap();

    public Telebow() {
    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow)event.getEntity();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player)arrow.getShooter();
                if (this.telebowData.containsKey(player.getUniqueId()) && itemHasEnchant(((TelebowData)this.telebowData.get(player.getUniqueId())).getBow(), this) && arrow == ((TelebowData)this.telebowData.get(player.getUniqueId())).getArrow() && ((TelebowData)this.telebowData.get(player.getUniqueId())).isSneaking()) {
                    this.attemptEnchantExecution(((TelebowData)this.telebowData.get(player.getUniqueId())).getBow(), new Object[]{player, arrow});
                }
            }
        }

    }

    @EventHandler
    public void onArrowHitPlayer(EntityDamageByEntityEvent event) {
        Arrow arrow;
        Player shooter;
        if (event.getDamager() instanceof Arrow) {
            arrow = (Arrow)event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                shooter = (Player)arrow.getShooter();
                if (itemHasEnchant(shooter.getInventory().getItemInHand(), this) && this.getCooldownTime(shooter) != 0L && shooter.isSneaking()) {
                    PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + ChatColor.YELLOW + "Telebow: " + ChatColor.RED + this.getCooldownTime(shooter) + "s cooldown!" + "\"}"), (byte)2);
                    ((CraftPlayer)shooter).getHandle().playerConnection.sendPacket(packet);
                }

                if (this.telebowData.containsKey(shooter.getUniqueId()) && itemHasEnchant(((TelebowData)this.telebowData.get(shooter.getUniqueId())).getBow(), this) && arrow == ((TelebowData)this.telebowData.get(shooter.getUniqueId())).getArrow() && ((TelebowData)this.telebowData.get(shooter.getUniqueId())).isSneaking()) {
                    this.attemptEnchantExecution(((TelebowData)this.telebowData.get(shooter.getUniqueId())).getBow(), new Object[]{shooter, arrow});
                }
            }
        }

        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            arrow = (Arrow)event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                shooter = (Player)arrow.getShooter();
                if (itemHasEnchant(BowManager.getInstance().getBowFromArrow(arrow), this)) {
                    this.setCooldownTime(shooter, this.getCooldownTime(shooter) - 3L, true);
                }
            }
        }

    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow) {
            Player player = (Player)event.getEntity();
            Arrow arrow = (Arrow)event.getProjectile();
            Location loc = player.getLocation();
            if (itemHasEnchant(player.getInventory().getItemInHand(), this)) {
                if (player.isSneaking()) {
                    this.telebowData.put(player.getUniqueId(), new TelebowData(arrow, player.getInventory().getItemInHand(), true));
                    if (this.isNotOnCooldown(player)) {
                        Main.getInstance().telebowTrail(arrow);
                    }
                } else {
                    this.telebowData.put(player.getUniqueId(), new TelebowData(arrow, player.getInventory().getItemInHand(), false));
                    PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + ChatColor.YELLOW + "Telebow: " + ChatColor.RED + this.getCooldownTime(player) + "s cooldown!" + "\"}"), (byte)2);
                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }

    }

    public void applyEnchant(int level, Object... args) {
        Player player = (Player)args[0];
        Arrow arrow = (Arrow)args[1];
        if (this.isNotOnCooldown(player) && !RegionManager.getInstance().locationIsInRegion(arrow.getLocation(), RegionType.SPAWN)) {
            player.teleport(arrow);
            player.getWorld().playSound(arrow.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 2.0F);
        }

        this.startCooldown(player, (long)(Integer)this.cooldownTimes.getValueAtLevel(level), true);
    }

    public String getName() {
        return "Telebow";
    }

    public String getEnchantReferenceName() {
        return "Telebow";
    }

    public ArrayList<String> getDescription(int level) {
        return (new LoreBuilder()).declareVariable(new String[]{"90s", "45s", "20s"}).write("Sneak to shoot a teleportation").next().write("arrow (").writeVariable(0, level).write(" cooldown, -3 per bow").next().write("hit)").build();
    }

    public boolean isDisabledOnPassiveWorld() {
        return false;
    }

    public EnchantGroup getEnchantGroup() {
        return EnchantGroup.B;
    }

    public boolean isRareEnchant() {
        return true;
    }

    public Material[] getEnchantItemTypes() {
        return new Material[]{Material.BOW};
    }
}
