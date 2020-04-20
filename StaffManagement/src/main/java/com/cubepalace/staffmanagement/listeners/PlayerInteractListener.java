package com.cubepalace.staffmanagement.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerInteractListener implements Listener {

	private StaffManagement plugin;
	
	public PlayerInteractListener(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (plugin.getDataHandler().getPlayerDataByPlayer(player) == null)
			return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(player).hasAssignedAuthentication()
				|| plugin.getDataHandler().getPlayerDataByPlayer(player).hasPendingSet())
			event.setCancelled(true);
	}
}
