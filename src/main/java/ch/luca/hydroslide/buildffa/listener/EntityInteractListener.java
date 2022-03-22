package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractListener implements Listener {
	
	@EventHandler
	public void onEntityInteract(EntityInteractEvent e) {
		e.setCancelled(true);
	}

}
