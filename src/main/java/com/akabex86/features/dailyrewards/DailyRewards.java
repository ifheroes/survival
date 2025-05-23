package com.akabex86.features.dailyrewards;

import org.bukkit.Bukkit;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.dailyrewards.listeners.PlayerJoin;

@FeatureComponent(owner = "I_Dev", version = "1.0")
public class DailyRewards extends FeaturePlugin{

	@Override
	public void onLoad() {
		registerEvents();
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), getPlugin());
	}
}
