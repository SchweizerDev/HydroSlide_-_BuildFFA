package ch.luca.hydroslide.buildffa.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ranking implements CommandExecutor {

	private BuildFFA buildFFA;

	public Ranking(BuildFFA buildFFA) {
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
		Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> {
			Connection connection = null;
			try {
				connection = BuildFFA.getInstance().getSqlPool().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BuildFFA ORDER BY kills DESC LIMIT 10");
				ResultSet rs = preparedStatement.executeQuery();
				p.sendMessage(buildFFA.getPrefix() + "§cRanking wird geladen...");
				while(rs.next()) {
					p.sendMessage(buildFFA.getPrefix() + "Name §b➼ §e" + rs.getString("name") + " §8| §7Kills §b➼ §a" + rs.getInt("kills"));
				}
				preparedStatement.close();
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(connection != null) {
						connection.close();
					}
				} catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		return true;
	}
}
