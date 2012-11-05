package com.minebans.antispam;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.minebans.antispam.data.PlayerData;
import com.minebans.antispam.data.ServerData;

public class PlayerDataManager {
	
	private HashMap<String, PlayerData> playerData;
	private ServerData serverData;
	
	public PlayerDataManager(){
		this.playerData = new HashMap<String, PlayerData>();
		this.serverData = new ServerData();
	}
	
	/**
	 * Creates a new data entry for the player.
	 * 
	 * @param playerName	The name of the player.
	 */
	public void registerPlayer(String playerName){
		if (this.playerData.containsKey(playerName) == false){
			this.playerData.put(playerName, new PlayerData());
		}
	}
	
	/**
	 * Removes a players data entry if it exists.
	 * 
	 * @param playerName	The name of the player.
	 */
	public void unregisterPlayer(String playerName){
		if (this.playerData.containsKey(playerName)){
			this.playerData.remove(playerName);
		}
	}
	
	/**
	 * Checks to see if a player has data registered.
	 * 
	 * @param playerName	The name of the player
	 * @return				True if there is data available.
	 */
	public boolean gotDataFor(String playerName){
		return this.playerData.containsKey(playerName);
	}
	
	/**
	 * Gets the data for a player.
	 * 
	 * @param playerName	The name of the player.
	 * @return				The {@link playerData} for the player.
	 */
	public PlayerData getPlayerData(String playerName){
		return this.playerData.get(playerName);
	}
	
	/**
	 * Gets the data for all players.
	 * 
	 * NOTE: The players are not necessarily online still.
	 * 
	 * @return	The data.
	 */
	public Set<Entry<String, PlayerData>> getAll(){
		return ((HashMap<String, PlayerData>) this.playerData.clone()).entrySet();
	}
	
	/**
	 * Gets the {@link serverData}
	 * 
	 * @return The data.
	 */
	public ServerData getServerData(){
		return this.serverData;
	}
	
}
