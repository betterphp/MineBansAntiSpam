package com.minebans.antispam;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.minebans.MineBans;
import com.minebans.antispam.checks.PlayerDataChecker;
import com.minebans.antispam.checks.PlayerLocationCheck;
import com.minebans.antispam.data.PlayerDataListener;
import com.minebans.pluginapi.MineBansPluginAPI;
import com.minebans.util.PluginLogger;

public class AntiSpam extends JavaPlugin {
	
	protected PluginLogger log;
	
	protected Server server;
	public PluginManager pluginManager;
	protected BukkitScheduler scheduler;
	
	public PlayerDataManager dataManager;
	
	public MineBansPluginAPI mineBans;
	
	public void onEnable(){
		this.log = new com.minebans.util.PluginLogger(this);
		
		this.server = this.getServer();
		this.pluginManager = this.server.getPluginManager();
		this.scheduler = this.server.getScheduler();
		
		this.dataManager = new PlayerDataManager();
		
		this.mineBans = ((MineBans) this.pluginManager.getPlugin("MineBans")).getPluginAPI(this);
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		this.pluginManager.registerEvents(new PlayerLocationCheck(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new PlayerDataChecker(this), 100, 100);
		this.scheduler.scheduleSyncRepeatingTask(this, new CleanUpTask(this), 36000, 36000);
		
		this.log.info("Enabled");
	}
	
}
