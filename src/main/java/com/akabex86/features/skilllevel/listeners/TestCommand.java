package com.akabex86.features.skilllevel.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ifheroes.core.InfinityHeroesCoreAPI;
import de.ifheroes.core.InfinityHeroesCorePlugin;
import de.ifheroes.core.profile.HeroProfile;
import de.ifheroes.core.profile.levelstructur.DomainKey;
import de.ifheroes.core.profile.levelstructur.plugin.PluginData;

public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) return true;
		
		Player player = (Player) sender;
		InfinityHeroesCoreAPI api = InfinityHeroesCorePlugin.getAPI();
		HeroProfile profile = api.getProfile(player).get();
		
		PluginData data = profile.getPluginData();
		
		data.set(new DomainKey("testPluginString", "testString"), args[0]);
		
		player.sendMessage("send %s to database".formatted(args[0]));
		
		
		return true;
	}

	

	
	
}
