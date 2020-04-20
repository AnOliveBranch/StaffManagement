package com.cubepalace.staffmanagement.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerMoveListener implements Listener {

	private StaffManagement plugin;

	public PlayerMoveListener(StaffManagement plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.getDataHandler().getPlayerDataByPlayer(event.getPlayer()) == null)
				return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(event.getPlayer()).hasAssignedAuthentication() || plugin.getDataHandler().getPlayerDataByPlayer(event.getPlayer()).hasPendingSet())
			if (event.getFrom().getBlockX() != event.getTo().getBlockX()
					|| event.getFrom().getBlockY() != event.getTo().getBlockY()
					|| event.getFrom().getBlockZ() != event.getTo().getBlockZ())
				event.setCancelled(true);
	}
}
