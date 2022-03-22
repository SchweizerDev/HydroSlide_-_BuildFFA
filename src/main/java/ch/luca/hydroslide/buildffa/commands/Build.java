package ch.luca.hydroslide.buildffa.commands;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Build implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(BuildFFA.getNotPlayer());
			return true;
		}
		Player p = (Player) sender;
		
		if(!p.hasPermission("buildffa.build")) {
			p.sendMessage(BuildFFA.getNoPerms());
			return true;
		}
		if(args.length == 1) {
			Player p2 = Bukkit.getPlayer(args[0]);
			if(p2 == null) {
				p.sendMessage(BuildFFA.getNotOnline());
				return true;
			}
			if(BuildFFA.getInstance().getAllowBuild().contains(p2)) {
				BuildFFA.getInstance().getAllowBuild().remove(p2);
				p.sendMessage(BuildFFA.getPrefix() + "§e" + p2.getName() + "'s Build-Modus wurde §cdeaktiviert§7.");
				return true;
			}
			BuildFFA.getInstance().getAllowBuild().add(p2);
			p.sendMessage(BuildFFA.getPrefix() + "§e" + p2.getName() + "'s Dein Build-Modus wurde §aaktiviert§7.");
			return true;
		} else {
			if(BuildFFA.getInstance().getAllowBuild().contains(p)) {
				BuildFFA.getInstance().getAllowBuild().remove(p);
				p.sendMessage(BuildFFA.getPrefix() + "Dein Build-Modus wurde §cdeaktiviert§7.");
				return true;
			}
			BuildFFA.getInstance().getAllowBuild().add(p);
			p.sendMessage(BuildFFA.getPrefix() + "Dein Build-Modus wurde §aaktiviert§7.");
			return true;
		}
	}
}
