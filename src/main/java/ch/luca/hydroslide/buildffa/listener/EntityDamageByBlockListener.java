package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class EntityDamageByBlockListener implements Listener {
	
	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
		e.setCancelled(true);
	}
}
