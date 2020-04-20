package com.cubepalace.staffmanagement.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;

public class PlayerData {

	private StaffManagement plugin;
	private File file;
	private FileConfiguration config;
	private Authentication assignedAuth;
	private DateFormat format;

	public PlayerData(StaffManagement plugin, Player player) {
		this.plugin = plugin;
		format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		file = new File(plugin.getDataHandler().getPlayerFolder(), player.getUniqueId().toString() + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().warning("Could not create file " + file.getName());
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(file);
		config.set("failedAttempts", 0);
		config.set("pendingSet", false);
		saveData();
	}
	
	public PlayerData(StaffManagement plugin, UUID uuid) {
		this.plugin = plugin;
		format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		file = new File(plugin.getDataHandler().getPlayerFolder(), uuid.toString() + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().warning("Could not create file " + file.getName());
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(file);
		config.set("failedAttempts", 0);
		config.set("pendingSet", false);
		saveData();
	}

	public void saveData() {
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.getLogger().warning("Could not save file " + file.getName());
			e.printStackTrace();
		}
	}

	public boolean hasAssignedAuthentication() {
		return assignedAuth != null;
	}

	public void setAssignedAuthentication(Authentication auth) {
		assignedAuth = auth;
	}

	public void removeAssignedAuthentication() {
		assignedAuth = null;
	}

	public Authentication getAssignedAuthentication() {
		return assignedAuth;
	}
	
	public int getFailedAttempts() {
		return config.getInt("failedAttempts");
	}
	
	public void setPending(boolean bool) {
		config.set("pendingSet", bool);
		saveData();
	}
	
	public boolean hasPendingSet() {
		return config.getBoolean("pendingSet");
	}
	
	public void clearFailedAttempts() {
		config.set("failedAttempts", 0);
		saveData();
	}

	public void addAuthenticationAttempt(AuthenticationResult result) {
		Date date = new Date();
		String dateStr = format.format(date);
		String resultStr = result.toString();
		config.set("attempts." + dateStr, resultStr);

		if (result == AuthenticationResult.FAIL)
			config.set("failedAttempts", config.getInt("failedAttempts") + 1);
		if (result == AuthenticationResult.PASS)
			config.set("failedAttempts", 0);
		saveData();
	}
}
