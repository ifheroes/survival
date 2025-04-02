package com.akabex86.features.basket.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.akabex86.features.basket.Basket;
import com.akabex86.features.basket.MobBasket;
import com.akabex86.main.Main;

public class PlayerInteract implements Listener{


	private List<Player> interactQueue = new ArrayList<>();
	
	@EventHandler
	public void fillBasket(PlayerInteractAtEntityEvent event) {
		//TODO: Make this work with offhand aswell 
		Player player = event.getPlayer();
		if(interactQueue.contains(player)) return;
		
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		Optional<Basket> opBasket = MobBasket.createBasket(itemInHand);
		if(opBasket.isEmpty()) return;
		
		Basket basket = opBasket.get();
		if(basket.hasEntity()) return;
		
		Entity rightClicked = event.getRightClicked();
		if(!((rightClicked instanceof Animals) || (rightClicked instanceof Breedable))) return;
			
		basket.setEntity(rightClicked);
		rightClicked.remove();
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
		
		addPlayerToQueue(5, player);
	}
	
	@EventHandler
	public void emptyBasket(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(interactQueue.contains(player)) return;
		
		//TODO: Make this work with offhand aswell
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		Optional<Basket> opBasket = MobBasket.createBasket(itemInHand);
		if(opBasket.isEmpty()) return;
		
		Basket basket = opBasket.get();
		if(!basket.hasEntity()) return;
		
		Location clickedLocation = event.getClickedBlock().getLocation().add(event.getClickedPosition());
		if(clickedLocation.clone().add(0, 1, 0).getBlock().getType().isOccluding()) return;
		
		basket.removeEntity().createEntity(clickedLocation);
		clickedLocation.getWorld().spawnParticle(Particle.WAX_ON, clickedLocation, 10, 0.2, 0.1, 0.2, 1, null, false);
		clickedLocation.getWorld().playSound(clickedLocation, Sound.ENTITY_ARMADILLO_UNROLL_FINISH, 1, 1);
		
		if(basket.getItemMeta().getDamage() >= basket.getItemMeta().getMaxDamage()) {
			player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			player.getWorld().playSound(player, Sound.ENTITY_PAINTING_BREAK, 1, 1);
		}
		
		addPlayerToQueue(5, player);
	}
	
	public void addPlayerToQueue(int ticks, Player player) {
		interactQueue.add(player);
		Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Main.class), () -> interactQueue.remove(player), ticks);
	}
}
