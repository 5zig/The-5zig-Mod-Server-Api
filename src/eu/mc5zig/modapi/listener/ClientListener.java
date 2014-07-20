package eu.mc5zig.modapi.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import eu.mc5zig.modapi.The5zigMod;
import eu.mc5zig.modapi.api.The5zigModUser;
import eu.mc5zig.modapi.events.ModUserLoggedInEvent;
import eu.mc5zig.modapi.events.ModUserLoginEvent;

public class ClientListener implements PluginMessageListener {

	private The5zigMod plugin;

	public ClientListener(The5zigMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Called when a plugin message has been received
	 */
	public void onPluginMessageReceived(String string, Player player, byte[] arg2) {
		if (string.equals("5ZIG")) {
			try {
				String response = new String(arg2);
				if (response.startsWith("/l/connect")) {
					response = response.replace("/l/connect", "");
					ModUserLoginEvent e = new ModUserLoginEvent(player);
					Bukkit.getPluginManager().callEvent(e);
					if (!e.isCancelled()) {
						int ver = Integer.parseInt(response);
						if (ver == plugin.APIVER) {
							player.sendPluginMessage(plugin, "5ZIG", "/l/connected".getBytes());
							System.out.println("Player " + player.getName() + " connected using the 5zig Mod!");
							if (!The5zigMod.getApi().isUsingMod(player)) {
								The5zigModUser user = new The5zigModUser(player.getName());
								plugin.users.add(user);
								Bukkit.getPluginManager().callEvent(new ModUserLoggedInEvent(user));
							} else {
								The5zigModUser user = The5zigMod.getApi().getUser(player);
								user.resendAll();
								Bukkit.getPluginManager().callEvent(new ModUserLoggedInEvent(user));
							}
						} else {
							player.sendPluginMessage(plugin, "5ZIG", "/l/outdated".getBytes());
						}
					} else {
						player.sendPluginMessage(plugin, "5ZIG", "/l/cancelled".getBytes());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
