package com.akabex86.features.basket;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Basket {

	private ItemStack basketItem;
	private Damageable basketMeta;
	private PersistentDataContainer basketData;
	
	protected Basket(ItemStack item) {
		this.basketItem = item;
		this.basketMeta = (Damageable) basketItem.getItemMeta();
		this.basketData = basketMeta.getPersistentDataContainer();
	}
	
	public boolean hasEntity() {
		return basketData.has(MobBasket.basketAttribute);
	}
	
	public EntitySnapshot getEntity() {
		return Bukkit.getEntityFactory().createEntitySnapshot(basketData.get(MobBasket.basketAttribute, PersistentDataType.STRING));
	}
	
	public void setEntity(Entity entity) {
		basketData.set(MobBasket.basketAttribute, PersistentDataType.STRING, entity.createSnapshot().getAsString());
		basketMeta.setEnchantmentGlintOverride(true);
		changeCustomModelData(2);
		basketItem.setItemMeta(basketMeta);
	}
	
	public EntitySnapshot removeEntity() {
		EntitySnapshot entitySnapshot = getEntity();
		basketData.remove(MobBasket.basketAttribute);
		basketMeta.setEnchantmentGlintOverride(false);
		changeCustomModelData(1);
		basketMeta.setDamage(basketMeta.getDamage()+1);
		basketItem.setItemMeta(basketMeta);
		return entitySnapshot;
	}
	
	public ItemStack getItemStack() {
		return this.basketItem;
	}
	
	public Damageable getItemMeta() {
		return this.basketMeta;
	}
	
	public PersistentDataContainer getPersistentDataContainer() {
		return this.basketData;
	}
	
	private void changeCustomModelData(float value) {
		CustomModelDataComponent customModelDataComponent = basketMeta.getCustomModelDataComponent();
		customModelDataComponent.setFloats(Arrays.asList(value));
		basketMeta.setCustomModelDataComponent(customModelDataComponent);
	}
}
