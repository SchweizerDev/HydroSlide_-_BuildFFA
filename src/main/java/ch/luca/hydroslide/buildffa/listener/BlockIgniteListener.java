package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class BlockIgniteListener implements Listener {
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e) {
		if(e.getCause().equals(IgniteCause.FLINT_AND_STEEL)) return;
		e.setCancelled(true);
	}

}
