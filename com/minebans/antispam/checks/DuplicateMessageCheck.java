package com.minebans.antispam.checks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.minebans.antispam.AntiSpam;

public class DuplicateMessageCheck implements Listener {
	
	private AntiSpam plugin;
	private String lastMessage;
	
	public DuplicateMessageCheck(AntiSpam plugin){
		this.plugin = plugin;
		this.lastMessage = "";
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		String message = event.getMessage();
		
		if (message.equalsIgnoreCase(this.lastMessage)){
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.formatMessage(ChatColor.RED + "To prevent spamming you cannot repeat the last message in chat"));
		}
		
		this.lastMessage = message;
	}
	
}
