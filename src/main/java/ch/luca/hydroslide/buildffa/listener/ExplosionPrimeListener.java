package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionPrimeListener implements Listener {
	
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent e) {
		e.setCancelled(true);
		e.getEntity().remove();
	}
}
