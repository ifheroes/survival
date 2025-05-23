package com.akabex86.features.dailyrewards.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.akabex86.features.dailyrewards.LoginTracker;

public class PlayerJoin implements Listener{

	@EventHandler
	public void trackLastLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		LoginTracker.setLastLogin(player, System.currentTimeMillis());
	}
}
