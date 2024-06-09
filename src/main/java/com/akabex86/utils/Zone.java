package com.akabex86.utils;

import com.akabex86.main.Main;
import com.akabex86.objects.Cuboid;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.logging.Level;

public class Zone {
    public static HashMap<String, Cuboid> ZoneCache = new HashMap();

    //TODO USE WORLD FROM INSTEAD OF USING INDEX 0
    public static String _mainWorld="world";
    Zone() {
        Main.main.getLogger().log(Level.INFO,"[ZONE] zone class initialized.");
    }
    //THE ZONE SYSTEM IS TESTED SEPERATELY.
    //GENERAL STATEMENTS
    public static boolean isWithinZone(String UUID){
        //TODO BUILD ZONE DETECTOR (detects if a player is within a zone - low priority)
        return false;
    }

    public static int create(String UUID, Cuboid selection){
        String pname = UuidFetcher.getName(UUID);
        RegionContainer cont = WorldGuard.getInstance().getPlatform().getRegionContainer();

        World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
        Location loc1 = selection.getLoc1();
        Location loc2 = selection.getLoc2();
        //WORLD IS VALIDATED THROUGH _mainWorld variable
        if(exists("Zone von "+pname)){
            //REGION ALREADY EXISTS
            return 1;
        }
        if(mainWorld != null && loc1.getWorld() == loc2.getWorld() && loc2.getWorld() == mainWorld){
            RegionManager rm = cont.get(BukkitAdapter.adapt(mainWorld));
            ProtectedCuboidRegion reg = new ProtectedCuboidRegion("Zone von "+pname,selection.getBv1(),selection.getBv2());
            if (rm != null) {
                //todo DEBUG MESSAGES
                rm.addRegion(reg);
                DefaultDomain owner = new DefaultDomain();
                owner.addPlayer(pname);
                reg.setOwners(owner);
                //REGION CREATED
                return 0;
            }
            //REGION MANAGER IS NULL
            return 2;
        }
        //INVALID LOCATION DATA
        return 3;
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
    public static boolean exists(String name){
        //TODO CHECK IF WORLDGUARD REGION WITH THE NAME EXISTS
        try{
            World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
            ProtectedRegion rg = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(mainWorld)).getRegion(name);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }
    public static boolean intersectsWith(Cuboid selection, Cuboid target){
        //TODO CHECK IF WORLDGUARD REGION INTERSECTS WITH THE TARGET
        return false;
    }


    //TODO
    // advanced error handling in update and create command(intersecting regions, too small, too large)
    //POSITION HANDLERS
    //GETTERS
    public static Location getPos1(Player p){
        String playerName = p.getName();
        if(ZoneCache.containsKey(playerName)){
            Cuboid c = ZoneCache.get(playerName);
            return c.getLoc1();
        }
        return null;
    }
    public static Location getPos2(Player p){
        String playerName = p.getName();
        if(ZoneCache.containsKey(playerName)){
            Cuboid c = ZoneCache.get(playerName);
            return c.getLoc2();
        }
        return null;
    }
    //SETTERS return true if positions have been updated
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
                c.setLoc2(pos2);
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
    public static boolean hasPos1(Player p){
        String playerName = p.getName();
        if(ZoneCache.containsKey(playerName)){
            Cuboid c = ZoneCache.get(playerName);
            if(c.getLoc1() !=null){
                return true;
            }
        }
        return false;
    }
    public static boolean hasPos2(Player p){
        String playerName = p.getName();
        if(ZoneCache.containsKey(playerName)){
            Cuboid c = ZoneCache.get(playerName);
            if(c.getLoc2() !=null){
                return true;
            }
        }
        return false;
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
