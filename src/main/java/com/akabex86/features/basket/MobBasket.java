package com.akabex86.features.basket;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.plugin.java.JavaPlugin;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.basket.listeners.PlayerInteract;
import com.akabex86.main.Main;

@FeatureComponent(owner = "I_Dev", version = "1.0")
public class MobBasket extends FeaturePlugin{
	
	protected static final NamespacedKey basketAttribute = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "entity");
	
	private static ItemStack defaultBasket = createBasket();
	
	//TODO: FIX Bug of basketName being null
	private static String basketName = "Basket";
	
	private static ItemStack createBasket() {
		ItemStack basket = new ItemStack(Material.BLAZE_ROD);
		Damageable basketMeta = (Damageable) basket.getItemMeta();
		basketMeta.setItemName("Basket");
		basketMeta.setDisplayName("§fBasket");
		CustomModelDataComponent modelData = basketMeta.getCustomModelDataComponent();
		modelData.setFloats(Arrays.asList(1f));
		basketMeta.setCustomModelDataComponent(modelData);
		basketMeta.setMaxDamage(20);
		basketMeta.setMaxStackSize(1);
		
		basketMeta.setLore(Arrays.asList("§9A basket to pickup friendly mobs"));
		basket.setItemMeta(basketMeta);
		return basket;
	}
	
	/**
	 * Creates a {@link Basket} object from the given {@link ItemStack}, if it is valid.
	 * <p>
	 * This method checks whether the provided ItemStack meets the required conditions 
	 * to be considered a valid Basket. If the item is valid, a new {@link Basket} object 
	 * is created and returned inside an {@link Optional}. If the item is invalid, an empty 
	 * Optional is returned.
	 * </p>
	 * 
	 * @param item The {@link ItemStack} to be validated and converted into a Basket object.
	 * @return An {@link Optional} containing a {@link Basket} if the item is valid, 
	 *         otherwise an empty Optional.
	 * 
	 * @see Basket
	 * @see ItemStack
	 * @see Optional
	 */
	public static Optional<Basket> createBasket(ItemStack item) {
		return Optional.ofNullable(item).filter(MobBasket::isParsable).map(Basket::new);
	}
	
	private static boolean isParsable(ItemStack itemStack) {
		return Optional.ofNullable(itemStack)
				.flatMap(item -> item.hasItemMeta() ? Optional.of(item.getItemMeta()) : Optional.empty())
				.filter(meta -> meta.getItemName().equalsIgnoreCase("Basket"))
				.filter(meta -> {
					CustomModelDataComponent comp = meta.getCustomModelDataComponent();
					return comp.getFloats().contains(1f) || comp.getFloats().contains(2f);
					})
				.filter(meta -> meta.getMaxStackSize() == 1).isPresent();
	}
	
	@Override
	public void onLoad() {
		registerCraftinRecipes();
		registerListeners();
	}
	
	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerInteract(), getPlugin());
	}

	private void registerCraftinRecipes() {
		/*
		 * Basket Create
		 */
		ShapedRecipe basketCraftingRecipe = new ShapedRecipe(new NamespacedKey(getPlugin(), basketName), defaultBasket);
		basketCraftingRecipe.shape("lil","lwl","lll");
		basketCraftingRecipe.setIngredient('l', Material.LEATHER);
		basketCraftingRecipe.setIngredient('i', Material.IRON_INGOT);
		basketCraftingRecipe.setIngredient('w', Material.WHEAT);
		
		basketCraftingRecipe.setCategory(CraftingBookCategory.MISC);
		
		Bukkit.addRecipe(basketCraftingRecipe);
	}
}
