package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		for(Team t : BuildFFA.getInstance().getScoreboard().getTeams()) {
			if(t.hasEntry(p.getName())) {
				t.removeEntry(p.getName());
			}
		}
		BuildFFA.getInstance().getUserManager().removeUser(p);
	}
}
