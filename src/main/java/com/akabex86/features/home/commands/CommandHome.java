package com.akabex86.features.home.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.home.HomeManager;

public class CommandHome implements CommandExecutor, TabCompleter {
   
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    	if (!(sender instanceof Player)) return false;
    	Player player = (Player) sender; 
        String uuid = player.getUniqueId().toString();
        if (!HomeManager.hasHomes(uuid)) {
            player.sendMessage("Fehler: Du hast noch kein Home gesetzt.");
            return true;
        }

        String homeName = (args.length == 0) ? "home" : args[0];
        Optional<Location> home = HomeManager.getHome(uuid, homeName);
        
        home.ifPresentOrElse(homeLocation -> {
        	player.teleport(homeLocation);
            player.sendMessage("Teleportiere zum Home [" + homeName.toUpperCase() + "] ...");
        }, () -> player.sendMessage("Fehler: Home [" + homeName.toUpperCase() + "] nicht gefunden."));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
    	if(!(sender instanceof Player)) return Collections.emptyList();
		String uuidString = ((Player) sender).getUniqueId().toString();
		
		return HomeManager.getHomes(uuidString);
    }
}
