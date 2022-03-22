package ch.luca.hydroslide.buildffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import ch.luca.hydroslide.buildffa.BuildFFA;

public class BlockDamageListener implements Listener {
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent e) {
		if(BuildFFA.getInstance().getAllowBuild().contains(e.getPlayer())) return;
		e.setCancelled(true);
	}
}
