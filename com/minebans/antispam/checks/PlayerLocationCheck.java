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
		PlayerData data = plugin.dataManager.getPlayerData(player.getName());
		
		// TODO: Test how easy it is to move back to your exact join location.
		if (player.getLocation().equals(data.joinLocation)){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot send chat messages until you have left your join location");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		this.onPlayerChat(event);
	}
	
}
