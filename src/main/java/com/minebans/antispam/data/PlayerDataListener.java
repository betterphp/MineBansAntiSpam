package com.minebans.antispam.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener;

import com.minebans.antispam.MineBansAntiSpam;

public class PlayerDataListener extends BaseListener<MineBansAntiSpam> {
	
	private ArrayList<String> dangerousCommands;
	
	public PlayerDataListener(MineBansAntiSpam plugin){
		super(plugin);
		
		this.dangerousCommands = new ArrayList<String>();
		
		this.dangerousCommands.add("me");
		this.dangerousCommands.add("tell");
		this.dangerousCommands.add("pm");
		this.dangerousCommands.add("faction");
		this.dangerousCommands.add("kill");
		this.dangerousCommands.add("suicide");
		
		Map<String, String[]> aliases = plugin.server.getCommandAliases();
		
		for (String command : new ArrayList<String>(this.dangerousCommands)){
			if (aliases.containsKey(command)){
				this.dangerousCommands.addAll(Arrays.asList(aliases.get(command)));
			}
		}
		
		for (int i = this.dangerousCommands.size() - 1; i >= 0; --i){
			this.dangerousCommands.set(i, "/" + this.dangerousCommands.get(i));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		
		if (player.hasPermission("minebans.antispam.exempt")){
			return;
		}
		
		String playerName = player.getName();
		
		if (plugin.dataManager.gotDataFor(playerName) == false){
			plugin.dataManager.registerPlayer(playerName);
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		long currentTime = System.currentTimeMillis();
		
		++playerData.loginCount;
		
		if (playerData.lastLoginTime != 0L){
			playerData.loginDelays.add(currentTime - playerData.lastLoginTime);
			
			if (playerData.loginDelays.size() > 50){
				playerData.loginDelays.remove(0);
			}
		}
		
		playerData.lastLoginTime = currentTime;
		
		playerData.joinLocation = player.getLocation();
		
		// Give the player a tiny fall on join, this stops the location check getting in the way for normal players.
		// 0.15 was found experimentally, it's just enough to make the client reliably send a move packet without
		// jamming people into the ceiling.
		double joinY = playerData.joinLocation.getY();
		
		if (Math.rint(joinY) == joinY){
			player.teleport(playerData.joinLocation.add(0D, 0.015D, 0D));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.dataManager.gotDataFor(playerName) == false){
			return;
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		long currentTime = System.currentTimeMillis();
		
		++playerData.logoutCount;
		
		if (playerData.lastLogoutTime != 0L){
			playerData.logoutDelays.add(currentTime - playerData.lastLogoutTime);
			
			if (playerData.logoutDelays.size() > 50){
				playerData.loginDelays.remove(0);
			}
		}
		
		playerData.lastLogoutTime = currentTime;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.dataManager.gotDataFor(playerName) == false){
			return;
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		
		if (event.getFrom().equals(playerData.joinLocation)){
			playerData.joinLocation = event.getTo();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (plugin.dataManager.gotDataFor(playerName) == false){
			return;
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		
		if (player.getLocation().equals(playerData.joinLocation)){
			playerData.joinLocation = event.getRespawnLocation();
		}
	}
	
	private void onPlayerMessage(String playerName, String message){
		if (plugin.dataManager.gotDataFor(playerName) == false){
			return;
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		long currentTime = System.currentTimeMillis();
		
		++playerData.messageCount;
		
		// Some mods send chat on join, and some players have more than 1 mod.
		// So let the player send 6 fast messages within the first 5 seconds.
		if (currentTime - playerData.lastLoginTime < 5000 && playerData.messageCount <= 6){
			return;
		}
		
		if (playerData.lastMessageTime != 0L){
			playerData.messageDelays.add(currentTime - playerData.lastMessageTime);
			
			if (playerData.messageDelays.size() > 50){
				playerData.messageDelays.remove(0);
			}
		}
		
		playerData.lastMessageTime = currentTime;
		playerData.lastMessageCleaned = message.replaceAll("[^a-zA-Z]", "");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		this.onPlayerMessage(event.getPlayer().getName(), event.getMessage());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.dataManager.gotDataFor(playerName) == false){
			return;
		}
		
		PlayerData playerData = plugin.dataManager.getPlayerData(playerName);
		
		String message = event.getMessage();
		String command = message.split(" ", 2)[0];
		
		// This should ignore roughly half of the safe commands
		if (playerData.messageCount % 2 == 0 && !this.dangerousCommands.contains(command)){
			++playerData.messageCount;
			return;
		}
		
		this.onPlayerMessage(playerName, message);
	}
	
}
