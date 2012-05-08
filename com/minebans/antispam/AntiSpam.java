package com.minebans.antispam;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.minebans.MineBans;
import com.minebans.antispam.checks.DuplicateMessageCheck;
import com.minebans.antispam.checks.PlayerDataChecker;
import com.minebans.antispam.checks.PlayerLocationCheck;
import com.minebans.antispam.data.PlayerDataListener;
import com.minebans.pluginapi.MineBansPluginAPI;
import com.minebans.util.PluginLogger;

public class AntiSpam extends JavaPlugin {
	
	protected PluginLogger log;
	
	public Server server;
	public PluginManager pluginManager;
	public BukkitScheduler scheduler;
	public PluginDescriptionFile pdFile;
	
	public PlayerDataManager dataManager;
	
	public MineBansPluginAPI mineBans;
	
	public void onEnable(){
		this.log = new com.minebans.util.PluginLogger(this);
		
		this.server = this.getServer();
		this.pluginManager = this.server.getPluginManager();
		this.scheduler = this.server.getScheduler();
		this.pdFile = this.getDescription();
		
		this.dataManager = new PlayerDataManager();
		
		this.mineBans = ((MineBans) this.pluginManager.getPlugin("MineBans")).getPluginAPI(this);
		
		this.pluginManager.registerEvents(new PlayerDataListener(this), this);
		this.pluginManager.registerEvents(new PlayerLocationCheck(this), this);
		this.pluginManager.registerEvents(new DuplicateMessageCheck(this), this);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new PlayerDataChecker(this), 50, 50); // 50 ticks = 2.5 seconds
		this.scheduler.scheduleSyncRepeatingTask(this, new CleanUpTask(this), 36000, 36000);
		
		this.log.info("Enabled");
	}
	
	public String formatMessage(String message, boolean colour, boolean version){
		StringBuilder line = new StringBuilder();
		
		if (colour){
			line.append(ChatColor.BLUE);
		}
		
		line.append("[");
		line.append(this.pdFile.getName());
		
		if (version){
			line.append(" v");
			line.append(this.pdFile.getVersion());
		}
		
		line.append("] ");
		line.append(message);
		
		return line.toString();
	}
	
	public String formatMessage(String message, boolean colour){
		return this.formatMessage(message, colour, !colour);
	}
	
	public String formatMessage(String message){
		return this.formatMessage(message, true, false);
	}
	
}
