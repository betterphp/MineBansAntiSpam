package com.minebans.antispam;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;

import com.minebans.MineBans;
import com.minebans.antispam.checks.DuplicateMessageCheck;
import com.minebans.antispam.checks.PlayerDataChecker;
import com.minebans.antispam.checks.PlayerLocationCheck;
import com.minebans.antispam.data.PlayerDataListener;
import com.minebans.pluginapi.MineBansPluginAPI;

public class AntiSpam extends BasePlugin {
	
	public PlayerDataManager dataManager;
	
	public MineBansPluginAPI mineBans;
	
	public void onEnable(){
		super.onEnable(false);
		
		this.dataManager = new PlayerDataManager();
		
		this.mineBans = ((MineBans) this.pluginManager.getPlugin("MineBans")).getPluginAPI(this);
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		this.pluginManager.registerEvents(new PlayerLocationCheck(this), this);
		this.pluginManager.registerEvents(new DuplicateMessageCheck(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new PlayerDataChecker(this), 50, 50); // 50 ticks = 2.5 seconds
		this.scheduler.scheduleSyncRepeatingTask(this, new CleanUpTask(this), 36000, 36000);
		
		this.log.info("Enabled");
	}
	
}
