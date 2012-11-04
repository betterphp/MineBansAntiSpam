package com.minebans.antispam;

import uk.co.jacekk.bukkit.baseplugin.v4.BasePlugin;

import com.minebans.antispam.checks.DuplicateMessageCheck;
import com.minebans.antispam.checks.PlayerDataChecker;
import com.minebans.antispam.checks.PlayerLocationCheck;
import com.minebans.antispam.data.PlayerDataListener;

public class AntiSpam extends BasePlugin {
	
	public PlayerDataManager dataManager;
	
	public void onEnable(){
		super.onEnable(false);
		
		this.dataManager = new PlayerDataManager();
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		this.pluginManager.registerEvents(new PlayerLocationCheck(this), this);
		this.pluginManager.registerEvents(new DuplicateMessageCheck(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new PlayerDataChecker(this), 50, 50); // 50 ticks = 2.5 seconds
		this.scheduler.scheduleSyncRepeatingTask(this, new CleanUpTask(this), 36000, 36000);
		
		this.log.info("Enabled");
	}
	
}
