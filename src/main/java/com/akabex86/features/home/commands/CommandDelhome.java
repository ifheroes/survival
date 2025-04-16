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

public class CommandDelhome implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player))return false;
		if (args.length == 0) return false;

		Player p = (Player) sender;
		String uuidString = p.getUniqueId().toString();
		String homeName = args[0].toLowerCase();
		
		// TODO USE LOCAL UUID FETCHER
		Optional<Location> oLocation = HomeManager.getHome(uuidString, homeName);
		
		oLocation.ifPresentOrElse(home -> {
			HomeManager.delHome(p.getUniqueId().toString(), args[0].toLowerCase());
			p.sendMessage("Home [" + args[0].toUpperCase() + "] erfolgreich entfernt.");
		}, () -> p.sendMessage("Fehler: Home [" + args[0].toUpperCase() + "] nicht gefunden."));
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
		if(!(sender instanceof Player)) return Collections.emptyList();
		String uuidString = ((Player) sender).getUniqueId().toString();
		
		return HomeManager.getHomes(uuidString);
	}
}
