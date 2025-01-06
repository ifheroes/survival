package com.akabex86.features.lootshare;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityDamageRegistry {
	
	private Map<Entity, Map<Player, Double>> entityDamageComposition = new HashMap<>();

	public void addDamage(Entity entity, Player damager, double damage) {
		double recordedDamage = entityDamageComposition.computeIfAbsent(entity, map -> new HashMap<>())
				.getOrDefault(damager, 0D);
		entityDamageComposition.get(entity).put(damager, recordedDamage + damage);
	}

	public Map<Player, Double> getDamageComposite(Entity entity) {
		return entityDamageComposition.getOrDefault(entity, new HashMap<>());
	}

	public LinkedHashMap<Player, Double> getDamageComposite(Entity entity, Comparator<Map.Entry<Player, Double>> comparator) {
		Map<Player, Double> damageMap = entityDamageComposition.getOrDefault(entity, new HashMap<>());
		
		return damageMap.entrySet().stream().sorted(comparator).collect(
				Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue, 
					(oldValue, newValue) -> oldValue, 
					LinkedHashMap::new)
				);
	}

}
