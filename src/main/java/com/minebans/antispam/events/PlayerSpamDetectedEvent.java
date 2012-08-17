package com.minebans.antispam.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSpamDetectedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private String playerName;
	
	public PlayerSpamDetectedEvent(String playerName){
		this.playerName = playerName;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public String getPlayerName(){
		return this.playerName;
	}
	
}