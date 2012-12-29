package com.minebans.antispam.checks;

import java.util.Collections;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v7.scheduler.BaseTask;
import uk.co.jacekk.bukkit.baseplugin.v7.util.ListUtils;

import com.minebans.antispam.MineBansAntiSpam;
import com.minebans.antispam.data.PlayerData;
import com.minebans.antispam.events.PlayerSpamDetectedEvent;

public class PlayerDataChecker extends BaseTask<MineBansAntiSpam> {
	
	public PlayerDataChecker(MineBansAntiSpam plugin){
		super(plugin);
	}
	
	private boolean isLoginSpammer(PlayerData playerData){
		if (playerData.loginCount >= 4){
			return true;
		}
		
		if (playerData.loginDelays.size() < 2){
			return false;
		}
		
		if (Collections.min(playerData.loginDelays) < 140){
			return true;
		}
		
		return (ListUtils.stddev(playerData.loginDelays) < 25);
	}
	
	private boolean isLogoutSpammer(PlayerData playerData){
		if (playerData.logoutCount >= 4){
			return true;
		}
		
		if (playerData.logoutDelays.size() < 2){
			return false;
		}
		
		if (Collections.min(playerData.logoutDelays) < 140){
			return true;
		}
		
		return (ListUtils.stddev(playerData.logoutDelays) < 25);
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
			
			if (this.isChatSpamer(playerData) || this.isLoginSpammer(playerData) || this.isLogoutSpammer(playerData)){
				PlayerSpamDetectedEvent event = new PlayerSpamDetectedEvent(playerName);
				
				plugin.pluginManager.callEvent(event);
				
				if (!event.isHandled()){
					if (playerData.warningCount > 3){
						plugin.server.dispatchCommand(plugin.server.getConsoleSender(), "ban " + playerName + " 1h");
						plugin.dataManager.unregisterPlayer(playerName);
						
						plugin.server.broadcastMessage(plugin.formatMessage(ChatColor.GREEN + playerName + " has been banned for spamming."));
					}else{
						++playerData.warningCount;
						
						Player player = plugin.server.getPlayer(playerName);
						
						if (player != null){
							player.sendMessage(plugin.formatMessage(ChatColor.RED + "You have received a warning for spamming."));
							player.sendMessage(plugin.formatMessage(ChatColor.RED + "More than 3 of these will result in a 1 hour ban."));
						}
						
						playerData.resetCounters();
						playerData.resetDelays();
					}
				}
			}else{
				playerData.resetCounters();
			}
		}
	}
	
}
