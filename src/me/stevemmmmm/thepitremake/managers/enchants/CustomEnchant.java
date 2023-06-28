//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.enchants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import me.stevemmmmm.thepitremake.commands.TogglePvPCommand;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.game.RegionManager.RegionType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class CustomEnchant implements Listener {
    private final HashMap<UUID, Boolean> playersToCooldownState = new HashMap();
    private final HashMap<UUID, Long> cooldownTimes = new HashMap();
    private final HashMap<UUID, Integer> cooldownTasks = new HashMap();
    private final HashMap<UUID, Integer> playersToHitsWithEnchant = new HashMap();
    private final HashMap<UUID, Long> hitAmountResetTimes = new HashMap();
    private final HashMap<UUID, Integer> hitAmountResetTasks = new HashMap();

    public CustomEnchant() {
    }

    public abstract void applyEnchant(int var1, Object... var2);

    public abstract String getName();

    public abstract String getEnchantReferenceName();

    public abstract ArrayList<String> getDescription(int var1);

    public abstract boolean isDisabledOnPassiveWorld();

    public abstract EnchantGroup getEnchantGroup();

    public abstract boolean isRareEnchant();

    public abstract Material[] getEnchantItemTypes();

    public boolean isCompatibleWith(Material material) {
        Material[] var2 = this.getEnchantItemTypes();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Material mat = var2[var4];
            if (mat == material) {
                return true;
            }
        }

        return false;
    }

    public boolean attemptEnchantExecution(ItemStack source, Object... args) {
        if (TogglePvPCommand.pvpIsToggledOff) {
            return false;
        } else {
            return itemHasEnchant(source, this) ? this.calculateConditions(source, args) : false;
        }
    }

    public boolean attemptEnchantExecution(ItemStack source, boolean condition, Object... args) {
        if (TogglePvPCommand.pvpIsToggledOff) {
            return false;
        } else if (itemHasEnchant(source, this)) {
            return !condition ? false : this.calculateConditions(source, args);
        } else {
            return false;
        }
    }

    private boolean calculateConditions(ItemStack source, Object[] args) {
        Object[] var3 = args;
        int var4 = args.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object object = var3[var5];
            if (object instanceof Player) {
                Player player = (Player)object;
                if (DamageManager.getInstance().playerIsInCanceledEvent(player)) {
                    return false;
                }

                if (player.getWorld().getName().equals("ThePit_0")) {
                    Iterator var8 = CustomEnchantManager.getInstance().getRawItemEnchants(source).iterator();

                    while(var8.hasNext()) {
                        CustomEnchant enchant = (CustomEnchant)var8.next();
                        if (enchant.isDisabledOnPassiveWorld()) {
                            return false;
                        }
                    }
                }

                if (RegionManager.getInstance().playerIsInRegion(player, RegionType.SPAWN)) {
                    return false;
                }
            }

            if (object instanceof Arrow) {
                Arrow arrow = (Arrow)object;
                if (DamageManager.getInstance().arrowIsInCanceledEvent(arrow)) {
                    return false;
                }

                if (RegionManager.getInstance().locationIsInRegion(arrow.getLocation(), RegionType.SPAWN)) {
                    return false;
                }
            }
        }

        this.applyEnchant(getEnchantLevel(source, this), args);
        return true;
    }

    public boolean getAttemptedEnchantExecutionFeedback(ItemStack source) {
        return false;
    }

    public void startCooldown(Player player, long ticks, boolean isSeconds) {
        if (isSeconds) {
            ticks *= 20L;
        }

        if (!this.cooldownTimes.containsKey(player.getUniqueId())) {
            this.cooldownTimes.put(player.getUniqueId(), ticks);
        }

        if (!this.playersToCooldownState.containsKey(player.getUniqueId())) {
            this.playersToCooldownState.put(player.getUniqueId(), false);
        }

        if (!this.cooldownTasks.containsKey(player.getUniqueId())) {
            this.cooldownTimes.put(player.getUniqueId(), ticks);
            this.cooldownTasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                this.playersToCooldownState.put(player.getUniqueId(), true);
                this.cooldownTimes.put(player.getUniqueId(), (Long)this.cooldownTimes.get(player.getUniqueId()) - 1L);
                if ((float)(Long)this.cooldownTimes.get(player.getUniqueId()) <= 0.0F) {
                    this.playersToCooldownState.put(player.getUniqueId(), false);
                    this.cooldownTimes.put(player.getUniqueId(), 0L);
                    Bukkit.getServer().getScheduler().cancelTask((Integer)this.cooldownTasks.get(player.getUniqueId()));
                    this.cooldownTasks.remove(player.getUniqueId());
                }

            }, 0L, 1L));
        }

    }

    public boolean percentChance(int percent) {
        return ThreadLocalRandom.current().nextInt(0, 100) <= percent;
    }

    public static boolean itemHasEnchant(ItemStack item, CustomEnchant enchant) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.getItemMeta().getLore() == null) {
                return false;
            } else {
                List<String> lore = item.getItemMeta().getLore();
                String appendRare = "";
                if (enchant.isRareEnchant()) {
                    appendRare = ChatColor.LIGHT_PURPLE + "RARE! ";
                }

                if (lore.contains(appendRare + ChatColor.BLUE + enchant.getName())) {
                    return true;
                } else {
                    for(int i = 2; i <= 3; ++i) {
                        if (lore.contains(appendRare + ChatColor.BLUE + enchant.getName() + " " + CustomEnchantManager.getInstance().convertToRomanNumeral(i))) {
                            return true;
                        }
                    }

                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static boolean itemHasEnchant(ItemStack item, CustomEnchant enchant, int level) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.getItemMeta().getLore() == null) {
                return false;
            } else {
                List<String> lore = item.getItemMeta().getLore();
                String appendRare = "";
                if (enchant.isRareEnchant()) {
                    appendRare = ChatColor.LIGHT_PURPLE + "RARE! ";
                }

                return level == 1 ? lore.contains(appendRare + ChatColor.BLUE + enchant.getName()) : lore.contains(appendRare + ChatColor.BLUE + enchant.getName() + " " + CustomEnchantManager.getInstance().convertToRomanNumeral(level));
            }
        } else {
            return false;
        }
    }

    public static int getEnchantLevel(ItemStack item, CustomEnchant enchant) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.getItemMeta().getLore() == null) {
                return 0;
            } else {
                List<String> lore = item.getItemMeta().getLore();
                String appendRare = "";
                if (enchant.isRareEnchant()) {
                    appendRare = ChatColor.LIGHT_PURPLE + "RARE! ";
                }

                if (lore.contains(appendRare + ChatColor.BLUE + enchant.getName())) {
                    return 1;
                } else {
                    for(int i = 2; i <= 3; ++i) {
                        if (lore.contains(appendRare + ChatColor.BLUE + enchant.getName() + " " + CustomEnchantManager.getInstance().convertToRomanNumeral(i))) {
                            return i;
                        }
                    }

                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    public boolean isNotOnCooldown(Player player) {
        if (!this.playersToCooldownState.containsKey(player.getUniqueId())) {
            this.playersToCooldownState.put(player.getUniqueId(), false);
        }

        return !(Boolean)this.playersToCooldownState.get(player.getUniqueId());
    }

    public long getCooldownTime(Player player) {
        if (!this.cooldownTimes.containsKey(player.getUniqueId())) {
            this.cooldownTimes.put(player.getUniqueId(), 0L);
        }

        return (Long)this.cooldownTimes.get(player.getUniqueId()) / 20L;
    }

    public void setCooldownTime(Player player, long ticks, boolean isSeconds) {
        if (isSeconds) {
            ticks *= 20L;
        }

        if (!this.cooldownTimes.containsKey(player.getUniqueId())) {
            this.cooldownTimes.put(player.getUniqueId(), 0L);
        }

        this.cooldownTimes.put(player.getUniqueId(), Math.max(ticks, 0L));
    }

    public void updateHitCount(Player player) {
        if (!this.playersToHitsWithEnchant.containsKey(player.getUniqueId())) {
            this.playersToHitsWithEnchant.put(player.getUniqueId(), 0);
            this.hitAmountResetTimes.put(player.getUniqueId(), 0L);
        }

        this.hitAmountResetTimes.put(player.getUniqueId(), 0L);
        this.playersToHitsWithEnchant.put(player.getUniqueId(), (Integer)this.playersToHitsWithEnchant.get(player.getUniqueId()) + 1);
        this.startHitResetTimer(player);
    }

    public void updateHitCount(Player player, int amount) {
        if (!this.playersToHitsWithEnchant.containsKey(player.getUniqueId())) {
            this.playersToHitsWithEnchant.put(player.getUniqueId(), 1);
        }

        this.playersToHitsWithEnchant.put(player.getUniqueId(), (Integer)this.playersToHitsWithEnchant.get(player.getUniqueId()) + amount);
    }

    public boolean hasRequiredHits(Player player, int hitAmount) {
        if (!this.playersToHitsWithEnchant.containsKey(player.getUniqueId())) {
            this.playersToHitsWithEnchant.put(player.getUniqueId(), 1);
        }

        if ((Integer)this.playersToHitsWithEnchant.get(player.getUniqueId()) >= hitAmount) {
            this.playersToHitsWithEnchant.put(player.getUniqueId(), 0);
            return true;
        } else {
            return false;
        }
    }

    public void startHitResetTimer(Player player) {
        if (!this.hitAmountResetTasks.containsKey(player.getUniqueId())) {
            this.hitAmountResetTasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
                this.hitAmountResetTimes.put(player.getUniqueId(), (Long)this.hitAmountResetTimes.getOrDefault(player.getUniqueId(), 0L) + 1L);
                if ((Long)this.hitAmountResetTimes.get(player.getUniqueId()) >= 5L) {
                    this.playersToHitsWithEnchant.put(player.getUniqueId(), 0);
                    this.hitAmountResetTimes.put(player.getUniqueId(), 0L);
                    Bukkit.getServer().getScheduler().cancelTask((Integer)this.hitAmountResetTasks.get(player.getUniqueId()));
                    this.hitAmountResetTasks.remove(player.getUniqueId());
                }

            }, 0L, 20L));
        } else {
            this.hitAmountResetTimes.put(player.getUniqueId(), 0L);
        }

    }
}
