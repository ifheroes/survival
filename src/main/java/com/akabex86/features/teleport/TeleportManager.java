package com.akabex86.features.teleport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;

@FeatureComponent(owner = "AkabeX86, I_Dev", version = "1.1")
public class TeleportManager extends FeaturePlugin {

	private static TeleportManager instance;

	public static TeleportManager getInstance() {
		if(instance == null) {
			instance = new TeleportManager();
		}
		return instance;
	}
	
	@Override
	public void onLoad() {
		
	}
	
	private Map<UUID, List<UUID>> tpaRequests = new HashMap<>();
	
	private TeleportManager() {}
	
	public boolean addRequest(UUID reciever, UUID sender) {
		if(hasRequest(reciever, sender)) return false;
		return tpaRequests.computeIfAbsent(reciever, map -> new ArrayList<>()).add(sender);
	}
	
	private List<UUID> getTpaRequests(UUID uuid) {
		return tpaRequests.computeIfAbsent(uuid, map -> Collections.emptyList());
	}
	
	public boolean hasRequest(UUID reciever, UUID sender) {
		return getTpaRequests(reciever).contains(sender);
	}
	
	
}
