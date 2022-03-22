package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockFormListener implements Listener {
	
	@EventHandler
	public void onEntityBlockForm(EntityBlockFormEvent e) {
		e.setCancelled(true);
	}

}
