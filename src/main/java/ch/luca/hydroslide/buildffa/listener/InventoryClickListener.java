package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(e.getInventory() == null) return;
		if(e.getClickedInventory() == null) return;
		if(e.getCurrentItem() == null) return;
		
		if(e.getInventory().getTitle().equals("§eInventar Sortierung")) {
			if(!e.getClickedInventory().equals(p.getInventory())) {
				e.setCancelled(true);
				return;
			}
		} else if(e.getClickedInventory().equals(p.getInventory())) {
			if(BuildFFA.getInstance().getAllowBuild().contains(p)) return;
			e.setCancelled(true);
			return;
		}
	}
}
