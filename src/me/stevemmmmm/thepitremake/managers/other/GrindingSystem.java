//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.other;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import me.stevemmmmm.configapi.core.ConfigAPI;
import me.stevemmmmm.thepitremake.managers.other.PitScoreboardManager;
import me.stevemmmmm.configapi.core.ConfigReader;
import me.stevemmmmm.configapi.core.ConfigWriter;
import me.stevemmmmm.permissions.core.PermissionsManager;
import me.stevemmmmm.thepitremake.core.Main;
import me.stevemmmmm.thepitremake.utils.RomanUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GrindingSystem implements Listener, ConfigWriter, ConfigReader {
    static int kills = 0;
    public static GrindingSystem instance;
    public final HashMap<Integer, Integer> xpPerLevel = new HashMap();
    public final HashMap<Integer, Float> prestigeMultiplier = new HashMap();
    public final HashMap<UUID, Integer> playerPrestiges = new HashMap();
    public final HashMap<UUID, Integer> playerLevels = new HashMap();
    public final HashMap<UUID, Integer> playerXP = new HashMap();
    public final HashMap<UUID, Double> playerGold = new HashMap();

    private GrindingSystem() {
        this.initializeMaps();
    }

    public static GrindingSystem getInstance() {
        if (instance == null) {
            instance = new GrindingSystem();
        }

        return instance;
    }

//    public void updateLevel(Player player) {
//        if ((Integer)this.playerLevels.get(player.getUniqueId()) != 120) {
//            if ((float)(Integer)this.playerXP.get(player.getUniqueId()) >= (float)(Integer)this.xpPerLevel.get((Integer)this.playerLevels.get(player.getUniqueId()) + 1) * (Float)this.prestigeMultiplier.getOrDefault(this.playerPrestiges.get(player.getUniqueId()), 1.0F)) {
//                IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.AQUA.toString() + ChatColor.BOLD + "LEVEL UP!" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
//                PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
//                PacketPlayOutTitle length = new PacketPlayOutTitle(20, 20, 20);
//                IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.YELLOW + this.getFormattedPlayerLevelWithoutPrestige(player) + " ➟ " + this.getFutureFormattedPlayerLevelWithoutPrestige(player) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
//                PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
//                PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(20, 20, 20);
//                PacketPlayOutChat message = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.AQUA.toString() + ChatColor.BOLD + "LEVEL UP! " + ChatColor.YELLOW + getFormattedPlayerLevelWithoutPrestige(player) + " ➟ " + getFutureFormattedPlayerLevelWithoutPrestige(player) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}"));
//                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
//                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
//                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitle);
//                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitleLength);
//                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(message);
//                this.playerLevels.put(player.getUniqueId(), (Integer)this.playerLevels.get(player.getUniqueId()) + 1);
//            }
//        }
//   }

//    public int giveRandomXP(Player player, int min, int max) {
//        int xp = ThreadLocalRandom.current().nextInt(min, max);
//        playerXP.put(player.getUniqueId(), playerXP.get(player.getUniqueId()) + xp);

//        return xp;
//    }


//    public void giveGold(Player player, int amount) {
//        this.playerGold.put(player.getUniqueId(), (Double)this.playerGold.get(player.getUniqueId()) + (double)amount);
//    }
    
//    public void levelUp(Player player) {
//        int currentXP = this.getPlayerXP(player);
//        int neededXP = this.getPlayerNeededXP(player);
//        int current = this.getPlayerLevel(player);
//        if (currentXP >= neededXP && current <=119) {
//        	this.playerXP.put(player.getUniqueId(), 0);
//            this.playerLevels.put(player.getUniqueId(), this.playerLevels.get(player.getUniqueId()) + 1);
//           this.updateLevel(player);
//        }
//    }
    
//    public void setXP(Player player, int amount) {
//        int currentXP = getPlayerXP(player);
//        int neededXP = Cwel(player);
//        int newXP = currentXP + 4000;
//        if (newXP < currentXP) {
//            newXP = Integer.MAX_VALUE;
//        }
//        while (newXP >= neededXP) {
//           levelUp(player);
//            newXP -= neededXP;
//            neededXP = Cwel(player);
//        }
//        playerXP.put(player.getUniqueId(), newXP);
//    }


//    public double giveRandomGold(Player player) {
//        DecimalFormat df = new DecimalFormat("###.##");
//        double gold = ThreadLocalRandom.current().nextDouble(10, 25);
//        this.playerGold.put(player.getUniqueId(), Double.parseDouble(df.format(playerGold.get(player.getUniqueId()) + gold).replace(",", ".")));
//        return gold;
//    }
//   
//    public void giveXP(Player player, int amount) {
//        this.playerXP.put(player.getUniqueId(), (Integer)this.playerXP.get(player.getUniqueId()) + amount);
//    }
//
//    public int giveRandomXP(Player player) {
//        int xp = ThreadLocalRandom.current().nextInt(22,30);
//        playerXP.put(player.getUniqueId(), playerXP.get(player.getUniqueId()) + xp);
//
//        return xp;
//    }
//
//
//    public void giveGold(Player player, int amount) {
//        this.playerGold.put(player.getUniqueId(), (Double)this.playerGold.get(player.getUniqueId()) + (double)amount);
//    }
//
//    public double giveRandomGold(Player player) {
//        DecimalFormat df = new DecimalFormat("###.##");
//       double gold = ThreadLocalRandom.current().nextDouble(10, 25);
//        this.playerGold.put(player.getUniqueId(), Double.parseDouble(df.format(playerGold.get(player.getUniqueId()) + gold).replace(",", ".")));
//        return gold;
//    }
//    @EventHandler
//    public void onPlayerKill(PlayerDeathEvent event) {
//        if (event.getEntity().getKiller() == null) {
//            event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
//        } else if (event.getEntity().getName().equalsIgnoreCase(event.getEntity().getKiller().getName())) {
//            event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
//        } else {
//            Player player = event.getEntity().getKiller();
//            DecimalFormat df = new DecimalFormat("##0.00");
//            event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH! " + ChatColor.GRAY + "by " + this.getFormattedPlayerLevelWithoutPrestige(player) + " " + PermissionsManager.getInstance().getPlayerRank(player).getNameColor() + player.getName() + " " + ChatColor.YELLOW.toString() + ChatColor.BOLD + "VIEW RECAP");
//            IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.RED + "YOU DIED" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
//            PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
//            PacketPlayOutTitle length = new PacketPlayOutTitle(20, 20, 20);
//            ((CraftPlayer)event.getEntity()).getHandle().playerConnection.sendPacket(title);
//            ((CraftPlayer)event.getEntity()).getHandle().playerConnection.sendPacket(length);
//            int currentLevel = this.getPlayerLevel(player);
//            if (currentLevel < 120) {
//                this.updateLevel(event.getEntity().getKiller());
//            }
//            event.getEntity().getKiller().playSound(event.getEntity().getKiller().getLocation(), Sound.ORB_PICKUP, 1.0F, 1.75F);
//            PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + PermissionsManager.getInstance().getPlayerRank(event.getEntity()).getNameColor() + event.getEntity().getName() + ChatColor.GREEN.toString() + ChatColor.BOLD + " KILL! \"}"), (byte)2);
//            ((CraftPlayer)event.getEntity().getKiller()).getHandle().playerConnection.sendPacket(packet);
//            ++kills;
//        }
//    }
//  public int neededXP(Player player) {
//  return (Integer)this.xpPerLevel.get((Integer)this.playerLevels.get(player.getUniqueId()) + 1);
//}

//public int Cwel(Player player) {
//  int level = getPlayerLevel(player);
//  if (level < 1) {
//      return 0;
//  }
//  return xpPerLevel.get(level);
//}

    public void updateLevel(Player player) {
        if ((Integer)this.playerLevels.get(player.getUniqueId()) != 120) {
            if ((float)(Integer)this.playerXP.get(player.getUniqueId()) >= (float)(Integer)this.xpPerLevel.get((Integer)this.playerLevels.get(player.getUniqueId()) + 1) * (Float)this.prestigeMultiplier.getOrDefault(this.playerPrestiges.get(player.getUniqueId()), 1.0F)) {
                IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.AQUA.toString() + ChatColor.BOLD + "LEVEL UP!" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
                PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
                PacketPlayOutTitle length = new PacketPlayOutTitle(20, 20, 20);
                IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.YELLOW + this.getFormattedPlayerLevelWithoutPrestige(player) + " ➟ " + this.getFutureFormattedPlayerLevelWithoutPrestige(player) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
                PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
                PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(20, 20, 20);
                PacketPlayOutChat message = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.AQUA.toString() + ChatColor.BOLD + "LEVEL UP! " + ChatColor.YELLOW + getFormattedPlayerLevelWithoutPrestige(player) + " ➟ " + getFutureFormattedPlayerLevelWithoutPrestige(player) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}"));
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitle);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitleLength);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(message);
                this.playerLevels.put(player.getUniqueId(), (Integer)this.playerLevels.get(player.getUniqueId()) + 1);
                ((CraftPlayer)player).getHandle().listName = CraftChatMessage.fromString(this.getFormattedPlayerLevelWithoutPrestige(player) + PermissionsManager.getInstance().getPlayerRank(player).getNameColor() + " " + player.getName())[0];
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[]{((CraftPlayer)player).getHandle()}));
                ((CraftPlayer)player).getHandle().listName = CraftChatMessage.fromString(this.getFormattedPlayerLevelWithoutPrestige(player) + PermissionsManager.getInstance().getPlayerRank(player).getNameColor() + " " + player.getName())[0];
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[]{((CraftPlayer)player).getHandle()}));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }

        }
    }

    public void giveXP(Player player, int amount) {
        this.playerXP.put(player.getUniqueId(), (Integer)this.playerXP.get(player.getUniqueId()) + amount);
    }

    public int giveRandomXP(Player player) {
        int xp = ThreadLocalRandom.current().nextInt(22,30);
        playerXP.put(player.getUniqueId(), playerXP.get(player.getUniqueId()) + xp);

        return xp;
    }


    public void giveGold(Player player, int amount) {
        this.playerGold.put(player.getUniqueId(), (Double)this.playerGold.get(player.getUniqueId()) + (double)amount);
    }

    public double giveRandomGold(Player player) {
        DecimalFormat df = new DecimalFormat("###.##");
        double gold = ThreadLocalRandom.current().nextDouble(10, 25);
        this.playerGold.put(player.getUniqueId(), Double.parseDouble(df.format(playerGold.get(player.getUniqueId()) + gold).replace(",", ".")));
        return gold;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
        } else if (event.getEntity().getName().equalsIgnoreCase(event.getEntity().getKiller().getName())) {
            event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
        } else {
            DecimalFormat df = new DecimalFormat("##0.00");
            event.getEntity().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH! " + ChatColor.GRAY + "by " + this.getFormattedPlayerLevelWithoutPrestige(event.getEntity().getKiller()) + " " + PermissionsManager.getInstance().getPlayerRank(event.getEntity().getKiller()).getNameColor() + event.getEntity().getKiller().getName() + " " + ChatColor.YELLOW.toString() + ChatColor.BOLD + "VIEW RECAP");
            IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + ChatColor.RED + "YOU DIED" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
            PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
            PacketPlayOutTitle length = new PacketPlayOutTitle(10, 10, 10);

            ((CraftPlayer)event.getEntity()).getHandle().playerConnection.sendPacket(title);
            ((CraftPlayer)event.getEntity()).getHandle().playerConnection.sendPacket(length);
            this.updateLevel(event.getEntity().getKiller());
            PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + PermissionsManager.getInstance().getPlayerRank(event.getEntity()).getNameColor() + event.getEntity().getName() + ChatColor.GREEN.toString() + ChatColor.BOLD + " KILL!" + "\"}"), (byte)2);
            ((CraftPlayer)event.getEntity().getKiller()).getHandle().playerConnection.sendPacket(packet);
            ++kills;
        }
    }
    
    private void initializeMaps() {
        xpPerLevel.put(1, 9);

        for (int i = 2; i <= 9; i++) {
            int lastLevelXP = xpPerLevel.get(i - 1);
            int nextLevelXP = (int) (lastLevelXP * 1.15);
            xpPerLevel.put(i, nextLevelXP);
        }

        xpPerLevel.put(10, 20);

        for (int i = 11; i <= 19; i++) {
            int lastLevelXP = xpPerLevel.get(i - 1);
            int nextLevelXP = (int) (lastLevelXP * 1.08);
            xpPerLevel.put(i, nextLevelXP);
        }
        
        xpPerLevel.put(20, 37);
        
        for (int i = 21; i <= 39; i++) {
            int lastLevelXP = xpPerLevel.get(i - 1);
            int nextLevelXP = (int) (lastLevelXP * 1.12);
            xpPerLevel.put(i, nextLevelXP);
        }

        xpPerLevel.put(40, 139);
        
        for (int i = 21; i <= 120; i++) {
            int lastLevelXP = xpPerLevel.get(i - 1);
            int nextLevelXP = (int) (lastLevelXP * 1.04);
            xpPerLevel.put(i, nextLevelXP);
        }
    }
    
    public int getPlayerLevel(Player player) {
        return (Integer)this.playerLevels.getOrDefault(player.getUniqueId(), 0);
    }

    public String getFormattedPlayerLevel(Player player) {
        if (this.getPlayerPrestige(player) != 0) {
            ChatColor color = ChatColor.GRAY;
            if (getPlayerPrestige(player) < 5) {
                color = ChatColor.BLUE;
            } else if (getPlayerPrestige(player) < 10) {
                color = ChatColor.YELLOW;
            } else if (getPlayerPrestige(player) < 15) {
                color = ChatColor.GOLD;
            } else if (getPlayerPrestige(player) < 20) {
                color = ChatColor.RED;
            } else if (getPlayerPrestige(player) < 25) {
                color = ChatColor.DARK_PURPLE;
            } else if (getPlayerPrestige(player) < 30) {
                color = ChatColor.LIGHT_PURPLE;
            } else if (getPlayerPrestige(player) < 35) {
                color = ChatColor.WHITE;
            } else if (getPlayerPrestige(player) < 40) {
                color = ChatColor.AQUA;
            } else if (getPlayerPrestige(player) < 45) {
                color = ChatColor.GREEN;
            } else if (getPlayerPrestige(player) < 50) {
                color = ChatColor.DARK_RED;
            } else if (getPlayerPrestige(player) < 55) {
                color = ChatColor.DARK_AQUA;
            } else if (getPlayerPrestige(player) < 60) {
                color = ChatColor.DARK_GREEN;
            } else if (getPlayerPrestige(player) < 65) {
                color = ChatColor.DARK_BLUE;
            } else if (getPlayerPrestige(player) < 70) {
                color = ChatColor.DARK_GRAY;
            } else if (getPlayerPrestige(player) == 70) {
                color = ChatColor.BLACK;
            } 

            if (this.getPlayerLevel(player) < 10) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.GRAY + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 10 && this.getPlayerLevel(player) < 20) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.BLUE + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 20 && this.getPlayerLevel(player) < 30) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_AQUA + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 30 && this.getPlayerLevel(player) < 40) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_GREEN + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 40 && this.getPlayerLevel(player) < 50) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.GREEN + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 50 && this.getPlayerLevel(player) < 60) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.YELLOW + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 60 && this.getPlayerLevel(player) < 70) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.GOLD.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 70 && this.getPlayerLevel(player) < 80) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 80 && this.getPlayerLevel(player) < 90) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 90 && this.getPlayerLevel(player) < 100) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 100 && this.getPlayerLevel(player) < 110) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 110 && this.getPlayerLevel(player) < 120) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.WHITE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) == 120) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.AQUA.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }
        } else {
            if (this.getPlayerLevel(player) < 10) {
                return ChatColor.GRAY + "[" + this.getPlayerLevel(player) + "]";
            }

            if (this.getPlayerLevel(player) >= 10 && this.getPlayerLevel(player) < 20) {
                return ChatColor.GRAY + "[" + ChatColor.BLUE + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 20 && this.getPlayerLevel(player) < 30) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 30 && this.getPlayerLevel(player) < 40) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 40 && this.getPlayerLevel(player) < 50) {
                return ChatColor.GRAY + "[" + ChatColor.GREEN + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 50 && this.getPlayerLevel(player) < 60) {
                return ChatColor.GRAY + "[" + ChatColor.YELLOW + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 60 && this.getPlayerLevel(player) < 70) {
                return ChatColor.GRAY + "[" + ChatColor.GOLD.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 70 && this.getPlayerLevel(player) < 80) {
                return ChatColor.GRAY + "[" + ChatColor.RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 80 && this.getPlayerLevel(player) < 90) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 90 && this.getPlayerLevel(player) < 100) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 100 && this.getPlayerLevel(player) < 110) {
                return ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) >= 110 && this.getPlayerLevel(player) < 120) {
                return ChatColor.GRAY + "[" + ChatColor.WHITE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) == 120) {
                return ChatColor.GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
            }
        }

        return null;
    }

    public ChatColor getPlayerPrestigeColor(Player player) {
        int prestige = getPlayerPrestige(player);

        if (prestige == 0) {
            return ChatColor.GRAY;
        } else if (prestige < 5) {
            return ChatColor.BLUE;
        } else if (prestige < 10) {
            return ChatColor.YELLOW;
        } else if (prestige < 15) {
            return ChatColor.GOLD;
        } else if (prestige < 20) {
            return ChatColor.RED;
        } else if (prestige < 25) {
            return ChatColor.DARK_PURPLE;
        } else if (prestige < 30) {
            return ChatColor.LIGHT_PURPLE;
        } else if (prestige < 35) {
            return ChatColor.WHITE;
        } else if (prestige < 40) {
            return ChatColor.AQUA;
        } else if (prestige < 45) {
            return ChatColor.GREEN;
        } else if (prestige < 50) {
            return ChatColor.DARK_RED;
        } else if (prestige < 55) {
            return ChatColor.DARK_AQUA;
        } else if (prestige < 60) {
            return ChatColor.DARK_GREEN;
        } else if (prestige < 65) {
            return ChatColor.DARK_BLUE;
        } else if (prestige < 70) {
            return ChatColor.DARK_GRAY;
        } else if (prestige == 70) {
            return ChatColor.BLACK;
        }

        return null;
    }

    public String getLevelColorFromInt(int i) {
        if (i == 1) {
            return "§7";
        } else if (i >= 10 && i < 20) {
            return "§9";
        } else if (i >= 20 && i < 30) {
            return "§3";
        } else if (i >= 30 && i < 40) {
            return "§2";
        } else if (i >= 40 && i < 50) {
            return "§a";
        } else if (i >= 50 && i < 60) {
            return "§e";
        } else if (i >= 60 && i < 70) {
            return "§6§l";
        } else if (i >= 70 && i < 80) {
            return "§c§l";
        } else if (i >= 80 && i < 90) {
            return "§4§l";
        } else if (i >= 90 && i < 100) {
            return "§5§l";
        } else if (i >= 100 && i < 110) {
            return "§d§l";
        } else if (i >= 110 && i < 120) {
            return "§f§l";
        } else {
            return i == 120 ? "§b§l" : null;
        }
    }

    public String getFutureFormattedPlayerLevel(Player player) {
        if (this.getPlayerPrestige(player) != 0) {
            ChatColor color = ChatColor.GRAY;
            if (getPlayerPrestige(player) < 5) {
                color = ChatColor.BLUE;
            } else if (getPlayerPrestige(player) < 10) {
                color = ChatColor.YELLOW;
            } else if (getPlayerPrestige(player) < 15) {
                color = ChatColor.GOLD;
            } else if (getPlayerPrestige(player) < 20) {
                color = ChatColor.RED;
            } else if (getPlayerPrestige(player) < 25) {
                color = ChatColor.DARK_PURPLE;
            } else if (getPlayerPrestige(player) < 30) {
                color = ChatColor.LIGHT_PURPLE;
            } else if (getPlayerPrestige(player) < 35) {
                color = ChatColor.WHITE;
            } else if (getPlayerPrestige(player) < 40) {
                color = ChatColor.AQUA;
            } else if (getPlayerPrestige(player) < 45) {
                color = ChatColor.GREEN;
            } else if (getPlayerPrestige(player) < 50) {
                color = ChatColor.DARK_RED;
            } else if (getPlayerPrestige(player) < 55) {
                color = ChatColor.DARK_AQUA;
            } else if (getPlayerPrestige(player) < 60) {
                color = ChatColor.DARK_GREEN;
            } else if (getPlayerPrestige(player) < 65) {
                color = ChatColor.DARK_BLUE;
            } else if (getPlayerPrestige(player) < 70) {
                color = ChatColor.DARK_GRAY;
            } else if (getPlayerPrestige(player) == 70) {
                color = ChatColor.BLACK;
            } 

            if (this.getPlayerLevel(player) + 1 < 10) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.GRAY + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 10 && this.getPlayerLevel(player) + 1 < 20) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.BLUE + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 20 && this.getPlayerLevel(player) + 1 < 30) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_AQUA + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 30 && this.getPlayerLevel(player) + 1 < 40) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_GREEN + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 40 && this.getPlayerLevel(player) + 1 < 50) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.GREEN + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 50 && this.getPlayerLevel(player) + 1 < 60) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.YELLOW + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 60 && this.getPlayerLevel(player) + 1 < 70) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.GOLD.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 70 && this.getPlayerLevel(player) + 1 < 80) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 80 && this.getPlayerLevel(player) + 1 < 90) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 90 && this.getPlayerLevel(player) + 1 < 100) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 100 && this.getPlayerLevel(player) + 1 < 110) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 110 && this.getPlayerLevel(player) + 1 < 120) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.WHITE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 == 120) {
                return color + "[" + ChatColor.YELLOW + RomanUtils.getInstance().convertToRomanNumeral(this.getPlayerPrestige(player)) + color + "-" + ChatColor.AQUA.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }
        } else {
            if (this.getPlayerLevel(player) + 1 < 10) {
                return ChatColor.GRAY + "[" + (this.getPlayerLevel(player) + 1) + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 10 && this.getPlayerLevel(player) + 1 < 20) {
                return ChatColor.GRAY + "[" + ChatColor.BLUE + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 20 && this.getPlayerLevel(player) + 1 < 30) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 30 && this.getPlayerLevel(player) + 1 < 40) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 40 && this.getPlayerLevel(player) + 1 < 50) {
                return ChatColor.GRAY + "[" + ChatColor.GREEN + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 50 && this.getPlayerLevel(player) + 1 < 60) {
                return ChatColor.GRAY + "[" + ChatColor.YELLOW + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 60 && this.getPlayerLevel(player) + 1 < 70) {
                return ChatColor.GRAY + "[" + ChatColor.GOLD.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 70 && this.getPlayerLevel(player) + 1 < 80) {
                return ChatColor.GRAY + "[" + ChatColor.RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 80 && this.getPlayerLevel(player) + 1 < 90) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 90 && this.getPlayerLevel(player) + 1 < 100) {
                return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 100 && this.getPlayerLevel(player) + 1 < 110) {
                return ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 110 && this.getPlayerLevel(player) + 1 < 120) {
                return ChatColor.GRAY + "[" + ChatColor.WHITE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }

            if (this.getPlayerLevel(player) + 1 == 120) {
                return ChatColor.GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
            }
        }

        return null;
    }

    public String getFormattedPlayerLevelWithoutPrestige(Player player) {
        if (this.getPlayerPrestige(player) != 0) {
            ChatColor color = ChatColor.GRAY;
            if (getPlayerPrestige(player) < 5) {
                color = ChatColor.BLUE;
            } else if (getPlayerPrestige(player) < 10) {
                color = ChatColor.YELLOW;
            } else if (getPlayerPrestige(player) < 15) {
                color = ChatColor.GOLD;
            } else if (getPlayerPrestige(player) < 20) {
                color = ChatColor.RED;
            } else if (getPlayerPrestige(player) < 25) {
                color = ChatColor.DARK_PURPLE;
            } else if (getPlayerPrestige(player) < 30) {
                color = ChatColor.LIGHT_PURPLE;
            } else if (getPlayerPrestige(player) < 35) {
                color = ChatColor.WHITE;
            } else if (getPlayerPrestige(player) < 40) {
                color = ChatColor.AQUA;
            } else if (getPlayerPrestige(player) < 45) {
                color = ChatColor.GREEN;
            } else if (getPlayerPrestige(player) < 50) {
                color = ChatColor.DARK_RED;
            } else if (getPlayerPrestige(player) < 55) {
                color = ChatColor.DARK_AQUA;
            } else if (getPlayerPrestige(player) < 60) {
                color = ChatColor.DARK_GREEN;
            } else if (getPlayerPrestige(player) < 65) {
                color = ChatColor.DARK_BLUE;
            } else if (getPlayerPrestige(player) < 70) {
                color = ChatColor.DARK_GRAY;
            } else if (getPlayerPrestige(player) == 70) {
                color = ChatColor.BLACK;
            } 

            if (this.getPlayerLevel(player) < 10) {
                return color + "[" + ChatColor.GRAY + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 10 && this.getPlayerLevel(player) < 20) {
                return color + "[" + ChatColor.BLUE + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 20 && this.getPlayerLevel(player) < 30) {
                return color + "[" + ChatColor.DARK_AQUA + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 30 && this.getPlayerLevel(player) < 40) {
                return color + "[" + ChatColor.DARK_GREEN + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 40 && this.getPlayerLevel(player) < 50) {
                return color + "[" + ChatColor.GREEN + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 50 && this.getPlayerLevel(player) < 60) {
                return color + "[" + ChatColor.YELLOW + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 60 && this.getPlayerLevel(player) < 70) {
                return color + "[" + ChatColor.GOLD.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 70 && this.getPlayerLevel(player) < 80) {
                return color + "[" + ChatColor.RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 80 && this.getPlayerLevel(player) < 90) {
                return color + "[" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 90 && this.getPlayerLevel(player) < 100) {
                return color + "[" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 100 && this.getPlayerLevel(player) < 110) {
                return color + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) >= 110 && this.getPlayerLevel(player) < 120) {
                return color + "[" + ChatColor.WHITE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }

            if (this.getPlayerLevel(player) == 120) {
                return color + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + color + "]";
            }
        }

        if (this.getPlayerLevel(player) < 10) {
            return ChatColor.GRAY + "[" + this.getPlayerLevel(player) + "]";
        } else if (this.getPlayerLevel(player) >= 10 && this.getPlayerLevel(player) < 20) {
            return ChatColor.GRAY + "[" + ChatColor.BLUE + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 20 && this.getPlayerLevel(player) < 30) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 30 && this.getPlayerLevel(player) < 40) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 40 && this.getPlayerLevel(player) < 50) {
            return ChatColor.GRAY + "[" + ChatColor.GREEN + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 50 && this.getPlayerLevel(player) < 60) {
            return ChatColor.GRAY + "[" + ChatColor.YELLOW + this.getPlayerLevel(player) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 60 && this.getPlayerLevel(player) < 70) {
            return ChatColor.GRAY + "[" + ChatColor.GOLD.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 70 && this.getPlayerLevel(player) < 80) {
            return ChatColor.GRAY + "[" + ChatColor.RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 80 && this.getPlayerLevel(player) < 90) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 90 && this.getPlayerLevel(player) < 100) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 100 && this.getPlayerLevel(player) < 110) {
            return ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) >= 110 && this.getPlayerLevel(player) < 120) {
            return ChatColor.GRAY + "[" + ChatColor.WHITE.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else {
            return this.getPlayerLevel(player) == 120 ? ChatColor.GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD + this.getPlayerLevel(player) + ChatColor.RESET + ChatColor.GRAY + "]" : null;
        }
    }

    public String getFutureFormattedPlayerLevelWithoutPrestige(Player player) {
        if (this.getPlayerPrestige(player) != 0) {
            ChatColor color = ChatColor.GRAY;
            if (getPlayerPrestige(player) < 5) {
                color = ChatColor.BLUE;
            } else if (getPlayerPrestige(player) < 10) {
                color = ChatColor.YELLOW;
            } else if (getPlayerPrestige(player) < 15) {
                color = ChatColor.GOLD;
            } else if (getPlayerPrestige(player) < 20) {
                color = ChatColor.RED;
            } else if (getPlayerPrestige(player) < 25) {
                color = ChatColor.DARK_PURPLE;
            } else if (getPlayerPrestige(player) < 30) {
                color = ChatColor.LIGHT_PURPLE;
            } else if (getPlayerPrestige(player) < 35) {
                color = ChatColor.WHITE;
            } else if (getPlayerPrestige(player) < 40) {
                color = ChatColor.AQUA;
            } else if (getPlayerPrestige(player) < 45) {
                color = ChatColor.GREEN;
            } else if (getPlayerPrestige(player) < 50) {
                color = ChatColor.DARK_RED;
            } else if (getPlayerPrestige(player) < 55) {
                color = ChatColor.DARK_AQUA;
            } else if (getPlayerPrestige(player) < 60) {
                color = ChatColor.DARK_GREEN;
            } else if (getPlayerPrestige(player) < 65) {
                color = ChatColor.DARK_BLUE;
            } else if (getPlayerPrestige(player) < 70) {
                color = ChatColor.DARK_GRAY;
            } else if (getPlayerPrestige(player) == 70) {
                color = ChatColor.BLACK;
            } 

            if (this.getPlayerLevel(player) + 1 < 10) {
                return color + "[" + ChatColor.GRAY + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 10 && this.getPlayerLevel(player) + 1 < 20) {
                return color + "[" + ChatColor.BLUE + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 20 && this.getPlayerLevel(player) + 1 < 30) {
                return color + "[" + ChatColor.DARK_AQUA + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 30 && this.getPlayerLevel(player) + 1 < 40) {
                return color + "[" + ChatColor.DARK_GREEN + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 40 && this.getPlayerLevel(player) + 1 < 50) {
                return color + "[" + ChatColor.GREEN + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 50 && this.getPlayerLevel(player) + 1 < 60) {
                return color + "[" + ChatColor.YELLOW + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 60 && this.getPlayerLevel(player) + 1 < 70) {
                return color + "[" + ChatColor.GOLD.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 70 && this.getPlayerLevel(player) + 1 < 80) {
                return color + "[" + ChatColor.RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 80 && this.getPlayerLevel(player) + 1 < 90) {
                return color + "[" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 90 && this.getPlayerLevel(player) + 1 < 100) {
                return color + "[" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 100 && this.getPlayerLevel(player) + 1 < 110) {
                return color + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 >= 110 && this.getPlayerLevel(player) + 1 < 120) {
                return color + "[" + ChatColor.WHITE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }

            if (this.getPlayerLevel(player) + 1 == 120) {
                return color + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + color + "]";
            }
        }

        if (this.getPlayerLevel(player) + 1 < 10) {
            return ChatColor.GRAY + "[" + (this.getPlayerLevel(player) + 1) + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 10 && this.getPlayerLevel(player) + 1 < 20) {
            return ChatColor.GRAY + "[" + ChatColor.BLUE + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 20 && this.getPlayerLevel(player) + 1 < 30) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 30 && this.getPlayerLevel(player) + 1 < 40) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 40 && this.getPlayerLevel(player) + 1 < 50) {
            return ChatColor.GRAY + "[" + ChatColor.GREEN + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 50 && this.getPlayerLevel(player) + 1 < 60) {
            return ChatColor.GRAY + "[" + ChatColor.YELLOW + (this.getPlayerLevel(player) + 1) + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 60 && this.getPlayerLevel(player) + 1 < 70) {
            return ChatColor.GRAY + "[" + ChatColor.GOLD.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 70 && this.getPlayerLevel(player) + 1 < 80) {
            return ChatColor.GRAY + "[" + ChatColor.RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 80 && this.getPlayerLevel(player) + 1 < 90) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 90 && this.getPlayerLevel(player) + 1 < 100) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 100 && this.getPlayerLevel(player) + 1 < 110) {
            return ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else if (this.getPlayerLevel(player) + 1 >= 110 && this.getPlayerLevel(player) + 1 < 120) {
            return ChatColor.GRAY + "[" + ChatColor.WHITE.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]";
        } else {
            return this.getPlayerLevel(player) + 1 == 120 ? ChatColor.GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD + (this.getPlayerLevel(player) + 1) + ChatColor.RESET + ChatColor.GRAY + "]" : null;
        }
    }

    public HashMap<UUID, Integer> getPlayerPrestiges() {
        return this.playerPrestiges;
    }

    public int getPlayerPrestige(Player player) {
        return (Integer)this.playerPrestiges.getOrDefault(player.getUniqueId(), 0);
    }

    public int getPlayerXP(Player player) {
        return (Integer)this.playerXP.getOrDefault(player.getUniqueId(), 0);
    }

    public double getPlayerGold(Player player) {
        return (Double)this.playerGold.getOrDefault(player.getUniqueId(), 0.0);
    }

    public int getPlayerNeededXP(Player player) {
    	int currentLevel = this.getPlayerLevel(player);
    	if (currentLevel <= 119) {
    		return (Integer)this.xpPerLevel.get((Integer)this.playerLevels.get(player.getUniqueId()) + 1);
    	} else {
    		return (Integer)this.xpPerLevel.get((Integer)this.playerLevels.get(player.getUniqueId()) + 0);
    	}
    }


    public String getFormattedPlayerGold(Player player) {
        String decimalFormat = null;
        if (this.getPlayerGold(player) < 10000.0) {
            decimalFormat = (new DecimalFormat("###,###,###,###,###,##0.00g")).format(this.playerGold.getOrDefault(player.getUniqueId(), 0.0));
        } else {
            decimalFormat = (new DecimalFormat("###,###,###,###,###,##0g")).format(this.playerGold.getOrDefault(player.getUniqueId(), 0.0));
        }

        return decimalFormat;
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
    }

    public void readConfig(Player player) {
        this.playerPrestiges.put(player.getUniqueId(), (Integer)ConfigAPI.read(Main.INSTANCE, player, "Prestiges", Integer.class, 0));
        this.playerXP.put(player.getUniqueId(), (Integer)ConfigAPI.read(Main.INSTANCE, player, "XP", Integer.class, 0));
        this.playerGold.put(player.getUniqueId(), (Double)ConfigAPI.read(Main.INSTANCE, player, "Gold", Double.class, 0.0));
        this.playerLevels.put(player.getUniqueId(), (Integer)ConfigAPI.read(Main.INSTANCE, player, "Levels", Integer.class, 1));
    }

    public void writeToConfig() {
        ConfigAPI.write(Main.INSTANCE, "XP", this.playerXP);
        ConfigAPI.write(Main.INSTANCE, "Gold", this.playerGold);
        ConfigAPI.write(Main.INSTANCE, "Prestiges", this.playerPrestiges);
        ConfigAPI.write(Main.INSTANCE, "Levels", this.playerLevels);
    }
}
