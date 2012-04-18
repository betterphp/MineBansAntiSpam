package com.minebans.antispam.checks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.minebans.antispam.AntiSpam;
import com.minebans.antispam.data.PlayerData;

public class PlayerLocationCheck implements Listener {
	
	private AntiSpam plugin;
	
	public PlayerLocationCheck(AntiSpam plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		PlayerData playerData = plugin.dataManager.getPlayerData(player.getName());
		
		// See lines 133 and 134 of PlayerDataListener.java
		if (System.currentTimeMillis() - playerData.lastLoginTime < 5000 && playerData.messageCount <= 6){
			return;
		}
		
		// The vanilla client won't even teleport back to the exact spawn location so this seems fine.
		if (player.getLocation().equals(playerData.joinLocation)){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot send chat messages until you have left your join location");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		this.onPlayerChat(event);
	}
	
}
