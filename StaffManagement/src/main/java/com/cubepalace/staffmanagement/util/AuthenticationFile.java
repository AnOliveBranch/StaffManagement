package com.cubepalace.staffmanagement.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	/*
	 * TODO: Separate salts & hashesinto separate files
	 */
	
	public Map<UUID, List<String>> loadTokens() {
		Map<UUID, List<String>> tokens = new HashMap<UUID, List<String>>();

		try {
			for (String uuidStr : config.getConfigurationSection("authTokens").getKeys(false)) {
				plugin.getLogger().info(uuidStr);
				UUID uuid = UUID.fromString(uuidStr);
				plugin.getLogger().info(config.getList("authTokens." + uuidStr).toString());

				String hash = new String((String) config.getList("authTokens." + uuidStr).get(0));
				String salt = new String((String) config.getList("authTokens." + uuidStr).get(1));
				List<String> saltAndHash = new ArrayList<String>();
				saltAndHash.add(hash);
				saltAndHash.add(salt);
				tokens.put(uuid, saltAndHash);
			}
		} catch (NullPointerException e) {
			// This is in case "authTokens" hasn't been generated yet, we don't need to do anything
		}

		return tokens;
	}

	public void saveTokens(Map<UUID, List<String>> tokens) {
		Map<String, List<String>> strTokens = new HashMap<String, List<String>>();

		for (UUID uuid : tokens.keySet()) {
			strTokens.put(uuid.toString(), tokens.get(uuid));
		}

		config.set("authTokens", strTokens);
		save();
	}
}
