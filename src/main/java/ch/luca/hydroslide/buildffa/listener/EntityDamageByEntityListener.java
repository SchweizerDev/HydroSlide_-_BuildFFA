package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getDamager() == null) return;
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			
			if(p.getLocation().getBlockY() >= BuildFFA.getInstance().getFightHeight()
					|| d.getLocation().getBlockY() >= BuildFFA.getInstance().getFightHeight()) {
				e.setCancelled(true);
				return;
			}
			if(p.equals(d)) {
				e.setCancelled(true);
				return;
			}
			BuildFFA.getInstance().getUserManager().getUser(p).setDamage(d);
		} else if(e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
			if(e.getDamager() instanceof Arrow || e.getDamager() instanceof FishHook) {
				if(((Projectile)e.getDamager()).getShooter() instanceof Player) {
					Player p = (Player) e.getEntity();
					Player d = (Player) ((Projectile)e.getDamager()).getShooter(); 
					
					if(p.getLocation().getBlockY() >= BuildFFA.getInstance().getFightHeight()
							|| d.getLocation().getBlockY() >= BuildFFA.getInstance().getFightHeight()) {
						e.setCancelled(true);
						return;
					}
					if(p.equals(d)) {
						e.setCancelled(true);
						return;
					}
					BuildFFA.getInstance().getUserManager().getUser(p).setDamage(d);
				}
			}
		}
	}

}
