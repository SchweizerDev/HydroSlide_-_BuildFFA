package ch.luca.hydroslide.buildffa.commands;

import java.sql.SQLException;
import ch.luca.hydroslide.buildffa.BuildFFA;
import ch.luca.hydroslide.buildffa.user.User;
import ch.luca.hydroslide.coinsapi.CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stats implements CommandExecutor {

	private BuildFFA buildFFA;

	public Stats(BuildFFA buildFFA) {
		this.buildFFA = buildFFA;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(buildFFA.getNoPlayer());
			return true;
		}
		Player p = (Player) sender;
		if(!BuildFFA.getInstance().getUserManager().getUser(p).checkDelay()) {
			p.sendMessage(buildFFA.getPrefix() + "§cBitte warte kurz...");
			return true;
		}
		if(args.length == 1) {
			Player p2 = Bukkit.getPlayer(args[0]);
			if(p2 == null) {
				BuildFFA.getInstance().getBuildFFADatabase().executeQuery("SELECT * FROM BuildFFA WHERE name='" + args[0] + "'", true, rs -> {
					if(rs == null) return;
					try {
						if(rs.next()) {
							int rank = BuildFFA.getInstance().getBuildFFADatabase().getRank(rs.getString("UUID"));
							p.sendMessage(buildFFA.getPrefix() + "Name §b➼ §e" + rs.getString("name"));
							p.sendMessage(buildFFA.getPrefix() + "Coins §b➼ §6" + CoinsAPI.getInstance().getCoinsRepository().getCoins(p.getUniqueId()));
							p.sendMessage(buildFFA.getPrefix() + "Kills §b➼ §a" + rs.getInt("kills"));
							p.sendMessage(buildFFA.getPrefix() + "Tode §b➼ §c" + rs.getInt("deaths"));
							p.sendMessage(buildFFA.getPrefix() + "K/D §b➼ §a" + getKd(rs.getInt("kills"), rs.getInt("deaths")));
							p.sendMessage(buildFFA.getPrefix() + "Rang im Ranking §b➼ §a" + rank);
							return;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					p.sendMessage(buildFFA.getPlayerNeverOnline());
					return;
				});
				return true;
			}
			User user = BuildFFA.getInstance().getUserManager().getUser(p2);
			Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> {
				int rank = BuildFFA.getInstance().getBuildFFADatabase().getRank(p2.getUniqueId().toString());
				p.sendMessage(buildFFA.getPrefix() + "Name §b➼ §e" + p2.getName());
				p.sendMessage(buildFFA.getPrefix() + "Coins §b➼ §6" + CoinsAPI.getInstance().getCoinsRepository().getCoins(p2.getUniqueId()));
				p.sendMessage(buildFFA.getPrefix() + "Kills §b➼ §a" + user.getKills());
				p.sendMessage(buildFFA.getPrefix() + "Tode §b➼ §c" + user.getDeaths());
				p.sendMessage(buildFFA.getPrefix() + "K/D §b➼ §a" + getKd(user.getKills(), user.getDeaths()));
				p.sendMessage(buildFFA.getPrefix() + "Rang im Ranking §b➼ §a" + rank);
			});
			return true;
		} 
		User user = BuildFFA.getInstance().getUserManager().getUser(p);
		Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> {
			int rank = BuildFFA.getInstance().getBuildFFADatabase().getRank(p.getUniqueId().toString());
			p.sendMessage(buildFFA.getPrefix() + "Name §b➼ §e" + p.getName());
			p.sendMessage(buildFFA.getPrefix() + "Coins §b➼ §6" + CoinsAPI.getInstance().getCoinsRepository().getCoins(p.getUniqueId()));
			p.sendMessage(buildFFA.getPrefix() + "Kills §b➼ §a" + user.getKills());
			p.sendMessage(buildFFA.getPrefix() + "Tode §b➼ §c" + user.getDeaths());
			p.sendMessage(buildFFA.getPrefix() + "K/D §b➼ §a" + getKd(user.getKills(), user.getDeaths()));
			p.sendMessage(buildFFA.getPrefix() + "Rang im Ranking §b➼ §a" + rank);
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
