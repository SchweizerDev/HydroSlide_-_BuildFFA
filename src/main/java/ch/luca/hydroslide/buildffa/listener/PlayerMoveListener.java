package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(BuildFFA.getInstance().getAllowBuild().contains(p)) return;
		if(p.getLocation().getBlockY() <= BuildFFA.getInstance().getDeathHeight()) {
			BuildFFA.getInstance().getUserManager().getUser(p).death(true);
		}
	}

}
