package ch.luca.hydroslide.buildffa.commands;

import java.util.ArrayList;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class Vanish implements CommandExecutor, Listener {

	private BuildFFA buildFFA;

	public Vanish(BuildFFA buildFFA) {
		this.buildFFA = buildFFA;
	}

	public static ArrayList<Player> vanish = new ArrayList();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(buildFFA.getNoPlayer());
			return true;
		}
		Player p = (Player)sender;
		if(!p.hasPermission("buildffa.vanish")) {
			p.sendMessage(buildFFA.getNoPermission());
			return true;
		}
		if(args.length == 0) {
			if(!vanish.contains(p)) {
				vanish.add(p);
				p.setAllowFlight(true);
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
				p.sendMessage(buildFFA.getPrefix() + "§aDu bist nun im Vanish.");
				for(Player o : Bukkit.getOnlinePlayers()) {
					if(!o.hasPermission("buildffa.vanish")) {
						o.hidePlayer(p);
					}
				}
			} else {
				vanish.remove(p);
				if(!p.getGameMode().equals(GameMode.CREATIVE)) {
					p.setAllowFlight(false);
				}
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
				p.sendMessage(buildFFA.getPrefix()+ "§cDu bist nun nicht mehr im Vanish.");
				for(Player o : Bukkit.getOnlinePlayers()) {
					o.showPlayer(p);
				}
			}
		} else {
			Player t = Bukkit.getPlayer(args[0]);
			if(!p.hasPermission("buildffa.vanish.other")) {
				p.sendMessage(buildFFA.getNoPermission());
				return true;
			}
			if(!vanish.contains(t)) {
				vanish.add(t);
				t.setAllowFlight(true);
				t.playSound(t.getLocation(), Sound.NOTE_PLING, 2, 2);
				t.sendMessage(buildFFA.getPrefix()+ "§aDu bist num im Vanish.");
				p.sendMessage(buildFFA.getPrefix()+ "Der Spieler §e" + t.getName() + " §7ist nun im Vanish.");
				for(Player o : Bukkit.getOnlinePlayers()) {
					if(!o.hasPermission("buildffa.vanish")) {
						o.hidePlayer(t);
					}
				}
			} else {
				vanish.remove(p);
				p.setAllowFlight(false);
				t.playSound(t.getLocation(), Sound.NOTE_BASS, 2, 2);
				t.sendMessage(buildFFA.getPrefix() + "§cDu bist nun nicht mehr im Vanish.");
				p.sendMessage(buildFFA.getPrefix() + "Der Spieler §e" + t.getName() + " §7ist nun nicht mehr im Vanish.");
				for(Player o : Bukkit.getOnlinePlayers()) {
					o.showPlayer(p);
				}
			}
		}
		return true;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		for(Player o : vanish) {
			if(!e.getPlayer().hasPermission("buildffa.vanish")) {
				e.getPlayer().hidePlayer(o);
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(vanish.contains(e.getPlayer())) {
			vanish.remove(e.getPlayer());
			for(Player o : Bukkit.getOnlinePlayers()) {
				o.showPlayer(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void onVanish(EntityDamageByEntityEvent e) {
		Entity p = e.getEntity();
		Entity pd = e.getDamager();
		if(((p instanceof Player)) && ((pd instanceof Player)) && (vanish.contains(pd)) && (!pd.hasPermission("buildffa.vanish.allow"))) {
			e.setCancelled(true);
			pd.sendMessage(buildFFA.getPrefix() + "§cDu kannst im Vanish niemanden schlagen.");
		}
	}

	@EventHandler
	public void onVanishInteract(PlayerInteractEvent e) {
		if ((vanish.contains(e.getPlayer())) && ((e.getClickedBlock().getType() == Material.CHEST) || ((e.getClickedBlock().getType() == Material.TRAPPED_CHEST) && (e.getClickedBlock() != null))) &&
				(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			e.setCancelled(true);
			Chest chest = (Chest)e.getClickedBlock().getState();
			Inventory chestView = Bukkit.createInventory(e.getPlayer(), chest.getInventory().getSize(), "§8=-= §eVanish Chest §8=-=");
			chestView.setContents(chest.getInventory().getContents());
			e.getPlayer().openInventory(chestView);
		}
	}

	@EventHandler
	public void onVanishChest(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(e.getView().getTitle().equals("§8=-= §eVanish Chest §8=-=")) {
			e.setCancelled(true);
			p.sendMessage(buildFFA.getPrefix() + "§cDu kannst im Vanish nicht editieren.");
		}
	}
}