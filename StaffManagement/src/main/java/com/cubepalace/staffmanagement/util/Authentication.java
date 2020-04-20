package com.cubepalace.staffmanagement.util;

import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;

public class Authentication {

	private String hash;
	private String salt;
	
	public Authentication(StaffManagement plugin, Player player, String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getSalt() {
		return salt;
	}
}
