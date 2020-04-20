package com.cubepalace.staffmanagement.util;

import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;

public class Authentication {

	private String hash;
	private String salt;
	
	public Authentication(StaffManagement plugin, Player player) {
		hash = plugin.getAuthMap().get(player.getUniqueId()).get(0);
		salt = plugin.getAuthMap().get(player.getUniqueId()).get(1);
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getSalt() {
		return salt;
	}
}
