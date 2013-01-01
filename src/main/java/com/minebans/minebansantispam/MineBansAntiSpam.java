package com.minebans.minebansantispam;

import uk.co.jacekk.bukkit.baseplugin.v7.BasePlugin;

import com.minebans.minebansantispam.checks.PlayerDataChecker;
import com.minebans.minebansantispam.checks.PlayerMessageChecker;
import com.minebans.minebansantispam.data.PlayerData;
import com.minebans.minebansantispam.data.PlayerDataListener;

public class MineBansAntiSpam extends BasePlugin {
	
	public PlayerDataManager dataManager;
	
	public void onEnable(){
		super.onEnable(false);
		
		this.dataManager = new PlayerDataManager();
		
		this.permissionManager.registerPermissions(Permission.class);
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		this.pluginManager.registerEvents(new PlayerMessageChecker(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new PlayerDataChecker(this), PlayerData.TIME_PERIOD, PlayerData.TIME_PERIOD);
		this.scheduler.scheduleSyncRepeatingTask(this, new CleanUpTask(this), 36000, 36000);
	}
	
}
