package com.akabex86.features.skilllevel;

import net.md_5.bungee.api.ChatColor;

public enum SkillCategory {

	AGRICULTURE("Agriculture", ChatColor.of("#1CE764"), ChatColor.of("#1CE764")), 
	MINING("Mining", ChatColor.of("#E4AE4C"), ChatColor.of("#E54DC2")), 
	COMBAT("Combat", ChatColor.of("#E41D3B"), ChatColor.of("#E41D3B")), 
	COLLECTOR("Collector", ChatColor.of("#FF0000"), ChatColor.of("#FF0000"));
	
	private final String name;
	private final ChatColor baseColor, secondaryColor;
	
	SkillCategory(String name, ChatColor baseColor, ChatColor secondaryColor) {
		this.name = name;
		this.baseColor = baseColor;
		this.secondaryColor = secondaryColor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ChatColor getBaseColor() {
		return baseColor;
	}
	
	public ChatColor getSecondaryColor() {
		return secondaryColor;
	}
	
}
