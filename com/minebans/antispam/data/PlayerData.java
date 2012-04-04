package com.minebans.antispam.data;

import java.util.ArrayList;

public class PlayerData {
	
	// delays between the last 50 logins and logout
	public ArrayList<Long> loginDelays;
	public ArrayList<Long> logoutDelays;
	
	// delays between the last 50 messages
	public ArrayList<Long> messageDelays;
	
	// number of logins and logouts during this 100 ticks
	public int loginCount;
	public int logoutCount;
	
	// number of messages sent during this 100 ticks
	public int messageCount;
	
	// the time the player last logged in and out
	public long lastLoginTime;
	public long lastLogoutTime;
	
	// the time the last message was sent
	public long lastMessageTime;
	
	public PlayerData(){
		this.loginDelays = new ArrayList<Long>();
		this.logoutDelays = new ArrayList<Long>();
		
		this.messageDelays = new ArrayList<Long>();
		
		this.loginCount = 0;
		this.logoutCount = 0;
		
		this.messageCount = 0;
		
		this.lastLoginTime = 0L;
		this.lastLogoutTime = 0L;
		
		this.lastMessageTime = 0L;
	}
	
}
