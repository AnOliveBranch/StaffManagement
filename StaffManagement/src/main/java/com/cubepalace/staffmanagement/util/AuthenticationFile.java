package com.cubepalace.staffmanagement.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.cubepalace.staffmanagement.StaffManagement;

public class AuthenticationFile {

	private StaffManagement plugin;
	private File file;
	private FileConfiguration config;

	public AuthenticationFile(StaffManagement plugin, String fileName) {
		this.plugin = plugin;
		file = new File(plugin.getDataFolder(), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().warning("Could not create file " + file.getName());
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.getLogger().warning("Could not save file " + file.getName());
			e.printStackTrace();
		}
	}

	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
		save();
	}
	
	public Map<UUID, String> loadTokens() {
		Map<UUID, String> tokens = new HashMap<>();

		try {
			for (String uuidStr : config.getConfigurationSection("authTokens").getKeys(false)) {
				plugin.getLogger().info(uuidStr);
				UUID uuid = UUID.fromString(uuidStr);
				String token = config.getString("authTokens." + uuidStr);
				tokens.put(uuid, token);
			}
		} catch (NullPointerException e) {
			// This is in case "authTokens" hasn't been generated yet, we don't need to do anything
		}

		return tokens;
	}

	public void saveTokens(Map<UUID, String> tokens) {
		Map<String, String> strTokens = new HashMap<>();

		for (UUID uuid : tokens.keySet()) {
			strTokens.put(uuid.toString(), tokens.get(uuid));
		}

		config.set("authTokens", strTokens);
		save();
	}
}
