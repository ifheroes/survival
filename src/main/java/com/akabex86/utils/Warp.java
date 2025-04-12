package com.akabex86.utils;

import com.akabex86.main.Main;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.logging.Level;

public class Warp {
    private static final HashMap<String, Location> _warps = new HashMap<>();

    public static Location GetWarpLocation(String name) {
        return _warps.get(name);
    }

    public static HashMap<String, Location> GetWarps() {
        return _warps;
    }

    public static boolean SetWarp(String name, Location location) {
        //DEBUG PARAMETER TO TEST IF METHOD IS EXECUTED PROPERLY!
        //Main.main.getLogger().log(Level.INFO,"[DEBUG] Methode setWarp() erfolgreich ausgefuehrt!");
        //if warp already exists, return false
        if (_warps.containsKey(name)) return false;
        _warps.put(name, location);
        Config.set("warps", name, Utils.locationToString(location));
        return true;
    }

    public static void DeleteWarp(String name) {
        //DEBUG PARAMETER TO TEST IF METHOD IS EXECUTED PROPERLY!
        //Main.main.getLogger().log(Level.INFO,"[DEBUG] Methode delWarp() erfolgreich ausgefuehrt!");
        if (!_warps.containsKey(name)) return;
        _warps.remove(name);
        Config.delete("warps", name);
    }

    public static void LoadWarps() {
        //TODO try creating the warps.yml BEFORE (if it doesnt exist) in order to avoid errors.
        //inititiallizes all data from warps.yml
        Main.main.getLogger().log(Level.INFO, "Lade Warps...");
        for (String key : Config.getKeys("warps", false)) {
            _warps.put(key, Config.getLocation("warps", key));
            Main.main.getLogger().log(Level.INFO, " - Warp '" + key + "' geladen.");

        }
        Main.main.getLogger().log(Level.INFO, "Alle Warps geladen.");
    }
}
