package ch.luca.hydroslide.buildffa.commands;

import java.io.File;
import java.io.IOException;

import ch.luca.hydroslide.buildffa.BuildFFA;
import ch.luca.hydroslide.buildffa.util.LocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Setup implements CommandExecutor {

	private BuildFFA buildFFA;

	public Setup(BuildFFA buildFFA) {
		this.buildFFA = buildFFA;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(buildFFA.getNoPlayer());
			return true;
		}
		Player p = (Player) sender;
		
		if(!p.isOp()) {
			p.sendMessage(buildFFA.getNoPermission());
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setspawn")) {
				File configFile = new File(buildFFA.getDataFolder().getPath(), "config.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				buildFFA.setSpawnLocation(p.getLocation());
				cfg.set("Spawn", LocationUtil.locationToString(p.getLocation()));
				try {
					cfg.save(configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(buildFFA.getPrefix() + "Der Spawn wurde gesetzt.");
				return true;
			} else if(args[0].equalsIgnoreCase("setkampf")) {
				File configFile = new File(buildFFA.getDataFolder().getPath(), "config.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				int y = p.getLocation().getBlockY();
				buildFFA.setFightHeight(y);
				cfg.set("FightHeight", y);
				try {
					cfg.save(configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(buildFFA.getPrefix() + "Die Kampf-Höhe wurde gesetzt.");
				return true;
			} else if(args[0].equalsIgnoreCase("settod")) {
				File configFile = new File(buildFFA.getDataFolder().getPath(), "config.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				int y = p.getLocation().getBlockY();
				buildFFA.setDeathHeight(y);
				cfg.set("DeathHeight", y);
				try {
					cfg.save(configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(buildFFA.getPrefix() + "Die Todes-Höhe wurde gesetzt.");
				return true;
			}
		}
		p.sendMessage(buildFFA.getPrefixUse() + "setup setspawn");
		p.sendMessage(buildFFA.getPrefixUse() + "setup setkampf");
		p.sendMessage(buildFFA.getPrefixUse() + "setup settod");
		return true;
	}
}
