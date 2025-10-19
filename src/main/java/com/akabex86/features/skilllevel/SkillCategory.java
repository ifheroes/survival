package com.akabex86.features.skilllevel;

import net.md_5.bungee.api.ChatColor;

public enum SkillCategory {

	AGRICULTURE("Agriculture", ChatColor.of("#1CE764")), 
	MINING("Mining", ChatColor.of("#FF0000")), 
	COMBAT("Combat", ChatColor.of("#E41D3B")), 
	COLLECTOR("Collector", ChatColor.of("#FF0000"));
	
	final String name;
	final ChatColor color;
	
	SkillCategory(String name, ChatColor color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
}
