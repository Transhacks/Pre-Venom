//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.enchants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.utils.SortCustomEnchantByName;
import me.stevemmmmm.thepitremake.commands.GiveFreshItemCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CustomEnchantManager {
	private static CustomEnchantManager instance;
	private final ArrayList<CustomEnchant> enchants = new ArrayList();

	private CustomEnchantManager() {
	}

	public static CustomEnchantManager getInstance() {
		if (instance == null) {
			instance = new CustomEnchantManager();
		}

		return instance;
	}

	public ArrayList<CustomEnchant> getEnchants() {
		return this.enchants;
	}

	public void registerEnchant(CustomEnchant enchant) {
		Main.INSTANCE.getServer().getPluginManager().registerEvents(enchant, Main.INSTANCE);
		this.enchants.add(enchant);
		this.enchants.sort(new SortCustomEnchantByName());
	}

	public boolean playerEnchantProcIsNotCanceled(Player player) {
		Iterator var2 = this.getEnchants().iterator();

		while (var2.hasNext()) {
			CustomEnchant enchant = (CustomEnchant) var2.next();
			if (enchant instanceof EnchantCanceler) {
				EnchantCanceler cancelEnchant = (EnchantCanceler) enchant;
				if (cancelEnchant.isCanceled(player)) {
					return false;
				}
			}
		}

		return true;
	}

	public void addEnchants(ItemStack item, int level, CustomEnchant... enchants) {
		boolean itemHasAlreadyTieredUp = false;
		CustomEnchant[] var5 = enchants;
		int var6 = enchants.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			CustomEnchant enchant = var5[var7];
			ItemMeta meta = item.getItemMeta();
			String previousDisplayName = meta.getDisplayName();
			int tierValue = this.getItemTier(item);
			if (tierValue > 2) {
				tierValue = 2;
			}

			String tier = this.convertToRomanNumeral(tierValue + (itemHasAlreadyTieredUp ? 0 : 1));
			ChatColor tierColor = null;
			switch (tierValue + 1) {
			case 1:
				tierColor = ChatColor.GREEN;
				break;
			case 2:
				tierColor = ChatColor.YELLOW;
				break;
			case 3:
				tierColor = ChatColor.RED;
			}

			String itemIndentifier = "";
			if (this.getTokensOnItem(item) == 8) {
				itemIndentifier = "Legendary ";
			}

			if (this.getItemLives(item) == 100) {
				itemIndentifier = "Artifact ";
			}

			switch (item.getType()) {
			case GOLD_SWORD:
				meta.setDisplayName(tierColor + itemIndentifier + "Tier " + tier + " Sword");
				break;
			case BOW:
				meta.setDisplayName(tierColor + itemIndentifier + "Tier " + tier + " Bow");
				break;
			case LEATHER_LEGGINGS:
				if (this.getItemTier(item) == 0) {
					meta.setDisplayName(
							this.getChatColorFromPantsColor(ChatColor.stripColor(meta.getDisplayName().split(" ")[1]))
									+ itemIndentifier + "Tier " + tier + " Pants");
				} else {
					meta.setDisplayName(
							meta.getDisplayName().substring(0, 2) + itemIndentifier + "Tier " + tier + " Pants");
				}
			}

			String rare = ChatColor.LIGHT_PURPLE + "RARE! " + ChatColor.BLUE + enchant.getName()
					+ (level != 1 ? " " + this.convertToRomanNumeral(level) : "");
			String normal = ChatColor.BLUE + enchant.getName()
					+ (level != 1 ? " " + this.convertToRomanNumeral(level) : "");
			List<String> enchantLore = enchant.getDescription(level);
			if (enchantLore == null) {
				return;
			}

			enchantLore.add(0, enchant.isRareEnchant() ? rare : normal);
			if (meta.getLore() != null) {
				enchantLore.add(0, " ");
			}

			List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList();
			if (previousDisplayName != null && item.getType() == Material.LEATHER_LEGGINGS
					&& ChatColor.stripColor(previousDisplayName.split(" ")[0]).equalsIgnoreCase("Fresh")) {
				lore = new ArrayList();
				((List) lore).add(ChatColor.GRAY + "Lives: " + ChatColor.GREEN + "69" + ChatColor.GRAY + "/420");
				((List) lore).add(" ");
				meta.setDisplayName(
						this.getChatColorFromPantsColor(ChatColor.stripColor(previousDisplayName.split(" ")[1]))
								+ meta.getDisplayName());
				enchantLore.remove(0);
			}

			if (previousDisplayName != null && item.getType() == Material.GOLD_SWORD
					&& ChatColor.stripColor(previousDisplayName.split(" ")[0]).equalsIgnoreCase("Mystic")) {
				lore = new ArrayList();
				((List) lore).add(ChatColor.GRAY + "Lives: " + ChatColor.GREEN + "69" + ChatColor.GRAY + "/420");
				((List) lore).add(" ");
				meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
				enchantLore.remove(0);
			}

			if (previousDisplayName != null && item.getType() == Material.BOW
					&& ChatColor.stripColor(previousDisplayName.split(" ")[0]).equalsIgnoreCase("Mystic")) {
				lore = new ArrayList();
				((List) lore).add(ChatColor.GRAY + "Lives: " + ChatColor.GREEN + "69" + ChatColor.GRAY + "/420");
				((List) lore).add(" ");
				meta.addEnchant(Enchantment.DURABILITY, 1, true);
				enchantLore.remove(0);
			}

			if (item.getType() == Material.LEATHER_LEGGINGS
					&& ((List) lore).contains(meta.getDisplayName().substring(0, 2) + "As strong as iron")) {
				((List) lore).remove(((List) lore).size() - 1);
				((List) lore).remove(((List) lore).size() - 1);
			}

			((List) lore).addAll(enchantLore);
			if (item.getType() == Material.LEATHER_LEGGINGS) {
				((List) lore).add(" ");
				((List) lore).add(meta.getDisplayName().substring(0, 2) + "As strong as iron");
			}

			meta.setLore((List) lore);
			item.setItemMeta(meta);
			itemHasAlreadyTieredUp = true;
		}

	}

	public int getItemLives(ItemStack item) {
		if (item.getItemMeta().getLore() == null) {
			return 0;
		} else {
			ArrayList<String> keyWords = new ArrayList(
					Arrays.asList(ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ")));
			if (!keyWords.contains("Fresh") && !keyWords.contains("Mystic")) {
				List<String> lore = item.getItemMeta().getLore();
				String livesLine = (String) lore.get(0);
				return Integer.parseInt(ChatColor.stripColor(livesLine.split(" ")[1]).split("/")[0]);
			} else {
				return 0;
			}
		}
	}

	public int getMaximumItemLives(ItemStack item) {
		if (item.getItemMeta().getLore() == null) {
			return 0;
		} else {
			ArrayList<String> keyWords = new ArrayList(
					Arrays.asList(ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ")));
			if (!keyWords.contains("Fresh") && !keyWords.contains("Mystic")) {
				List<String> lore = item.getItemMeta().getLore();
				String livesLine = (String) lore.get(0);
				return Integer.parseInt(ChatColor.stripColor(livesLine.split(" ")[1]).split("/")[1]);
			} else {
				return 0;
			}
		}
	}

	public void setItemLives(ItemStack item, int value) {
		if (item.getItemMeta().getLore() != null) {
			List<String> lore = item.getItemMeta().getLore();
			lore.set(0, ChatColor.GRAY + "Lives: " + (value > 3 ? ChatColor.GREEN : ChatColor.RED) + value
					+ ChatColor.GRAY + "/" + this.getMaximumItemLives(item));
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
	}

	public void setMaximumItemLives(ItemStack item, int value) {
		if (item.getItemMeta().getLore() != null) {
			int lives = this.getItemLives(item);

			if (value > 100) {
				value = 100;
			}

			if (lives > value) {
				lives = value;
			}

			List<String> lore = item.getItemMeta().getLore();
			lore.set(0, ChatColor.GRAY + "Lives: " + (value > 3 ? ChatColor.GREEN : ChatColor.RED) + lives
					+ ChatColor.GRAY + "/" + value);
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
	}

	public void setItemTier(ItemStack item, int tier) {
		ItemMeta meta = item.getItemMeta();
		ChatColor tierColor;

		switch (tier) {
		case 1:
			tierColor = ChatColor.GREEN;
			break;
		case 2:
			tierColor = ChatColor.YELLOW;
			break;
		case 3:
			tierColor = ChatColor.RED;
			break;
		default:
			tierColor = ChatColor.RESET;
			break;
		}

		String itemIdentifier = "";
		if (getTokensOnItem(item) == 8) {
			itemIdentifier = "Legendary ";
		} else if (getItemLives(item) == 100) {
			itemIdentifier = "Artifact ";
		}

		String itemName = "";
		switch (item.getType()) {
		case GOLD_SWORD:
			itemName = "Sword";
			break;
		case BOW:
			itemName = "Bow";
			break;
		case LEATHER_LEGGINGS:
			itemName = "Pants";
			break;
		default:
			itemName = "Item";
			break;
		}

		String displayName = tierColor + itemIdentifier + "Tier " + convertToRomanNumeral(tier) + " " + itemName;
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
	}

	public void DecreaseItemTier(ItemStack item) {
		int currentTier = getItemTier(item);
		ItemMeta meta = item.getItemMeta();

		if (currentTier == 3) {
			setItemTier(item, 2);
		} else if (currentTier == 2) {
			setItemTier(item, 1);
		} else if (currentTier == 1) {
			if (meta != null) {
				switch (item.getType()) {
				case BOW:
					meta.setDisplayName(ChatColor.AQUA + "Mystic Bow");
					meta.setLore((new LoreBuilder()).write("Kept on death").next().next()
							.write("Used in the mystic well").build());
					break;
				case GOLD_SWORD:
					meta.setDisplayName(ChatColor.YELLOW + "Mystic Sword");
					meta.setLore((new LoreBuilder()).write("Kept on death").next().next()
							.write("Used in the mystic well").build());
					break;
				case LEATHER_LEGGINGS:
					break;
				default:
					break;
				}
				item.setItemMeta(meta);
			}
		}
	}

	public void removeEnchant(ItemStack item, CustomEnchant enchant) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();

		if (lore != null) {
			List<String> formattedLore = new ArrayList<>();
			Iterator<String> iterator = lore.iterator();

			while (iterator.hasNext()) {
				String string = iterator.next();
				ArrayList<String> enchantData = new ArrayList<>(Arrays.asList(string.split(" ")));
				StringBuilder enchantName = new StringBuilder();

				if (string.length() < 2) {
					formattedLore.add(" ");
				} else {
					String chatColor = string.substring(0, 2);

					for (int i = 0; i < enchantData.size(); ++i) {
						enchantData.set(i, ChatColor.stripColor(enchantData.get(i)));
					}

					if (enchantData.get(0).equalsIgnoreCase("RARE!")) {
						enchantData.remove(0);
					}

					for (String data : enchantData) {
						if (this.convertRomanNumeralToInteger(data) == 1) {
							enchantName.append(data).append(" ");
						}
					}

					String name = enchantName.toString().trim();
					formattedLore.add(chatColor + name);
				}
			}

			int index = -1;
			for (int i = 0; i < formattedLore.size(); ++i) {
				if (ChatColor.stripColor(formattedLore.get(i)).equalsIgnoreCase(enchant.getName())) {
					index = i;
					break;
				}
			}

			List<String> finalLore = meta.getLore();
			boolean oneBackIndex = false;

			while (!finalLore.get(index).equals(" ")) {
				finalLore.remove(index);
				if (index == finalLore.size()) {
					oneBackIndex = true;
					break;
				}
			}

			if (oneBackIndex) {
				finalLore.remove(index - 1);
			} else {
				finalLore.remove(index);
			}

			meta.setLore(finalLore);
			item.setItemMeta(meta);

			// After removing the enchantment, adjust the item's tier
			DecreaseItemTier(item);
		}
	}

	public boolean itemContainsEnchant(ItemStack item, CustomEnchant enchant) {
		if (item.getItemMeta().getLore() != null && enchant != null) {
			List<String> lore = item.getItemMeta().getLore();
			String appendRare = "";
			if (enchant.isRareEnchant()) {
				appendRare = ChatColor.LIGHT_PURPLE + "RARE! ";
			}

			if (lore.contains(appendRare + ChatColor.BLUE + enchant.getName())) {
				return true;
			} else {
				for (int i = 2; i <= 3; ++i) {
					if (lore.contains(appendRare + ChatColor.BLUE + enchant.getName() + " "
							+ getInstance().convertToRomanNumeral(i))) {
						return true;
					}
				}

				return false;
			}
		} else {
			return false;
		}
	}

	public HashMap<CustomEnchant, Integer> getItemEnchants(ItemStack item) {
		HashMap<CustomEnchant, Integer> enchantsToLevels = new HashMap();
		if (item.getType() == Material.AIR) {
			return enchantsToLevels;
		} else if (item.getItemMeta().getLore() == null) {
			return enchantsToLevels;
		} else {
			Iterator var3 = item.getItemMeta().getLore().iterator();

			while (true) {
				while (true) {
					ArrayList enchantData;
					StringBuilder enchantName;
					int level;
					do {
						if (!var3.hasNext()) {
							return enchantsToLevels;
						}

						String string = (String) var3.next();
						enchantData = new ArrayList(Arrays.asList(string.split(" ")));
						enchantName = new StringBuilder();
						level = 0;
					} while (enchantData.size() == 0);

					int i;
					for (i = 0; i < enchantData.size(); ++i) {
						enchantData.set(i, ChatColor.stripColor((String) enchantData.get(i)));
					}

					if (((String) enchantData.get(0)).equalsIgnoreCase("RARE!")) {
						enchantData.remove(0);
					}

					for (i = 0; i < enchantData.size(); ++i) {
						if (this.convertRomanNumeralToInteger((String) enchantData.get(i)) == 1) {
							enchantName.append((String) enchantData.get(i));
							if (i != enchantData.size() - 1) {
								enchantName.append(" ");
							}
						} else {
							level = this.convertRomanNumeralToInteger((String) enchantData.get(i));
						}
					}

					String name = enchantName.toString().trim();
					Iterator var9 = this.getEnchants().iterator();

					while (var9.hasNext()) {
						CustomEnchant enchant = (CustomEnchant) var9.next();
						if (enchant.getName().equals(name)) {
							enchantsToLevels.put(enchant, level);
							break;
						}
					}
				}
			}
		}
	}

	public int getTokensOnItem(ItemStack item) {
		if (item.getType() != Material.AIR && item.getItemMeta().getLore() != null) {
			int tokens = 0;

			for (Map.Entry<CustomEnchant, Integer> entry : this.getItemEnchants(item).entrySet()) {
				if (entry.getValue() == 0) {
					entry.setValue(1);
				}

				tokens += entry.getValue();
			}

			return tokens;
		} else {
			return 0;
		}
	}

	public List<CustomEnchant> getRawItemEnchants(ItemStack item) {
		if (item == null) {
			return new ArrayList();
		} else {
			ArrayList<CustomEnchant> enchants = new ArrayList();
			Iterator var3 = this.getEnchants().iterator();

			while (var3.hasNext()) {
				CustomEnchant enchant = (CustomEnchant) var3.next();
				if (this.itemContainsEnchant(item, enchant)) {
					enchants.add(enchant);
				}
			}

			return enchants;
		}
	}

	public int getItemTier(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> tokens = new ArrayList(Arrays.asList(ChatColor.stripColor(meta.getDisplayName()).split(" ")));
		if (tokens.contains("I")) {
			return 1;
		} else if (tokens.contains("II")) {
			return 2;
		} else if (tokens.contains("III")) {
			return 3;
		} else {
			return !tokens.contains("Fresh") && !tokens.contains("Mystic") ? -1 : 0;
		}
	}

	public boolean percentChance(double percent) {
		return Double.parseDouble(
				(new DecimalFormat("#0.0")).format(ThreadLocalRandom.current().nextDouble(0.0, 99.0))) <= percent;
	}

	public String convertToRomanNumeral(int value) {
		if (value < 1 || value > 70) {
			return null;
		}

		String[] units = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
		String[] tens = { "", "X", "XX", "XXX", "XL", "L" };

		int unitDigit = value % 10;
		int tenDigit = (value / 10) % 10;

		return tens[tenDigit] + units[unitDigit];
	}

	public int convertRomanNumeralToInteger(String numeral) {
		switch (numeral) {
		case "I":
			return 1;
		case "II":
			return 2;
		case "III":
			return 3;
		default:
			return 1;
		}
	}

	public ChatColor getChatColorFromPantsColor(String color) {
		switch (color.toLowerCase()) {
		case "red":
			return ChatColor.RED;
		case "green":
			return ChatColor.GREEN;
		case "blue":
			return ChatColor.BLUE;
		case "orange":
			return ChatColor.GOLD;
		case "yellow":
			return ChatColor.YELLOW;
		case "dark":
			return ChatColor.DARK_PURPLE;
		case "aqua":
			return ChatColor.AQUA;
		case "sewer":
			return ChatColor.DARK_AQUA;
		default:
			return ChatColor.WHITE;
		}
	}
}
