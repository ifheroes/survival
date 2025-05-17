package com.akabex86.features.skilllevel;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.google.gson.Gson;

import de.ifheroes.core.InfinityHeroesCoreAPI;
import de.ifheroes.core.InfinityHeroesCorePlugin;
import de.ifheroes.core.profile.HeroProfile;
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
		Optional<HeroProfile> oProfile = api.getProfile(player);
		if(oProfile.isEmpty()) return new Skills(player.getUniqueId());
		
		HeroProfile profile = oProfile.get();
		PluginData pluginData = profile.getPluginData();
		if(!pluginData.has(domainKey)) return new Skills(player.getUniqueId()); 
		
		String jsonSkillLevel = profile.getPluginData().get(domainKey, String.class).get();
		return skillsFromString(jsonSkillLevel);
	}
	
	protected static void setSkills(UUID uuid, Skills skills) {
		Optional<HeroProfile> oProfile = api.getProfile(uuid);
		HeroProfile profile = oProfile.get();
		PluginData pluginData = profile.getPluginData();
		
		pluginData.set(domainKey, skillLevelToString(skills));
	}
	
	/*
	 * Using GSON to convert @link{SkillLevel} class to String
	 */
	protected static String skillLevelToString(Skills skilllevel) {
		return new Gson().toJson(skilllevel);
	}
	
	/*
	 * Using GSON to convert a String to @link{SkillLevel}
	 */
	protected static Skills skillsFromString(String skilllevelstring) {
		return new Gson().fromJson(skilllevelstring, Skills.class);
	}
	
}
