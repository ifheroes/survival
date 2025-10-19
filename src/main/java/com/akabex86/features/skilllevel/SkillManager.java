package com.akabex86.features.skilllevel;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.skilllevel.listeners.Combat.CombatSkillXpListener;
import com.akabex86.features.skilllevel.listeners.Combat.DamageEntityListener;
import com.akabex86.features.skilllevel.listeners.Mining.BreakBlockListener;
import com.akabex86.features.skilllevel.listeners.Mining.MiningSkillXpListener;

import de.ifheroes.core.InfinityHeroesCoreAPI;
import de.ifheroes.core.InfinityHeroesCorePlugin;
import de.ifheroes.core.profile.levelstructur.DomainKey;
import de.ifheroes.core.profile.levelstructur.plugin.PluginData;

@FeatureComponent(owner = "I_Dev", version = "1.1")
public class SkillManager extends FeaturePlugin{

	private static DomainKey domainKey;
	private static InfinityHeroesCoreAPI api = InfinityHeroesCorePlugin.getAPI();
	
	@Override
	public void onLoad() {
		if(domainKey == null) domainKey = new DomainKey(getPlugin(), "skillLevel");
		
		registerEvents();
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new CombatSkillXpListener(), getPlugin());
		Bukkit.getPluginManager().registerEvents(new DamageEntityListener(), getPlugin());
		
		Bukkit.getPluginManager().registerEvents(new MiningSkillXpListener(), getPlugin());
		Bukkit.getPluginManager().registerEvents(new BreakBlockListener(), getPlugin());
	}

	public static PlayerSkills getSkills(Player player) {
		return api.getProfile(player)
		        .flatMap(profile -> {
		        	String s = profile.getPluginData()
	                .get(domainKey, String.class).get();
		        	
		        	System.out.println(s);
		        	
		            return profile.getPluginData()
		                .get(domainKey, String.class)
		                .map(PlayerSkills::fromJson);}
		        )
		        .orElse(new PlayerSkills());
	}
	
	public static void updatePlayerSkills(UUID uuid, PlayerSkills skills) {
		api.getProfile(uuid).ifPresent(profile -> {
	        PluginData data = profile.getPluginData();
	        data.set(domainKey, skills.toJson());
	    });
	}
	
}
