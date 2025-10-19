package com.akabex86.features.skilllevel;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class PlayerSkills {

	private static final Gson gson = new Gson();
	
	private final EnumMap<SkillCategory, Skill> map = new EnumMap<>(SkillCategory.class);
	
	public Skill get(SkillCategory category) {
		return map.computeIfAbsent(category, k -> new Skill());
	}
	
	public Map<SkillCategory, Skill> getMap(){
		return Collections.unmodifiableMap(map);
	}
	
	@Override
	public String toString() {
		return map.entrySet().stream()
		        .map(e -> e.getKey().name() + ": " + e.getValue())
		        .collect(Collectors.joining(" | ", "SkillLevel: ", ""));
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static PlayerSkills fromJson(String jsonString) {
		return gson.fromJson(jsonString, PlayerSkills.class);
	}
}
