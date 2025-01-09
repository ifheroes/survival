package com.akabex86.features.lootshare;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.lootshare.listeners.DamageEntity;
import com.akabex86.features.lootshare.listeners.EntityDeath;
import com.akabex86.features.lootshare.listeners.ItemMerge;
import com.akabex86.features.lootshare.listeners.PickupItemandXP;
import com.akabex86.main.Main;

@FeatureComponent(owner = "I_Dev", version = "1.0")
public class LootShare extends FeaturePlugin{

	public static final NamespacedKey keyTag = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "lootshareowner");
	private static EntityDamageRegistry entityDamageRegistry = new EntityDamageRegistry();
	public static final double SHARELOOTPERCENTAGE = 0.6;
	
	@Override
	public void onLoad() {
		Bukkit.getPluginManager().registerEvents(new DamageEntity(), getPlugin());
		Bukkit.getPluginManager().registerEvents(new EntityDeath(), getPlugin());
		Bukkit.getPluginManager().registerEvents(new PickupItemandXP(), getPlugin());
		Bukkit.getPluginManager().registerEvents(new ItemMerge(), getPlugin());
	}
	
	public static EntityDamageRegistry getEntityDamageRegistry() {
		return entityDamageRegistry;
	}
	
	public static boolean isValidEntity(Entity entity) {
		return !((entity instanceof Player) || (entity instanceof Boss));
	}
	
	public static void spawnLoot(ItemStack item, Location location, Player owner, int xp) {
		World world = location.getWorld();
		world.spawn(location, ExperienceOrb.class, t -> t.setExperience(xp));
		world.dropItemNaturally(location, item, droppedItem -> {
			PersistentDataContainer data = droppedItem.getPersistentDataContainer();
			data.set(keyTag, PersistentDataType.STRING, owner.getUniqueId().toString());
		});
	}
}
