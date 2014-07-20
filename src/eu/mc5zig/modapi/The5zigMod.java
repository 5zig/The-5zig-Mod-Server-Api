package eu.mc5zig.modapi;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import eu.mc5zig.modapi.api.The5zigModManager;
import eu.mc5zig.modapi.api.The5zigModUser;
import eu.mc5zig.modapi.listener.PlayerListener;
import eu.mc5zig.modapi.listener.ClientListener;
import eu.mc5zig.modapi.utils.Utils;

public class The5zigMod extends JavaPlugin implements Listener {

	/**
	 * @author 5zig
	 */

	private static The5zigMod instance;
	private static The5zigModManager manager;

	/**
	 * All online users
	 */
	public ArrayList<The5zigModUser> users = new ArrayList<The5zigModUser>();

	/**
	 * Version of the API. If older the the one from the mod, the user will be rejected
	 */
	public final int APIVER = 1;

	/**
	 * Returns an instance of the 5zig mod
	 * 
	 * @return The5zigMod
	 */

	public static The5zigMod getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "5ZIG");
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "5ZIG", new ClientListener(this));
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

		manager = new The5zigModManager(this);

		loadUsers();

		for (Player p : Bukkit.getOnlinePlayers()) {
			getApi().connectUser(p);
		}
		
		new The5zigModTimerManager(this);

		System.out.println("[The5zigModAPI] ModAPI version " + getDescription().getVersion() + " enabled");
	}

	public void onDisable() {
		saveUsers();
	}

	private void saveUsers() {
		try {
			if (!getDataFolder().exists()) getDataFolder().mkdir();
			File dir = new File(getDataFolder(), "usertmp");
			if (!dir.exists()) dir.mkdir();
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getPlayer().isOnline()) {
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.setPrettyPrinting().create();
					String json = gson.toJson(users.get(i));
					File file = new File(dir, users.get(i).getPlayer().getName() + ".json");
					file.createNewFile();
					FileWriter writer = new FileWriter(file);
					writer.write(json);
					writer.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadUsers() {
		try {
			File dir = new File(getDataFolder(), "usertmp");
			if (!dir.exists()) return;
			for (File file : dir.listFiles()) {
				FileReader reader = new FileReader(file);
				Gson gson = new Gson();
				The5zigModUser user = gson.fromJson(reader, The5zigModUser.class);
				users.add(user);
				reader.close();
			}
			Utils.deleteFiles(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the API Manager
	 * 
	 * @return
	 */

	public static The5zigModManager getApi() {
		return manager;
	}

}
