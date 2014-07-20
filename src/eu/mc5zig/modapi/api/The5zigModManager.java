package eu.mc5zig.modapi.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.entity.Player;

import eu.mc5zig.modapi.The5zigMod;

public class The5zigModManager {

	private The5zigMod plugin;

	public The5zigModManager(The5zigMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Returns the 5zig Mod User by player
	 * 
	 * @param player
	 * @return The5zigModUser
	 */

	public The5zigModUser getUser(Player player) {
		Iterator<The5zigModUser> it = plugin.users.iterator();
		while (it.hasNext()) {
			The5zigModUser user = it.next();
			if (!user.getPlayer().isOnline()) {
				it.remove();
				continue;
			}
			if (user.getPlayer().getName().equals(player.getName())) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Sends a reconnect request to a player. Use this method to connect a player that joined over a bungeecord lobby/network
	 * 
	 * @param player
	 */

	public void connectUser(Player player) {
		player.sendMessage("§1 §2 §5 §6 §9 §a §d");
	}

	/**
	 * Gets all online users that use the 5zig Mod
	 * 
	 * @return Set<The5zigModUser>
	 */

	public Set<The5zigModUser> getOnlineModUsers() {
		Set<The5zigModUser> players = new HashSet<>();
		for (int i = 0; i < plugin.users.size(); i++) {
			if (plugin.users.get(i).getPlayer() != null) players.add(plugin.users.get(i));
		}
		return players;
	}

	/**
	 * Checks if a player uses the 5zig Mod
	 * 
	 * @param player
	 * @return
	 */

	public boolean isUsingMod(Player player) {
		return getUser(player) != null;
	}

}
