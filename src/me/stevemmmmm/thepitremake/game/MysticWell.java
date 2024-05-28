package me.stevemmmmm.thepitremake.game;

import me.stevemmmmm.animationapi.core.Sequence;
import me.stevemmmmm.animationapi.core.SequenceAPI;
import me.stevemmmmm.animationapi.core.SequenceActions;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import me.stevemmmmm.thepitremake.managers.enchants.EnchantGroup;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import me.stevemmmmm.thepitremake.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MysticWell implements Listener {
	private final HashMap<UUID, Inventory> playerGuis = new HashMap<>();

	private final HashMap<UUID, MysticWellAnimation> mysticWellStates = new HashMap<>();
	private final HashMap<UUID, Sequence> mysticWellSequences = new HashMap<>();

	private final ItemStack enchantmentTableInfoIdle = new ItemStack(Material.ENCHANTMENT_TABLE);
	private final ItemStack enchantmentTableInfoT1 = new ItemStack(Material.ENCHANTMENT_TABLE);
	private final ItemStack enchantmentTableInfoT2 = new ItemStack(Material.ENCHANTMENT_TABLE);
	private final ItemStack enchantmentTableInfoT3 = new ItemStack(Material.ENCHANTMENT_TABLE);
	private final ItemStack enchantmentTableInfoItsRollin = new ItemStack(Material.ENCHANTMENT_TABLE);
	private final ItemStack enchantmentTableInfoMaxTier = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);

	private final int[] glassPanes = new int[] { 10, 11, 12, 21, 30, 29, 28, 19 };

	public MysticWell() {
		setEnchantmentTableInfoMeta(enchantmentTableInfoIdle, ChatColor.LIGHT_PURPLE + "Mystic Well",
				Arrays.asList(
						ChatColor.GRAY + "Find a " + ChatColor.AQUA + "Mystic Bow" + ChatColor.GRAY + ", "
								+ ChatColor.YELLOW + "Mystic",
						ChatColor.YELLOW + "Sword" + ChatColor.GRAY + " or " + ChatColor.RED + "P" + ChatColor.GOLD
								+ "a" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "t" + ChatColor.BLUE + "s"
								+ ChatColor.GRAY + " from",
						ChatColor.GRAY + "killing players", " ", ChatColor.GRAY + "Enchant these items in the well",
						ChatColor.GRAY + "for tons of buffs.", " ",
						ChatColor.LIGHT_PURPLE + "Click an item in your inventory!"));

		setEnchantmentTableInfoMeta(enchantmentTableInfoT1, ChatColor.LIGHT_PURPLE + "Mystic Well",
				Arrays.asList(ChatColor.GRAY + "Upgrade:" + ChatColor.GREEN + " Tier I",
						ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 1,000g", " ",
						ChatColor.YELLOW + "Click to upgrade!"));

		setEnchantmentTableInfoMeta(enchantmentTableInfoT2, ChatColor.LIGHT_PURPLE + "Mystic Well",
				Arrays.asList(ChatColor.GRAY + "Upgrade:" + ChatColor.YELLOW + " Tier II",
						ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 4,000g", " ",
						ChatColor.YELLOW + "Click to upgrade!"));

		setEnchantmentTableInfoMeta(enchantmentTableInfoT3, ChatColor.LIGHT_PURPLE + "Mystic Well",
				Arrays.asList(ChatColor.GRAY + "Upgrade:" + ChatColor.RED + " Tier III",
						ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 8,000g", " ",
						ChatColor.YELLOW + "Click to upgrade!"));

		setSimpleItemMeta(enchantmentTableInfoItsRollin, ChatColor.LIGHT_PURPLE + "It's rollin!");

		setEnchantmentTableInfoMeta(enchantmentTableInfoMaxTier, ChatColor.RED + "Mystic Well",
				Arrays.asList(ChatColor.GRAY + "This item cannot be", ChatColor.GRAY + "upgraded any further", " ",
						ChatColor.RED + "Maxed out upgrade tier!"));
	}

	private void setEnchantmentTableInfoMeta(ItemStack item, String displayName, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(displayName);
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
	}

	private void setSimpleItemMeta(ItemStack item, String displayName) {
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(displayName);
			item.setItemMeta(meta);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!playerGuis.containsKey(event.getPlayer().getUniqueId()))
			playerGuis.put(event.getPlayer().getUniqueId(), createMysticWell());
		// TODO Read from inventory API
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (event.getInventory().getType() == InventoryType.ENCHANTING) {
			event.setCancelled(true);

			if (mysticWellSequences.containsKey(event.getPlayer().getUniqueId())) {
				if (mysticWellStates.get(event.getPlayer().getUniqueId()) == MysticWellAnimation.ENCHANTING) {
					SequenceAPI.stopSequence(mysticWellSequences.get(event.getPlayer().getUniqueId()));
				}
			}

			setMysticWellAnimation(((Player) event.getPlayer()), MysticWellAnimation.IDLE);
			event.getPlayer().openInventory(playerGuis.get(event.getPlayer().getUniqueId()));
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equals(ChatColor.GRAY + "Mystic Well")
				&& event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
			event.setCancelled(true);

			Player player = (Player) event.getWhoClicked();
			UUID playerUUID = player.getUniqueId();
			Inventory playerInventory = player.getInventory();
			Inventory gui = playerGuis.get(playerUUID);

			ItemStack clickedItem = event.getCurrentItem();
			ItemStack guiItem = gui.getItem(20);

			if (clickedItem != null && guiItem != null) {
				if (clickedItem.getType() == Material.ENCHANTMENT_TABLE) {
					if (gui.getItem(24).getType() != Material.STAINED_CLAY) {
						handleEnchantClick(player, guiItem);
					}
				}
			}

			handleInventoryItemClick(event, player, playerUUID, gui, playerInventory);
		}
	}

	private void handleEnchantClick(Player player, ItemStack guiItem) {
		int goldAmount = 0;

		switch (getItemTier(guiItem)) {
		case 0:
			goldAmount = 1000;
			break;
		case 1:
			goldAmount = 4000;
			break;
		case 2:
			goldAmount = 8000;
			break;
		}

		if (GrindingSystem.getInstance().getPlayerGold(player) >= goldAmount) {
			GrindingSystem.getInstance().setPlayerGold(player,
					Math.max(0, GrindingSystem.getInstance().getPlayerGold(player) - goldAmount));
			enchantItem(player, guiItem);
		} else {
			player.sendMessage(ChatColor.RED + "Not enough gold!");
		}
	}

	private void handleInventoryItemClick(InventoryClickEvent event, Player player, UUID playerUUID, Inventory gui,
			Inventory playerInventory) {
		ItemStack currentItem = event.getCurrentItem();

		if (event.getRawSlot() == 20) {
			if (currentItem != null && currentItem.getType() != Material.AIR) {
				if (currentItem.getType() == Material.GOLD_SWORD || currentItem.getType() == Material.BOW
						|| currentItem.getType() == Material.LEATHER_LEGGINGS) {
					moveItemToPlayerInventory(player, gui, playerInventory, currentItem, event.getSlot());
				}
			}
		} else if (currentItem != null && currentItem.getType() != Material.AIR && event.getRawSlot() > 36
				&& gui.getItem(20) == null && mysticWellStates.get(playerUUID).equals(MysticWellAnimation.IDLE)) {
			if (currentItem.getItemMeta() != null && currentItem.getItemMeta().getDisplayName() != null) {
				String[] itemTokens = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).split(" ");
				if (itemTokens[0].equalsIgnoreCase("Fresh") || itemTokens[0].equalsIgnoreCase("Mystic")
						|| itemTokens[0].equalsIgnoreCase("Tier")) {
					gui.setItem(24, getInfoFromTier(getItemTier(currentItem)));
					gui.setItem(20, currentItem);
					playerInventory.setItem(event.getSlot(), new ItemStack(Material.AIR));
				}
			}
		}
	}

	private void moveItemToPlayerInventory(Player player, Inventory gui, Inventory playerInventory,
			ItemStack currentItem, int slot) {
		for (int i = 0; i < playerInventory.getSize(); i++) {
			if (playerInventory.getItem(i) == null) {
				gui.setItem(24, enchantmentTableInfoIdle);
				playerInventory.setItem(i, currentItem);
				gui.setItem(slot, new ItemStack(Material.AIR));
				break;
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {

	}

	private void enchantItem(Player player, ItemStack item) {
		if (item == null)
			return;

		if (mysticWellStates.get(player.getUniqueId()) == MysticWellAnimation.ENCHANTING)
			return;

		SequenceAPI.stopSequence(mysticWellSequences.get(player.getUniqueId()));

		mysticWellStates.put(player.getUniqueId(), MysticWellAnimation.ENCHANTING);

		Inventory gui = playerGuis.get(player.getUniqueId());

		ItemStack itemInput = gui.getItem(20);

		ItemStack[] animationItems = new ItemStack[] { new ItemStack(Material.INK_SACK, 1, (byte) 0),
				new ItemStack(Material.INK_SACK, 1, (byte) 1), new ItemStack(Material.INK_SACK, 1, (byte) 2),
				new ItemStack(Material.INK_SACK, 1, (byte) 3), new ItemStack(Material.INK_SACK, 1, (byte) 4),
				new ItemStack(Material.INK_SACK, 1, (byte) 5), new ItemStack(Material.INK_SACK, 1, (byte) 6),
				new ItemStack(Material.INK_SACK, 1, (byte) 7), new ItemStack(Material.INK_SACK, 1, (byte) 8),
				new ItemStack(Material.INK_SACK, 1, (byte) 9), new ItemStack(Material.INK_SACK, 1, (byte) 10),
				new ItemStack(Material.INK_SACK, 1, (byte) 11), new ItemStack(Material.INK_SACK, 1, (byte) 12),
				new ItemStack(Material.INK_SACK, 1, (byte) 13), new ItemStack(Material.INK_SACK, 1, (byte) 14) };
		final int[] position = { 0 };

		Sequence enchantmentSequence = new Sequence().addKeyFrame(0, () -> setGlassPanesToColor(player, "Green"))
				.addKeyFrame(2, () -> setGlassPanesToColor(player, "Gray"))
				.addKeyFrame(4, () -> setGlassPanesToColor(player, "Green"))
				.addKeyFrame(6, () -> setGlassPanesToColor(player, "Gray")).repeatAddKeyFrame(() -> {
					setPaneToPink(player, position[0]);
					gui.setItem(20, animationItems[ThreadLocalRandom.current().nextInt(animationItems.length)]);

					position[0]++;

					if (position[0] + 1 > glassPanes.length) {
						position[0] = 0;
					}
				}, 8, 2, 40).setAnimationActions(new SequenceActions() {

					@Override
					public void onSequenceStart() {
						playerGuis.get(player.getUniqueId()).setItem(24, enchantmentTableInfoItsRollin);
					}

					@Override
					public void onSequenceEnd() {
						addNewEnchantsToItem(itemInput);
						gui.setItem(20, itemInput);
						setMysticWellAnimation(player, MysticWellAnimation.IDLE);
						playerGuis.get(player.getUniqueId()).setItem(24, getInfoFromTier(getItemTier(itemInput)));
					}
				});

		mysticWellSequences.put(player.getUniqueId(), enchantmentSequence);

		SequenceAPI.startSequence(enchantmentSequence);
	}

	private void addNewEnchantsToItem(ItemStack item) {
		int itemTier = getItemTier(item);

		switch (itemTier) {
		case 0:
			handleTier0(item);
			break;
		case 1:
			handleTier1(item);
			break;
		case 2:
			handleTier2(item);
			break;
		}
	}

	private void handleTier0(ItemStack item) {
		int determinant = ThreadLocalRandom.current().nextInt(1, 31);

		if (determinant <= 9) {
			addEnchantFromGroup(item, EnchantGroup.A, 5, 10, 2.25, 2);
		} else if (determinant <= 19) {
			addEnchantFromGroup(item, EnchantGroup.B, 5, 10, 2.25, 2);
		} else if (determinant <= 29) {
			addEnchantFromGroup(item, EnchantGroup.C, 5, 10, 2.25, 2);
		} else {
			addEnchantFromGroup(item, EnchantGroup.RARE, 4, 10, 2, 3);
		}
	}

	private void handleTier1(ItemStack item) {
		int determinant = ThreadLocalRandom.current().nextInt(1, 11);
		CustomEnchant[] buffer = new CustomEnchant[3];
		int numEnchants = 0;

		if (determinant <= 5) {
			numEnchants = addEnchantsForDeterminant5(item, buffer, numEnchants);
		} else {
			numEnchants = addEnchantsForDeterminantElse(item, buffer, numEnchants);
		}

		addEnchantsToItem(item, 5, 20, 2.35, numEnchants, Arrays.copyOf(buffer, numEnchants));
	}

	private void handleTier2(ItemStack item) {
		int tokenDeterminant = ThreadLocalRandom.current().nextInt(1, 16);

		if (tokenDeterminant <= 10) {
			addEnchantFromGroup(item, EnchantGroup.B, 5, 15, 2.75, 3);
		} else if (tokenDeterminant <= 12) {
			addEnchantFromGroup(item, EnchantGroup.C, 5, 15, 2.75, 3);
		} else {
			addEnchantFromGroup(item, EnchantGroup.RARE, 10, 15, 2.75, 3);
		}
	}

	private int addEnchantsForDeterminant5(ItemStack item, CustomEnchant[] buffer, int numEnchants) {
		int tokenDeterminant = ThreadLocalRandom.current().nextInt(1, 19);

		if (tokenDeterminant <= 10 && numEnchants < 3) {
			buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.A, item.getType());
		} else if ((tokenDeterminant >= 17 || tokenDeterminant == 18) && numEnchants < 3) {
			buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.RARE, item.getType());
		}

		if (tokenDeterminant >= 11 && tokenDeterminant <= 15 && numEnchants < 3) {
			buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.C, item.getType());
		}

		if (tokenDeterminant == 16 && numEnchants < 3) {
			buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.AUCTION, item.getType());
		}

		return numEnchants;
	}

	private int addEnchantsForDeterminantElse(ItemStack item, CustomEnchant[] buffer, int numEnchants) {
		int tokenDeterminant = ThreadLocalRandom.current().nextInt(1, 11);

		if (tokenDeterminant <= 5 && numEnchants < 3) {
			buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.B, item.getType());
		} else {
			if (numEnchants < 3) {
				buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.A, item.getType());
			}

			if (numEnchants < 3) {
				buffer[numEnchants++] = getRandomEnchantFromGroup(EnchantGroup.B, item.getType());
			}
		}

		return numEnchants;
	}

	private void addEnchantFromGroup(ItemStack item, EnchantGroup group, int level, int chance, double weight,
			int maxEnchants) {
		CustomEnchant enchant = getRandomEnchantFromGroup(group, item.getType());
		addEnchantsToItem(item, level, chance, weight, maxEnchants, enchant);
	}

	private void addEnchantsToItem(ItemStack item, int minLives, int maxLives, double livesBias, int maxToken,
			CustomEnchant... enchants) {
		CustomEnchantManager manager = CustomEnchantManager.getInstance();
		HashMap<CustomEnchant, Integer> enchantments = manager.getItemEnchants(item);
		int itemTokens = manager.getTokensOnItem(item);

		if (itemTokens >= 9) {
			return;
		}

		if (enchantments.size() == 3) {
			handleFullEnchantmentSlots(item, enchantments, minLives, maxLives, livesBias, itemTokens);
		}

		if (addNewEnchantIfNecessary(item, enchants)) {
			return;
		}

		if (getItemTier(item) == 2 && enchantments.size() == 2) {
			handleTier2Upgrades(item, enchantments, minLives, maxLives, livesBias, itemTokens);
		}

		enchantItem(item, minLives, maxLives, livesBias, maxToken, 0, true, enchants);
	}

	private void handleFullEnchantmentSlots(ItemStack item, HashMap<CustomEnchant, Integer> enchantments, int minLives,
			int maxLives, double livesBias, int itemTokens) {
		List<CustomEnchant> enchantmentsToRemove = new ArrayList<>(enchantments.keySet());
		Collections.shuffle(enchantmentsToRemove);

		for (int i = 0; i < 3; i++) {
			CustomEnchantManager.getInstance().removeEnchant(item, enchantmentsToRemove.get(i));
		}

		List<Map.Entry<CustomEnchant, Integer>> entryList = new ArrayList<>(enchantments.entrySet());
		Collections.shuffle(entryList);
		boolean hasUpgraded = false;

		for (Map.Entry<CustomEnchant, Integer> entry : entryList) {
			int tokens = entry.getValue();
			tokens = tokens == 0 ? 1 : tokens;

			if (tokens == 1) {
				hasUpgraded = processToken1(item, minLives, maxLives, livesBias, itemTokens, entry.getKey(),
						hasUpgraded);
			} else if (tokens == 2) {
				hasUpgraded = processToken2(item, minLives, maxLives, livesBias, itemTokens, entry.getKey(),
						hasUpgraded);
			}
		}
	}

	private boolean processToken1(ItemStack item, int minLives, int maxLives, double livesBias, int itemTokens,
			CustomEnchant enchantment, boolean hasUpgraded) {
		if (!hasUpgraded) {
			if (itemTokens + 2 > 8) {
				if (itemTokens + 1 > 8) {
					enchantItem(item, minLives, maxLives, livesBias, 1, 0, false, enchantment);
				} else {
					enchantItem(item, minLives, maxLives, livesBias, 1, 1, false, enchantment);
					itemTokens += 1;
				}
			} else {
				enchantItem(item, minLives, maxLives, livesBias, 1, 2, false, enchantment);
				itemTokens += 2;
			}
			hasUpgraded = true;
		} else {
			hasUpgraded = handlePotentialUpgrade(item, minLives, maxLives, livesBias, itemTokens, enchantment, 1);
		}
		return hasUpgraded;
	}

	private boolean processToken2(ItemStack item, int minLives, int maxLives, double livesBias, int itemTokens,
			CustomEnchant enchantment, boolean hasUpgraded) {
		if (!hasUpgraded) {
			if (itemTokens + 1 > 8) {
				enchantItem(item, minLives, maxLives, livesBias, 2, 0, false, enchantment);
			} else {
				enchantItem(item, minLives, maxLives, livesBias, 2, 1, false, enchantment);
				itemTokens += 1;
			}
			hasUpgraded = true;
		} else {
			hasUpgraded = handlePotentialUpgrade(item, minLives, maxLives, livesBias, itemTokens, enchantment, 2);
		}
		return hasUpgraded;
	}

	private boolean handlePotentialUpgrade(ItemStack item, int minLives, int maxLives, double livesBias, int itemTokens,
			CustomEnchant enchantment, int token) {
		int upgradeAgain = ThreadLocalRandom.current().nextInt(1, 4);
		if (upgradeAgain == 2) {
			int randomUpgradeInt = ThreadLocalRandom.current().nextInt(1, 3);
			if (itemTokens + 2 > 8) {
				if (itemTokens + 1 > 8) {
					enchantItem(item, minLives, maxLives, livesBias, token, 0, false, enchantment);
				} else {
					enchantItem(item, minLives, maxLives, livesBias, token, 1, false, enchantment);
					itemTokens += 1;
				}
			} else {
				enchantItem(item, minLives, maxLives, livesBias, token, randomUpgradeInt, false, enchantment);
				itemTokens += randomUpgradeInt;
			}
		} else {
			enchantItem(item, minLives, maxLives, livesBias, token, 0, false, enchantment);
		}
		return true;
	}

	private boolean addNewEnchantIfNecessary(ItemStack item, CustomEnchant... enchants) {
		CustomEnchantManager manager = CustomEnchantManager.getInstance();
		for (CustomEnchant enchant : enchants) {
			if (manager.itemContainsEnchant(item, enchant)) {
				addNewEnchantsToItem(item);
				return true;
			}
		}
		return false;
	}

	private void handleTier2Upgrades(ItemStack item, HashMap<CustomEnchant, Integer> enchantments, int minLives,
			int maxLives, double livesBias, int itemTokens) {
		int upgradeTier = ThreadLocalRandom.current().nextInt(1, 4);
		if (upgradeTier == 3) {
			List<CustomEnchant> enchantmentsToRemove = new ArrayList<>(enchantments.keySet());
			Collections.shuffle(enchantmentsToRemove);

			for (int i = 0; i < 2; i++) {
				CustomEnchantManager.getInstance().removeEnchant(item, enchantmentsToRemove.get(i));
			}

			List<Map.Entry<CustomEnchant, Integer>> entryList = new ArrayList<>(enchantments.entrySet());
			Collections.shuffle(entryList);
			boolean upgradedOnce = false;

			for (Map.Entry<CustomEnchant, Integer> entry : entryList) {
				int tokens = entry.getValue();
				tokens = tokens == 0 ? 1 : tokens;

				if (upgradedOnce) {
					upgradedOnce = handleUpgradedToken(item, minLives, maxLives, livesBias, itemTokens, entry.getKey(),
							tokens);
				} else {
					upgradedOnce = handleFirstUpgrade(item, minLives, maxLives, livesBias, itemTokens, entry.getKey(),
							tokens);
				}
			}
		}
	}

	private boolean handleUpgradedToken(ItemStack item, int minLives, int maxLives, double livesBias, int itemTokens,
			CustomEnchant enchantment, int tokens) {
		int upgradeRandomNumber = ThreadLocalRandom.current().nextInt(1, 3);
		if (upgradeRandomNumber == 1) {
			if (tokens + 2 > 3 || itemTokens + 2 >= 6) {
				if (tokens + 1 < 3 && itemTokens + 1 < 6) {
					enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);
					itemTokens += 1;
				} else {
					enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
				}
			} else {
				enchantItem(item, minLives, maxLives, livesBias, tokens, 2, false, enchantment);
				itemTokens += 2;
			}
		} else {
			enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
		}
		return true;
	}

	private boolean handleFirstUpgrade(ItemStack item, int minLives, int maxLives, double livesBias, int itemTokens,
			CustomEnchant enchantment, int tokens) {
		if (tokens + 2 > 3 || itemTokens + 2 >= 6) {
			if (tokens + 1 < 3 && itemTokens + 1 < 6) {
				enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);
				itemTokens += 1;
				return true;
			} else {
				enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
			}
		} else {
			int randomUpgradedTierNumber = ThreadLocalRandom.current().nextInt(1, 3);
			enchantItem(item, minLives, maxLives, livesBias, tokens, randomUpgradedTierNumber, false, enchantment);
			itemTokens += randomUpgradedTierNumber;
			return true;
		}
		return false;
	}

	private void enchantItem(ItemStack item, int minLives, int maxLives, double livesBias, int maxToken,
			int extraTokens, boolean useBias, CustomEnchant... enchants) {
		int lives = CustomEnchantManager.getInstance().getItemLives(item)
				+ MathUtils.biasedRandomness(minLives, maxLives, livesBias);

		int tokens = useBias ? MathUtils.biasedRandomness(1, maxToken, 3.5) : maxToken + extraTokens;
		CustomEnchantManager.getInstance().addEnchants(item, tokens, enchants);

		CustomEnchantManager.getInstance().setItemLives(item, lives);
		CustomEnchantManager.getInstance().setMaximumItemLives(item, lives);
	}

	private CustomEnchant getRandomEnchantFromGroup(EnchantGroup group, Material type) {
		List<CustomEnchant> groupEnchants = getCompatibleEnchants(group, type);

		if (groupEnchants.isEmpty()) {
			groupEnchants = getCompatibleEnchantsFromOtherGroups(group, type);
		}

		return groupEnchants.get(ThreadLocalRandom.current().nextInt(groupEnchants.size()));
	}

	private List<CustomEnchant> getCompatibleEnchants(EnchantGroup group, Material type) {
		List<CustomEnchant> groupEnchants = new ArrayList<>();

		for (CustomEnchant enchant : CustomEnchantManager.getInstance().getEnchants()) {
			if (enchant != null && enchant.getEnchantGroup() == group && enchant.isCompatibleWith(type)) {
				groupEnchants.add(enchant);
			}
		}

		return groupEnchants;
	}

	private List<CustomEnchant> getCompatibleEnchantsFromOtherGroups(EnchantGroup excludedGroup, Material type) {
		List<EnchantGroup> nonEmptyGroups = getNonEmptyGroups(excludedGroup, type);

		if (nonEmptyGroups.isEmpty()) {
			return new ArrayList<>();
		}

		EnchantGroup randomGroup = nonEmptyGroups.get(ThreadLocalRandom.current().nextInt(nonEmptyGroups.size()));
		return getCompatibleEnchants(randomGroup, type);
	}

	private List<EnchantGroup> getNonEmptyGroups(EnchantGroup excludedGroup, Material type) {
		List<EnchantGroup> nonEmptyGroups = new ArrayList<>();

		for (EnchantGroup group : EnchantGroup.values()) {
			if (group != excludedGroup && !getCompatibleEnchants(group, type).isEmpty()) {
				nonEmptyGroups.add(group);
			}
		}

		return nonEmptyGroups;
	}

	private void setMysticWellAnimation(Player player, MysticWellAnimation animation) {

		if (animation == MysticWellAnimation.IDLE) {
			if (isPlayerInAnimationState(player, MysticWellAnimation.IDLE)) {
				return;
			}

			final int[] position = { 0 };
			Sequence idleSequence = createIdleSequence(player, position);
			mysticWellStates.put(player.getUniqueId(), MysticWellAnimation.IDLE);
			mysticWellSequences.put(player.getUniqueId(), idleSequence);

			SequenceAPI.startSequence(idleSequence);
		}
	}

	private boolean isPlayerInAnimationState(Player player, MysticWellAnimation animation) {
		return mysticWellStates.containsKey(player.getUniqueId())
				&& mysticWellStates.get(player.getUniqueId()) == animation;
	}

	private Sequence createIdleSequence(Player player, int[] position) {
		return new Sequence().repeatAddKeyFrame(() -> {
			setPaneToPink(player, position[0]);
			position[0]++;

			if (position[0] + 1 > glassPanes.length) {
				position[0] = 0;
			}
		}, 0, 2, 50).loop();
	}

	public static int getItemTier(ItemStack item) {
		ItemMeta meta = item.getItemMeta();

		if (meta != null && meta.getDisplayName() != null) {
			List<String> tokens = Arrays.asList(ChatColor.stripColor(meta.getDisplayName()).split(" "));

			if (tokens.contains("I"))
				return 1;
			if (tokens.contains("II"))
				return 2;
			if (tokens.contains("III"))
				return 3;
			if (tokens.contains("Fresh") || tokens.contains("Mystic"))
				return 0;
		}

		return -1;
	}

	private void setPaneToPink(Player player, int index) {
		setGlassPanesToColor(player, "Gray");
		playerGuis.get(player.getUniqueId()).setItem(glassPanes[index],
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6));
	}

	private void setGlassPanesToColor(Player player, String color) {
		byte colorCode = getColorCode(color);
		for (int glassPane : glassPanes) {
			playerGuis.get(player.getUniqueId()).setItem(glassPane,
					new ItemStack(Material.STAINED_GLASS_PANE, 1, colorCode));
		}
	}

	private byte getColorCode(String color) {
		switch (color.toLowerCase()) {
		case "green":
			return 13;
		case "gray":
			return 7;
		case "pink":
			return 6;
		default:
			throw new IllegalArgumentException("Unknown color: " + color);
		}
	}

	private Inventory createMysticWell() {
		Inventory gui = Bukkit.createInventory(null, 45, ChatColor.GRAY + "Mystic Well");

		ItemStack grayGlassPane = createGrayGlassPane();
		int[] positions = { 10, 11, 12, 19, 21, 28, 29, 30 };
		for (int pos : positions) {
			gui.setItem(pos, grayGlassPane);
		}

		gui.setItem(24, enchantmentTableInfoIdle);
		return gui;
	}

	private ItemStack createGrayGlassPane() {
		ItemStack grayGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta gpMeta = grayGlassPane.getItemMeta();
		gpMeta.setDisplayName(ChatColor.GRAY + "Click an item in your inventory!");
		grayGlassPane.setItemMeta(gpMeta);
		return grayGlassPane;
	}

	private ItemStack getInfoFromTier(int tier) {
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

	enum MysticWellAnimation {
		IDLE, ENCHANTING
	}

}
