package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockFadeListener implements Listener {
	
	@EventHandler
	public void onBlockFade(BlockFadeEvent e) {
		e.setCancelled(true);
	}
}
