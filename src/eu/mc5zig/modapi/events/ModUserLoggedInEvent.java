package eu.mc5zig.modapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import eu.mc5zig.modapi.api.The5zigModUser;

public class ModUserLoggedInEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private The5zigModUser user;

	public ModUserLoggedInEvent(The5zigModUser user) {
		this.user = user;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public The5zigModUser getUser() {
		return user;
	}

}
