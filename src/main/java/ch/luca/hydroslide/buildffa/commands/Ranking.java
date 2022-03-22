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
		Bukkit.getScheduler().runTaskAsynchronously(BuildFFA.getInstance(), () -> {
			Connection connection = null;
			try {
				connection = BuildFFA.getInstance().getSqlPool().getConnection();

				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BuildFFA ORDER BY Kills DESC LIMIT 10");
				ResultSet rs = preparedStatement.executeQuery();
				int rank = 0;
				String color = "§7";
				p.sendMessage(BuildFFA.getHeader());
				while(rs.next()) {
					rank++;
					p.sendMessage(BuildFFA.getPrefix() + color + "Platz §c" + rank + " §8➼ §e" + rs.getString("Name") + " §8| §a" + rs.getInt("Kills") + " Kills");
					if(color.equals("§6§l")) {
						color = "§c§l";
					} else if(color.equals("§c§l")) {
						color = "§7§l";
					} else if(color.equals("§7§l")) {
						color = "§b";
					}
				}
				p.sendMessage(BuildFFA.getFooter());
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
