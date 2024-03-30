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

/*
 * This class is lazily coded.
 */

/*
 * Copyright (c) 2020. Created by Stevemmmmm.
 */

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
		ItemMeta etMeta = enchantmentTableInfoIdle.getItemMeta();

		etMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
		etMeta.setLore(new ArrayList<String>() {
			{
				add(ChatColor.GRAY + "Find a " + ChatColor.AQUA + "Mystic Bow" + ChatColor.GRAY + ", "
						+ ChatColor.YELLOW + "Mystic");
				add(ChatColor.YELLOW + "Sword" + ChatColor.GRAY + " or " + ChatColor.RED + "P" + ChatColor.GOLD + "a"
						+ ChatColor.YELLOW + "n" + ChatColor.GREEN + "t" + ChatColor.BLUE + "s" + ChatColor.GRAY
						+ " from");
				add(ChatColor.GRAY + "killing players");
				add(" ");
				add(ChatColor.GRAY + "Enchant these items in the well");
				add(ChatColor.GRAY + "for tons of buffs.");
				add(" ");
				add(ChatColor.LIGHT_PURPLE + "Click an item in your inventory!");
			}
		});

		enchantmentTableInfoIdle.setItemMeta(etMeta);

		ItemMeta et1Meta = enchantmentTableInfoT1.getItemMeta();

		et1Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
		et1Meta.setLore(new ArrayList<String>() {
			{
				add(ChatColor.GRAY + "Upgrade:" + ChatColor.GREEN + " Tier I");
				add(ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 1,000g");
				add(" ");
				add(ChatColor.YELLOW + "Click to upgrade!");
			}
		});

		enchantmentTableInfoT1.setItemMeta(et1Meta);

		ItemMeta et2Meta = enchantmentTableInfoT1.getItemMeta();

		et2Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
		et2Meta.setLore(new ArrayList<String>() {
			{
				add(ChatColor.GRAY + "Upgrade:" + ChatColor.YELLOW + " Tier II");
				add(ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 4,000g");
				add(" ");
				add(ChatColor.YELLOW + "Click to upgrade!");
			}
		});

		enchantmentTableInfoT2.setItemMeta(et2Meta);

		ItemMeta et3Meta = enchantmentTableInfoT1.getItemMeta();

		et3Meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mystic Well");
		et3Meta.setLore(new ArrayList<String>() {
			{
				add(ChatColor.GRAY + "Upgrade:" + ChatColor.RED + " Tier III");
				add(ChatColor.GRAY + "Cost:" + ChatColor.GOLD + " 8,000g");
				add(" ");
				add(ChatColor.YELLOW + "Click to upgrade!");
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
				add(ChatColor.GRAY + "This item cannot be");
				add(ChatColor.GRAY + "upgraded any further");
				add(" ");
				add(ChatColor.RED + "Maxed out upgrade tier!");
			}
		});

		enchantmentTableInfoMaxTier.setItemMeta(etMax);
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

			if (playerGuis.get(player.getUniqueId()).getItem(20) != null
					&& playerGuis.get(player.getUniqueId()).getItem(event.getSlot()) != null) {
				if (event.getInventory().getItem(event.getSlot()).getType() == Material.ENCHANTMENT_TABLE) {
					if (playerGuis.get(player.getUniqueId()).getItem(24).getType() != Material.STAINED_CLAY) {
						int goldAmount = 0;

						switch (getItemTier(playerGuis.get(player.getUniqueId()).getItem(20))) {
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
							// TODO Not enough gold

							enchantItem(player, event.getInventory().getItem(20));
						}
					}
				}
			}

			if (event.getRawSlot() == 20) {
				if (event.getCurrentItem().getType() != Material.AIR) {
					if (event.getCurrentItem().getType() == Material.GOLD_SWORD
							|| event.getCurrentItem().getType() == Material.BOW
							|| event.getCurrentItem().getType() == Material.LEATHER_LEGGINGS) {
						for (int i = 0; i < player.getInventory().getSize(); i++) {
							if (event.getWhoClicked().getInventory().getItem(i) == null) {
								playerGuis.get(player.getUniqueId()).setItem(24, enchantmentTableInfoIdle);
								player.getInventory().setItem(i, event.getCurrentItem());
								playerGuis.get(player.getUniqueId()).setItem(event.getSlot(),
										new ItemStack(Material.AIR));
								break;
							}
						}
					}
				}
			} else if (event.getCurrentItem().getType() != Material.AIR && event.getRawSlot() > 36
					&& playerGuis.get(player.getUniqueId()).getItem(20) == null
					&& mysticWellStates.get(player.getUniqueId()).equals(MysticWellAnimation.IDLE)) {
				if (event.getCurrentItem().getItemMeta().getDisplayName() != null) {
					String[] itemTokens = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
							.split(" ");
					if (itemTokens[0].equalsIgnoreCase("Fresh") || itemTokens[0].equalsIgnoreCase("Mystic")
							|| itemTokens[0].equalsIgnoreCase("Tier")) {
						playerGuis.get(player.getUniqueId()).setItem(24,
								getInfoFromTier(getItemTier(event.getCurrentItem())));
						playerGuis.get(player.getUniqueId()).setItem(20, event.getCurrentItem());
						player.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
					}
				}
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
		if (getItemTier(item) == 0) {
			int determinant = ThreadLocalRandom.current().nextInt(0, 30) + 1;

			if (determinant >= 1 && determinant <= 9) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.A, item.getType());
				addEnchantsToItem(item, 5, 10, 2.25, 2, enchant);
			}

			if (determinant >= 10 && determinant <= 19) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.B, item.getType());
				addEnchantsToItem(item, 5, 10, 2.25, 2, enchant);
			}

			if (determinant >= 20 && determinant <= 29) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.C, item.getType());
				addEnchantsToItem(item, 5, 10, 2.25, 2, enchant);
			}

			if (determinant == 30) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.RARE, item.getType());
				addEnchantsToItem(item, 4, 10, 2, 3, enchant);
			}

			return;
		}

		if (getItemTier(item) == 1) {
			int determinant = ThreadLocalRandom.current().nextInt(0, 10) + 1;

			CustomEnchant[] buffer = new CustomEnchant[3];
			int numEnchants = 0;

			if (determinant <= 5) {
				int tokenDeterminant = ThreadLocalRandom.current().nextInt(0, 18) + 1;
				boolean addRare = ThreadLocalRandom.current().nextInt(0, 150) == 0;

				if (tokenDeterminant <= 10 && numEnchants < 3) {
					CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.A, item.getType());
					buffer[numEnchants++] = enchant;
				} else if ((tokenDeterminant == 17 || tokenDeterminant == 18) && numEnchants < 3) {
					CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.RARE, item.getType());
					buffer[numEnchants++] = enchant;
				}

				if (tokenDeterminant >= 11 && tokenDeterminant <= 15 && numEnchants < 3) {
					CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.C, item.getType());
					buffer[numEnchants++] = enchant;
				}

				if (tokenDeterminant == 16 && numEnchants < 3) {
					CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.AUCTION, item.getType());
					buffer[numEnchants++] = enchant;
				}

			} else {
				int order = ThreadLocalRandom.current().nextInt(0, 1);
				int tokenDeterminant = ThreadLocalRandom.current().nextInt(0, 10) + 1;

				if (tokenDeterminant <= 5 && numEnchants < 3) {
					CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.B, item.getType());
					buffer[numEnchants++] = enchant;
				} else {
					if (numEnchants < 3) {
						CustomEnchant enchantA = getRandomEnchantFromGroup(EnchantGroup.A, item.getType());
						buffer[numEnchants++] = enchantA;
					}

					if (numEnchants < 3) {
						CustomEnchant enchantC = getRandomEnchantFromGroup(EnchantGroup.B, item.getType());
						buffer[numEnchants++] = enchantC;
					}
				}
			}

			addEnchantsToItem(item, 5, 20, 2.35, numEnchants, Arrays.copyOf(buffer, numEnchants));
			return;
		}

		if (getItemTier(item) == 2) {
			int tokenDeterminant = ThreadLocalRandom.current().nextInt(0, 15) + 1;

			if (tokenDeterminant <= 10) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.B, item.getType());
				addEnchantsToItem(item, 5, 15, 2.75, 3, enchant);
			} else if (tokenDeterminant >= 11 && tokenDeterminant <= 12) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.C, item.getType());
				addEnchantsToItem(item, 5, 15, 2.75, 3, enchant);
			} else if (tokenDeterminant >= 13) {
				CustomEnchant enchant = getRandomEnchantFromGroup(EnchantGroup.RARE, item.getType());
				addEnchantsToItem(item, 10, 15, 2.75, 3, enchant);
			}
		}
	}

	private void addEnchantsToItem(ItemStack item, int minLives, int maxLives, double livesBias, int maxToken,
			CustomEnchant... enchants) {
		HashMap<CustomEnchant, Integer> enchantments = CustomEnchantManager.getInstance().getItemEnchants(item);

		int itemTokens = CustomEnchantManager.getInstance().getTokensOnItem(item);
		boolean hasUpgraded = false;

		if (itemTokens >= 9) {
			return;
		}

		if (enchantments.size() == 3) {
			List<CustomEnchant> enchantmentsToRemove = new ArrayList<>();
			enchantmentsToRemove.addAll(enchantments.keySet());
			Collections.shuffle(enchantmentsToRemove); // Randomize the order

			for (int i = 0; i < 3; i++) {
				CustomEnchant enchantment = enchantmentsToRemove.get(i);

				CustomEnchantManager.getInstance().removeEnchant(item, enchantment);
			}

			List<Map.Entry<CustomEnchant, Integer>> entryList = new ArrayList<>(enchantments.entrySet());
			Collections.shuffle(entryList);

			for (Map.Entry<CustomEnchant, Integer> entry : entryList) {
				CustomEnchant enchantment = entry.getKey();
				int tokens = entry.getValue();

				if (tokens == 0) {
					tokens = 1;
				}

				if (tokens == 1) {
					if (!hasUpgraded) {
						if (itemTokens + 2 > 8) {
							if (itemTokens + 1 > 8) {
								enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);

								hasUpgraded = true;
							} else {
								enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);

								itemTokens += 1;
								hasUpgraded = true;
							}
						} else {
							enchantItem(item, minLives, maxLives, livesBias, tokens, 2, false, enchantment);

							itemTokens += 2;
							hasUpgraded = true;
						}
					} else {
						int upgradeAgain = ThreadLocalRandom.current().nextInt(1, 4); // Random number between 1 and 3
						if (upgradeAgain == 2) {
							int randomUpgradeInt = ThreadLocalRandom.current().nextInt(1, 3); // Random number between 1
																								// and 2

							if (itemTokens + 2 > 8) {
								if (itemTokens + 1 > 8) {
									enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
								} else {
									enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);

									itemTokens += 1;
								}
							} else {
								enchantItem(item, minLives, maxLives, livesBias, tokens, randomUpgradeInt, false,
										enchantment);

								itemTokens += randomUpgradeInt;
							}
						} else {
							enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
						}
					}
				}

				if (tokens == 2) {
					if (!hasUpgraded) {
						if (itemTokens + 1 > 8) {
							enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);

							hasUpgraded = true;
						} else {
							enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);

							itemTokens += 1;
							hasUpgraded = true;
						}
					} else {
						int upgradeAgain = ThreadLocalRandom.current().nextInt(1, 4); // Random number between 1 and 3
						if (upgradeAgain == 2) {
							if (itemTokens + 1 > 8) {
								enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);

							} else {
								enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);

								itemTokens += 1;
							}
						} else {
							enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
						}
					}
				}
			}

		}

		if (hasUpgraded) {
			return;
		}

		for (CustomEnchant enchant : enchants) {
			if (CustomEnchantManager.getInstance().itemContainsEnchant(item, enchant)) {
				addNewEnchantsToItem(item);
				return;
			}
		}

		if (getItemTier(item) == 2) {
			if (enchantments.size() == 2) {
				int upgradeTier = ThreadLocalRandom.current().nextInt(1, 4); // 1-3
				boolean upgradedOnce = false;

				if (upgradeTier == 3) {
					List<CustomEnchant> enchantmentsToRemove = new ArrayList<>();
					enchantmentsToRemove.addAll(enchantments.keySet());
					Collections.shuffle(enchantmentsToRemove); // Randomize the order

					for (int i = 0; i < 2; i++) {
						CustomEnchant enchantment = enchantmentsToRemove.get(i);

						CustomEnchantManager.getInstance().removeEnchant(item, enchantment);
					}

					List<Map.Entry<CustomEnchant, Integer>> entryList = new ArrayList<>(enchantments.entrySet());
					Collections.shuffle(entryList);

					for (Map.Entry<CustomEnchant, Integer> entry : entryList) {
						CustomEnchant enchantment = entry.getKey();
						int tokens = entry.getValue();

						if (tokens == 0) {
							tokens = 1;
						}

						if (upgradedOnce) {
							int upgradeRandomNumber = ThreadLocalRandom.current().nextInt(1, 3); // 1-2

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
						} else {
							if (tokens + 2 > 3 || itemTokens + 2 >= 6) {
								if (tokens + 1 < 3 && itemTokens + 1 < 6) {
									enchantItem(item, minLives, maxLives, livesBias, tokens, 1, false, enchantment);

									itemTokens += 1;
									upgradedOnce = true;
								} else {
									enchantItem(item, minLives, maxLives, livesBias, tokens, 0, false, enchantment);
								}
							} else {
								int randomUpgradedTierNumber = ThreadLocalRandom.current().nextInt(1, 3); // 1-2

								enchantItem(item, minLives, maxLives, livesBias, tokens, randomUpgradedTierNumber,
										false, enchantment);

								itemTokens += randomUpgradedTierNumber;
								upgradedOnce = true;
							}
						}
					}
				}
			}
		}

		enchantItem(item, minLives, maxLives, livesBias, maxToken, 0, true, enchants);
	}

	private void enchantItem(ItemStack item, int minLives, int maxLives, double livesBias, int maxToken,
			int extraTokens, boolean useBias, CustomEnchant... enchants) {
		int lives = CustomEnchantManager.getInstance().getItemLives(item)
				+ MathUtils.biasedRandomness(minLives, maxLives, livesBias);

		if (useBias) {
			CustomEnchantManager.getInstance().addEnchants(item, MathUtils.biasedRandomness(1, maxToken, 3.5),
					enchants);

		} else {
			CustomEnchantManager.getInstance().addEnchants(item, maxToken + extraTokens, enchants);
		}

		CustomEnchantManager.getInstance().setItemLives(item, lives);
		CustomEnchantManager.getInstance().setMaximumItemLives(item, lives);
	}

	private CustomEnchant getRandomEnchantFromGroup(EnchantGroup group, Material type) {
	    ArrayList<CustomEnchant> groupEnchants = new ArrayList<>();

	    for (CustomEnchant enchant : CustomEnchantManager.getInstance().getEnchants()) {
	        if (enchant != null && enchant.getEnchantGroup() == group && enchant.isCompatibleWith(type)) {
	            groupEnchants.add(enchant);
	        }
	    }

	    if (groupEnchants.size() == 1) {
	        return groupEnchants.get(0);
	    }

	    if (groupEnchants.isEmpty()) {
	        // Find an alternative group
	        EnchantGroup[] allGroups = EnchantGroup.values();
	        List<EnchantGroup> nonEmptyGroups = new ArrayList<>();

	        for (EnchantGroup grp : allGroups) {
	            if (grp != group) { // Exclude the original group
	                boolean groupHasEnchants = false;

	                for (CustomEnchant enchant : CustomEnchantManager.getInstance().getEnchants()) {
	                    if (enchant != null && enchant.getEnchantGroup() == grp && enchant.isCompatibleWith(type)) {
	                        groupHasEnchants = true;
	                        break;
	                    }
	                }

	                if (groupHasEnchants) {
	                    nonEmptyGroups.add(grp);
	                }
	            }
	        }

	        if (!nonEmptyGroups.isEmpty()) {
	            EnchantGroup randomGroup = nonEmptyGroups.get(ThreadLocalRandom.current().nextInt(nonEmptyGroups.size()));

	            for (CustomEnchant enchant : CustomEnchantManager.getInstance().getEnchants()) {
	                if (enchant != null && enchant.getEnchantGroup() == randomGroup && enchant.isCompatibleWith(type)) {
	                    groupEnchants.add(enchant);
	                }
	            }
	        } 
	    }

	    return groupEnchants.get(ThreadLocalRandom.current().nextInt(groupEnchants.size()));
	}

	private void setMysticWellAnimation(Player player, MysticWellAnimation animation) {
		Inventory gui = playerGuis.get(player.getUniqueId());

		if (animation == MysticWellAnimation.IDLE) {
			if (mysticWellStates.containsKey(player.getUniqueId())) {
				if (mysticWellStates.get(player.getUniqueId()) == MysticWellAnimation.IDLE)
					return;
			}

			final int[] position = { 0 };

			Sequence idleSequence = new Sequence().repeatAddKeyFrame(() -> {
				setPaneToPink(player, position[0]);
				position[0]++;

				if (position[0] + 1 > glassPanes.length) {
					position[0] = 0;
				}
			}, 0, 2, 50).loop();

			mysticWellStates.put(player.getUniqueId(), MysticWellAnimation.IDLE);
			mysticWellSequences.put(player.getUniqueId(), idleSequence);

			SequenceAPI.startSequence(idleSequence);
		}
	}

	public static int getItemTier(ItemStack item) {
	    ItemMeta meta = item.getItemMeta();

	    if (meta != null && meta.getDisplayName() != null) {
	        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(ChatColor.stripColor(meta.getDisplayName()).split(" ")));

	        if (tokens.contains("I")) {
	            return 1;
	        } else if (tokens.contains("II")) {
	            return 2;
	        } else if (tokens.contains("III")) {
	            return 3;
	        } else if (tokens.contains("Fresh") || tokens.contains("Mystic")) {
	            return 0;
	        }
	    }

	    return -1;
	}

	private void setPaneToPink(Player player, int index) {
		setGlassPanesToColor(player, "Gray");

		playerGuis.get(player.getUniqueId()).setItem(glassPanes[index],
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6));
	}

	private void setGlassPanesToColor(Player player, String color) {
		if (color.equalsIgnoreCase("Green")) {
			for (int glassPane : glassPanes) {
				playerGuis.get(player.getUniqueId()).setItem(glassPane,
						new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13));
			}
		}

		if (color.equalsIgnoreCase("Gray")) {
			for (int glassPane : glassPanes) {
				playerGuis.get(player.getUniqueId()).setItem(glassPane,
						new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
			}
		}

		if (color.equalsIgnoreCase("Pink")) {
			for (int glassPane : glassPanes) {
				playerGuis.get(player.getUniqueId()).setItem(glassPane,
						new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6));
			}
		}
	}

	private Inventory createMysticWell() {
		Inventory gui = Bukkit.createInventory(null, 45, ChatColor.GRAY + "Mystic Well");

		ItemStack grayGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta gpMeta = grayGlassPane.getItemMeta();

		gpMeta.setDisplayName(ChatColor.GRAY + "Click an item in your inventory!");

		grayGlassPane.setItemMeta(gpMeta);

		gui.setItem(10, grayGlassPane);
		gui.setItem(11, grayGlassPane);
		gui.setItem(12, grayGlassPane);

		gui.setItem(19, grayGlassPane);
		gui.setItem(21, grayGlassPane);

		gui.setItem(24, enchantmentTableInfoIdle);

		gui.setItem(28, grayGlassPane);
		gui.setItem(29, grayGlassPane);
		gui.setItem(30, grayGlassPane);

		return gui;
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
		}

		return null;
	}

	enum MysticWellAnimation {
		IDLE, ENCHANTING
	}
}
