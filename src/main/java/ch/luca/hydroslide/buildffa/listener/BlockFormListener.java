package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {
	
	@EventHandler
	public void onBlockForm(BlockFormEvent e) {
		e.setCancelled(true);
	}
}
