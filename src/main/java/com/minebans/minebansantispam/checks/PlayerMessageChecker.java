package com.minebans.minebansantispam.checks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.minebans.minebansantispam.MineBansAntiSpam;
import com.minebans.minebansantispam.data.PlayerData;

public class PlayerMessageChecker implements Listener {
	
	private MineBansAntiSpam plugin;
	
	public PlayerMessageChecker(MineBansAntiSpam plugin){
		this.plugin = plugin;
	}
	
	private boolean checkMessage(Player player, String message){
		String playerName = player.getName();
		
		if (!plugin.dataManager.gotDataFor(playerName)){
			return false;
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		
		if (player.getLocation().equals(playerData.joinLocation)){
			player.sendMessage(ChatColor.RED + "You cannot send chat messages until you have left your join location");
			return true;
		}
		
		if (message.replaceAll("[^a-zA-Z0-9]", "").equalsIgnoreCase(playerData.lastMessageCleaned)){
			player.sendMessage(ChatColor.RED + "You cannot repeat your last message.");
			return true;
		}
		
		return false;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		if (this.checkMessage(event.getPlayer(), event.getMessage())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		if (this.checkMessage(event.getPlayer(), event.getMessage())){
			event.setCancelled(true);
		}
	}
	
}
