 package ch.luca.hydroslide.buildffa;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.luca.hydroslide.buildffa.util.TitleAPI;
import ch.luca.hydroslide.buildffa.database.BuildFFADatabase;
import ch.luca.hydroslide.buildffa.item.ItemManager;
import ch.luca.hydroslide.buildffa.user.UserManager;
import ch.luca.hydroslide.buildffa.util.LocationUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import com.google.common.reflect.ClassPath;

import ch.luca.hydroslide.buildffa.commands.Build;
import ch.luca.hydroslide.buildffa.commands.Setup;

import ch.luca.hydroslide.buildffa.commands.Gamemode;
import ch.luca.hydroslide.buildffa.commands.Inventory;
import ch.luca.hydroslide.buildffa.commands.Ranking;
import ch.luca.hydroslide.buildffa.commands.Stats;
import ch.luca.hydroslide.buildffa.commands.Teleport;
import ch.luca.hydroslide.buildffa.commands.Vanish;

 public class BuildFFA extends JavaPlugin {

	//INSTANCE
	@Getter
	private static BuildFFA instance;

	//CHAT PREFIX
	@Getter
	private static String prefix = "§5BuildFFA §a✧ §7";

	@Getter
	private static String use = "§5BuildFFA §a✧ §cBenutze: §e";

	@Getter
	private static String notPlayer = "§5BuildFFA §a✧ §cDu bist leider kein Spieler.";

	@Getter
	private static String notOnline = "§5BuildFFA §a✧ §cDer angegebene Spieler ist momentan nicht online.";

	@Getter
	private static String neverOnline = "§3§lCube§bSlide §a✧ §cDer angegebene Spieler war noch nie auf CubeSlide.";

	@Getter
	private static String noPerms = "§5BuildFFA §a✧ §cDazu fehlen dir die nötigen Rechte.";

	@Getter
	private static String header = "§8§l§m*-*-*-*-*-*-*-*§3§l Cube§bSlide §8§l§m*-*-*-*-*-*-*-*";
	@Getter
	private static String footer = "§8§l§m*-*-*-*-*-*-*§b§l§m-*-*-*-*-*-*-§8§l§m*-*-*-*-*-*-*";

	//MYSQL POOL
	@Getter
	private BasicDataSource sqlPool;
	//LOBBY DATABASE
	@Getter
	private BuildFFADatabase buildFFADatabase;

	//USER MANAGER
	@Getter
	private UserManager userManager;
	
	//API
	@Getter
	private TitleAPI titelapi;
	
	//ITEM MANAGER
	@Getter
	private ItemManager itemManager;
	
	//SCOREBOARD
	@Getter
	private Scoreboard scoreboard;

	//LOCATION
	@Getter
	@Setter
	private Location spawnLocation;
	//KAMPF & TOD HÖHE
	@Getter
	@Setter
	private int fightHeight = 0, deathHeight = 0;
	
	//ALLOW BUILD
	@Getter
	private List<Player> allowBuild = new ArrayList<>();
	
	//BLOCK DESTROY 
	@Getter
	private Map<Location, Long> blockDestroy = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "BuildFFA wurde §aaktiviert§7!");
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "Author§8: §eCrafter75 & Luca §7| §7Version§8: §a2.0.0");
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "MySQL wird verbunden...");
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "Alle Daten wurden §aerfolgreich §7geladen!");
		try {
			if(!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			File configFile = new File(getDataFolder().getPath(), "config.yml");
			if(!configFile.exists()) {
				configFile.createNewFile();
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				cfg.set("MySQL.Host", "host");
				cfg.set("MySQL.Database", "db");
				cfg.set("MySQL.Password", "pw");
				cfg.set("MySQL.User", "user");
				cfg.set("MySQL.Port", 3306);
				cfg.save(configFile);
			} else {
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);

				if(cfg.get("Spawn") != null) {
					this.spawnLocation = LocationUtil.stringToLocation(cfg.getString("Spawn"));
				}
				if(cfg.get("FightHeight") != null) {
					this.fightHeight = cfg.getInt("FightHeight");
				}
				if(cfg.get("DeathHeight") != null) {
					this.deathHeight = cfg.getInt("DeathHeight");
				}

				try {
					sqlPool = new BasicDataSource();

					sqlPool.setDriverClassName("com.mysql.jdbc.Driver");
					sqlPool.setUrl("jdbc:mysql://" + cfg.getString("MySQL.Host") + ":" + cfg.getInt("MySQL.Port") + "/" + cfg.getString("MySQL.Database"));
					sqlPool.setUsername(cfg.getString("MySQL.User"));
					sqlPool.setPassword(cfg.getString("MySQL.Password"));

					sqlPool.setMaxIdle(30);
					sqlPool.setMinIdle(5);
					sqlPool.setDriverClassLoader(BuildFFA.class.getClassLoader());

					System.out.println("[BuildFFA] MySQL connected!");

					Connection connection = null;
					try {
						connection = sqlPool.getConnection();

						PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS BuildFFA (UUID VARCHAR(100), Name VARCHAR(100), Kills INT(100), Deaths INT(100), Coins INT(100), Inventory LONGTEXT, PRIMARY KEY(UUID))");
						preparedStatement.executeUpdate();
						preparedStatement.close();
					} catch(SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if(connection != null) {
								connection.close();
							}
						} catch(Exception exc) {
							exc.printStackTrace();
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.titelapi = new TitleAPI();
		this.buildFFADatabase = new BuildFFADatabase();
		this.userManager = new UserManager();
		this.itemManager = new ItemManager();

		PluginManager pluginManager = Bukkit.getPluginManager();
		getCommand("build").setExecutor(new Build());
		getCommand("setup").setExecutor(new Setup());
		getCommand("inventory").setExecutor(new Inventory());
		getCommand("ranking").setExecutor(new Ranking());
		getCommand("stats").setExecutor(new Stats());
		getCommand("vanish").setExecutor(new Vanish());
		getCommand("teleport").setExecutor(new Teleport());
		getCommand("gamemode").setExecutor(new Gamemode());
		pluginManager.registerEvents(new Vanish(), this);

		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		try {
			for(ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader()).getTopLevelClasses( "ch.luca.cubeslide.buildffa.listener" ) ) {
				@SuppressWarnings("rawtypes")
				Class clazz = Class.forName(classInfo.getName());

				if(Listener.class.isAssignableFrom(clazz)) {
					pluginManager.registerEvents((Listener) clazz.newInstance(), this);
				}
			}
		} catch(IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			long current = System.currentTimeMillis();
			
			Iterator<Entry<Location, Long>> iterator = this.blockDestroy.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<Location, Long> entry = iterator.next();
				Location loc = entry.getKey();
				long time = entry.getValue();
				
				if(loc.getBlock().getType().equals(Material.SANDSTONE) && current >= time+5000) {
					loc.getBlock().setType(Material.RED_SANDSTONE);
					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float)(loc.getX()+0.5), (float)(loc.getY()+0.8), (float)(loc.getZ()+0.5), 1.0f, 1.0f, 1.0f, 0, 5);
					for(Player all : Bukkit.getOnlinePlayers()) {
						((CraftPlayer)all).getHandle().playerConnection.sendPacket(packet);
					}
				}  else if(current >= time+10000) {
					loc.getBlock().setType(Material.AIR);
					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, true, (float)(loc.getX()+0.5), (float)(loc.getY()+0.5), (float)(loc.getZ()+0.5), 0.5f, 0.5f, 0.5f, 0, 3);
					for(Player all : Bukkit.getOnlinePlayers()) {
						((CraftPlayer)all).getHandle().playerConnection.sendPacket(packet);
					}
					iterator.remove();
				}
			}
		}, 1L, 1L);
		for(Player all : Bukkit.getOnlinePlayers()) {
			userManager.getUser(all).createScoreboard();
		}
	}
	@Override
	public void onDisable() {
		if(sqlPool == null || sqlPool.isClosed()) return;
		try {
			sqlPool.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
