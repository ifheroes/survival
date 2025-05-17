package com.akabex86.features.skilllevel;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Skills {

	private final EnumMap<Skill, Integer> levels = new EnumMap<>(Skill.class);
	private final UUID uuid;
	
	public Skills(UUID uuid) {
		this.uuid = uuid;
		for(Skill skill : Skill.values()) {
			levels.put(skill, 0);
		}
	}
	
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
		StringBuilder builder = new StringBuilder("SkillLevel:");
		
		levels.entrySet().forEach(entry -> builder.append(" "+entry.getKey().name+": "+ entry.getValue() + " |"));
		return builder.toString();
	}
}
