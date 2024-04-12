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
import me.stevemmmmm.thepitremake.commands.*;
import me.stevemmmmm.thepitremake.enchants.pants.*;
import me.stevemmmmm.thepitremake.enchants.special.Guardian;
import me.stevemmmmm.thepitremake.enchants.special.Royalty;
import me.stevemmmmm.thepitremake.enchants.bow.*;
import me.stevemmmmm.thepitremake.enchants.sword.*;
import me.stevemmmmm.thepitremake.enchants.universal.*;
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
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchant;
import me.stevemmmmm.thepitremake.managers.enchants.CustomEnchantManager;
import me.stevemmmmm.thepitremake.managers.enchants.DamageManager;
import me.stevemmmmm.thepitremake.managers.other.*;
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
                	ServerMOTDInitializer.getInstance(Main.getInstance()).handlePingAsync(event, (WrappedServerPing)event.getPacket().getServerPings().read(0));

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
            
            registerEnchants();
            registerPerks();
            
            getServer().getPluginManager().registerEvents(new GameUtility(), this);
            getServer().getPluginManager().registerEvents(new ClearArrows(), this);
            getServer().getPluginManager().registerEvents(new AntiFall(), this);
            getServer().getPluginManager().registerEvents(new AutoRespawn(), this);
            getServer().getPluginManager().registerEvents(new PlayerUtility(), this);
            getServer().getPluginManager().registerEvents(new DeveloperUpdates(), this);
            getServer().getPluginManager().registerEvents(new WorldProtection(), this);
            getServer().getPluginManager().registerEvents(RegionManager.getInstance(), this);
            getServer().getPluginManager().registerEvents(new ServerMOTDInitializer(this), this);
            getServer().getPluginManager().registerEvents(new ChatManagement(), this);
            getServer().getPluginManager().registerEvents(new TogglePvPCommand(), this);
            getServer().getPluginManager().registerEvents(new AntiFire(), this);
            getServer().getPluginManager().registerEvents(new Death(), this);
            getServer().getPluginManager().registerEvents(new LivesSystem(), this);
            getServer().getPluginManager().registerEvents(new KillAnnouncementCommand(), this);
            
            getCommand("pitenchant").setExecutor(new EnchantCommand());
            getCommand("mysticenchants").setExecutor(new MysticEnchantsCommand());
            getCommand("pitabout").setExecutor(new PitAboutCommand());
            getCommand("givefreshitem").setExecutor(new GiveFreshItemCommand());
            getCommand("giveprot").setExecutor(new GiveProtCommand());
            getCommand("givearmageddon").setExecutor(new GiveArmageddonCommand());
            getCommand("givearch").setExecutor(new GiveArchCommand());
            getCommand("givegolden").setExecutor(new GiveGoldenCommand());
            getCommand("givefeather").setExecutor(new GiveFeatherCommand());
            getCommand("setgold").setExecutor(new SetGoldCommand());
            getCommand("givebread").setExecutor(new GiveBreadCommand());
            getCommand("givearrows").setExecutor(new GiveArrowCommand());
            getCommand("giveobsidian").setExecutor(new GiveObsidianCommand());
            getCommand("unenchant").setExecutor(new UnenchantCommand());
            getCommand("togglepvp").setExecutor(new TogglePvPCommand());
            getCommand("selectworld").setExecutor(new SelectWorldCommand());
            getCommand("pithelp").setExecutor(new PitHelpCommand());
            getCommand("giveegg").setExecutor(new GiveEggCommand());
            getCommand("mutechat").setExecutor(new MuteChatCommand());
            getCommand("setlevel").setExecutor(new SetLevelCommand());
            getCommand("setprestige").setExecutor(new SetPrestigeCommand());
            getCommand("gambleimmunity").setExecutor(new GambleImmunityCommand());
            getCommand("streak").setExecutor(new StreakCommand());
            getCommand("killannouncements").setExecutor(new KillAnnouncementCommand());
            getCommand("ka").setExecutor(new KillAnnouncementCommand());
            getCommand("setplayerhealth").setExecutor(new SetPlayerHealthCommand());
            getCommand("nick").setExecutor(new NickCommand());
            SpawnCommand spawnCommand = new SpawnCommand();
            getCommand("spawn").setExecutor(spawnCommand);
            getCommand("respawn").setExecutor(spawnCommand);
            getCommand("reloadcommand").setExecutor(new Reload(this));
            
            getServer().getPluginManager().registerEvents(new MysticWell(), this);
            getServer().getPluginManager().registerEvents(PitScoreboardManager.getInstance(), this);
            getServer().getPluginManager().registerEvents(DuelingManager.getInstance(), this);
            getServer().getPluginManager().registerEvents(DamageIndicator.getInstance(), this);
            getServer().getPluginManager().registerEvents(CombatManager.getInstance(), this);
            getServer().getPluginManager().registerEvents(DamageManager.getInstance(), this);
            getServer().getPluginManager().registerEvents(BowManager.getInstance(), this);
            getServer().getPluginManager().registerEvents(new LevelChatFormatting(), this);
            getServer().getPluginManager().registerEvents(new Bread(), this);
            getServer().getPluginManager().registerEvents(new FirstAidEgg(this), this);
            getServer().getPluginManager().registerEvents(GrindingSystem.getInstance(), this);
            getServer().getPluginManager().registerEvents(Obsidian.getInstance(), this);
            getServer().getPluginManager().registerEvents(new StopLiquidFlow(), this);
            getServer().getPluginManager().registerEvents(WorldSelection.getInstance(), this);
            getServer().getPluginManager().registerEvents(EnderChest.getInstance(), this);
            getServer().getPluginManager().registerEvents(this, this);
            
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
    
    CustomEnchant[] enchantmentsToRegister = {
    			
    		//Pants enchants
    		new Assassin(),
    		new BooBoo(),
    		new Cricket(),
    		new CriticallyFunky(),
    		new DoubleJump(),
    		new EscapePod(),
    		new FractionalReserve(),
    		new GottaGoFast(),
    		new Hearts(),
    		new LastStand(),
    		new Mirror(),
    		new NotGladiator(),
    		new Peroxide(),
    		new Prick(),
    		new Protection(),
    		new RespawnAbsorption(),
    		new Revitalize(),
    		new RingArmor(),
    		new Solitude(),
    		new TNT(),
    		
    		// Bow Enchants
    		new Chipping(),
    		new DevilChicks(),
    		new Explosive(),
    		new Fletching(),
    		new Jumpspammer(),
    		new LuckyShot(),
    		new Megalongbow(),
    		new Parasite(),
    		new Pullbow(),
    		new PushComesToShove(),
    		new Robinhood(),
    		new SprintDrain(),
    		new Telebow(),
    		new TrueShot(),
    		new Volley(),
    		new Wasp(),

    		// Sword Enchants
    		new BeatTheSpammers(),
    		new Berserker(),
    		new Billionaire(),
    		new BountyReaper(),
    		new Bruiser(),
    		new BulletTime(),
    		new ComboDamage(),
    		new ComboHeal(),
    		new ComboStun(),
    		new ComboSwift(),
    		new CounterJanitor(),
    		new Crush(),
    		new DiamondStomp(),
    		new Duelist(),
    		new Executioner(),
    		new FancyRaider(),
    		new Frostbite(),
    		new Gamble(),
    		new GoldAndBoosted(),
    		new Grasshopper(),
    		new Guts(),
    		new Healer(),
    		new Hemorrhage(),
    		new KingBuster(),
    		new Knockback(),
    		new Lifesteal(),
    		new PainFocus(),
    		new Perun(),
    		new Pitpocket(),
    		new Punisher(),
    		new Revengeance(),
    		new Shark(),
    		new Sharp(),
    		new SpeedyHit(),
    		new SpeedyKill(),
    		new ThePunch(),
    		
    		// Universal enchants
    		
    		new CriticallyRich(),
    		new GoldBoost(),
    		new GoldBump(),
    		new Moctezuma(),
    		new PantsRadar(),
    		new StrikeGold(),
    		new Sweaty(),
    		new XPBoost(),
    		new XPBump(),
    		
    		// Special enchants
    		new Guardian(),
    		new Royalty()
    	};
    
    private void registerEnchants() {
        CustomEnchantManager enchantManager = CustomEnchantManager.getInstance();
        for (CustomEnchant enchantment : enchantmentsToRegister) {
            enchantManager.registerEnchant(enchantment);
        }
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
