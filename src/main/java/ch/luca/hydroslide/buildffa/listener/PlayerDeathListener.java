package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		e.setDroppedExp(0);
		e.getDrops().clear();
		e.setKeepLevel(true);
		e.setDeathMessage(null);
		BuildFFA.getInstance().getUserManager().getUser(p).death(false);
	}

}
