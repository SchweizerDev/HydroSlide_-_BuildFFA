package ch.luca.hydroslide.buildffa.commands;

import java.util.ArrayList;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	
	public static ArrayList<Player> vanish = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(BuildFFA.getNotPlayer());
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("buildffa.vanish")) {
			if (args.length == 0) {
				if (vanish.contains(p)) {
					vanish.remove(p);
					p.setAllowFlight(false);
					p.sendMessage(BuildFFA.getPrefix() + "§cDu bist nun nicht mehr im Vanish.");
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.showPlayer(p);

					}
				} else {
					vanish.add(p);
					p.setAllowFlight(true);
					p.sendMessage(BuildFFA.getPrefix() + "Du bist nun im Vanish und kannst fliegen.");
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (!all.hasPermission("buildffa.vanish")) {
							all.hidePlayer(p);
						}
					}
				}
			} else {
				p.sendMessage(BuildFFA.getUse() + "/vanish");
			}
		} else {
			p.sendMessage(BuildFFA.getNoPerms());
		}
		return false;
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
		if(((p instanceof Player)) && ((pd instanceof Player)) && (vanish.contains(pd)) && (!pd.hasPermission("buildffa.vanish.hitother"))) {
			e.setCancelled(true);
			pd.sendMessage(BuildFFA.getPrefix() + "§cDu kannst im Vanish niemand schlagen!");
		}
	}

	@EventHandler
	public void onVanishInteract(PlayerInteractEvent e) {
		if ((vanish.contains(e.getPlayer())) && ((e.getClickedBlock().getType() == Material.CHEST) || ((e.getClickedBlock().getType() == Material.TRAPPED_CHEST) && (e.getClickedBlock() != null))) &&
				(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			e.setCancelled(true);
			Chest chest = (Chest)e.getClickedBlock().getState();
			Inventory chestview = Bukkit.createInventory(e.getPlayer(), chest.getInventory().getSize(), "§8=-= §eVanish Chest §8=-=");
			chestview.setContents(chest.getInventory().getContents());
			e.getPlayer().openInventory(chestview);
		}
	}

	@EventHandler
	public void onVanishChest(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(e.getInventory().getTitle().equals("§8=-= §eVanish Chest §8=-=")) {
			e.setCancelled(true);
			p.sendMessage(BuildFFA.getPrefix() + "§cDu kannst im Vanish nicht editieren!");
		}
	}
}