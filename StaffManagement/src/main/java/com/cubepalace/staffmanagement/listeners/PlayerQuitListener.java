package com.cubepalace.staffmanagement.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cubepalace.staffmanagement.StaffManagement;
import com.cubepalace.staffmanagement.util.AuthenticationResult;

public class PlayerQuitListener implements Listener {

	private StaffManagement plugin;
	
	public PlayerQuitListener(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (plugin.getDataHandler().getPlayerDataByPlayer(event.getPlayer()) == null)
			return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(event.getPlayer()).hasAssignedAuthentication())
			plugin.getDataHandler().getPlayerDataByPlayer(event.getPlayer()).addAuthenticationAttempt(AuthenticationResult.LEAVE);;
	}
}
