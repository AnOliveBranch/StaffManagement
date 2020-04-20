package com.cubepalace.staffmanagement.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.cubepalace.staffmanagement.StaffManagement;

public class InventoryClickListener implements Listener {

	private StaffManagement plugin;

	public InventoryClickListener(StaffManagement plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		HumanEntity entity = event.getWhoClicked();
		if (!(entity instanceof Player))
			return;
		Player player = (Player) entity;
		if (plugin.getDataHandler().getPlayerDataByPlayer(player) == null)
			return;
		if (plugin.getDataHandler().getPlayerDataByPlayer(player).hasAssignedAuthentication()
				|| plugin.getDataHandler().getPlayerDataByPlayer(player).hasPendingSet())
			event.setCancelled(true);
	}
}
