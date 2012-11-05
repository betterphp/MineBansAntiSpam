package com.minebans.antispam;

import uk.co.jacekk.bukkit.baseplugin.v4.BasePlugin;

import com.minebans.antispam.checks.PlayerDataChecker;
import com.minebans.antispam.checks.PlayerMessageChecker;
import com.minebans.antispam.data.PlayerData;
import com.minebans.antispam.data.PlayerDataListener;

public class MineBansAntiSpam extends BasePlugin {
	
	public PlayerDataManager dataManager;
	
	public void onEnable(){
		super.onEnable(false);
		
		this.dataManager = new PlayerDataManager();
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		this.pluginManager.registerEvents(new PlayerMessageChecker(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new PlayerDataChecker(this), PlayerData.TIME_PERIOD, PlayerData.TIME_PERIOD);
		this.scheduler.scheduleSyncRepeatingTask(this, new CleanUpTask(this), 36000, 36000);
	}
	
}
