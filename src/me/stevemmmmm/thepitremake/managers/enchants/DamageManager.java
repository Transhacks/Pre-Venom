package me.stevemmmmm.thepitremake.managers.enchants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import me.stevemmmmm.thepitremake.enchants.pants.Mirror;
import me.stevemmmmm.thepitremake.game.CombatManager;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class DamageManager implements Listener {
    private static DamageManager instance;
    private final HashMap<EntityDamageByEntityEvent, Double> additiveDamageBuffer = new HashMap();
    private final HashMap<EntityDamageByEntityEvent, Double> multiplicativeDamageBuffer = new HashMap();
    private final HashMap<EntityDamageByEntityEvent, Double> reductionBuffer = new HashMap();
    private final HashMap<EntityDamageByEntityEvent, Double> absoluteReductionBuffer = new HashMap();
    private final ArrayList<EntityDamageByEntityEvent> canceledEvents = new ArrayList();
    private final ArrayList<EntityDamageByEntityEvent> removeCriticalDamage = new ArrayList();

    private DamageManager() {
    }

    public static DamageManager getInstance() {
        if (instance == null) {
            instance = new DamageManager();
        }

        return instance;
    }

    @EventHandler(
        priority = EventPriority.HIGHEST
    )
    public void onHit(EntityDamageByEntityEvent event) {
        if (this.canceledEvents.contains(event)) {
            event.setCancelled(true);
        } else {
            event.setDamage(this.getDamageFromEvent(event));
        }

        this.canceledEvents.remove(event);
        this.additiveDamageBuffer.remove(event);
        this.multiplicativeDamageBuffer.remove(event);
        this.absoluteReductionBuffer.remove(event);
        this.reductionBuffer.remove(event);
        this.removeCriticalDamage.remove(event);
    }

    public double getDamageFromEvent(EntityDamageByEntityEvent event) {
        double damage = event.getDamage() * (Double)this.multiplicativeDamageBuffer.getOrDefault(event, 1.0);
        
        damage += (Double)this.additiveDamageBuffer.getOrDefault(event, 0.0);
        
        if (damage - (Double)this.absoluteReductionBuffer.getOrDefault(event, 0.0) >= 0.0) {
        	damage -= (Double)this.absoluteReductionBuffer.getOrDefault(event, 0.0);
        }else {
        	damage = 0;
        }
        
        damage *= (Double)this.reductionBuffer.getOrDefault(event, 1.0);
        
        if (this.removeCriticalDamage.contains(event)) {
            damage *= 0.667;
        }

        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS ) {
                damage *= 0.871;
            }
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE && !CustomEnchantManager.getInstance().getItemEnchants(player.getInventory().getChestplate()).isEmpty()) {
                damage *= 0.9;
            }
            if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() == Material.GOLD_HELMET && !CustomEnchantManager.getInstance().getItemEnchants(player.getInventory().getHelmet()).isEmpty()) {
                damage *= 0.9565;
            }
        }

        if (damage <= 0.0) {
            damage = 0.0;
        }

        return damage;
    }

    public double getFinalDamageFromEvent(EntityDamageByEntityEvent event) {
        double damage = event.getFinalDamage() * (Double)this.multiplicativeDamageBuffer.getOrDefault(event, 1.0);
        
        damage += (Double)this.additiveDamageBuffer.getOrDefault(event, 0.0);
        
        if (damage - (Double)this.absoluteReductionBuffer.getOrDefault(event, 0.0) >= 0.0) {
        	damage -= (Double)this.absoluteReductionBuffer.getOrDefault(event, 0.0);
        }else {
        	damage = 0;
        }
        
        damage *= (Double)this.reductionBuffer.getOrDefault(event, 1.0);
        
        if (this.removeCriticalDamage.contains(event)) {
            damage *= 0.667;
        }

        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS ) {
                damage *= 0.871;
            }
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE && !CustomEnchantManager.getInstance().getItemEnchants(player.getInventory().getChestplate()).isEmpty()) {
                damage *= 0.9;
            }
            if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() == Material.GOLD_HELMET && !CustomEnchantManager.getInstance().getItemEnchants(player.getInventory().getHelmet()).isEmpty()) {
            	damage *= 0.9565;
            }
        }

        if (damage <= 0.0) {
            damage = 0;
        }

        return damage;
    }

    public void addDamage(EntityDamageByEntityEvent event, double value, CalculationMode mode) {
        if (mode == CalculationMode.ADDITIVE) {
            this.additiveDamageBuffer.put(event, (Double)this.additiveDamageBuffer.getOrDefault(event, 0.0) + value);
        }

        if (mode == CalculationMode.MULTIPLICATIVE) {
            this.multiplicativeDamageBuffer.put(event, (Double)this.multiplicativeDamageBuffer.getOrDefault(event, 1.0) + value);
        }

    }

    public void setEventAsCanceled(EntityDamageByEntityEvent event) {
        this.canceledEvents.add(event);
    }

    public boolean isEventNotCancelled(EntityDamageByEntityEvent event) {
        return !this.canceledEvents.contains(event);
    }

    public boolean playerIsInCanceledEvent(Player player) {
        Iterator var2 = this.canceledEvents.iterator();

        EntityDamageByEntityEvent event;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            event = (EntityDamageByEntityEvent)var2.next();
            if (event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof Player && ((Projectile)event.getDamager()).getShooter().equals(player)) {
                return true;
            }

            if (event.getDamager() instanceof Player && event.getDamager().equals(player)) {
                return true;
            }
        } while(!(event.getEntity() instanceof Player) || !event.getEntity().equals(player));

        return true;
    }

    public boolean arrowIsInCanceledEvent(Arrow projectile) {
        Iterator var2 = this.canceledEvents.iterator();

        EntityDamageByEntityEvent event;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            event = (EntityDamageByEntityEvent)var2.next();
            if (event.getDamager() instanceof Arrow && event.getDamager().equals(projectile)) {
                return true;
            }
        } while(!(event.getEntity() instanceof Arrow) || !event.getEntity().equals(projectile));

        return true;
    }

    public void reduceDamage(EntityDamageByEntityEvent event, double value) {
        if (!this.reductionBuffer.containsKey(event)) {
            this.reductionBuffer.put(event, 1.0);
        }

        if ((Double)this.reductionBuffer.get(event) == 1.0) {
            this.reductionBuffer.put(event, Math.abs(1.0 - (1.0 * value)));;
        } else {
            this.reductionBuffer.put(event, Math.abs((Double)this.reductionBuffer.get(event) - ((Double)this.reductionBuffer.get(event) * value)));
        }
    }

    public void reduceAbsoluteDamage(EntityDamageByEntityEvent event, double value) {
        this.absoluteReductionBuffer.put(event, (Double)this.absoluteReductionBuffer.getOrDefault(event, 0.0) + value);
    }

    public void removeExtraCriticalDamage(EntityDamageByEntityEvent event) {
        this.removeCriticalDamage.add(event);
    }

    public void doTrueDamage(Player target, double damage) {
        Mirror mirror = new Mirror();
        if (!CustomEnchant.itemHasEnchant(target.getInventory().getLeggings(), mirror)) {
            target.setHealth(Math.max(0.0, target.getHealth() - damage));
            target.damage(0.0);
        }

    }

    public void doTrueDamage(Player target, double damage, Player reflectTo) {
        Mirror mirror = new Mirror();
        CombatManager.getInstance().combatTag(target);
        if (!CustomEnchant.itemHasEnchant(target.getInventory().getLeggings(), mirror)) {
            if (target.getHealth() - damage < 0.0) {
                this.safeSetPlayerHealth(target, 0.0);
            } else {
                target.damage(0.0);
                this.safeSetPlayerHealth(target, target.getHealth() - damage);
            }
        } else if (CustomEnchant.getEnchantLevel(target.getInventory().getLeggings(), mirror) != 1) {
            try {
                if (reflectTo.getHealth() - damage * (double)(Float)mirror.damageReflection.getValueAtLevel(CustomEnchant.getEnchantLevel(target.getInventory().getLeggings(), mirror)) < 0.0) {
                    this.safeSetPlayerHealth(target, 0.0);
                } else {
                    reflectTo.damage(0.0);
                    CombatManager.getInstance().combatTag(target);
                    this.safeSetPlayerHealth(reflectTo, Math.max(0.0, reflectTo.getHealth() - damage * (double)(Float)mirror.damageReflection.getValueAtLevel(CustomEnchant.getEnchantLevel(target.getInventory().getLeggings(), mirror))));
                }
            } catch (NullPointerException var7) {
            }
        }

    }

    public void safeSetPlayerHealth(Player player, double health) {
        if (!RegionManager.getInstance().playerIsInRegion(player, RegionType.SPAWN) && health >= 0.0 && health <= player.getMaxHealth()) {
            player.setHealth(health);
        }

    }

    public boolean isCriticalHit(Player player) {
        return player.getFallDistance() > 0.0F && !player.isOnGround() && player.getLocation().getBlock().getType() != Material.LADDER && player.getLocation().getBlock().getType() != Material.VINE && player.getLocation().getBlock().getType() != Material.STATIONARY_WATER && player.getLocation().getBlock().getType() != Material.STATIONARY_LAVA && player.getLocation().getBlock().getType() != Material.WATER && player.getLocation().getBlock().getType() != Material.LAVA && player.getVehicle() == null && !player.hasPotionEffect(PotionEffectType.BLINDNESS);
    }
}
