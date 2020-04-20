package com.cubepalace.staffmanagement.handlers;

import org.bukkit.ChatColor;

import com.cubepalace.staffmanagement.StaffManagement;

public class MessageHandler {

	public MessageHandler(StaffManagement plugin) {
	}
	
	public String getMessage(String input) {
		if (input.equals("authenticationAssigned"))
			return ChatColor.RED + "Your account has authentication enabled. Type /authenticate <password> to continue.";
		if (input.equals("consoleSender"))
			return ChatColor.RED + "The console cannot use this command.";
		if (input.equals("authNoArgs"))
			return ChatColor.RED + "Usage: /authenticate <password>";
		if (input.equals("authSuccess"))
			return ChatColor.GREEN + "Authentication complete";
		if (input.equals("authFail"))
			return ChatColor.DARK_RED + "Authentication failed";
		if (input.equals("noPendingAuth"))
			return ChatColor.RED + "No authentication needed";
		if (input.equals("noPermission"))
			return ChatColor.RED + "No permission";
		if (input.equals("notPlayed"))
			return ChatColor.RED + "That player has not played before";
		if (input.equals("authRemoved"))
			return ChatColor.GREEN + "Authentication successfully removed";
		if (input.equals("pendingAuthSet"))
			return ChatColor.GREEN + "Authentication was assigned to you. Do \"/staffmanagement auth set <password>\" to set your password.\nMake it memorable, but secure.";
		if (input.equals("authAdded"))
			return ChatColor.GREEN + "Authentication successfully added";
		if (input.equals("noPending"))
			return ChatColor.RED + "No pending authentication to set";
		if (input.equals("authSet"))
			return ChatColor.GREEN + "Authentication token set";
		return "";
	}
}
