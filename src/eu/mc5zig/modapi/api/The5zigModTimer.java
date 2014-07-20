package eu.mc5zig.modapi.api;

import org.bukkit.entity.Player;

import eu.mc5zig.modapi.The5zigMod;

public class The5zigModTimer {

	private int id;
	private String name;
	private int seconds;
	private boolean up;
	private long timeStarted;

	private boolean sent = false;

	private boolean canceled = false;

	public The5zigModTimer(The5zigModUser user, String name, int seconds, boolean up) {
		this.id = user.getNextTimerId();
		this.name = name;
		this.seconds = seconds;
		this.up = up;
		this.timeStarted = System.currentTimeMillis();
		sendRequest(user.getPlayer());
	}

	public The5zigModTimer(The5zigModUser user, int seconds, boolean up) {
		this.id = user.getNextTimerId();
		this.name = "Time";
		this.seconds = seconds;
		this.up = up;
		this.timeStarted = System.currentTimeMillis();
	}

	/**
	 * Sends a timer request to the mod
	 * 
	 * @param player
	 */

	public void sendRequest(Player player) {
		if (sent) return;
		player.sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/ts/seconds=" + seconds + ";up=" + up + ";id=" + id + ";name=" + name).getBytes());
		sent = true;
	}

	/**
	 * Cancels the timer
	 * 
	 * @param Player
	 *            player
	 */

	public void cancel(Player player) {
		player.sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/tc/id=" + id).getBytes());
		canceled = true;
	}

	/**
	 * Gets the remaining time of this timer
	 * 
	 * @return int seconds
	 */

	public int getSeconds() {
		return (int) (up ? (System.currentTimeMillis() - (timeStarted + seconds * 1000)) * 1000 : ((timeStarted + seconds * 1000) - System.currentTimeMillis()) * 1000);
	}

	public boolean isOver() {
		return (!up ? getSeconds() < 0 : false) || canceled;
	}

}
