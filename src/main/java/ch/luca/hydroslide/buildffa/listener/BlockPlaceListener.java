package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(BuildFFA.getInstance().getAllowBuild().contains(e.getPlayer())) return;
		
		if(e.getBlock().getLocation().getBlockY() >= BuildFFA.getInstance().getFightHeight()) {
			e.setCancelled(true);
			return;
		}
		BuildFFA.getInstance().getBlockDestroy().put(e.getBlock().getLocation(), System.currentTimeMillis());
	}
}
