package com.cubepalace.staffmanagement.handlers;

import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;
import com.cubepalace.staffmanagement.util.Authentication;
import com.cubepalace.staffmanagement.util.AuthenticationResult;
import com.cubepalace.staffmanagement.util.PlayerData;
import com.cubepalace.staffmanagement.util.StringHasher;

public class AuthenticationHandler {

	private StaffManagement plugin;
	
	public AuthenticationHandler(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	public void assignAuthenticationTask(Player player, String hash, String salt) {
		player.sendMessage(plugin.getMsgHandler().getMessage("authenticationAssigned"));
		plugin.getDataHandler().addPlayerDataByPlayer(player);
		plugin.getDataHandler().getPlayerDataByPlayer(player).setAssignedAuthentication(new Authentication(plugin, player, hash, salt));
	}
	
	public void authenticationAttempt(Player player, String pass) {
		PlayerData data = plugin.getDataHandler().getPlayerDataByPlayer(player);

		if (data.hasAssignedAuthentication()) {
			if (pass == null) {
				data.addAuthenticationAttempt(AuthenticationResult.LEAVE);
				return;
			}
			
			if (verifyPassword(player, pass)) {
				player.sendMessage(plugin.getMsgHandler().getMessage("authSuccess"));
				data.addAuthenticationAttempt(AuthenticationResult.PASS);
				removeAuthenticationTask(player);
			} else {
				player.sendMessage(plugin.getMsgHandler().getMessage("authFail"));
				data.addAuthenticationAttempt(AuthenticationResult.FAIL);
				if (data.getFailedAttempts() >= plugin.getConfig().getInt("max-attempts")) {
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
	
	private boolean verifyPassword(Player player, String pass) {
		PlayerData data = plugin.getDataHandler().getPlayerDataByPlayer(player);
		Authentication playerAuth = data.getAssignedAuthentication();
		return new StringHasher(pass, getSalt(player)).saltAndHash().equals(playerAuth.getHash());
	}
	
	private byte[] getSalt(Player player) {
		return plugin.getDataHandler().getPlayerDataByPlayer(player).getAssignedAuthentication().getSalt().getBytes();
	}
}
