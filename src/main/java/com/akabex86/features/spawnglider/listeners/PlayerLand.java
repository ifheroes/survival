package com.akabex86.features.spawnglider.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import com.akabex86.features.spawnglider.SpawnGlider;

public class PlayerLand implements Listener{

	private SpawnGlider spawnGlider;
	
	public PlayerLand(SpawnGlider spawnGlider) {
		this.spawnGlider = spawnGlider;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void disableBoost(EntityToggleGlideEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(event.isGliding()) return;
		Player player = (Player) event.getEntity();
		if(!player.isOnGround()) {
			event.setCancelled(true);
			return;
		}
		
		spawnGlider.removePlayerBoostTime(player);
		
	}
}
