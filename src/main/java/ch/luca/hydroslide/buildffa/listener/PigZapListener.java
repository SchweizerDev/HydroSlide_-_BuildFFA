package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PigZapEvent;

public class PigZapListener implements Listener {
	
	@EventHandler
	public void onPigZap(PigZapEvent e) {
		e.setCancelled(true);
	}
}
