package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreakByEntityListener implements Listener {
	
	@EventHandler
	public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {
		e.setCancelled(true);
	}
}
