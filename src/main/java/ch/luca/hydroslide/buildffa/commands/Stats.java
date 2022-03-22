package ch.luca.hydroslide.buildffa.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

import ch.luca.hydroslide.buildffa.BuildFFA;
import ch.luca.hydroslide.buildffa.user.User;
import ch.luca.cubeslide.coinsapi.CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(BuildFFA.getNotPlayer());
			return true;
		}
		Player p = (Player) sender;

		if(!BuildFFA.getInstance().getUserManager().getUser(p).checkDelay()) {
			p.sendMessage(BuildFFA.getPrefix() + "§cBitte warte kurz...");
			return true;
		}
		if(args.length == 1) {
			Player p2 = Bukkit.getPlayer(args[0]);
			if(p2 == null) {
				BuildFFA.getInstance().getBuildFFADatabase().executeQuery("SELECT * FROM BuildFFA WHERE Name='" + args[0] + "'", true, new Consumer<ResultSet>() {

					@Override
					public void accept(ResultSet rs) {
						if(rs == null) return;
						try {
							if(rs.next()) {
								p.sendMessage(BuildFFA.getHeader());
								int rank = BuildFFA.getInstance().getBuildFFADatabase().getRank(rs.getString("UUID"));
								p.sendMessage(BuildFFA.getPrefix() + "§7Name §8➼ §e" + rs.getString("Name"));
								p.sendMessage(BuildFFA.getPrefix() + "§7Coins §8➼ §6" + CoinsAPI.getInstance().getCoinsRepository().getCoins(p.getUniqueId()));
								p.sendMessage(BuildFFA.getPrefix());
								p.sendMessage(BuildFFA.getPrefix() + "§7Kills §8➼ §a" + rs.getInt("Kills"));
								p.sendMessage(BuildFFA.getPrefix() + "§7Tode §8➼ §c" + rs.getInt("Deaths"));
								p.sendMessage(BuildFFA.getPrefix() + "§7K/D §8➼ §5" + getKd(rs.getInt("Kills"), rs.getInt("Deaths")));
								p.sendMessage(BuildFFA.getPrefix());
								p.sendMessage(BuildFFA.getPrefix() + "§7Platz §8➼ §4" + rank);
								p.sendMessage(BuildFFA.getFooter());
								return;
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						p.sendMessage(BuildFFA.getNeverOnline());
						return;
					}
				});
				return true;
			}
			User user = BuildFFA.getInstance().getUserManager().getUser(p2);
			Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> {
				p.sendMessage(BuildFFA.getHeader());
				int rank = BuildFFA.getInstance().getBuildFFADatabase().getRank(p2.getUniqueId().toString());
				p.sendMessage(BuildFFA.getPrefix() + "§7Name §8➼ §e" + p2.getName());
				p.sendMessage(BuildFFA.getPrefix() + "§7Coins §8➼ §6" + CoinsAPI.getInstance().getCoinsRepository().getCoins(p2.getUniqueId()));
				p.sendMessage(BuildFFA.getPrefix());
				p.sendMessage(BuildFFA.getPrefix() + "§7Kills §8➼ §a" + user.getKills());
				p.sendMessage(BuildFFA.getPrefix() + "§7Tode §8➼ §c" + user.getDeaths());
				p.sendMessage(BuildFFA.getPrefix() + "§7K/D §8➼ §5" + getKd(user.getKills(), user.getDeaths()));
				p.sendMessage(BuildFFA.getPrefix());
				p.sendMessage(BuildFFA.getPrefix() + "§7Platz §8➼ §4" + rank);
				p.sendMessage(BuildFFA.getFooter());
			});
			return true;
		} 
		User user = BuildFFA.getInstance().getUserManager().getUser(p);
		Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> {
			p.sendMessage(BuildFFA.getHeader());
			int rank = BuildFFA.getInstance().getBuildFFADatabase().getRank(p.getUniqueId().toString());
			p.sendMessage(BuildFFA.getPrefix() + "§7Name §8➼ §e" + p.getName());
			p.sendMessage(BuildFFA.getPrefix() + "§7Coins §8➼ §6" + CoinsAPI.getInstance().getCoinsRepository().getCoins(p.getUniqueId()));
			p.sendMessage(BuildFFA.getPrefix());
			p.sendMessage(BuildFFA.getPrefix() + "§7Kills §8➼ §a" + user.getKills());
			p.sendMessage(BuildFFA.getPrefix() + "§7Tode §8➼ §c" + user.getDeaths());
			p.sendMessage(BuildFFA.getPrefix() + "§7K/D §8➼ §5" + getKd(user.getKills(), user.getDeaths()));
			p.sendMessage(BuildFFA.getPrefix());
			p.sendMessage(BuildFFA.getPrefix() + "§7Platz §8➼ §4" + rank);
			p.sendMessage(BuildFFA.getFooter());
		});
		return true;
	}
	private double getKd(int kills, int deaths) {
		if (deaths != 0) {
			double KD = (double)kills / (double)deaths;
			KD = (double)Math.round(KD * 10.0) / 10.0;
			return KD;
		}
		return 0.0;
	}
}
