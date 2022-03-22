package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import ch.luca.hydroslide.buildffa.BuildFFA;

public class BlockBreakListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(BuildFFA.getInstance().getAllowBuild().contains(e.getPlayer())) return;
		e.setCancelled(true);
	}
}
