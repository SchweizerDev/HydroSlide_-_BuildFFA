package ch.luca.hydroslide.buildffa.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

import ch.luca.hydroslide.buildffa.BuildFFA;
import ch.luca.hydroslide.buildffa.item.ItemManager;
import ch.luca.hydroslide.buildffa.util.Util;
import ch.luca.hydroslide.buildffa.util.InventoryUtil;
import ch.luca.hydroslide.buildffa.util.PacketScoreboard;
import ch.luca.cubeslide.coinsapi.CoinsAPI;
import de.crafter75.perms.bukkit.api.BukkitRankAPI;
import de.crafter75.perms.global.rank.Rank;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class User {

	@Getter
	private Player player;
	
	@Getter
	private int kills, deaths, coins;
	
	@Getter
	@Setter
	private Player damage;
	
	@Getter
	private ItemStack[] inventorySort;
	
	@Getter
	@Setter
	private PacketScoreboard scoreboard;
	
	private long delay = System.currentTimeMillis();
	
	public User(Player p) {
		this.player = p;
		
		BuildFFA.getInstance().getBuildFFADatabase().executeQuery("SELECT * FROM BuildFFA WHERE UUID='" + p.getUniqueId().toString() + "'", true, new Consumer<ResultSet>() {
			
			@Override
			public void accept(ResultSet rs) {
				if(rs == null) return;
				try {
					if(rs.next()) {
						kills = rs.getInt("Kills");
						deaths = rs.getInt("Deaths");
						coins = rs.getInt("Coins");
						
						inventorySort = InventoryUtil.fromBase64(rs.getString("Inventory"));
						
						createScoreboard();
						setInventory();
						
						BuildFFA.getInstance().getBuildFFADatabase().update("UPDATE BuildFFA SET Name='" + p.getName() + "' WHERE UUID='" + p.getUniqueId().toString() + "'", false);
					} else {
						kills = 0;
						deaths = 0;
						coins = 0;
						inventorySort = BuildFFA.getInstance().getItemManager().getDefaultItems();

						createScoreboard();
						setInventory();
						
						BuildFFA.getInstance().getBuildFFADatabase().update("INSERT INTO BuildFFA VALUES('" + p.getUniqueId().toString() + "','" + p.getName() + "',0,0,0,'" + InventoryUtil.toBase64(inventorySort) + "')", false);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void death(boolean fall) {
		this.player.setHealth(20.0D);
		if(fall) {
			this.player.teleport(BuildFFA.getInstance().getSpawnLocation());
			this.deaths++;
			this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_DEATH, 1F, 1F);
			CoinsAPI.getInstance().getCoinsRepository().hasCoins(player.getUniqueId(), 2, coins -> {
				if(!coins) {
					player.sendMessage(BuildFFA.getPrefix() + "Da du zu wenig Coins hast, wurden dir keine abgezogen.");
					return;
				}
				CoinsAPI.getInstance().getCoinsRepository().removeCoins(player.getUniqueId(), 2);
			});

			this.scoreboard.setLine(1, "  §b➼ §c" + this.deaths);
			this.scoreboard.setLine(7, "  §b➼ §6" + Util.asString(CoinsAPI.getInstance().getCoinsRepository().getCoins(player.getUniqueId())));
			if(this.damage != null) {
				Player d = this.damage;
				this.damage = null;
				
				if(d != null) {
					this.player.sendMessage(BuildFFA.getPrefix() + "Du wurdest von §e" + d.getDisplayName() + " §cgetötet§7!");
					this.player.sendMessage(BuildFFA.getPrefix() + "§c- §62 §7Coins");
					BuildFFA.getInstance().getUserManager().getUser(d).kill(this.player);
				} else {
					this.player.sendMessage(BuildFFA.getPrefix() + "Du bist §cgestorben§7!");
					this.player.sendMessage(BuildFFA.getPrefix() + "§c- §62 §7Coins");
				}
			} else {
				this.player.sendMessage(BuildFFA.getPrefix() + "Du bist §cgestorben§7!");
				this.player.sendMessage(BuildFFA.getPrefix() + "§c- §62 §7Coins");
			}
			setInventory();
			BuildFFA.getInstance().getBuildFFADatabase().update("UPDATE BuildFFA SET Deaths=" + this.deaths + ", Coins=" + this.coins + " WHERE UUID='" + this.player.getUniqueId().toString() + "'", true);
			return;
		}
		Bukkit.getScheduler().runTaskLater(BuildFFA.getInstance(), () -> {
			this.player.teleport(BuildFFA.getInstance().getSpawnLocation());
			this.deaths++;
			this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_DEATH, 1F, 1F);
			CoinsAPI.getInstance().getCoinsRepository().hasCoins(player.getUniqueId(), 2, coins -> {
				if(!coins) {
					player.sendMessage(BuildFFA.getPrefix() + "Da du zu wenig Coins hast, wurden dir keine abgezogen.");
					return;
				}
				CoinsAPI.getInstance().getCoinsRepository().removeCoins(player.getUniqueId(), 2);
			});
			this.scoreboard.setLine(1, "  §b➼ §c" + this.deaths);
			this.scoreboard.setLine(7, "  §b➼ §6" + Util.asString(CoinsAPI.getInstance().getCoinsRepository().getCoins(player.getUniqueId())));
			if(this.damage != null) {
				Player d = this.damage;
				this.damage = null;
				
				if(d != null) {
					this.player.sendMessage(BuildFFA.getPrefix() + "Du wurdest von §e" + d.getDisplayName() + " §cgetötet§7!");
					this.player.sendMessage(BuildFFA.getPrefix() + "§c- §62 §7Coins");
					BuildFFA.getInstance().getUserManager().getUser(d).kill(this.player);
				} else {
					this.player.sendMessage(BuildFFA.getPrefix() + "Du bist §cgestorben§7!");
					this.player.sendMessage(BuildFFA.getPrefix() + "§c- §62 §7Coins");
				}
			} else {
				this.player.sendMessage(BuildFFA.getPrefix() + "Du bist §cgestorben§7!");
				this.player.sendMessage(BuildFFA.getPrefix() + "§c- §62 §7Coins");
			}
			setInventory();
			BuildFFA.getInstance().getBuildFFADatabase().update("UPDATE BuildFFA SET Deaths=" + this.deaths + ", Coins=" + this.coins + " WHERE UUID='" + this.player.getUniqueId().toString() + "'", true);
		}, 2L);
	}
	public void kill(Player p) {
		this.player.setHealth(20.0D);
		this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
		this.kills++;
		if(player.hasPermission("buildffa.doublecoins")) {
			CoinsAPI.getInstance().getCoinsRepository().addCoins(player.getUniqueId(), 10);
			this.player.sendMessage(BuildFFA.getPrefix() + "Du hast §e" + p.getDisplayName() + " §cgetötet§7!");
			this.player.sendMessage(BuildFFA.getPrefix() + "§a+ §65 §7Coins §8(§a+ §65 §7Coinbonus§8)");
		} else {
			CoinsAPI.getInstance().getCoinsRepository().addCoins(player.getUniqueId(), 5);
			this.player.sendMessage(BuildFFA.getPrefix() + "Du hast §e" + p.getDisplayName() + " §cgetötet§7!");
			this.player.sendMessage(BuildFFA.getPrefix() + "§a+ §65 §7Coins");
		}
		this.scoreboard.setLine(4, "  §b➼ §a" + this.kills);
		this.scoreboard.setLine(7, "  §b➼ §6" + Util.asString(CoinsAPI.getInstance().getCoinsRepository().getCoins(player.getUniqueId())));
		BuildFFA.getInstance().getBuildFFADatabase().update("UPDATE BuildFFA SET Kills=" + this.kills + ", Coins=" + this.coins + " WHERE UUID='" + this.player.getUniqueId().toString() + "'", true);
	}
	public void setInventory() {
		PlayerInventory inv = this.player.getInventory();
		inv.setContents(this.inventorySort);
		
		ItemManager itemManager = BuildFFA.getInstance().getItemManager();
		
		inv.setHelmet(itemManager.getHelmet());
		inv.setChestplate(itemManager.getChestplate());
		inv.setLeggings(itemManager.getLeggings());
		inv.setBoots(itemManager.getBoots());
	}
	public void saveInventory() {
		this.inventorySort = this.player.getInventory().getContents();
		BuildFFA.getInstance().getBuildFFADatabase().update("UPDATE BuildFFA SET Inventory='" + InventoryUtil.toBase64(this.inventorySort) + "' WHERE UUID='" + this.player.getUniqueId().toString() + "'", true);
	}
	public void createScoreboard() {
		if(this.scoreboard != null) {
			this.scoreboard.remove();
	    }
		Rank rank = BukkitRankAPI.getRankSync(this.player.getUniqueId());
		String color = "§7";
		String rankName = "Unbekannt";
		if(rank != null) {
			 color = rank.getRankColor();
			 rankName = rank.getRankName();
		}
		this.scoreboard = new PacketScoreboard(this.player);
		this.scoreboard.sendSidebar("§3§lCube§bSlide §a✧ §7BuildFFA");
		this.scoreboard.setLine(1, "  §b➼ §c" + this.deaths);
		this.scoreboard.setLine(2, "§a✧ §7Deine Tode");
		this.scoreboard.setLine(3, "§d");
		this.scoreboard.setLine(4, "  §b➼ §a" + this.kills);
		this.scoreboard.setLine(5, "§a✧ §7Deine Kills");
		this.scoreboard.setLine(6, "§c");
		this.scoreboard.setLine(7, "  §b➼ §6" + Util.asString(CoinsAPI.getInstance().getCoinsRepository().getCoins(player.getUniqueId())));
		this.scoreboard.setLine(8, "§a✧ §7Deine Coins");
		this.scoreboard.setLine(9, "§b");
		this.scoreboard.setLine(10, "  §b➼ §e" + color + this.player.getName());
		this.scoreboard.setLine(11, "§a✧ §7Dein Name");
		this.scoreboard.setLine(12, "§5");
	}
	public boolean checkDelay() {
		if(System.currentTimeMillis() > this.delay+500) {
			this.delay = System.currentTimeMillis();
			return true;
		}
		return false;
	}
}
