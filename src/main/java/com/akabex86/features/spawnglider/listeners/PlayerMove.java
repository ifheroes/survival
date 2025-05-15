package com.akabex86.features.spawnglider.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.akabex86.features.spawnglider.SpawnGlider;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class PlayerMove implements Listener{

	private SpawnGlider spawnGlider;
	
	public PlayerMove(SpawnGlider spawnGlider) {
		this.spawnGlider = spawnGlider;
	}
	
	@EventHandler
	public void playerGliding(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(player.isGliding()) return;
		if(!isPlayerInGlidingRegion(player)) return;
		
		player.setGliding(true);
		spawnGlider.addPlayerToBoostTimer(player, 10);
	}
	
	private boolean isPlayerInGlidingRegion(Player player) {
		return getPlayerRegions(player).getRegions().stream().map(ProtectedRegion::getId).filter(s -> s.equalsIgnoreCase("glide_zone")).count() != 0;
	}
	
	private ApplicableRegionSet getPlayerRegions(Player player) {
        WorldGuard wg = WorldGuard.getInstance();
        RegionContainer container = wg.getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        Location loc = BukkitAdapter.adapt(player.getLocation());
        return query.getApplicableRegions(loc);
    }
	
}
