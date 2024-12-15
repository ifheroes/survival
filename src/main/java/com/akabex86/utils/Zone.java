package com.akabex86.utils;

import com.akabex86.main.Main;
import com.akabex86.objects.Cuboid;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

public class Zone {
	
	public enum CreateResult {
		REGIONCREATED, ALLREADYEXISTS, SELECTIONTOLARGE, SELECTIONTOSMALL, INVALIDLOCATION, ZONEINTERSECTION, ZONEMANAGERNOTFOUND
	}
	
    public static HashMap<String, Cuboid> ZoneCache = new HashMap();

    //TODO USE WORLD FROM INSTEAD OF USING INDEX 0
    public static String _mainWorld="world";
    Zone() {
        Main.main.getLogger().log(Level.INFO,"[ZONE] zone class initialized.");
    }
    //THE ZONE SYSTEM IS TESTED SEPERATELY.
    //GENERAL STATEMENTS

    public static CreateResult create(String UUID, Cuboid selection){
        String pname = UuidFetcher.getName(UUID);
        RegionContainer cont = WorldGuard.getInstance().getPlatform().getRegionContainer();

        World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
        Location loc1 = selection.getLoc1();
        Location loc2 = selection.getLoc2();
        //THE RIGHT WORLD IS DETERMINED BY THE _mainWorld variable
        if(exists("zone_"+pname)){
            //ZONE ALREADY EXISTS
            //TODO MODIFY THIS IN THE FUTURE TO REMOVE THE ZONE AMOUNT LIMIT
            return CreateResult.ALLREADYEXISTS;
        }
        if(selection.getBlocks_2D() > 7000){
            //SELECTION TOO LARGE
            return CreateResult.SELECTIONTOLARGE;
        }
        if(selection.getLength_X() < 10||selection.getLength_Z() < 10){
            //SELECTION TOO SMALL
            return CreateResult.SELECTIONTOSMALL;
        }
        if(mainWorld != null && loc1.getWorld() == loc2.getWorld() && loc2.getWorld() == mainWorld){
            RegionManager rm = cont.get(BukkitAdapter.adapt(mainWorld));
            ProtectedCuboidRegion reg = new ProtectedCuboidRegion("zone_"+pname,selection.getBv1(),selection.getBv2());
            if (rm != null) {
                rm.addRegion(reg);
                if(isIntersecting(rm,reg)){
                    //THIS REMOVES THE REGION AGAIN IF IT INTERSECTS WITH ANOTHER ONE
                    rm.removeRegion(reg.getId());
                    return CreateResult.ZONEINTERSECTION;
                }
                DefaultDomain owner = new DefaultDomain();
                owner.addPlayer(pname);
                reg.setOwners(owner);
                //REGION CREATED
                return CreateResult.REGIONCREATED;
            }
            //REGION MANAGER IS NULL
            return CreateResult.ZONEMANAGERNOTFOUND;
        }
        //INVALID LOCATION DATA
        return CreateResult.INVALIDLOCATION;
    }
    public static boolean delete(String name){
        //TODO CHECK IF WORLDGUARD REGION WITH THE NAME EXISTS
        try{
            World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(mainWorld));
            ProtectedRegion rg = rm.getRegion(name);

            if(rg != null){
                rm.removeRegion(name);
                return true;
            }
            //TODO RG IS NULL
            Main.main.getLogger().log(Level.INFO,"DEBUG - RG IS NULL");
            return false;
        }catch (NoSuchElementException e){
            Main.main.getLogger().log(Level.INFO,"DEBUG - NoSuchElementException");
            return false;
        }
    }
    //SEPERATE ACTIONS
    public static boolean exists(String name){
        //CHECKS IF WORLDGUARD REGION WITH THE NAME EXISTS
        try{
            World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
            ProtectedRegion rg = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(mainWorld)).getRegion(name);

            if(rg != null){
                return true;
            }
            return false;
        }catch (NoSuchElementException e){
            return false;
        }
    }
    public static boolean isIntersecting(RegionManager rm,ProtectedRegion reg){

        List<ProtectedRegion> reglist = new ArrayList<ProtectedRegion>(rm.getRegions().values());
        List<ProtectedRegion> overlappingRegions = reg.getIntersectingRegions(reglist);

        if(overlappingRegions.size() > 1){//INFO THIS IS NOT 0 BECAUSE overlappingRegions CONTAINS reg!
            Main.main.getLogger().log(Level.INFO,"DEBUG - REGIONS FOUND: "+overlappingRegions.size());
            for(ProtectedRegion ovl:overlappingRegions){
                Main.main.getLogger().log(Level.INFO,"REGION="+ovl.getId());
            }
            return true;
        }
        return false;
    }
    public static boolean isOwner(Location loc, Player p){
        //TODO does this exclude __global__?
        RegionContainer cont = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery q = cont.createQuery();
        ApplicableRegionSet regions = q.getApplicableRegions(BukkitAdapter.adapt(loc));
        if(regions.getRegions().size() == 1){
            if(regions.isOwnerOfAll(WorldGuardPlugin.inst().wrapPlayer(p))){
                //TODO REMOVE DEBUGGER
                Main.main.getLogger().log(Level.INFO," "+p.getName()+" IS OWNER OF REGION @ "+loc.toString());
                return true;
            }
        }
        return false;
    }


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
    public static ProtectedRegion getZoneAt(Location loc){
        //CHECKS IF WORLDGUARD REGION WITH THE NAME EXISTS
        try{
            World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
            RegionContainer cont = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = cont.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));

            List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>(set.getRegions());

            Main.main.getLogger().log(Level.INFO,"Getting Zones at X:"+loc.getBlockX()+" Z:"+loc.getBlockZ()+" : ");
            for(ProtectedRegion reg:regions){
                //TODO REMOVE DEBUGGER
                if(reg.getId().startsWith("zone_")){
                    Main.main.getLogger().log(Level.INFO,"- found region: "+reg.getId()+" [ZONE]");
                    return reg;
                }
            }
            return null;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public static boolean hasZoneAt(Location loc){
        //CHECKS IF WORLDGUARD REGION WITH THE NAME EXISTS
        try{
            World mainWorld = Bukkit.getServer().getWorld(_mainWorld);
            RegionContainer cont = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = cont.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));

            List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>(set.getRegions());

            Main.main.getLogger().log(Level.INFO,"Getting Zones at X:"+loc.getBlockX()+" Z:"+loc.getBlockZ()+" : ");
            for(ProtectedRegion reg:regions){
                //TODO REMOVE DEBUGGER
                if(reg.getId().startsWith("zone_")){
                    Main.main.getLogger().log(Level.INFO,"- found region: "+reg.getId()+" [ZONE]");
                    return true;
                }
            }
            return false;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    //SETTERS
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
