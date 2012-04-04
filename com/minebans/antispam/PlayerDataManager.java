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
	
	public void registerPlayer(String playerName){
		if (this.playerData.containsKey(playerName) == false){
			this.playerData.put(playerName, new PlayerData());
		}
	}
	
	public void unregisterPlayer(String playerName){
		if (this.playerData.containsKey(playerName)){
			this.playerData.remove(playerName);
		}
	}
	
	public boolean gotDataFor(String playerName){
		return this.playerData.containsKey(playerName);
	}
	
	public PlayerData getPlayerData(String playerName){
		return this.playerData.get(playerName);
	}
	
	@SuppressWarnings("unchecked")
	public Set<Entry<String, PlayerData>> getAll(){
		return ((HashMap<String, PlayerData>) this.playerData.clone()).entrySet();
	}
	
	public ServerData getServerData(){
		return this.serverData;
	}
	
}
