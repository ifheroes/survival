package com.akabex86.features.lootshare.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.akabex86.features.lootshare.LootShare;

public class DamageEntity implements Listener{

	@EventHandler
	public void playerDamageEntity(EntityDamageByEntityEvent event) {
		if(!LootShare.isValidEntity(event.getEntity())) return;
		if(!(event.getDamager() instanceof Player)) return;
		
		LootShare.getEntityDamageRegistry().addDamage(event.getEntity(), (Player) event.getDamager(), event.getDamage());
	} 
	
}
