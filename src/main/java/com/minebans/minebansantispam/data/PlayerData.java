package com.minebans.minebansantispam.data;

import java.util.ArrayList;

import org.bukkit.Location;

/**
 * The data for a single player.
 * 
 * @author Jacek Kuzemczak
 */
public class PlayerData {
	
	/**
	 * The number of ticks between data checks and resets.
	 */
	public static final int TIME_PERIOD = 50;
	
	/**
	 * Delays between the last 50 logins.
	 */
	public ArrayList<Long> loginDelays;
	
	/**
	 * Delays between the last 50 logouts.
	 */
	public ArrayList<Long> logoutDelays;
	
	/**
	 * Delays between the last 50 messages.
	 */
	public ArrayList<Long> messageDelays;
	
	/**
	 * Number of logins in the last time period.
	 */
	public int loginCount;
	
	/**
	 * Number of logouts in the last time period.
	 */
	public int logoutCount;
	
	/**
	 * Number of messages sent in the last time period
	 */
	public int messageCount;
	
	/**
	 * Number of warnings the player has had.
	 */
	public int warningCount;
	
	/**
	 * Time the player last logged in.
	 */
	public long lastLoginTime;
	
	/**
	 * Time the player last logged out.
	 */
	public long lastLogoutTime;
	
	/**
	 * Time the last message was sent.
	 */
	public long lastMessageTime;
	
	/**
	 * The last message that was sent with any special characters removed.
	 */
	public String lastMessageCleaned;
	
	/**
	 * The location that the player joined at.
	 */
	public Location joinLocation;
	
	public PlayerData(){
		this.loginDelays = new ArrayList<Long>();
		this.logoutDelays = new ArrayList<Long>();
		
		this.messageDelays = new ArrayList<Long>();
		
		this.loginCount = 0;
		this.logoutCount = 0;
		
		this.messageCount = 0;
		
		this.warningCount = 0;
		
		this.lastLoginTime = 0L;
		this.lastLogoutTime = 0L;
		
		this.lastMessageTime = 0L;
		
		this.lastMessageCleaned = "";
	}
	
	/**
	 * Reset the data in the delay lists.
	 */
	public void resetDelays(){
		this.loginDelays.clear();
		this.logoutDelays.clear();
		
		this.messageDelays.clear();
	}
	
	/**
	 * Reset the data in in the counters.
	 */
	public void resetCounters(){
		this.loginCount = 0;
		this.logoutCount = 0;
		
		this.messageCount = 0;
	}
	
	/**
	 * Reset the time data.
	 */
	public void resetTimes(){
		this.lastLoginTime = 0L;
		this.lastLogoutTime = 0L;
		
		this.lastMessageTime = 0L;
	}
	
	/**
	 * Reset all data.
	 */
	public void resetAll(){
		this.resetDelays();
		this.resetCounters();
		this.resetTimes();
	}
	
}
