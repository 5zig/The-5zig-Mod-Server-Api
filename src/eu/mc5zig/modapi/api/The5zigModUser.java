package eu.mc5zig.modapi.api;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.mc5zig.modapi.The5zigMod;

public class The5zigModUser {

	/**
	 * Do all stuff that has something to do with the mod user
	 */

	private String player;

	private String kit;
	private Set<The5zigModTimer> timers = new HashSet<The5zigModTimer>();
	private int kills = -1;
	private int deaths = -1;
	private int killstreak = -1;
	private int money = -1;

	private static int timerid = 0;

	public The5zigModUser(String player) {
		this.player = player;
	}

	/**
	 * Disconnects a user
	 */

	public void disconnectUser() {
		getPlayer().sendPluginMessage(The5zigMod.getInstance(), "5ZIG", "/d/".getBytes());
	}

	public static int getNextTimerId() {
		return timerid++;
	}

	/**
	 * Returns the player
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return Bukkit.getPlayerExact(player);
	}

	/**
	 * Sets the kit for the mod user Use null to disable
	 * 
	 * @param String
	 *            kit
	 */
	public void setKit(String kit) {
		this.kit = kit;
		getPlayer().sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/s/kit=" + kit).getBytes());
	}

	/**
	 * Sets the amount of kills for the mod user Use -1 to disable
	 * 
	 * @param int kills
	 */
	public void setKills(int kills) {
		this.kills = kills;
		getPlayer().sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/s/kills=" + kills).getBytes());
	}

	/**
	 * Sets the amount of deaths for the mod use Use -1 to disable
	 * 
	 * @param int deaths
	 */
	public void setDeaths(int deaths) {
		this.deaths = deaths;
		getPlayer().sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/s/deaths=" + deaths).getBytes());
	}

	/**
	 * Sets the killstreak for the mod use Use -1 to disable
	 * 
	 * @param int killstreak
	 */
	public void setKillstreak(int killstreak) {
		this.killstreak = killstreak;
		getPlayer().sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/s/killstreak=" + killstreak).getBytes());
	}

	/**
	 * Sets the money for the mod use Use -1 to disable
	 * 
	 * @param int money
	 */
	public void setMoney(int money) {
		this.money = money;
		getPlayer().sendPluginMessage(The5zigMod.getInstance(), "5ZIG", ("/s/money=" + money).getBytes());
	}

	/**
	 * Adds a timer
	 * 
	 * @param The5zigModTimer
	 *            timer
	 */

	public void addTimer(The5zigModTimer timer) {
		timers.add(timer);
	}

	/**
	 * Cancels and removes a timer
	 * 
	 * @param The5zigModTimer
	 *            timer
	 */

	public void cancelTimer(The5zigModTimer timer) {
		timer.cancel(getPlayer());
	}

	/**
	 * Updates all stats to the player
	 */

	public void resendAll() {
		setKills(kills);
		setDeaths(deaths);
		setKit(kit);
		setMoney(money);
		setKillstreak(killstreak);
		for (The5zigModTimer timer : timers) {
			timer.sendRequest(getPlayer());
		}
	}

	/**
	 * Resets all stats
	 */

	public void resetAll() {
		setKills(-1);
		setDeaths(-1);
		setKit(null);
		setMoney(-1);
		setKillstreak(-1);
		for (The5zigModTimer timer : timers) {
			timer.cancel(getPlayer());
		}
	}

	/**
	 * Returns the kit of the Player
	 * 
	 * @return String
	 */

	public String getKit() {
		return kit;
	}

	/**
	 * Returns the amount of kills of the Player
	 * 
	 * @return int
	 */

	public int getKills() {
		return kills;
	}

	/**
	 * Returns the amount of deaths of the Player
	 * 
	 * @return int
	 */

	public int getDeaths() {
		return deaths;
	}

	/**
	 * Returns the killstreak of the Player
	 * 
	 * @return int
	 */

	public int getKillstreak() {
		return killstreak;
	}

	/**
	 * Returns the money of the Player
	 * 
	 * @return int
	 */

	public int getMoney() {
		return money;
	}

	/**
	 * Returns all timers. Could also return some inactive timers!!!
	 * 
	 * @return Set<ModTimer>
	 */

	public Set<The5zigModTimer> getTimers() {
		return timers;
	}

	public String toString() {
		return "ModUser{player=" + player + "}";
	}

}
