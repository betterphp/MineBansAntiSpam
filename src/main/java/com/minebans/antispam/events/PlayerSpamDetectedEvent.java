package com.minebans.antispam.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSpamDetectedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private String playerName;
	private boolean handled;
	
	public PlayerSpamDetectedEvent(String playerName){
		this.playerName = playerName;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	/**
	 * Gets the name of the spammer.
	 * 
	 * @return	the player name.
	 */
	public String getPlayerName(){
		return this.playerName;
	}
	
	/**
	 * Sets if the event has been handled or not, setting this to true will prevent the default action of the plugin.
	 * 
	 * @param handled	If the event has been handled.
	 */
	public void setHandled(boolean handled){
		this.handled = handled;
	}
	
	/**
	 * Checks to see if this event has been handled already, an event is handled if a plugin has acted on event.
	 * 
	 * @return	True if the event has been handled
	 */
	public boolean isHandled(){
		return this.handled;
	}
	
}