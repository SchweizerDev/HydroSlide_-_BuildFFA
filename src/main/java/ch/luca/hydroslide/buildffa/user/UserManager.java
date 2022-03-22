package ch.luca.hydroslide.buildffa.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import lombok.Getter;

import org.bukkit.entity.Player;

public class UserManager {

	@Getter
	private Map<UUID, User> users = new HashMap<>();
	
	public User getUser(Player p) {
		return this.users.computeIfAbsent(p.getUniqueId(), new Function<UUID, User>() {

			@Override
			public User apply(UUID uuid) {
				return new User(p);
			}
		});
	}
	public void removeUser(Player p) {
		if(this.users.containsKey(p.getUniqueId())) {
			this.users.remove(p.getUniqueId());
		}
	}
}
