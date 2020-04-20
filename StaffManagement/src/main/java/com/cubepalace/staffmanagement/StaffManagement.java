package com.cubepalace.staffmanagement;

import java.util.ArrayList;
import java.util.List;
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

	private Map<UUID, List<String>> authMap;
	private AuthenticationFile authFile;
	private AuthenticationHandler authHandler;
	private MessageHandler msgHandler;
	private PlayerDataHandler dataHandler;
	
	@Override
	public void onEnable() {
		getLogger().info("StaffManagement has been enabled");
		registerListeners();
		registerCommands();
		setup();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("StaffManagement has been disabled");
	}
	
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
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
		authFile = new AuthenticationFile(this, "authentication.yml");
		authMap = authFile.loadTokens();
		authHandler = new AuthenticationHandler(this);
		msgHandler = new MessageHandler(this);
		dataHandler = new PlayerDataHandler(this);
	}
	
	public AuthenticationFile getAuthFile() {
		return authFile;
	}
	
	public Map<UUID, List<String>> getAuthMap() {
		return authMap;
	}
	
	public void addAuthentication(UUID uuid, String hash, String salt) {
		List<String> saltAndHash = new ArrayList<String>();
		saltAndHash.add(hash);
		saltAndHash.add(salt);
		authMap.put(uuid, saltAndHash);
		authFile.saveTokens(authMap);
	}
	
	public void removeAuthentication(UUID uuid) {
		authMap.remove(uuid);
		authFile.saveTokens(authMap);
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
