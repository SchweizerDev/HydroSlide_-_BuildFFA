package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(p.getLocation().getBlockY() >= BuildFFA.getInstance().getFightHeight()) {
				e.setCancelled(true);
				return;
			}
			if(e.getCause().equals(DamageCause.FALL) || e.getCause().equals(DamageCause.VOID)) {
				e.setCancelled(true);
				return;
			}
		}
	}

}
