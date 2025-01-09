package com.akabex86.features.lootshare.listeners;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.entity.Boss;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.akabex86.features.lootshare.LootShare;

public class EntityDeath implements Listener{


	private static Comparator<Map.Entry<Player, Double>> comparator = Map.Entry.<Player, Double>comparingByValue().reversed();
	
	@EventHandler
	public void entityDeath(EntityDeathEvent event) {
		LivingEntity killedEntity = event.getEntity();
		if(!LootShare.isValidEntity(killedEntity)) return;
		if(killedEntity.getKiller() == null) return;
		
		LinkedHashMap<Player, Double> damageComposit = LootShare.getEntityDamageRegistry().getDamageComposite(killedEntity, comparator);
		
		double maxDamage = damageComposit.values().stream().findFirst().orElse(0D); if(maxDamage == 0D) return;
		
		damageComposit.entrySet().stream()
						.filter(entry -> entry.getValue() >= LootShare.SHARELOOTPERCENTAGE * maxDamage)
						.map(m -> m.getKey())
						.forEach(player -> {
							event.getDrops().forEach(drop -> LootShare.spawnLoot(drop, killedEntity.getLocation(), player));
							LootShare.spawnXP(player.getLocation(), event.getDroppedExp());
						});

		event.getDrops().clear();
		event.setDroppedExp(0);
		LootShare.getEntityDamageRegistry().removeEntity(killedEntity);
	}
	
	@EventHandler
	public void bossDeath(EntityDeathEvent event) {
		LivingEntity killedEntity = event.getEntity();
		if(!(killedEntity instanceof Boss)) return;
		if(killedEntity.getKiller() == null) return;
		
		LinkedHashMap<Player, Double> damageComposit = LootShare.getEntityDamageRegistry().getDamageComposite(killedEntity, comparator);
		Player player = damageComposit.keySet().stream().findFirst().orElse(killedEntity.getKiller());
		
		event.getDrops().forEach(item -> {
			LootShare.spawnLoot(item, killedEntity.getLocation(), player);
			LootShare.spawnXP(player.getLocation(), event.getDroppedExp());
		});
		
		event.setDroppedExp(0);
		event.getDrops().clear();
	}
	
}
