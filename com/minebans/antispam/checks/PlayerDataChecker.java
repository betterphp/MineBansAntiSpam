package com.minebans.antispam.checks;

import java.util.Collections;
import java.util.Map.Entry;

import com.minebans.antispam.AntiSpam;
import com.minebans.antispam.data.PlayerData;
import com.minebans.util.ListUtils;

public class PlayerDataChecker implements Runnable {
	
	public AntiSpam plugin;
	
	public PlayerDataChecker(AntiSpam plugin){
		this.plugin = plugin;
	}
	
	private boolean isLoginSpaammer(PlayerData playerData){
		if (playerData.loginCount >= 5){
			return true;
		}
		
		if (playerData.loginDelays.size() < 2){
			return false;
		}
		
		if (Collections.min(playerData.loginDelays) < 150){
			return true;
		}
		
		return (ListUtils.stddev(playerData.loginDelays) < 20);
	}
	
	private boolean isLogoutSpaammer(PlayerData playerData){
		if (playerData.logoutCount >= 5){
			return true;
		}
		
		if (playerData.logoutDelays.size() < 2){
			return false;
		}
		
		if (Collections.min(playerData.logoutDelays) < 150){
			return true;
		}
		
		return (ListUtils.stddev(playerData.logoutDelays) < 20);
	}
	
	private boolean isChatSpamer(PlayerData playerData){
		if (playerData.messageCount >= 8){
			return true;
		}
		
		if (playerData.messageDelays.size() < 2){
			return false;
		}
		
		if (Collections.min(playerData.messageDelays) < 100){
			return true;
		}
		
		return (ListUtils.stddev(playerData.messageDelays) < 20);
	}
	
	public void run(){
		String playerName;
		PlayerData playerData;
		
		for (Entry<String, PlayerData> entry : plugin.dataManager.getAll()){
			playerName = entry.getKey();
			playerData = entry.getValue();
			
			if (this.isChatSpamer(playerData) || this.isLoginSpaammer(playerData) || this.isLogoutSpaammer(playerData)){
				plugin.mineBans.tempBanPlayer(playerName, 600);
				plugin.dataManager.unregisterPlayer(playerName);
			}else{
				playerData.loginCount = 0;
				playerData.logoutCount = 0;
				
				playerData.messageCount = 0;
			}
		}
	}
	
}
