package com.akabex86.features.dailyrewards;

import java.util.Calendar;
import java.util.Optional;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.akabex86.main.Main;

import de.ifheroes.core.InfinityHeroesCoreAPI;
import de.ifheroes.core.InfinityHeroesCorePlugin;
import de.ifheroes.core.profile.HeroProfile;
import de.ifheroes.core.profile.levelstructur.DomainKey;


public class LoginTracker {

	
	
	private static InfinityHeroesCoreAPI coreAPI = InfinityHeroesCorePlugin.getAPI();
	private static DomainKey timeKey = new DomainKey(JavaPlugin.getPlugin(Main.class), "lastlogin");
	
	private LoginTracker(){}
		
	public static void setLastLogin(OfflinePlayer player, long time) {
		coreAPI.getProfile((Player) player).ifPresent(profile -> profile.getPluginData().set(timeKey, time));
	}
	
	public static long getLastLoginMilis(OfflinePlayer player) {
		Optional<HeroProfile> profile = coreAPI.getProfile((Player) player);
		if(profile.isEmpty()) return System.currentTimeMillis(); //Might need to change his result to 0
		
		Optional<Long> time = profile.get().getPluginData().get(timeKey, Long.class);
		if(time.isPresent()) return time.get();
		return System.currentTimeMillis(); //Might need to change his result to 0
	}
	
	public static Calendar getLastLogin(OfflinePlayer player) {
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(getLastLoginMilis(player));
		return calender;
	}
	
}
