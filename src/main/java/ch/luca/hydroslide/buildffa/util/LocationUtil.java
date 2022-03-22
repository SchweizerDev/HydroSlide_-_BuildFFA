package ch.luca.hydroslide.buildffa.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

	public static String locationToString(Location l) {
		if(l == null){
			Location loc = Bukkit.getWorlds().get(0).getSpawnLocation();
			return  loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":"
			+ loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
		}
		return l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":"
		+ l.getZ() + ":" + l.getYaw() + ":" + l.getPitch();
	}
	public static Location stringToLocation(String l) {
		String[] s = l.split(":");
		return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]),Double.parseDouble(s[2]), Double.parseDouble(s[3]),(float) Double.parseDouble(s[4]),(float) Double.parseDouble(s[5]));
	}
}
