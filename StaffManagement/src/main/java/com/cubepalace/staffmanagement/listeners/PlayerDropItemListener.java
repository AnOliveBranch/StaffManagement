package com.cubepalace.staffmanagement.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerDropItemListener implements Listener {

	private StaffManagement plugin;
	
	public PlayerDropItemListener(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (plugin.getDataHandler().getPlayerDataByPlayer(player) == null)
			return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(player).hasAssignedAuthentication()
				|| plugin.getDataHandler().getPlayerDataByPlayer(player).hasPendingSet())
			event.setCancelled(true);
	}
}
