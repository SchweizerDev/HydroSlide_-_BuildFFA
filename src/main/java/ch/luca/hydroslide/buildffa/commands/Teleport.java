package ch.luca.hydroslide.buildffa.commands;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {

	private BuildFFA buildFFA;

	public Teleport(BuildFFA buildFFA) {
		this.buildFFA = buildFFA;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(buildFFA.getNoPlayer());
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("buildffa.teleport")) {
			p.sendMessage(buildFFA.getNoPermission());
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(buildFFA.getPrefixUse() + "tp <Spieler> [Spieler]");
		} else if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0].toLowerCase());
			if (target != null) {
				Location targetLocation = target.getLocation();
				p.teleport(targetLocation);
				p.sendMessage(buildFFA.getPrefix() + "Du hast dich zu §e" + target.getName() + " §7teleportiert.");
			} else {
				p.sendMessage(buildFFA.getPlayerNotOnline());
			}
		} else if (args.length == 2) {
			Player target_1 = Bukkit.getPlayer(args[0].toLowerCase());
			Player target_2 = Bukkit.getPlayer(args[1].toLowerCase());
			if ((target_1 != null) && (target_2 != null)) {
				Location tloc2 = target_2.getLocation();
				target_1.teleport(tloc2);
				target_1.sendMessage(buildFFA.getPrefix() + "Du wurdest zu §e" + target_2.getDisplayName() + " §7teleportiert.");
				target_2.sendMessage(buildFFA.getPrefix() + "Der Spieler §e" + target_1.getDisplayName() + " §7wurde zu dir teleportiert.");
				if ((target_1 != p) || (target_2 != p)) {
					p.sendMessage(buildFFA.getPrefix() + "Der Spieler §e" + target_1.getDisplayName() + " §7wurde zu §c" + target_2.getDisplayName() + " §7teleportiert.");
					return true;
				}
			} else {
				p.sendMessage(buildFFA.getPlayerNotOnline());
			}
		} else if (args.length == 3) {
			try {
				Location loc = p.getLocation();
				double x;
				if (args[0].equalsIgnoreCase("~")) {
					x = loc.getX();
				} else {
					x = Integer.parseInt(args[0]);
				}
				double y;
				if (args[0].equalsIgnoreCase("~")) {
					y = loc.getX();
				} else {
					y = Integer.parseInt(args[1]);
				}
				double z;
				if (args[0].equalsIgnoreCase("~")) {
					z = loc.getX();
				} else {
					z = Integer.parseInt(args[2]);
				}
				loc.setX(x);
				loc.setY(y);
				loc.setZ(z);
				p.teleport(loc);
				p.sendMessage(buildFFA.getPrefix() + "Du wurdest teleportiert.");
			} catch (NumberFormatException error) {
				p.sendMessage(buildFFA.getPrefix() + "§cGib eine Zahl an.");
			}
		}
		return false;
	}
}