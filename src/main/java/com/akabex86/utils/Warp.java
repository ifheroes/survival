package com.akabex86.utils;

import com.akabex86.main.Main;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class Warp {
    private static HashMap<String, Location> warps = new HashMap<>();

    public static Location getWarp(String name){
        return warps.get(name);
    }
    public static HashMap<String, Location> getWarps(){
        return warps;
    }

    public static boolean setWarp(String name,Location loc){
        //DEBUG PARAMETER TO TEST IF METHOD IS EXECUTED PROPERLY!
        //Main.main.getLogger().log(Level.INFO,"[DEBUG] Methode setWarp() erfolgreich ausgefuehrt!");
        //if warp already exists, return false
        if(warps.containsKey(name))return false;
        warps.put(name,loc);
        Config.set("warps",name, Utils.locationToString(loc));
        return true;
    }

    public static boolean delWarp(String name){
        //DEBUG PARAMETER TO TEST IF METHOD IS EXECUTED PROPERLY!
        //Main.main.getLogger().log(Level.INFO,"[DEBUG] Methode delWarp() erfolgreich ausgefuehrt!");
        if(!warps.containsKey(name))return false;
        warps.remove(name);
        Config.delete("warps",name);
        return true;
    }

    public static void loadWarps(){
        //TODO try creating the warps.yml BEFORE (if it doesnt exist) in order to avoid errors.
        //inititiallizes all data from warps.yml
        Main.main.getLogger().log(Level.INFO,"Lade Warps...");
        for(String key : Config.getKeys("warps",false)){
            warps.put(key,Config.getLocation("warps",key));
            Main.main.getLogger().log(Level.INFO," - Warp '"+key+"' geladen.");

        }
        Main.main.getLogger().log(Level.INFO,"Alle Warps geladen.");
    }
}
