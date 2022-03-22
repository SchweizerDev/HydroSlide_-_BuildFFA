package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e) {
		e.setCancelled(true);
	}

}
