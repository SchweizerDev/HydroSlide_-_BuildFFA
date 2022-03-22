package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

public class HangingBreakListener implements Listener {
	
	@EventHandler
	public void onHangingBreak(HangingBreakEvent e) {
		e.setCancelled(true);
	}
}
