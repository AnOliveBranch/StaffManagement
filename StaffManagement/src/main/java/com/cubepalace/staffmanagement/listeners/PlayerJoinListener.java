package com.cubepalace.staffmanagement.listeners;

import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerJoinListener implements Listener {

	private StaffManagement plugin;

	private Map<UUID, String> hashMap;
	private Map<UUID, String> saltMap;

	public PlayerJoinListener(StaffManagement plugin, Map<UUID, String> hashMap, Map<UUID, String> saltMap) {
		this.plugin = plugin;
		this.hashMap = hashMap;
		this.saltMap = saltMap;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (hashMap.containsKey(e.getPlayer().getUniqueId())) {
			String hash = hashMap.get(e.getPlayer().getUniqueId());
			String salt = saltMap.get(e.getPlayer().getUniqueId());
			plugin.getAuthHandler().assignAuthenticationTask(e.getPlayer(), hash, salt);
			return;
		}
		if (plugin.getDataHandler().getPlayerDataByPlayer(e.getPlayer()) != null)
			if (plugin.getDataHandler().getPlayerDataByPlayer(e.getPlayer()).hasPendingSet())
				plugin.getMsgHandler().getMessage("pendingAuthSet");
	}

	public void updateMaps(Map<UUID, String> hashMap, Map<UUID, String> saltMap) {
		this.hashMap = hashMap;
		this.saltMap = saltMap;
	}
}
