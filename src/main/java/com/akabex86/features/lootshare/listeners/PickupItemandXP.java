package com.akabex86.features.lootshare.listeners;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;

import com.akabex86.features.lootshare.LootShare;

public class PickupItemandXP implements Listener {

	@EventHandler
	public void playerPickUpItem(EntityPickupItemEvent event) {
		
		Optional<String> optional = Optional.ofNullable(event.getItem().getPersistentDataContainer().get(LootShare.keyTag, PersistentDataType.STRING));
		if(!optional.isPresent()) return;
		
		UUID uuid = UUID.fromString(optional.get());
		
		if(uuid.toString().equalsIgnoreCase("")) return;
		if(event.getEntity().getUniqueId().compareTo(uuid) != 0) event.setCancelled(true);
	}
	
	//TODO XP Integration
}
