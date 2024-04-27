package com.akabex86.utils;

import com.akabex86.main.Main;
import com.akabex86.objects.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class Zone {
    public static HashMap<String, Cuboid> ZoneCache = new HashMap();
    Zone() {
        Main.main.getLogger().log(Level.INFO,"[ZONE] zone class initialized.");
    }
    //THE ZONE SYSTEM IS TESTED SEPERATELY.
    //GENERAL STATEMENTS
    public static boolean isWithinZone(String UUID){
        //TODO BUILD ZONE DETECTOR (detects if a player is within a zone)
        return false;
    }
    public static List<String> getTrusted(){
        // gets all trusted members of a zone
        return null;
    }
    //EDITOR ACTIONS
    public static void create(String UUID){
        //TODO CHECK FOR INTERSECTIONS
        //TODO ADD FORMULA FOR ZONE CREDITS AND LIMIT ZONE AMOUNT AND SIZE TO SAID CREDITS. (1 Credit = 10 blocks^2(squared))
    }
    public static void update(String UUID){
        //TODO CHECK FOR INTERSECTIONS
        //TODO ADD FORMULA FOR ZONE CREDITS AND LIMIT ZONE AMOUNT AND SIZE TO SAID CREDITS. (1 Credit = 10 blocks^2(squared))
    }
    public static boolean delete(String UUID){
        //TODO CHECK FOR INTERSECTIONS
        return false;
    }
    //SEPERATE ACTIONS
    public static boolean trust(String OwnerUUID,String TargetUUID){
        //TODO ADD 'TARGET' TO ALL ZONES OWNED BY 'OWNER'.
        //THIS NEEDS TO BE EXECUTED EVERYTIME 'OWNER' CREATES A NEW ZONE (TRUSTED USERS ARE CHECKED FROM THE HEROPROFILE) AND WHEN EXECUTING THE COMMAND
        //ADDS USER TO THE TRUSTED LIST INSIDE THE HERO PROFILE
        return false;
    }
    public static boolean untrust(String OwnerUUID,String TargetUUID){
        //TODO REMOVE 'TARGET' FROM ALL ZONES OWNED BY 'OWNER'.
        //THIS ONLY NEEDS TO BE EXECUTED WHEN EXECUTING THE COMMAND
        //REMOVES USER FROM THE TRUSTED LIST INSIDE THE HERO PROFILE
        return false;
    }

    // POSITION HANDLER
    // returns true if a new position has been set.
    //TODO
    // advanced error handling in update and create command(intersecting regions, too small, too large)
    public static boolean setPos1(Player p, Location pos1){
        if (p == null || pos1 == null) {
            return false;
        }
        String playerName = p.getName();
        if(ZoneCache.containsKey(playerName)){
            Cuboid c = ZoneCache.get(playerName);
            if(!Utils.isSameLocation(c.getLoc1(),pos1)){
                c.setLoc1(pos1);
                return true;
            }
            return false;
        }else{
            Cuboid c = new Cuboid();
            c.setLoc1(pos1);
            ZoneCache.put(playerName, c);
            return true;
        }
    }
    public static boolean setPos2(Player p, Location pos2){
        if (p == null || pos2 == null) {
            return false;
        }
        String playerName = p.getName();
        if(ZoneCache.containsKey(playerName)){
            Cuboid c = ZoneCache.get(playerName);
            if(!Utils.isSameLocation(c.getLoc2(),pos2)){
                c.setLoc1(pos2);
                return true;
            }
            return false;
        }else{
            Cuboid c = new Cuboid();
            c.setLoc2(pos2);
            ZoneCache.put(playerName, c);
            return true;
        }
    }
    // TOOL HANDLER
    public static boolean hasTool(Player p){
        if(p.getInventory().getItemInMainHand().getType() == Material.STICK) {
            if (p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
                return p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eZauberstab§7");
            }
        }
        return  false;
    }
    public static void createWand(ItemStack i){
        ItemMeta m = i.getItemMeta();
        m.setDisplayName("§eZauberstab§7");
        i.setItemMeta(m);
    }

}
