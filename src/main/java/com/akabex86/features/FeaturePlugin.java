package com.akabex86.features;

import org.bukkit.plugin.Plugin;

public abstract class FeaturePlugin implements Feature{

	private Plugin plugin;
	
	protected FeaturePlugin(Plugin plugin) {
		this.plugin = plugin;
		registerEvents();
		registerCommands();
	}
	
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	@Override
	public void registerCommands() {}
	
	@Override
	public void registerEvents() {}
	
}
