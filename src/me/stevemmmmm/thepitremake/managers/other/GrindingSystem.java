package me.stevemmmmm.thepitremake.managers.other;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import me.stevemmmmm.configapi.core.ConfigAPI;
import me.stevemmmmm.configapi.core.ConfigReader;
import me.stevemmmmm.configapi.core.ConfigWriter;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.utils.RomanUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GrindingSystem implements Listener, ConfigWriter, ConfigReader {

	public static GrindingSystem instance;
	public final HashMap<UUID, Integer> playerPrestiges = new HashMap<>();
	public final HashMap<UUID, Integer> playerLevels = new HashMap<>();
	public final HashMap<Integer, Long> xpPerLevel = new HashMap<>();
	public final HashMap<UUID, Long> playerXP = new HashMap<>();
	public final HashMap<UUID, Double> playerGold = new HashMap<>();

	private static final ChatColor[] PRESTIGE_COLORS = { ChatColor.BLUE, ChatColor.YELLOW, ChatColor.GOLD,
			ChatColor.RED, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, ChatColor.WHITE, ChatColor.AQUA,
			ChatColor.GREEN, ChatColor.DARK_RED, ChatColor.DARK_AQUA, ChatColor.DARK_GREEN, ChatColor.DARK_BLUE,
			ChatColor.DARK_GRAY, ChatColor.BLACK };

	private static final ChatColor[] LEVEL_COLORS = { ChatColor.GRAY, ChatColor.BLUE, ChatColor.DARK_AQUA,
			ChatColor.DARK_GREEN, ChatColor.GREEN, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED, ChatColor.DARK_RED,
			ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, ChatColor.WHITE, ChatColor.AQUA };

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		this.initializeMaps(player);
		sendLevelProgress(player);
	}

	public static GrindingSystem getInstance() {
		if (instance == null) {
			instance = new GrindingSystem();
		}

		return instance;
	}

	public void updateDisplayName(Player player) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		EntityPlayer entityPlayer = craftPlayer.getHandle();

		String formattedLevel = getFormattedPlayerLevelWithoutPrestige(player);
		ChatColor rankColor = PermissionsManager.getInstance().getPlayerRank(player).getNameColor();
		String displayName = formattedLevel + rankColor.toString() + " " + player.getName();
		IChatBaseComponent displayNameComponent = CraftChatMessage.fromString(displayName)[0];

		entityPlayer.listName = displayNameComponent;

		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(
				PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, entityPlayer);
		entityPlayer.playerConnection.sendPacket(packet);
	}

	private IChatBaseComponent createFullLevelUpMessage(Player player, int initialLevel, int finalLevel) {
		String oldLevel = getFormattedPlayerLevelWithoutPrestige(player, initialLevel);
		String newLevel = getFormattedPlayerLevelWithoutPrestige(player, finalLevel);

		String jsonString = String.format("{\"text\":\"%s%sLEVEL UP! %s%s ➟ %s%s\",\"color\":\"%s\"}", ChatColor.AQUA,
				ChatColor.BOLD, ChatColor.YELLOW, oldLevel, ChatColor.YELLOW, newLevel,
				ChatColor.GOLD.name().toLowerCase());
		return ChatSerializer.a(jsonString);
	}

	private IChatBaseComponent createLevelUpMessage(Player player) {
		String jsonString = String.format("{\"text\":\"%s%sLEVEL UP!\",\"color\":\"%s\"}", ChatColor.AQUA,
				ChatColor.BOLD, ChatColor.AQUA.name().toLowerCase());
		return ChatSerializer.a(jsonString);
	}

	private IChatBaseComponent createOldLevelNewLevelMessage(Player player, int initialLevel, int finalLevel) {
		String oldLevel = getFormattedPlayerLevelWithoutPrestige(player, initialLevel);
		String newLevel = getFormattedPlayerLevelWithoutPrestige(player, finalLevel);

		String jsonString = String.format("{\"text\":\"%s%s ➟ %s%s\",\"color\":\"%s\"}", ChatColor.YELLOW, oldLevel,
				ChatColor.YELLOW, newLevel, ChatColor.GOLD.name().toLowerCase());
		return ChatSerializer.a(jsonString);
	}

	public void sendLevelUpMessage(Player player, int initialLevel, int finalLevel) {
		IChatBaseComponent message = createFullLevelUpMessage(player, initialLevel, finalLevel);
		IChatBaseComponent title = createLevelUpMessage(player);
		IChatBaseComponent subTitle = createOldLevelNewLevelMessage(player, initialLevel, finalLevel);

		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, title, 20, 20, 20);
		PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subTitle, 20, 20, 20);
		PacketPlayOutChat chatMessage = new PacketPlayOutChat(message);

		CraftPlayer craftPlayer = (CraftPlayer) player;
		craftPlayer.getHandle().playerConnection.sendPacket(titlePacket);
		craftPlayer.getHandle().playerConnection.sendPacket(subTitlePacket);
		craftPlayer.getHandle().playerConnection.sendPacket(chatMessage);
	}
	
    public void sendPrestigeMessage(Player player, int prestige) {
        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "PRESTIGE" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.GRAY + "You unlocked prestige " + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(prestige) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle, 20, 20, 20);
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle, 20, 20, 20);

		CraftPlayer craftPlayer = (CraftPlayer) player;
		craftPlayer.getHandle().playerConnection.sendPacket(title);
		craftPlayer.getHandle().playerConnection.sendPacket(subTitle);
    }

	public void checkLevelUp(Player player) {
		UUID playerId = player.getUniqueId();
		Integer currentLevel = getPlayerLevel(player);
		Long currentXP = getPlayerXP(player);
		if (currentLevel >= 120)
			return;

		double multiplier = prestigeMultiplier(player);
		Long requiredXP = (long) (xpPerLevel.get(currentLevel + 1) * multiplier);
		boolean leveledUp = false;
		int initialLevel = currentLevel;
		int finalLevel = currentLevel;

		while (currentXP >= requiredXP && currentLevel < 120) {
			currentLevel++;
			currentXP -= requiredXP;
			if (currentLevel < 120) {
				requiredXP = (long) (xpPerLevel.get(currentLevel + 1) * multiplier);
			} else {
				requiredXP = 0L;
			}
			finalLevel = currentLevel;
			leveledUp = true;
		}

		playerLevels.put(playerId, currentLevel);
		playerXP.put(playerId, currentXP);

		if (leveledUp) {
			sendLevelUpMessage(player, initialLevel, finalLevel);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
		}
		updateDisplayName(player);
		sendLevelProgress(player);
	}

	public void sendLevelProgress(Player player) {
		int currentLevel = getPlayerLevel(player);
		long remainingXP = getPlayerNeededXP(player);
		Long totalNeededXP = getTotalXPForNextLevel(currentLevel, player);

		if (currentLevel >= 120 || totalNeededXP == 0) {
			player.setExp(0.0f);
			return;
		}

		float progress = (float) (totalNeededXP - remainingXP) / totalNeededXP;

		PacketPlayOutExperience packet = new PacketPlayOutExperience(progress, currentLevel, 0);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

		player.setLevel(currentLevel);
		player.setExp(progress);
	}

	private long getTotalXPForNextLevel(int currentLevel, Player player) {
		if (currentLevel >= 120) {
			return 0L;
		}
		Long neededXP = xpPerLevel.get(currentLevel + 1);
		if (neededXP == null) {
			neededXP = 0L;
		} else {
			double multiplier = this.prestigeMultiplier(player);
			neededXP = (long) (neededXP * multiplier);
		}
		return neededXP;
	}

	public void setXP(Player player, Long xp) {
		UUID playerId = player.getUniqueId();
		playerXP.put(playerId, xp);
		checkLevelUp(player);
	}

	public void giveXP(Player player, int amount) {
		this.playerXP.put(player.getUniqueId(), (Long) this.playerXP.get(player.getUniqueId()) + amount);
		checkLevelUp(player);
	}

	public int giveRandomXP(Player player) {
		int xp = new Random().nextInt(22, 30);
		playerXP.put(player.getUniqueId(), playerXP.get(player.getUniqueId()) + xp);
		checkLevelUp(player);
		return xp;
	}

	public void giveGold(Player player, int amount) {
		this.playerGold.put(player.getUniqueId(), (Double) this.playerGold.get(player.getUniqueId()) + (double) amount);
	}

	public double giveRandomGold(Player player) {
		DecimalFormat df = new DecimalFormat("###.##");
		double gold = ThreadLocalRandom.current().nextDouble(10, 25);
		this.playerGold.put(player.getUniqueId(),
				Double.parseDouble(df.format(playerGold.get(player.getUniqueId()) + gold).replace(",", ".")));
		return gold;
	}

	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event) {
		if (event.getEntity().getKiller() == null) {
			event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
		} else if (event.getEntity().getName().equalsIgnoreCase(event.getEntity().getKiller().getName())) {
			event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
		} else {
			Player player = event.getEntity().getKiller();
			event.getEntity()
					.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH! " + ChatColor.GRAY + "by "
							+ this.getFormattedPlayerLevelWithoutPrestige(player) + " "
							+ PermissionsManager.getInstance().getPlayerRank(player).getNameColor() + player.getName()
							+ " " + ChatColor.YELLOW.toString() + ChatColor.BOLD + "VIEW RECAP");
			IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.RED + "YOU DIED" + "\",color:"
					+ ChatColor.GOLD.name().toLowerCase() + "}");
			PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
			PacketPlayOutTitle length = new PacketPlayOutTitle(20, 20, 20);
			((CraftPlayer) event.getEntity()).getHandle().playerConnection.sendPacket(title);
			((CraftPlayer) event.getEntity()).getHandle().playerConnection.sendPacket(length);
		}
	}

	private double prestigeMultiplier(Player player) {
		double[] multipliers = { 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.75, 2.0, 2.25, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 3.0,
				3.2, 3.4, 3.6, 3.8, 4.0, 4.4, 4.8, 5.2, 5.6, 6.0, 6.5, 7.0, 9.5, 10.0, 10.10776, 10.10776, 10.10776,
				10.10776, 10.10776, 12.0, 13.0, 14.0, 15.0, 17.5, 20.0, 22.5, 25.0, 27.5, 30.0, 32.0, 35.0, 40.0, 50.0,
				70.0, 100.0, 140.0, 190.0, 250.0, 320.0, 400.0, 490.0, 600.0, 730.0, 840.0, 950.0, 1070.0, 1200.0,
				1340.0, 1490.0, 1650.0, 1820.0, 2000.0, };
		int prestigeLevel = this.getPlayerPrestige(player);
		return multipliers[Math.min(prestigeLevel, multipliers.length - 1)];
	}

	private void initializeMaps(Player player) {
		for (int level = 1; level <= 120; level++) {
			double multiplier = this.prestigeMultiplier(player);
			long xp = this.calculateXP(level, multiplier);
			xpPerLevel.put(level, xp);
		}
	}

	private long calculateXP(int level, double multiplier) {
		long value;
		if (level <= 10)
			value = 15;
		else if (level <= 20)
			value = 30;
		else if (level <= 30)
			value = 50;
		else if (level <= 40)
			value = 75;
		else if (level <= 50)
			value = 125;
		else if (level <= 60)
			value = 300;
		else if (level <= 70)
			value = 600;
		else if (level <= 80)
			value = 800;
		else if (level <= 90)
			value = 900;
		else if (level <= 100)
			value = 1000;
		else if (level <= 110)
			value = 1200;
		else if (level <= 120)
			value = 1500;
		else
			value = 0;

		return (long) (value * multiplier);
	}

	public int getPlayerLevel(Player player) {
		return playerLevels.getOrDefault(player.getUniqueId(), 0);
	}

	public ChatColor getColorByPrestige(int prestige) {
		return PRESTIGE_COLORS[Math.min(prestige / 5, PRESTIGE_COLORS.length - 1)];
	}

	public ChatColor getLevelColor(int level) {
		return LEVEL_COLORS[Math.min(level / 10, LEVEL_COLORS.length - 1)];
	}

	public ChatColor getPlayerPrestigeColor(Player player) {
		return getColorByPrestige(getPlayerPrestige(player));
	}

	private String formatLevel(int level, ChatColor prestigeColor, boolean includePrestige) {
		ChatColor levelColor = getLevelColor(level);
		String levelString = levelColor + String.valueOf(level) + (level >= 60 ? ChatColor.RESET : "");
		return includePrestige ? prestigeColor + "[" + levelString + prestigeColor + "]"
				: prestigeColor + "[" + levelString + prestigeColor + "]";
	}

	public String getFormattedPlayerLevel(Player player, boolean future, boolean includePrestige) {
		int prestige = getPlayerPrestige(player);
		int level = getPlayerLevel(player) + (future ? 1 : 0);
		ChatColor prestigeColor = prestige != 0 ? getColorByPrestige(prestige) : ChatColor.GRAY;
		return formatLevel(level, prestigeColor, includePrestige);
	}

	public String getFormattedPlayerLevel(Player player) {
		return getFormattedPlayerLevel(player, false, true);
	}

	public String getFutureFormattedPlayerLevel(Player player) {
		return getFormattedPlayerLevel(player, true, true);
	}

	public String getFormattedPlayerLevelWithoutPrestige(Player player) {
		return getFormattedPlayerLevel(player, false, false);
	}

	public String getFutureFormattedPlayerLevelWithoutPrestige(Player player) {
		return getFormattedPlayerLevel(player, true, false);
	}

	private String formatLevelWithoutPrestige(int level, ChatColor prestigeColor) {
		return formatLevel(level, prestigeColor, false);
	}

	private String getFormattedPlayerLevelWithoutPrestige(Player player, int level) {
		int prestige = getPlayerPrestige(player);
		ChatColor prestigeColor = prestige != 0 ? getColorByPrestige(prestige) : ChatColor.GRAY;
		return formatLevelWithoutPrestige(level, prestigeColor);
	}

	public HashMap<UUID, Integer> getPlayerPrestiges() {
		return this.playerPrestiges;
	}

	public int getPlayerPrestige(Player player) {
		return (Integer) this.playerPrestiges.getOrDefault(player.getUniqueId(), 0);
	}

	public long getPlayerXP(Player player) {
		return this.playerXP.getOrDefault(player.getUniqueId(), 0L);
	}

	public double getPlayerGold(Player player) {
		return (Double) this.playerGold.getOrDefault(player.getUniqueId(), 0.0);
	}

	public Long getPlayerNeededXP(Player player) {
		int currentLevel = this.getPlayerLevel(player);
		if (currentLevel >= 120) {
			return 0L;
		}
		int nextLevel = currentLevel + 1;
		long currentXP = this.getPlayerXP(player);
		Long neededXP = xpPerLevel.get(nextLevel);
		if (neededXP == null) {
			neededXP = 0L;
		} else {
			double multiplier = this.prestigeMultiplier(player);
			neededXP = (long) (neededXP * multiplier);
		}
		long remainingXP = neededXP - currentXP;
		return remainingXP;
	}

	public String getFormattedPlayerGold(Player player) {
		double gold = this.getPlayerGold(player);
		DecimalFormat decimalFormat;
		if (gold < 10000.0) {
			decimalFormat = new DecimalFormat("###,###,###,###,###,##0.00");
		} else {
			decimalFormat = new DecimalFormat("###,###,###,###,###,##0");
		}
		return decimalFormat.format(gold) + "g";
	}

	public void setPlayerLevel(Player player, int value) {
		this.playerLevels.put(player.getUniqueId(), value);
	}

	public void setPlayerGold(Player player, double value) {
		if (value < 0.0) {
			value = 0.0;
		}

		this.playerGold.put(player.getUniqueId(), value);
	}

	public void setPlayerPrestige(Player player, int value) {
		this.playerPrestiges.put(player.getUniqueId(), value);
		this.initializeMaps(player);
	}

	public void readConfig(Player player) {
		this.playerPrestiges.put(player.getUniqueId(),
				(Integer) ConfigAPI.read(Main.INSTANCE, player, "Prestiges", Integer.class, 0));
		this.playerXP.put(player.getUniqueId(), (Long) ConfigAPI.read(Main.INSTANCE, player, "XP", Long.class, 0L));
		this.playerGold.put(player.getUniqueId(),
				(Double) ConfigAPI.read(Main.INSTANCE, player, "Gold", Double.class, 0.0));
		this.playerLevels.put(player.getUniqueId(),
				(Integer) ConfigAPI.read(Main.INSTANCE, player, "Levels", Integer.class, 1));
	}

	public void writeToConfig() {
		ConfigAPI.write(Main.INSTANCE, "XP", this.playerXP);
		ConfigAPI.write(Main.INSTANCE, "Gold", this.playerGold);
		ConfigAPI.write(Main.INSTANCE, "Prestiges", this.playerPrestiges);
		ConfigAPI.write(Main.INSTANCE, "Levels", this.playerLevels);
	}

}