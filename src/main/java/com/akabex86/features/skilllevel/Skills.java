package com.akabex86.features.skilllevel;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Skills {

	private static final Gson gson = new Gson();
	
	private final EnumMap<Skill, Integer> levels = new EnumMap<>(Skill.class);
	private final UUID uuid;
	
	public Skills(UUID uuid) {
		this.uuid = uuid;
		for(Skill skill : Skill.values()) {
			levels.put(skill, 0);
		}
	}
	
	/**
	 * Sets the level for the given skill and immediately persists the change.
	 * 
	 * This method updates the internal skill level and then triggers
	 * a save operation via SkillLevelManager to ensure the state is stored.
	 *
	 * @param skill the skill to update
	 * @param level the new level to assign
	 */
	public void setLevel(Skill skill, int level) {
		levels.put(skill, level);
		SkillLevelManager.setSkills(uuid, this);
	}
	
	public int getLevel(Skill skill) {
		return levels.getOrDefault(skill, 0);
	}
	
	public Map<Skill, Integer> getSkillLevelMap(){
		return Collections.unmodifiableMap(levels);
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	@Override
	public String toString() {
		return levels.entrySet().stream()
		        .map(e -> e.getKey().name() + ": " + e.getValue())
		        .collect(Collectors.joining(" | ", "SkillLevel: ", ""));
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static Skills fromJson(String jsonString) {
		return gson.fromJson(jsonString, Skills.class);
	}
}
