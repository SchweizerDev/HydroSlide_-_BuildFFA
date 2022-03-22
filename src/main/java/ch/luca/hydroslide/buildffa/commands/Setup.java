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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(BuildFFA.getNotPlayer());
			return true;
		}
		Player p = (Player) sender;
		
		if(!p.isOp()) {
			p.sendMessage(BuildFFA.getNoPerms());
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setspawn")) {
				File configFile = new File(BuildFFA.getInstance().getDataFolder().getPath(), "config.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				
				BuildFFA.getInstance().setSpawnLocation(p.getLocation());
				cfg.set("Spawn", LocationUtil.locationToString(p.getLocation()));
				try {
					cfg.save(configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(BuildFFA.getPrefix() + "Spawn gesetzt.");
				return true;
			} else if(args[0].equalsIgnoreCase("setkampf")) {
				File configFile = new File(BuildFFA.getInstance().getDataFolder().getPath(), "config.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				
				int y = p.getLocation().getBlockY();
				BuildFFA.getInstance().setFightHeight(y);
				cfg.set("FightHeight", y);
				try {
					cfg.save(configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(BuildFFA.getPrefix() + "Kampf Höhe gesetzt.");
				return true;
			} else if(args[0].equalsIgnoreCase("settod")) {
				File configFile = new File(BuildFFA.getInstance().getDataFolder().getPath(), "config.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
				
				int y = p.getLocation().getBlockY();
				BuildFFA.getInstance().setDeathHeight(y);
				cfg.set("DeathHeight", y);
				try {
					cfg.save(configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(BuildFFA.getPrefix() + "Tod Höhe gesetzt.");
				return true;
			}
		}
		p.sendMessage(BuildFFA.getHeader());
		p.sendMessage(BuildFFA.getUse() + "/setup setspawn");
		p.sendMessage(BuildFFA.getUse() + "/setup setkampf");
		p.sendMessage(BuildFFA.getUse() + "/setup settod");
		p.sendMessage(BuildFFA.getFooter());
		return true;
	}
}
