package com.cubepalace.staffmanagement.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerJoinListener implements Listener {

	private StaffManagement plugin;
	
	public PlayerJoinListener(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (plugin.getAuthMap().containsKey(e.getPlayer().getUniqueId())) {
			plugin.getAuthHandler().assignAuthenticationTask(e.getPlayer());
			return;
		}
		if (plugin.getDataHandler().getPlayerDataByPlayer(e.getPlayer()).hasPendingSet())
			plugin.getMsgHandler().getMessage("pendingAuthSet");
	}
}
