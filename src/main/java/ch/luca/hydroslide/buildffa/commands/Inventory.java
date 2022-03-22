package ch.luca.hydroslide.buildffa.commands;

import ch.luca.hydroslide.buildffa.BuildFFA;
import ch.luca.hydroslide.buildffa.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Inventory implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(BuildFFA.getNotPlayer());
			return true;
		}
		Player p = (Player) sender;

		if(p.getLocation().getBlockY() < BuildFFA.getInstance().getFightHeight()) {
			p.sendMessage(BuildFFA.getPrefix() + "§cDu kannst dein Inventar nur am Spawn sortieren.");
			return true;
		}
		User user = BuildFFA.getInstance().getUserManager().getUser(p);
		p.openInventory(BuildFFA.getInstance().getItemManager().getChangeInventory());
		user.setInventory();
		return true;
	}
}
