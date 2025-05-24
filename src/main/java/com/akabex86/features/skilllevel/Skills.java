package com.akabex86.features.skilllevel;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Skills implements Iterable<ISkill>{

	private static final Gson gson = new Gson();
	
	private final EnumMap<SkillCategory, ISkill> map = new EnumMap<>(SkillCategory.class);
	private final UUID uuid;
	
	public Skills(UUID uuid) {
		this.uuid = uuid;
		for(SkillCategory skillCategory : SkillCategory.values()) {
			ISkill skillInstance = null;
			try {
				skillInstance = skillCategory.getSkillClass().getDeclaredConstructor(SkillCategory.class).newInstance(skillCategory);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			map.put(skillCategory, skillInstance);
		}
	}
	
	public ISkill get(SkillCategory category) {
		return map.get(category);
	}
	
	public Map<SkillCategory, ISkill> getMap(){
		return Collections.unmodifiableMap(map);
	}
	
	public UUID getUUID() {
		return this.uuid;
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
	
	public static Skills fromJson(String jsonString) {
		return gson.fromJson(jsonString, Skills.class);
	}

	@Override
	public Iterator<ISkill> iterator() {
		return map.values().iterator();
	}
}
