package com.akabex86.features.lootshare.listeners;

import java.util.Optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.persistence.PersistentDataType;

import com.akabex86.features.lootshare.LootShare;

public class ItemMerge implements Listener{

	@EventHandler
	public void itemMerge(ItemMergeEvent event) {
		String owner = Optional.ofNullable(event.getEntity().getPersistentDataContainer().get(LootShare.keyTag, PersistentDataType.STRING)).orElse("");
		String ownerTarget = Optional.ofNullable(event.getTarget().getPersistentDataContainer().get(LootShare.keyTag, PersistentDataType.STRING)).orElse("");
		if(!owner.equals(ownerTarget)) event.setCancelled(true);
	}
	
}
