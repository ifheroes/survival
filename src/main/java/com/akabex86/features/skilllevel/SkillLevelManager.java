package com.akabex86.features.skilllevel;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;

import de.ifheroes.core.InfinityHeroesCoreAPI;
import de.ifheroes.core.InfinityHeroesCorePlugin;
import de.ifheroes.core.profile.levelstructur.DomainKey;
import de.ifheroes.core.profile.levelstructur.plugin.PluginData;

@FeatureComponent(owner = "I_Dev", version = "1.0")
public class SkillLevelManager extends FeaturePlugin{

	private static DomainKey domainKey;
	private static InfinityHeroesCoreAPI api = InfinityHeroesCorePlugin.getAPI();
	
	@Override
	public void onLoad() {
		if(domainKey == null) domainKey = new DomainKey(getPlugin(), "skillLevel");
	}
	
	public static Skills getSkills(Player player) {
		return api.getProfile(player)
		        .flatMap(profile ->
		            profile.getPluginData()
		                .get(domainKey, String.class)
		                .map(Skills::fromJson)
		        )
		        .orElse(new Skills(player.getUniqueId()));
	}
	
	protected static void setSkills(UUID uuid, Skills skills) {
		api.getProfile(uuid).ifPresent(profile -> {
	        PluginData data = profile.getPluginData();
	        data.set(domainKey, skills.toJson());
	    });
	}
	
}
