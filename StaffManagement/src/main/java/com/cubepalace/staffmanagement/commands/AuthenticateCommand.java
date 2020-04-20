package com.cubepalace.staffmanagement.commands;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;
import com.cubepalace.staffmanagement.util.PlayerData;

public class AuthenticateCommand implements CommandExecutor {

	private StaffManagement plugin;
	
	public AuthenticateCommand(StaffManagement plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("authenticate")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(plugin.getMsgHandler().getMessage("consoleSender"));
				return true;
			}
			
			Player player = (Player) sender;
			PlayerData data = plugin.getDataHandler().getPlayerDataByPlayer(player);
			if (data == null)
				return true;
			
			if (args.length == 0) {
				sender.sendMessage(plugin.getMsgHandler().getMessage("authNoArgs"));
				return true;
			}
			
			String password = args[0];
			byte[] salt = plugin.getDataHandler().getPlayerDataByPlayer(player).getAssignedAuthentication().getSalt().getBytes();
			
			plugin.getLogger().info(password);
			plugin.getLogger().info(new String(salt));
			plugin.getLogger().info(saltAndHash(password, salt));
			
			plugin.getAuthHandler().authenticationAttempt(player, saltAndHash(password, salt));
		}
		return true;
	}
	
	private String saltAndHash(String str, byte[] salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] hashPassword = md.digest(str.getBytes(StandardCharsets.UTF_8));
			return new String(hashPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
