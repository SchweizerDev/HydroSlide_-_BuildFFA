package ch.luca.hydroslide.buildffa.commands;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Build implements CommandExecutor {

	private BuildFFA buildFFA;

	public Build(BuildFFA buildFFA) {
		this.buildFFA = buildFFA;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(buildFFA.getNoPlayer());
			return true;
		}
		Player p = (Player) sender;
		
		if(!p.hasPermission("buildffa.build")) {
			p.sendMessage(buildFFA.getNoPermission());
			return true;
		}
		if(args.length == 1) {
			Player p2 = Bukkit.getPlayer(args[0]);
			if(p2 == null) {
				p.sendMessage(buildFFA.getPlayerNotOnline());
				return true;
			}
			if(buildFFA.getAllowBuild().contains(p2)) {
				buildFFA.getAllowBuild().remove(p2);
				p.sendMessage(buildFFA.getPrefix() + "Der §cBuild-Modus §7von §e" + p2.getName() + " §7wurde §cdeaktiviert§7.");
				return true;
			}
			buildFFA.getAllowBuild().add(p2);
			p.sendMessage(buildFFA.getPrefix() + "Der §cBuild-Modus §7von §e" + p2.getName() + " §7wurde §aaktiviert§7.");
			return true;
		} else {
			if(buildFFA.getAllowBuild().contains(p)) {
				buildFFA.getAllowBuild().remove(p);
				p.sendMessage(buildFFA.getPrefix() + "Dein §cBuild-Modus §7wurde §cdeaktiviert§7.");
				return true;
			}
			buildFFA.getAllowBuild().add(p);
			p.sendMessage(buildFFA.getPrefix() + "Dein §cBuild-Modus §7wurde §aaktiviert§7.");
			return true;
		}
	}
}
