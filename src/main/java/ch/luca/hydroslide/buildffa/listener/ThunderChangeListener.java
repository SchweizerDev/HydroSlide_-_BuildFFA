package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;

public class ThunderChangeListener implements Listener {
	
	@EventHandler
	public void onThunderChange(ThunderChangeEvent e) {
		if(e.toThunderState()) {
			e.setCancelled(true);
		}
	}
}
