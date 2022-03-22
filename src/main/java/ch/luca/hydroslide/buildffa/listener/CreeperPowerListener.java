package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent;

public class CreeperPowerListener implements Listener {
	
	@EventHandler
	public void onCreeperPower(CreeperPowerEvent e) {
		e.setCancelled(true);
	}
}
