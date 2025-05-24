package com.akabex86.features.skilllevel;

public enum Skill {

	AGRICULTURE("Agriculture"), MINING("Mining"), MARTIALARTS("MartialArts"), COLLECTOR("Collector");
	
	final String name;
	
	Skill(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
