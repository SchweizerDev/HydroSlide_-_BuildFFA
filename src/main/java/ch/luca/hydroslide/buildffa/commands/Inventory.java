package ch.luca.hydroslide.buildffa.commands;

import ch.luca.hydroslide.buildffa.BuildFFA;
import ch.luca.hydroslide.buildffa.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Inventory implements CommandExecutor {

	private BuildFFA buildFFA;

	public Inventory(BuildFFA buildFFA) {
		this.buildFFA = buildFFA;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(buildFFA.getNoPlayer());
			return true;
		}
		Player p = (Player) sender;

		if(p.getLocation().getBlockY() < BuildFFA.getInstance().getFightHeight()) {
			p.sendMessage(buildFFA.getPrefix() + "§cDu kannst dein Inventar nur am Spawn sortieren.");
			return true;
		}
		User user = buildFFA.getUserManager().getUser(p);
		p.openInventory(buildFFA.getItemManager().getChangeInventory());
		user.setInventory();
		return true;
	}
}
