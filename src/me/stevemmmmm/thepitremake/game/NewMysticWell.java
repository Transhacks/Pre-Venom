//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import me.stevemmmmm.animationapi.core.Sequence;
import me.stevemmmmm.animationapi.core.SequenceAPI;
import me.stevemmmmm.animationapi.core.SequenceActions;
import me.stevemmmmm.thepitremake.game.MysticWellInventory.MysticWellState;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import me.stevemmmmm.thepitremake.utils.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NewMysticWell implements Listener {
    private final HashMap<UUID, MysticWellInventory> playerMysticWellInventorys = new HashMap();
    private static final ItemStack enchantmentTableInfoIdle;
    private static final ItemStack enchantmentTableInfoT1;
    private static final ItemStack enchantmentTableInfoT2;
    private static final ItemStack enchantmentTableInfoT3;
    private static final ItemStack enchantmentTableInfoItsRollin;
    private static final ItemStack enchantmentTableInfoMaxTier;
    private final int[] glassPanePositions = new int[]{10, 11, 12, 21, 30, 29, 28, 19};

    public NewMysticWell() {
        this.init();
    }

    public void init() {
        ItemMeta etMeta = enchantmentTableInfoIdle.getItemMeta();
        etMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
        etMeta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.GRAY + "Find a " + ChatColor.AQUA + "Mystic Bow" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "Mystic");
                this.add(ChatColor.YELLOW + "Sword" + ChatColor.GRAY + " or " + ChatColor.RED + "P" + ChatColor.GOLD + "a" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "t" + ChatColor.BLUE + "s" + ChatColor.GRAY + " from");
                this.add(ChatColor.GRAY + "killing players");
                this.add(" ");
                this.add(ChatColor.GRAY + "Enchant these items in the well");
                this.add(ChatColor.GRAY + "for tons of buffs.");
                this.add(" ");
                this.add(ChatColor.LIGHT_PURPLE + "Click an item in your inventory!");
            }
        });
        enchantmentTableInfoIdle.setItemMeta(etMeta);
        ItemMeta et1Meta = enchantmentTableInfoT1.getItemMeta();
        et1Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
        et1Meta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.GRAY + "Upgrade:" + ChatColor.GREEN + " Tier I");
                this.add(ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 1,000g");
                this.add(" ");
                this.add(ChatColor.YELLOW + "Click to upgrade!");
            }
        });
        enchantmentTableInfoT1.setItemMeta(et1Meta);
        ItemMeta et2Meta = enchantmentTableInfoT1.getItemMeta();
        et2Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
        et2Meta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.GRAY + "Upgrade:" + ChatColor.YELLOW + " Tier II");
                this.add(ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 4,000g");
                this.add(" ");
                this.add(ChatColor.YELLOW + "Click to upgrade!");
            }
        });
        enchantmentTableInfoT2.setItemMeta(et2Meta);
        ItemMeta et3Meta = enchantmentTableInfoT1.getItemMeta();
        et3Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
        et3Meta.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.GRAY + "Upgrade:" + ChatColor.RED + " Tier III");
                this.add(ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 8,000g");
                this.add(" ");
                this.add(ChatColor.YELLOW + "Click to upgrade!");
            }
        });
        enchantmentTableInfoT3.setItemMeta(et3Meta);
        ItemMeta enirMeta = enchantmentTableInfoItsRollin.getItemMeta();
        enirMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Its' rollin!");
        enchantmentTableInfoItsRollin.setItemMeta(enirMeta);
        ItemMeta etMax = enchantmentTableInfoMaxTier.getItemMeta();
        etMax.setDisplayName(ChatColor.RED + "Mystic Well");
        etMax.setLore(new ArrayList<String>() {
            {
                this.add(ChatColor.GRAY + "This item cannot be");
                this.add(ChatColor.GRAY + "upgraded any further");
                this.add(" ");
                this.add(ChatColor.RED + "Maxed out upgrade tier!");
            }
        });
        enchantmentTableInfoMaxTier.setItemMeta(etMax);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.playerMysticWellInventorys.containsKey(event.getPlayer().getUniqueId())) {
            this.playerMysticWellInventorys.put(event.getPlayer().getUniqueId(), MysticWellInventory.newInventory(event.getPlayer()));
        }

    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENCHANTING) {
            event.setCancelled(true);
            MysticWellInventory inventory = (MysticWellInventory)this.playerMysticWellInventorys.get(event.getPlayer().getUniqueId());
            if (inventory.getSequence() != null && inventory.getState() == MysticWellState.ENCHANTING) {
                SequenceAPI.stopSequence(inventory.getSequence());
            }

            this.setMysticWellToIdle((Player)event.getPlayer());
            event.getPlayer().openInventory(((MysticWellInventory)this.playerMysticWellInventorys.get(event.getPlayer().getUniqueId())).getRawInventory());
            this.updateGui((Player)event.getPlayer());
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(MysticWellInventory.INVENTORY_NAME) && event.getSlotType() != SlotType.OUTSIDE) {
            event.setCancelled(true);
            Player player = (Player)event.getWhoClicked();
            MysticWellInventory inventory = (MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId());
            if (inventory.getState() != MysticWellState.ENCHANTING) {
                if (event.getRawSlot() == 20 && inventory.getItemInEnchantmentSlot() != null) {
                    for(int i = 0; i < player.getInventory().getSize(); ++i) {
                        if (event.getWhoClicked().getInventory().getItem(i) == null) {
                            player.getInventory().setItem(i, event.getCurrentItem());
                            inventory.getRawInventory().setItem(20, new ItemStack(Material.AIR));
                            break;
                        }
                    }
                } else if (this.itemIsEnchantable(event.getCurrentItem()) && event.getRawSlot() != 24) {
                    inventory.getRawInventory().setItem(20, event.getCurrentItem());
                    player.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                }

                if (MysticWellInventory.pressedEnchantButton(event) && inventory.canEnchant() && inventory.getState() == MysticWellState.IDLE) {
                    this.startEnchantmentSequence(player);
                } else {
                    this.updateGui(player);
                }
            }
        }
    }

    private void startEnchantmentSequence(final Player player) {
        final MysticWellInventory inventory = (MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId());
        final ItemStack targetItem = inventory.getItemInEnchantmentSlot();
        SequenceAPI.stopSequence(inventory.getSequence());
        ItemStack[] animationItems = new ItemStack[]{new ItemStack(Material.INK_SACK, 1, (short)0), new ItemStack(Material.INK_SACK, 1, (short)1), new ItemStack(Material.INK_SACK, 1, (short)2), new ItemStack(Material.INK_SACK, 1, (short)3), new ItemStack(Material.INK_SACK, 1, (short)4), new ItemStack(Material.INK_SACK, 1, (short)5), new ItemStack(Material.INK_SACK, 1, (short)6), new ItemStack(Material.INK_SACK, 1, (short)7), new ItemStack(Material.INK_SACK, 1, (short)8), new ItemStack(Material.INK_SACK, 1, (short)9), new ItemStack(Material.INK_SACK, 1, (short)10), new ItemStack(Material.INK_SACK, 1, (short)11), new ItemStack(Material.INK_SACK, 1, (short)12), new ItemStack(Material.INK_SACK, 1, (short)13), new ItemStack(Material.INK_SACK, 1, (short)14)};
        int[] glassPaneIndex = new int[]{0};
        Sequence enchantmentSequence = (new Sequence()).addKeyFrame(0L, () -> {
            this.setGlassPanesToColor(player, "Green");
        }).addKeyFrame(2L, () -> {
            this.setGlassPanesToColor(player, "Gray");
        }).addKeyFrame(4L, () -> {
            this.setGlassPanesToColor(player, "Green");
        }).addKeyFrame(6L, () -> {
            this.setGlassPanesToColor(player, "Gray");
        }).repeatAddKeyFrame(() -> {
            this.setPaneToPink(player, glassPaneIndex[0]);
            inventory.getRawInventory().setItem(20, animationItems[ThreadLocalRandom.current().nextInt(animationItems.length)]);
            int var10002 = glassPaneIndex[0]++;
            if (glassPaneIndex[0] + 1 > this.glassPanePositions.length) {
                glassPaneIndex[0] = 0;
            }

        }, 8L, 2, 40);
        enchantmentSequence.setAnimationActions(new SequenceActions() {
            public void onSequenceStart() {
                ((MysticWellInventory)NewMysticWell.this.playerMysticWellInventorys.get(player.getUniqueId())).getRawInventory().setItem(24, NewMysticWell.enchantmentTableInfoItsRollin);
            }

            public void onSequenceEnd() {
                NewMysticWell.this.updateEnchants(targetItem);
                inventory.getRawInventory().setItem(20, targetItem);
                NewMysticWell.this.setMysticWellToIdle(player);
            }
        });
        inventory.setSequence(enchantmentSequence);
        inventory.setState(MysticWellState.ENCHANTING);
        SequenceAPI.startSequence(enchantmentSequence);
    }

    private void setPaneToPink(Player player, int index) {
        this.setGlassPanesToColor(player, "Gray");
        ((MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId())).getRawInventory().setItem(this.glassPanePositions[index], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6));
    }

    private void setMysticWellToIdle(Player player) {
        MysticWellInventory gui = (MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId());
        if (gui.getSequence() == null || gui.getState() != MysticWellState.IDLE) {
            int[] position = new int[]{0};
            Sequence idleSequence = (new Sequence()).repeatAddKeyFrame(() -> {
                this.setPaneToPink(player, position[0]);
                int var10002 = position[0]++;
                if (position[0] + 1 > this.glassPanePositions.length) {
                    position[0] = 0;
                }

            }, 0L, 2, 50).loop();
            gui.setState(MysticWellState.IDLE);
            gui.setSequence(idleSequence);
            this.updateGui(player);
            SequenceAPI.startSequence(idleSequence);
        }
    }

    private void setGlassPanesToColor(Player player, String color) {
        int[] var3;
        int var4;
        int var5;
        int glassPane;
        if (color.equalsIgnoreCase("Green")) {
            var3 = this.glassPanePositions;
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                glassPane = var3[var5];
                ((MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId())).getRawInventory().setItem(glassPane, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13));
            }
        }

        if (color.equalsIgnoreCase("Gray")) {
            var3 = this.glassPanePositions;
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                glassPane = var3[var5];
                ((MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId())).getRawInventory().setItem(glassPane, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7));
            }
        }

        if (color.equalsIgnoreCase("Pink")) {
            var3 = this.glassPanePositions;
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                glassPane = var3[var5];
                ((MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId())).getRawInventory().setItem(glassPane, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6));
            }
        }

    }

    private boolean itemIsEnchantable(ItemStack item) {
        return CustomEnchantManager.getInstance().getItemTier(item) != -1;
    }

    private ItemStack getIconFromTier(int tier) {
        switch (tier) {
            case 0:
                return enchantmentTableInfoT1;
            case 1:
                return enchantmentTableInfoT2;
            case 2:
                return enchantmentTableInfoT3;
            case 3:
                return enchantmentTableInfoMaxTier;
            default:
                return null;
        }
    }

    private void updateEnchants(ItemStack item) {
    }

    private void addEnchantsToItem(ItemStack item, int minLives, int maxLives, double livesBias, int maxToken, CustomEnchant... enchants) {
        CustomEnchant[] var8 = enchants;
        int var9 = enchants.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            CustomEnchant enchant = var8[var10];
            if (CustomEnchantManager.getInstance().itemContainsEnchant(item, enchant)) {
                this.updateEnchants(item);
                return;
            }
        }

        int lives = CustomEnchantManager.getInstance().getItemLives(item) + MathUtils.biasedRandomness(minLives, maxLives, livesBias);
        CustomEnchantManager.getInstance().addEnchants(item, MathUtils.biasedRandomness(1, maxToken, 3.5), enchants);
        CustomEnchantManager.getInstance().setItemLives(item, lives);
        CustomEnchantManager.getInstance().setMaximumItemLives(item, lives);
    }

    private void updateGui(Player player) {
        MysticWellInventory inventory = (MysticWellInventory)this.playerMysticWellInventorys.get(player.getUniqueId());
        ItemStack item = inventory.getItemInEnchantmentSlot();
        if (item == null) {
            inventory.setEnchantmentButtonIcon(enchantmentTableInfoIdle);
        } else {
            inventory.setEnchantmentButtonIcon(this.getIconFromTier(CustomEnchantManager.getInstance().getItemTier(item)));
        }
    }

    static {
        enchantmentTableInfoIdle = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoT1 = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoT2 = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoT3 = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoItsRollin = new ItemStack(Material.ENCHANTMENT_TABLE);
        enchantmentTableInfoMaxTier = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
    }
}
