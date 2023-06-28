//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.PacketType.Status.Server;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import lol.pitremake.skinapi.SkinAPI;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import me.clip.placeholderapi.PlaceholderAPI;
import me.stevemmmmm.configapi.core.ConfigAPI;
import me.stevemmmmm.servercore.core.ServerGame;
import me.stevemmmmm.servercore.core.WorldType;
import me.stevemmmmm.thepitremake.chat.LevelChatFormatting;
import me.stevemmmmm.thepitremake.commands.EnchantCommand;
import me.stevemmmmm.thepitremake.commands.GambleImmunityCommand;
import me.stevemmmmm.thepitremake.commands.GiveArrowCommand;
import me.stevemmmmm.thepitremake.commands.GiveBreadCommand;
import me.stevemmmmm.thepitremake.commands.GiveEggCommand;
import me.stevemmmmm.thepitremake.commands.GiveFreshItemCommand;
import me.stevemmmmm.thepitremake.commands.GiveObsidianCommand;
import me.stevemmmmm.thepitremake.commands.GiveProtCommand;
import me.stevemmmmm.thepitremake.commands.KillAnnouncementCommand;
import me.stevemmmmm.thepitremake.commands.MuteChatCommand;
import me.stevemmmmm.thepitremake.commands.MysticEnchantsCommand;
import me.stevemmmmm.thepitremake.commands.NickCommand;
import me.stevemmmmm.thepitremake.commands.PitAboutCommand;
import me.stevemmmmm.thepitremake.commands.PitHelpCommand;
import me.stevemmmmm.thepitremake.commands.SelectWorldCommand;
import me.stevemmmmm.thepitremake.commands.SetGoldCommand;
import me.stevemmmmm.thepitremake.commands.SetLevelCommand;
import me.stevemmmmm.thepitremake.commands.SetPlayerHealthCommand;
import me.stevemmmmm.thepitremake.commands.SetPrestigeCommand;
import me.stevemmmmm.thepitremake.commands.SpawnCommand;
import me.stevemmmmm.thepitremake.commands.StreakCommand;
import me.stevemmmmm.thepitremake.commands.TogglePvPCommand;
import me.stevemmmmm.thepitremake.commands.UnenchantCommand;
import me.stevemmmmm.thepitremake.enchants.Assassin;
import me.stevemmmmm.thepitremake.enchants.BeatTheSpammers;
import me.stevemmmmm.thepitremake.enchants.Billionaire;
import me.stevemmmmm.thepitremake.enchants.BooBoo;
import me.stevemmmmm.thepitremake.enchants.Bruiser;
import me.stevemmmmm.thepitremake.enchants.BulletTime;
import me.stevemmmmm.thepitremake.enchants.Chipping;
import me.stevemmmmm.thepitremake.enchants.ComboDamage;
import me.stevemmmmm.thepitremake.enchants.ComboHeal;
import me.stevemmmmm.thepitremake.enchants.ComboStun;
import me.stevemmmmm.thepitremake.enchants.ComboSwift;
import me.stevemmmmm.thepitremake.enchants.CounterJanitor;
import me.stevemmmmm.thepitremake.enchants.Cricket;
import me.stevemmmmm.thepitremake.enchants.CriticallyFunky;
import me.stevemmmmm.thepitremake.enchants.Crush;
import me.stevemmmmm.thepitremake.enchants.DevilChicks;
import me.stevemmmmm.thepitremake.enchants.DiamondStomp;
import me.stevemmmmm.thepitremake.enchants.DoubleJump;
import me.stevemmmmm.thepitremake.enchants.Duelist;
import me.stevemmmmm.thepitremake.enchants.EscapePod;
import me.stevemmmmm.thepitremake.enchants.Executioner;
import me.stevemmmmm.thepitremake.enchants.Explosive;
import me.stevemmmmm.thepitremake.enchants.FancyRaider;
import me.stevemmmmm.thepitremake.enchants.Fletching;
import me.stevemmmmm.thepitremake.enchants.FractionalReserve;
import me.stevemmmmm.thepitremake.enchants.Frostbite;
import me.stevemmmmm.thepitremake.enchants.Gamble;
import me.stevemmmmm.thepitremake.enchants.GottaGoFast;
import me.stevemmmmm.thepitremake.enchants.Grasshopper;
import me.stevemmmmm.thepitremake.enchants.Guts;
import me.stevemmmmm.thepitremake.enchants.Healer;
import me.stevemmmmm.thepitremake.enchants.Hearts;
import me.stevemmmmm.thepitremake.enchants.Jumpspammer;
import me.stevemmmmm.thepitremake.enchants.KingBuster;
import me.stevemmmmm.thepitremake.enchants.LastStand;
import me.stevemmmmm.thepitremake.enchants.Lifesteal;
import me.stevemmmmm.thepitremake.enchants.LuckyShot;
import me.stevemmmmm.thepitremake.enchants.Megalongbow;
import me.stevemmmmm.thepitremake.enchants.Mirror;
import me.stevemmmmm.thepitremake.enchants.NotGladiator;
import me.stevemmmmm.thepitremake.enchants.PainFocus;
import me.stevemmmmm.thepitremake.enchants.Parasite;
import me.stevemmmmm.thepitremake.enchants.Peroxide;
import me.stevemmmmm.thepitremake.enchants.Perun;
import me.stevemmmmm.thepitremake.enchants.Pitpocket;
import me.stevemmmmm.thepitremake.enchants.Prick;
import me.stevemmmmm.thepitremake.enchants.Protection;
import me.stevemmmmm.thepitremake.enchants.Pullbow;
import me.stevemmmmm.thepitremake.enchants.Punisher;
import me.stevemmmmm.thepitremake.enchants.PushComesToShove;
import me.stevemmmmm.thepitremake.enchants.RespawnAbsorption;
import me.stevemmmmm.thepitremake.enchants.Revitalize;
import me.stevemmmmm.thepitremake.enchants.RingArmor;
import me.stevemmmmm.thepitremake.enchants.Robinhood;
import me.stevemmmmm.thepitremake.enchants.Sharp;
import me.stevemmmmm.thepitremake.enchants.Solitude;
import me.stevemmmmm.thepitremake.enchants.SpeedyHit;
import me.stevemmmmm.thepitremake.enchants.SprintDrain;
import me.stevemmmmm.thepitremake.enchants.Sweaty;
import me.stevemmmmm.thepitremake.enchants.Telebow;
import me.stevemmmmm.thepitremake.enchants.ThePunch;
import me.stevemmmmm.thepitremake.enchants.TrueShot;
import me.stevemmmmm.thepitremake.enchants.Volley;
import me.stevemmmmm.thepitremake.enchants.Wasp;
import me.stevemmmmm.thepitremake.game.Bread;
import me.stevemmmmm.thepitremake.game.ChatManagement;
import me.stevemmmmm.thepitremake.game.CombatManager;
import me.stevemmmmm.thepitremake.game.DamageIndicator;
import me.stevemmmmm.thepitremake.game.EnderChest;
import me.stevemmmmm.thepitremake.game.FirstAidEgg;
import me.stevemmmmm.thepitremake.game.MysticWell;
import me.stevemmmmm.thepitremake.game.Obsidian;
import me.stevemmmmm.thepitremake.game.RegionManager;
import me.stevemmmmm.thepitremake.game.StopLiquidFlow;
import me.stevemmmmm.thepitremake.game.WorldSelection;
import me.stevemmmmm.thepitremake.game.duels.DuelingManager;
import me.stevemmmmm.thepitremake.game.duels.GameUtility;
import me.stevemmmmm.thepitremake.game.killstreaks.Death;
import me.stevemmmmm.thepitremake.managers.enchants.LivesSystem;
import me.stevemmmmm.thepitremake.managers.enchants.BowManager;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.other.GrindingSystem;
import me.stevemmmmm.thepitremake.managers.other.PitScoreboardManager;
import me.stevemmmmm.thepitremake.managers.other.ServerMOTDInitializer;
import me.stevemmmmm.thepitremake.perks.Vampire;
import me.stevemmmmm.thepitremake.world.AntiFall;
import me.stevemmmmm.thepitremake.world.AntiFire;
import me.stevemmmmm.thepitremake.world.AutoRespawn;
import me.stevemmmmm.thepitremake.world.ClearArrows;
import me.stevemmmmm.thepitremake.world.DeveloperUpdates;
import me.stevemmmmm.thepitremake.world.PlayerUtility;
import me.stevemmmmm.thepitremake.world.WorldProtection;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements ServerGame, Listener {
    public static Main INSTANCE;
    private boolean titlechanged = false;
    public static String prefix;
    public static String version;
    private static final int WORKER_THREADS = 4;

    static {
        prefix = ChatColor.YELLOW.toString() + ChatColor.BOLD + "Fabian's Pit Sandbox " + ChatColor.LIGHT_PURPLE + " ▶  ";
        version = "1.0";
    }

    public Main() {
    }

    public void onEnable() {
        INSTANCE = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            this.getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            (new BukkitRunnable() {
                public void run() {
                    try {
                        Field a = packet.getClass().getDeclaredField("a");
                        a.setAccessible(true);
                        Field b = packet.getClass().getDeclaredField("b");
                        b.setAccessible(true);
                        Object header1 = new ChatComponentText("§e§lFABIAN'S PIT SANDBOX");
    					Object header2 = new ChatComponentText("§6§lFABIAN'S PIT SANDBOX");
    					String _footer = "§6TPS: §e§l%server_tps_1% §6 Players: §e§l" + Bukkit.getOnlinePlayers().size() + " §etraficantes.wtf";
                        Iterator var7 = Bukkit.getOnlinePlayers().iterator();

                        Player player;
                        while(var7.hasNext()) {
                            player = (Player)var7.next();
                            Object footer = new ChatComponentText(PlaceholderAPI.setPlaceholders(player, _footer));
                            b.set(packet, footer);
                        }

                        if (Main.this.titlechanged) {
                            a.set(packet, header2);
                            Main.this.titlechanged = false;
                        } else {
                            a.set(packet, header1);
                            Main.this.titlechanged = true;
                        }

                        if (Bukkit.getOnlinePlayers().size() == 0) {
                            return;
                        }

                        var7 = Bukkit.getOnlinePlayers().iterator();

                        while(var7.hasNext()) {
                            player = (Player)var7.next();
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                        }
                    } catch (IllegalAccessException | NoSuchFieldException var9) {
                        var9.printStackTrace();
                    }

                }
            }).runTaskTimer(this, 0L, 20L);
            ProtocolLibrary.getProtocolManager().getAsynchronousManager().registerAsyncHandler(new PacketAdapter(this, ListenerPriority.NORMAL, Arrays.asList(Server.OUT_SERVER_INFO), new ListenerOptions[]{ListenerOptions.ASYNC}) {
                public void onPacketSending(PacketEvent event) {
                    ServerMOTDInitializer.getInstance().handlePingAsync(event, (WrappedServerPing)event.getPacket().getServerPings().read(0));
                }
            }).start(4);
            ConfigAPI.registerConfigWriteLocations(this, new HashMap<String, String>() {
                {
                    this.put("Gold", "stats.gold");
                    this.put("XP", "stats.xp");
                    this.put("Prestiges", "stats.prestige");
                    this.put("Levels", "stats.level");
                }
            });
            ConfigAPI.registerConfigWriter(GrindingSystem.getInstance());
            ConfigAPI.registerConfigReader(GrindingSystem.getInstance());
            Logger log = Bukkit.getLogger();
            log.info("------------------------------------------");
            log.info("The Hypixel Pit Remake by Stevemmmmm");
            log.info("------------------------------------------");
            this.registerEnchants();
            this.registerPerks();
            this.getServer().getPluginManager().registerEvents(new GameUtility(), this);
            this.getServer().getPluginManager().registerEvents(new ClearArrows(), this);
            this.getServer().getPluginManager().registerEvents(new AntiFall(), this);
            this.getServer().getPluginManager().registerEvents(new AutoRespawn(), this);
            this.getServer().getPluginManager().registerEvents(new PlayerUtility(), this);
            this.getServer().getPluginManager().registerEvents(new DeveloperUpdates(), this);
            this.getServer().getPluginManager().registerEvents(new WorldProtection(), this);
            this.getServer().getPluginManager().registerEvents(RegionManager.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(new ServerMOTDInitializer(), this);
            this.getServer().getPluginManager().registerEvents(new ChatManagement(), this);
            this.getServer().getPluginManager().registerEvents(new TogglePvPCommand(), this);
            this.getServer().getPluginManager().registerEvents(new AntiFire(), this);
            this.getServer().getPluginManager().registerEvents(new Death(), this);
            this.getServer().getPluginManager().registerEvents(new LivesSystem(), this);
            this.getServer().getPluginManager().registerEvents(new KillAnnouncementCommand(), this);
            this.getCommand("pitenchant").setExecutor(new EnchantCommand());
            this.getCommand("mysticenchants").setExecutor(new MysticEnchantsCommand());
            this.getCommand("pitabout").setExecutor(new PitAboutCommand());
            this.getCommand("givefreshitem").setExecutor(new GiveFreshItemCommand());
            this.getCommand("giveprot").setExecutor(new GiveProtCommand());
            this.getCommand("setgold").setExecutor(new SetGoldCommand());
            this.getCommand("givebread").setExecutor(new GiveBreadCommand());
            this.getCommand("givearrows").setExecutor(new GiveArrowCommand());
            this.getCommand("giveobsidian").setExecutor(new GiveObsidianCommand());
            this.getCommand("unenchant").setExecutor(new UnenchantCommand());
            this.getCommand("togglepvp").setExecutor(new TogglePvPCommand());
            this.getCommand("selectworld").setExecutor(new SelectWorldCommand());
            this.getCommand("pithelp").setExecutor(new PitHelpCommand());
            this.getCommand("giveegg").setExecutor(new GiveEggCommand());
            this.getCommand("mutechat").setExecutor(new MuteChatCommand());
            this.getCommand("setlevel").setExecutor(new SetLevelCommand());
            this.getCommand("setprestige").setExecutor(new SetPrestigeCommand());
            this.getCommand("gambleimmunity").setExecutor(new GambleImmunityCommand());
            this.getCommand("streak").setExecutor(new StreakCommand());
            this.getCommand("killannouncements").setExecutor(new KillAnnouncementCommand());
            this.getCommand("ka").setExecutor(new KillAnnouncementCommand());
            this.getCommand("setplayerhealth").setExecutor(new SetPlayerHealthCommand());
            this.getCommand("nick").setExecutor(new NickCommand());
            SpawnCommand spawnCommand = new SpawnCommand();
            this.getCommand("spawn").setExecutor(spawnCommand);
            this.getCommand("respawn").setExecutor(spawnCommand);
            this.getServer().getPluginManager().registerEvents(new MysticWell(), this);
            this.getServer().getPluginManager().registerEvents(PitScoreboardManager.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(DuelingManager.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(DamageIndicator.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(CombatManager.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(DamageManager.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(BowManager.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(new LevelChatFormatting(), this);
            this.getServer().getPluginManager().registerEvents(new Bread(), this);
            this.getServer().getPluginManager().registerEvents(new FirstAidEgg(this), this);
            this.getServer().getPluginManager().registerEvents(GrindingSystem.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(Obsidian.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(new StopLiquidFlow(), this);
            this.getServer().getPluginManager().registerEvents(WorldSelection.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(EnderChest.getInstance(), this);
            this.getServer().getPluginManager().registerEvents(this, this);
        }
    }

    public void onDisable() {
        Iterator var2 = Bukkit.getOnlinePlayers().iterator();

        while(var2.hasNext()) {
            Player player = (Player)var2.next();
            ((CraftPlayer)player).disconnect("Server restart!");
        }

        var2 = Bukkit.getWorlds().iterator();

        while(var2.hasNext()) {
            World world = (World)var2.next();
            Iterator var4 = world.getEntities().iterator();

            while(var4.hasNext()) {
                Entity entity = (Entity)var4.next();
                if (entity.isValid()) {
                    entity.remove();
                }
            }
        }

        GrindingSystem.getInstance().writeToConfig();
        Obsidian.getInstance().removeObsidian();
    }

    @EventHandler
	public void onJoin(PlayerJoinEvent event) {
		SkinAPI.getInstance();
		SkinAPI.setSkin(event.getPlayer(), event.getPlayer().getName());
	}

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        GrindingSystem.getInstance().writeToConfig();
    }

    private void registerPerks() {
        this.getServer().getPluginManager().registerEvents(new Vampire(), this);
    }

    private void registerEnchants() {
    	CustomEnchantManager.getInstance().registerEnchant(new Assassin());
    	CustomEnchantManager.getInstance().registerEnchant(new BeatTheSpammers());
    	CustomEnchantManager.getInstance().registerEnchant(new Billionaire());
    	CustomEnchantManager.getInstance().registerEnchant(new BooBoo());
    	CustomEnchantManager.getInstance().registerEnchant(new Bruiser());
    	CustomEnchantManager.getInstance().registerEnchant(new BulletTime());
    	CustomEnchantManager.getInstance().registerEnchant(new Chipping());
    	CustomEnchantManager.getInstance().registerEnchant(new ComboDamage());
    	CustomEnchantManager.getInstance().registerEnchant(new ComboHeal());
    	CustomEnchantManager.getInstance().registerEnchant(new ComboStun());
    	CustomEnchantManager.getInstance().registerEnchant(new ComboSwift());
    	CustomEnchantManager.getInstance().registerEnchant(new CounterJanitor());
    	CustomEnchantManager.getInstance().registerEnchant(new Cricket());
    	CustomEnchantManager.getInstance().registerEnchant(new CriticallyFunky());
    	CustomEnchantManager.getInstance().registerEnchant(new Crush());
    	CustomEnchantManager.getInstance().registerEnchant(new DevilChicks());
    	CustomEnchantManager.getInstance().registerEnchant(new DiamondStomp());
    	CustomEnchantManager.getInstance().registerEnchant(new DoubleJump());
    	CustomEnchantManager.getInstance().registerEnchant(new Duelist());
    	CustomEnchantManager.getInstance().registerEnchant(new EscapePod());
    	CustomEnchantManager.getInstance().registerEnchant(new Executioner());
    	CustomEnchantManager.getInstance().registerEnchant(new Explosive());
    	CustomEnchantManager.getInstance().registerEnchant(new FancyRaider());
    	CustomEnchantManager.getInstance().registerEnchant(new Fletching());
    	CustomEnchantManager.getInstance().registerEnchant(new FractionalReserve());
    	CustomEnchantManager.getInstance().registerEnchant(new Frostbite());
    	CustomEnchantManager.getInstance().registerEnchant(new Gamble());
    	CustomEnchantManager.getInstance().registerEnchant(new GottaGoFast());
    	CustomEnchantManager.getInstance().registerEnchant(new Grasshopper());
    	CustomEnchantManager.getInstance().registerEnchant(new Guts());
    	CustomEnchantManager.getInstance().registerEnchant(new Healer());
    	CustomEnchantManager.getInstance().registerEnchant(new Hearts());
    	CustomEnchantManager.getInstance().registerEnchant(new Jumpspammer());
    	CustomEnchantManager.getInstance().registerEnchant(new KingBuster());
    	CustomEnchantManager.getInstance().registerEnchant(new NotGladiator());
    	CustomEnchantManager.getInstance().registerEnchant(new LastStand());
    	CustomEnchantManager.getInstance().registerEnchant(new Lifesteal());
    	CustomEnchantManager.getInstance().registerEnchant(new LuckyShot());
    	CustomEnchantManager.getInstance().registerEnchant(new Megalongbow());
    	CustomEnchantManager.getInstance().registerEnchant(new Mirror());
    	CustomEnchantManager.getInstance().registerEnchant(new PainFocus());
    	CustomEnchantManager.getInstance().registerEnchant(new Parasite());
    	CustomEnchantManager.getInstance().registerEnchant(new Peroxide());
    	CustomEnchantManager.getInstance().registerEnchant(new Perun());
    	CustomEnchantManager.getInstance().registerEnchant(new Pitpocket());
    	CustomEnchantManager.getInstance().registerEnchant(new Prick());
    	CustomEnchantManager.getInstance().registerEnchant(new Protection());
    	CustomEnchantManager.getInstance().registerEnchant(new Pullbow());
    	CustomEnchantManager.getInstance().registerEnchant(new Punisher());
    	CustomEnchantManager.getInstance().registerEnchant(new PushComesToShove());
    	CustomEnchantManager.getInstance().registerEnchant(new RespawnAbsorption());
    	CustomEnchantManager.getInstance().registerEnchant(new Revitalize());
    	CustomEnchantManager.getInstance().registerEnchant(new RingArmor());
    	CustomEnchantManager.getInstance().registerEnchant(new Robinhood());
    	CustomEnchantManager.getInstance().registerEnchant(new Sharp());
    	CustomEnchantManager.getInstance().registerEnchant(new Solitude());
    	CustomEnchantManager.getInstance().registerEnchant(new SpeedyHit());
    	CustomEnchantManager.getInstance().registerEnchant(new SprintDrain());
    	CustomEnchantManager.getInstance().registerEnchant(new Sweaty());
    	CustomEnchantManager.getInstance().registerEnchant(new Telebow());
    	CustomEnchantManager.getInstance().registerEnchant(new ThePunch());
    	CustomEnchantManager.getInstance().registerEnchant(new TrueShot());
    	CustomEnchantManager.getInstance().registerEnchant(new Volley());
    	CustomEnchantManager.getInstance().registerEnchant(new Wasp());
    }

    public void telebowTrail(final Arrow proj) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, (Runnable)(new BukkitRunnable() {
            public void run() {
                Location loc = proj.getLocation();
                if (!proj.isOnGround() && !proj.isDead()) {
                    PacketPlayOutWorldParticles spell = new PacketPlayOutWorldParticles(EnumParticle.SPELL_MOB, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 1.0F, 1.0F, 1.0F, 1.0F, 3, new int[0]);
                    Iterator var4 = Bukkit.getOnlinePlayers().iterator();

                    while(var4.hasNext()) {
                        Player p = (Player)var4.next();
                        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(spell);
                    }
                }

            }
        }).runTaskTimerAsynchronously(this, 0L, 1L));
    }

    public static Main getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Main();
        }

        return INSTANCE;
    }

    public String getGameName() {
        return "Pit Remake";
    }

    public String getReferenceName() {
        return "ThePit";
    }

    public World getGameMap() {
        return Bukkit.getWorld("world");
    }

    public WorldType getWorldType() {
        return WorldType.MONITORED;
    }
}
