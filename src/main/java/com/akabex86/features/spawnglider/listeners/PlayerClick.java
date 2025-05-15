package com.akabex86.features.spawnglider.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.akabex86.features.spawnglider.SpawnGlider;
import com.akabex86.main.Main;

public class PlayerClick implements Listener{

	private List<Player> interactQueue = new ArrayList<>();
	private SpawnGlider spawnGlider;
	
	public PlayerClick(SpawnGlider spawnGlider) {
		this.spawnGlider = spawnGlider;
	}
	
	@EventHandler
	public void boostPlayer(PlayerInteractEvent event) {
		Player player = event.getPlayer();	
		if(!player.isGliding()) return;
		if(!spawnGlider.hasPlayerBoostTime(player)) return;
		
		boostPlayer(player);
		spawnGlider.removePlayerBoostTime(player);
		addPlayerToQueue(5, player);
		event.setCancelled(true);
	}
	
	public void boostPlayer(Player player) {
		player.setVelocity(player.getLocation().getDirection().multiply(4).add(new Vector(0, 10, 0)));
	}
	
	public void addPlayerToQueue(int ticks, Player player) {
		interactQueue.add(player);
		Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Main.class), () -> interactQueue.remove(player), ticks);
	}
}
