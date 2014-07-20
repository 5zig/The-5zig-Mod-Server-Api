package eu.mc5zig.modapi;

import java.util.Iterator;

import org.bukkit.scheduler.BukkitRunnable;

import eu.mc5zig.modapi.api.The5zigModTimer;
import eu.mc5zig.modapi.api.The5zigModUser;

public class The5zigModTimerManager extends BukkitRunnable {

	public The5zigModTimerManager(The5zigMod plugin) {
		runTaskTimer(plugin, 60, 10);
	}
	
	public void run() {
		for (The5zigModUser user : The5zigMod.getApi().getOnlineModUsers()) {
			Iterator<The5zigModTimer> it = user.getTimers().iterator();
			while (it.hasNext()) {
				The5zigModTimer timer = it.next();
				if (timer.isOver()) it.remove();
			}
		}
	}

}
