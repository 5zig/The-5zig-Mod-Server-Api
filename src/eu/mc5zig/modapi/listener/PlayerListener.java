package eu.mc5zig.modapi.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.mc5zig.modapi.The5zigMod;

public class PlayerListener implements Listener {

	private The5zigMod plugin;

	public PlayerListener(The5zigMod plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (The5zigMod.getApi().isUsingMod(e.getPlayer())) plugin.users.remove(The5zigMod.getApi().getUser(e.getPlayer()));
	}

}
