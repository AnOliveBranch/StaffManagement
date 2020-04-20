package com.cubepalace.staffmanagement.handlers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;
import com.cubepalace.staffmanagement.util.PlayerData;

public class PlayerDataHandler {

	private StaffManagement plugin;
	private Map<UUID, PlayerData> dataMap;
	private File playerFolder;
	
	public PlayerDataHandler(StaffManagement plugin) {
		this.plugin = plugin;
		dataMap = new HashMap<UUID, PlayerData>();
		
		playerFolder = new File(plugin.getDataFolder(), "players");
		if (!playerFolder.exists())
			playerFolder.mkdir();
	}
	
	public void addPlayerDataByPlayer(Player player) {
		dataMap.put(player.getUniqueId(), new PlayerData(plugin, player));
	}
	
	public void addPlayerDataByUUID(UUID uuid) {
		dataMap.put(uuid, new PlayerData(plugin, uuid));
	}
	
	public PlayerData getPlayerDataByPlayer(Player player) {
		return dataMap.get(player.getUniqueId());
	}
	
	public PlayerData getPlayerDataByUUID(UUID uuid) {
		return dataMap.get(uuid);
	}
	
	public void removePlayerDataByPlayer(Player player) {
		dataMap.remove(player.getUniqueId());
	}
	
	public void removePlayerDataByUUID(UUID uuid) {
		dataMap.remove(uuid);
	}
	
	public File getPlayerFolder() {
		return playerFolder;
	}
}
