package com.akabex86.features.home.commands;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.akabex86.features.home.HomeManager;
import com.akabex86.utils.UuidFetcher;

public class CommandSpyhome implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    	if (sender instanceof Player player && args.length >= 1) {
    	    String targetName = args[0].toLowerCase();

    	    if (!UuidFetcher.isFetched(targetName)) {
    	        player.sendMessage("Fehler: Spieler [" + targetName.toUpperCase() + "] nicht gefunden.");
    	        return true;
    	    }

    	    String uuid = UuidFetcher.getUUID(targetName);
    	    String name = UuidFetcher.getName(uuid).toUpperCase();

    	    if (!HomeManager.hasHomes(uuid)) {
    	        player.sendMessage("Fehler: " + name + " hat noch kein Home gesetzt.");
    	        return true;
    	    }

    	    String homeName = (args.length == 1) ? "home" : args[1];
    	    Optional<Location> home = HomeManager.getHome(uuid, homeName);

    	    if (home.isEmpty()) {
    	        player.sendMessage("Fehler: Home [" + homeName.toUpperCase() + "] nicht gefunden.");
    	        return true;
    	    }

    	    player.teleport(home.get());
    	    String msg = (args.length == 1)
    	        ? "Teleportiere zum Home von " + name + "..."
    	        : "Teleportiere zum Home [" + homeName.toUpperCase() + "] ...";
    	    player.sendMessage(msg);
    	    return true;
    	}
        return false;
    }
}
