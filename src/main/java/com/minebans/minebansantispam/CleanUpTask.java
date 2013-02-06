package com.minebans.minebansantispam;

import java.util.Map.Entry;

import uk.co.jacekk.bukkit.baseplugin.v9.scheduler.BaseTask;

import com.minebans.minebansantispam.data.PlayerData;

public class CleanUpTask extends BaseTask<MineBansAntiSpam> {
	
	public CleanUpTask(MineBansAntiSpam plugin){
		super(plugin);
	}
	
	public void run(){
		String playerName;
		PlayerData playerData;
		
		for (Entry<String, PlayerData> entry : plugin.dataManager.getAll()){
			playerName = entry.getKey();
			playerData = entry.getValue();
			
			if (playerData.lastLogoutTime > playerData.lastLoginTime && System.currentTimeMillis() - playerData.lastLoginTime > 3600000){
				plugin.dataManager.unregisterPlayer(playerName);
			}
		}
	}
	
}
