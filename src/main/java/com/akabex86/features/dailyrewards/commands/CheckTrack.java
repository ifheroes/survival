package com.akabex86.features.dailyrewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.akabex86.features.dailyrewards.LoginTracker;

public class CheckTrack implements CommandExecutor{
	
	/*
	 * Tmp just for debug
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		player.sendMessage("Last Login: "+LoginTracker.getLastLoginMilis(player));
		
		return true;
	}
}
