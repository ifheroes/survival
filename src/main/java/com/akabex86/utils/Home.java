package com.akabex86.utils;

import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;

public class Home {
    //TODO home system auf caching updaten um Filesystem calls zu verringern.
    public static Location getHome(String UUID, String homeName){
        if(Config.getString("userdata//"+UUID,"homes."+homeName)!=null){
                return Utils.stringToLocation(Config.getString("userdata//" + UUID, "homes." + homeName));
        }
        return null;
    }
    public static void setHome(String UUID, String homeName, Location loc){
        Config.set("userdata//"+UUID,"homes."+homeName, Utils.locationToString(loc));
    }
    public static void delHome(String UUID, String homeName){
        Config.delete("userdata//"+UUID,"homes."+homeName);
    }
    public static boolean hasHomes(String UUID){
        List<String> homes = new ArrayList<>();
        for(String key : Config.getKeys("userdata//"+UUID,true)){
            if(key.startsWith("homes.")){
                //Main.main.getLogger().log(Level.INFO,"["+UUID+"] Homes found: "+ key.replaceFirst("homes.",""));
                return true;
            }
        }
        return false;
    }
    public static List<String> getHomes(String UUID){
        List<String> homes = new ArrayList<>();
        for(String key : Config.getKeys("userdata//"+UUID,true)){
            if(key.startsWith("homes.")){
                homes.add(key.replaceFirst("homes.",""));
            }
        }
        return homes;
    }
}
