package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayListener implements Listener {
	
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		e.setCancelled(true);
	}
}
