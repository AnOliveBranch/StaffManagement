package com.cubepalace.staffmanagement;

import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import com.cubepalace.staffmanagement.commands.AuthenticateCommand;
import com.cubepalace.staffmanagement.commands.StaffManagementCommand;
import com.cubepalace.staffmanagement.handlers.AuthenticationHandler;
import com.cubepalace.staffmanagement.handlers.MessageHandler;
import com.cubepalace.staffmanagement.handlers.PlayerDataHandler;
import com.cubepalace.staffmanagement.listeners.InventoryClickListener;
import com.cubepalace.staffmanagement.listeners.PlayerChatListener;
import com.cubepalace.staffmanagement.listeners.PlayerDropItemListener;
import com.cubepalace.staffmanagement.listeners.PlayerInteractListener;
import com.cubepalace.staffmanagement.listeners.PlayerJoinListener;
import com.cubepalace.staffmanagement.listeners.PlayerMoveListener;
import com.cubepalace.staffmanagement.listeners.PlayerPreProcessListener;
import com.cubepalace.staffmanagement.listeners.PlayerQuitListener;
import com.cubepalace.staffmanagement.util.AuthenticationFile;

public class StaffManagement extends JavaPlugin {

	private Map<UUID, String> hashMap;
	private Map<UUID, String> saltMap;
	private AuthenticationFile hashesFile;
	private AuthenticationFile saltsFile;
	private AuthenticationHandler authHandler;
	private MessageHandler msgHandler;
	private PlayerDataHandler dataHandler;
	private PlayerJoinListener joinListener;
	
	@Override
	public void onEnable() {
		getLogger().info("StaffManagement has been enabled");
		setup();
		registerListeners();
		registerCommands();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("StaffManagement has been disabled");
	}
	
	private void registerListeners() {
		joinListener = new PlayerJoinListener(this, saltMap, hashMap);
		getServer().getPluginManager().registerEvents(joinListener, this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerPreProcessListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
	}
	
	private void registerCommands() {
		getCommand("authenticate").setExecutor(new AuthenticateCommand(this));
		getCommand("staffmanagement").setExecutor(new StaffManagementCommand(this));
	}
	
	private void setup() {
		if (!getDataFolder().exists())
			getDataFolder().mkdirs();
		hashesFile = new AuthenticationFile(this, "hashes.yml");
		hashMap = hashesFile.loadTokens();
		hashesFile.saveTokens(hashMap);
		saltsFile = new AuthenticationFile(this, "salts.yml");
		saltMap = saltsFile.loadTokens();
		saltsFile.saveTokens(saltMap);
		authHandler = new AuthenticationHandler(this);
		msgHandler = new MessageHandler(this);
		dataHandler = new PlayerDataHandler(this);
	}
	
	public AuthenticationFile getHashesFile() {
		return hashesFile;
	}
	
	public AuthenticationFile getSaltsFile() {
		return saltsFile;
	}
	
	public void addAuthentication(UUID uuid, String hash, String salt) {
		hashMap.put(uuid, hash);
		hashesFile.saveTokens(hashMap);
		saltMap.put(uuid, salt);
		saltsFile.saveTokens(saltMap);
		joinListener.updateMaps(hashMap, saltMap);
	}
	
	public void removeAuthentication(UUID uuid) {
		hashMap.remove(uuid);
		hashesFile.saveTokens(hashMap);
		saltMap.remove(uuid);
		saltsFile.saveTokens(saltMap);
		joinListener.updateMaps(hashMap, saltMap);
	}
	
	public AuthenticationHandler getAuthHandler() {
		return authHandler;
	}
	
	public MessageHandler getMsgHandler() {
		return msgHandler;
	}
	
	public PlayerDataHandler getDataHandler() {
		return dataHandler;
	}
}
