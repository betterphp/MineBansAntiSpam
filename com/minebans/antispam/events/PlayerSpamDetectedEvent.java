package com.minebans.antispam.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSpamDetectedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private String playerName;
	private boolean preventDefault;
	
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
	
	public boolean getPreventDefault(){
		return this.preventDefault;
	}
	
	public void setPreventDefault(boolean prevent){
		this.preventDefault = prevent;
	}
	
}
