package com.akabex86.features.home.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.home.HomeManager;
import com.akabex86.utils.Regex;

public class CommandSethome implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player player)) return false;

	    int maxHomes = 10; // TODO: MaxHomes aus HeroProfile laden
	    String homeName = (args.length == 0) ? "home" : args[0];

	    if (Regex.containsIllegalCharacters(homeName)) {
	        player.sendMessage("Fehler: Nur Buchstaben, Unterstriche und Zahlen sind erlaubt!");
	        return true;
	    }

	    setHome(player, homeName.toLowerCase(), maxHomes);
	    return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
	    return Collections.emptyList();
	}

	private void setHome(Player player, String home, int maxHomes) {
	    String uuid = player.getUniqueId().toString();

	    boolean hasHome = HomeManager.getHome(uuid, home).isPresent();
	    int currentHomes = HomeManager.hasHomes(uuid) ? HomeManager.getHomes(uuid).size() : 0;

	    if (currentHomes >= maxHomes && !hasHome) {
	        player.sendMessage("Du kannst nicht mehr als " + maxHomes + " Homes erstellen.");
	        return;
	    }

	    HomeManager.setHome(uuid, home, player.getLocation());
	    player.sendMessage("Home [" + home.toUpperCase() + "] gesetzt!");
	}
}
