package com.cubepalace.staffmanagement.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerChatListener implements Listener {

	private StaffManagement plugin;

	public PlayerChatListener(StaffManagement plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (plugin.getDataHandler().getPlayerDataByPlayer(player) == null)
			return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(player).hasAssignedAuthentication()
				|| plugin.getDataHandler().getPlayerDataByPlayer(player).hasPendingSet())
			event.setCancelled(true);
	}
}
