package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if(e.getEntityType().equals(EntityType.ARMOR_STAND)) return;
		e.setCancelled(true);
	}
}
