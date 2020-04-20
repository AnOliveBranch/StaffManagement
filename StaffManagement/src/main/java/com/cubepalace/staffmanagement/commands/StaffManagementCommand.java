package com.cubepalace.staffmanagement.commands;

import java.security.SecureRandom;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cubepalace.staffmanagement.StaffManagement;
import com.cubepalace.staffmanagement.util.StringHasher;

public class StaffManagementCommand implements CommandExecutor {

	private StaffManagement plugin;

	public StaffManagementCommand(StaffManagement plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staffmanagement")) {
			if (!sender.hasPermission("staffmanagement.command")) {
				sender.sendMessage(plugin.getMsgHandler().getMessage("noPermission"));
				return true;
			}
			
			if (args.length == 0) {
				showHelp(sender);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("auth")) {
				if (args.length < 3) {
					showHelp(sender);
					return true;
				}
				
				if (args[1].equalsIgnoreCase("add")) {
					if (!sender.hasPermission("staffmanagement.auth.add")) {
						sender.sendMessage(plugin.getMsgHandler().getMessage("noPermission"));
						return true;
					}
					
					Player target = plugin.getServer().getPlayer(args[2]);
					if (target == null) {
						@SuppressWarnings("deprecation")
						OfflinePlayer offlineTarget = plugin.getServer().getOfflinePlayer(args[2]);
						if (!offlineTarget.hasPlayedBefore()) {
							sender.sendMessage(plugin.getMsgHandler().getMessage("notPlayed"));
							return true;
						}
						UUID uuid = offlineTarget.getUniqueId();
						plugin.getDataHandler().addPlayerDataByUUID(uuid);
						plugin.getDataHandler().getPlayerDataByUUID(uuid).setPending(true);
						sender.sendMessage(plugin.getMsgHandler().getMessage("authAdded"));
					} else {
						plugin.getDataHandler().addPlayerDataByPlayer(target);
						plugin.getDataHandler().getPlayerDataByPlayer(target).setPending(true);
						
						target.sendMessage(plugin.getMsgHandler().getMessage("pendingAuthSet"));
						sender.sendMessage(plugin.getMsgHandler().getMessage("authAdded"));
					}
					
				} else if (args[1].equalsIgnoreCase("remove")) {
					if (!sender.hasPermission("staffmanagement.auth.remove")) {
						sender.sendMessage(plugin.getMsgHandler().getMessage("noPermission"));
						return true;
					}
					
					UUID uuid = getUUIDFromName(args[2]);
					if (uuid == null) {
						sender.sendMessage(plugin.getMsgHandler().getMessage("notPlayed"));
						return true;
					}
					
					plugin.removeAuthentication(uuid);
					plugin.getDataHandler().removePlayerDataByUUID(uuid);
					sender.sendMessage(plugin.getMsgHandler().getMessage("authRemoved"));
				} else if (args[1].equalsIgnoreCase("set")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(plugin.getMsgHandler().getMessage("consoleSender"));
						return true;
					}
					
					Player player = (Player) sender;
					
					if (!player.hasPermission("staffmanagement.auth.set")) {
						player.sendMessage(plugin.getMsgHandler().getMessage("noPermission"));
						return true;
					}
					
					if ((plugin.getDataHandler().getPlayerDataByPlayer(player) == null) || !plugin.getDataHandler().getPlayerDataByPlayer(player).hasPendingSet()) {
						player.sendMessage(plugin.getMsgHandler().getMessage("noPending"));
						return true;
					}
					
					String password = args[2];
					byte[] salt = generateSalt();
					
					plugin.addAuthentication(player.getUniqueId(), new StringHasher(password, salt).saltAndHash(), new String(salt));
					plugin.getDataHandler().getPlayerDataByPlayer(player).setPending(false);
					player.sendMessage(plugin.getMsgHandler().getMessage("authSet"));
				}
			}
		}
		return true;
	}

	private UUID getUUIDFromName(String name) {
		UUID uuid;
		Player target = plugin.getServer().getPlayer(name);
		if (target == null) {
			@SuppressWarnings("deprecation")
			OfflinePlayer offlineTarget = plugin.getServer().getOfflinePlayer(name);
			if (!offlineTarget.hasPlayedBefore()) {
				// sender.sendMessage(plugin.getMsgHandler().getMessage("notPlayed"));
				return null;
			}
			uuid = offlineTarget.getUniqueId();
		} else {
			uuid = target.getUniqueId();
		}
		return uuid;
	}

	private void showHelp(CommandSender sender) {
		if (sender.hasPermission("staffmanagement.auth.add"))
			sender.sendMessage(ChatColor.AQUA + "/staffmanagement auth add <player>");
		if (sender.hasPermission("staffmanagement.auth.remove"))
			sender.sendMessage(ChatColor.AQUA + "/staffmanagement auth remove <player>");
		if (sender.hasPermission("staffmanagement.auth.set"))
			sender.sendMessage(ChatColor.AQUA + "/staffmanagement auth set <password>");
	}
	
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
}
