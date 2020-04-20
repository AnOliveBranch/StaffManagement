package com.cubepalace.staffmanagement.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerPreProcessListener implements Listener {

	private StaffManagement plugin;

	public PlayerPreProcessListener(StaffManagement plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
		if (plugin.getDataHandler().getPlayerDataByPlayer(e.getPlayer()) == null)
			return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(e.getPlayer()).hasAssignedAuthentication()) {
			if (!e.getMessage().startsWith("/authenticate "))
				e.setCancelled(true);
		}

		if (plugin.getDataHandler().getPlayerDataByPlayer(e.getPlayer()).hasPendingSet()) {
			if (!e.getMessage().startsWith("/staffmanagement auth set "))
				e.setCancelled(true);
		}
	}

}
