package com.akabex86.features.warp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.warp.commands.CommandDelWarp;
import com.akabex86.features.warp.commands.CommandSetWarp;
import com.akabex86.features.warp.commands.CommandWarp;
import com.akabex86.features.warp.commands.CommandWarps;
import com.akabex86.main.Main;
import com.akabex86.utils.Config;
import com.akabex86.utils.Utils;

@FeatureComponent(owner = "AkabeX86, I_Dev")
public class WarpManager extends FeaturePlugin{
	
	private static WarpManager instance;
    private static final String WARPFILE = "warps";
	
    private Map<String, Location> warpsMap = new HashMap<>();
    
    private WarpManager() {}
    
    public static WarpManager getInstance() {
    	if(instance == null) {
    		instance = new WarpManager();
    	}
    	return instance;
    }
    

	@Override
	public void onLoad() {
        CommandWarp warp = new CommandWarp();
        getCommand("warp").setExecutor(warp);
        getCommand("warp").setTabCompleter(warp);

        CommandWarps warps = new CommandWarps();
        getCommand("warps").setExecutor(warps);
        getCommand("warps").setTabCompleter(warps);

        CommandSetWarp setwarp = new CommandSetWarp();
        getCommand("setwarp").setExecutor(setwarp);
        getCommand("setwarp").setTabCompleter(setwarp);

        CommandDelWarp delwarp = new CommandDelWarp();
        getCommand("delwarp").setExecutor(delwarp);
        getCommand("delwarp").setTabCompleter(delwarp);
	}
    
    public Location getWarp(String name){
        return warpsMap.get(name);
    }
    
    public Map<String, Location> getWarps(){
        return warpsMap;
    }

    /*
     * I might have overdone this method, sorry ~I_Dev
     */
    public boolean setWarp(String name,Location loc){
    	boolean[] existing = {true};
    	warpsMap.computeIfAbsent(name, map -> {
    		existing[0] = false;
            Config.set(WARPFILE,name, Utils.locationToString(loc));
    		return loc;
    	});
        return existing[0];
    }

    public boolean delWarp(String name){
        if(!warpsMap.containsKey(name))return false;
        warpsMap.remove(name);
        Config.delete(WARPFILE,name);
        return true;
    }

    public void loadWarps(){
        //TODO try creating the warps.yml BEFORE (if it doesnt exist) in order to avoid errors.
        //inititiallizes all data from warps.yml
    	
        Main.main.getLogger().log(Level.INFO,"Lade Warps...");
        Config.getKeys(WARPFILE, false).forEach(warp -> {
        	warpsMap.put(warp,Config.getLocation(WARPFILE,warp));
            Main.main.getLogger().log(Level.INFO," - Warp '%s' geladen.".formatted(warp));
        });
        Main.main.getLogger().log(Level.INFO,"Alle Warps geladen.");
    }
}
