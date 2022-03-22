package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

public class BlockGrowListener implements Listener {
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent e) {
		e.setCancelled(true);
	}
}
