package com.akabex86.features.lootshare.listeners;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.persistence.PersistentDataType;

import com.akabex86.features.lootshare.LootShare;

public class ItemMerge implements Listener{

	@EventHandler
	public void itemMerge(ItemMergeEvent event) {
		String owner = getOwnerOfItem(event.getEntity());
		String ownerTarget = getOwnerOfItem(event.getTarget());
		if(!owner.equals(ownerTarget)) event.setCancelled(true);
	}
	
	public String getOwnerOfItem(Entity item) {
		return Optional.ofNullable(item.getPersistentDataContainer().get(LootShare.keyTag, PersistentDataType.STRING)).orElse("");
	}
	
}
