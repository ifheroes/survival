package com.akabex86.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.warps.WarpManager;

public class CommandSpawn implements CommandExecutor, TabCompleter {
	
	private static final String SPAWNKEY = "spawn";
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    	if(sender instanceof Player player) {
    		Optional<Location> spawnLocation = Optional.ofNullable(WarpManager.getWarp(SPAWNKEY));
    		
    		spawnLocation.ifPresentOrElse(location -> {
    			player.teleport(location);
    			player.sendMessage("Teleportiere zum Spawn ...");
    		}, () ->  player.sendMessage("Fehler: Spawnpunkt nicht gefunden."));
    	}
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
