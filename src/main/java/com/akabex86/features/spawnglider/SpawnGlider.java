package com.akabex86.features.spawnglider;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.spawnglider.listeners.PlayerClick;
import com.akabex86.features.spawnglider.listeners.PlayerLand;
import com.akabex86.features.spawnglider.listeners.PlayerMove;
import com.sk89q.worldguard.WorldGuard;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@FeatureComponent(owner = "I_Dev", version = "1.0")
public class SpawnGlider extends FeaturePlugin {

	private Map<Player, Double> playerBoostTime = new HashMap<>();

	@Override
	public void onLoad() {
		if(!isWorldGuardLoaded()) return;
		registerEvents();
		reduceTimeScheduler();
	}
	
	/*
	 * @param time is in seconds
	 */
	public void addPlayerToBoostTimer(Player player, double time) {
		playerBoostTime.put(player, time);
	}
	
	public boolean hasPlayerBoostTime(Player player) {
		return playerBoostTime.containsKey(player);
	}
	
	public boolean removePlayerBoostTime(Player player) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(""));
		return playerBoostTime.remove(player) != null;
	}
	
	public double getPlayerBoostTime(Player player) {
		return playerBoostTime.get(player);
	}
	
	private void reduceTimeScheduler() {
		Bukkit.getScheduler().runTaskTimer(getPlugin(), () -> {
			playerBoostTime.forEach((player, time) -> playerBoostTime.compute(player, (k, v) -> v <= 0 ? null : v - 0.1));
			playerBoostTime.keySet().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacy("Time for your Elytraboost (LEFT_CLICK): %sSekunden".formatted(roundTime(getPlayerBoostTime(player))))));
		}, 2L, 2L);
	}
	
	private double roundTime(double time) {
		return Math.round(time * 10.0) / 10.0;
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerMove(this), getPlugin());
		Bukkit.getPluginManager().registerEvents(new PlayerClick(this), getPlugin());
		Bukkit.getPluginManager().registerEvents(new PlayerLand(this), getPlugin());
	}
	
	private boolean isWorldGuardLoaded() {
		if(WorldGuard.getInstance() == null) {
			Bukkit.getLogger().log(Level.WARNING, "Couldn't loade Feature: Spawnglider | Worldguard is not loaded!");
			return false;
		}
		return true;
	}
	
}
