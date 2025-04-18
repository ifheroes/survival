package com.akabex86.features.warp.commands;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.warp.WarpManager;

public class CommandDelWarp implements CommandExecutor, TabCompleter {
	
	private WarpManager warpManager;
	
    public CommandDelWarp(){
    	this.warpManager = WarpManager.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    	if(args.length == 0) return false;
    	if(sender instanceof Player player) {
    		String warpName = args[0].toLowerCase();
    		Optional<Location> warp = Optional.of(warpManager.getWarp(warpName));
    		
    		warp.ifPresentOrElse(warpLocation -> {
    			warpManager.delWarp(warpName);
                player.sendMessage("Warp ["+warpName+"] erfolgreich entfernt.");
    		}, () ->  player.sendMessage("Fehler: Warp ["+warpName+"] nicht gefunden."));
    		
    		return true;
    	}
    	return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
    	return warpManager.getWarps().keySet().stream().toList();
    }
}
