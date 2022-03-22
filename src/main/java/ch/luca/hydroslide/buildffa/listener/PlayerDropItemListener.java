package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
}
