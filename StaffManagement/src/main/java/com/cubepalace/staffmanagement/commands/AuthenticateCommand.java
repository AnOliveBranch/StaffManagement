package com.cubepalace.staffmanagement.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;

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
			
			if (plugin.getDataHandler().getPlayerDataByPlayer(player) == null)
				return true;
			
			if (args.length == 0) {
				player.sendMessage(plugin.getMsgHandler().getMessage("authNoArgs"));
				return true;
			}
			
			if (plugin.getDataHandler().getPlayerDataByPlayer(player).hasAssignedAuthentication()) {
				plugin.getAuthHandler().authenticationAttempt(player, args[0]);
			} else {
				player.sendMessage(plugin.getMsgHandler().getMessage("noPendingAuth"));
			}
			return true;
		}
		return true;
	}
}
