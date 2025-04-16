package com.akabex86.features;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.akabex86.main.Main;

public abstract class FeaturePlugin implements Feature{

	private Plugin plugin = JavaPlugin.getPlugin(Main.class);
	
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	public PluginCommand getCommand(String command) {
		return plugin.getServer().getPluginCommand(command);
	}
	
}
