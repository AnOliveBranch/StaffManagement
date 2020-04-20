package com.cubepalace.staffmanagement.handlers;

import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;
import com.cubepalace.staffmanagement.util.Authentication;
import com.cubepalace.staffmanagement.util.AuthenticationResult;
import com.cubepalace.staffmanagement.util.PlayerData;

public class AuthenticationHandler {

	private StaffManagement plugin;
	
	public AuthenticationHandler(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	public void assignAuthenticationTask(Player player) {
		player.sendMessage(plugin.getMsgHandler().getMessage("authenticationAssigned"));
		plugin.getDataHandler().addPlayerDataByPlayer(player);
		plugin.getDataHandler().getPlayerDataByPlayer(player).setAssignedAuthentication(new Authentication(plugin, player));
	}
	
	public void authenticationAttempt(Player player, String pass) {
		PlayerData data = plugin.getDataHandler().getPlayerDataByPlayer(player);
		if (data.hasAssignedAuthentication()) {
			if (pass == null) {
				data.addAuthenticationAttempt(AuthenticationResult.LEAVE);
				return;
			}
			
			if (pass.equals(data.getAssignedAuthentication().getHash())) {
				player.sendMessage(plugin.getMsgHandler().getMessage("authSuccess"));
				data.addAuthenticationAttempt(AuthenticationResult.PASS);
				removeAuthenticationTask(player);
			} else {
				player.sendMessage(plugin.getMsgHandler().getMessage("authFail"));
				data.addAuthenticationAttempt(AuthenticationResult.FAIL);
				if (data.getFailedAttempts() > plugin.getConfig().getInt("max-attempts")) {
					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ban " + player.getName() + " Too many failed authentication attempts, contact the owners to fix");
				}
			}
		} else {
			player.sendMessage(plugin.getMsgHandler().getMessage("noPendingAuth"));
			return;
		}
	}
	
	private void removeAuthenticationTask(Player player) {
		plugin.getDataHandler().getPlayerDataByPlayer(player).removeAssignedAuthentication();
	}
}
