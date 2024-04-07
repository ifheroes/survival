package com.akabex86.utils;

import com.akabex86.main.Main;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class HeroProfile {
    String uuid;
    String name;
    String nick;
    String firstJoined;
    String lastOnline;
    long playtime;

    static List<HeroProfile> profiles = new ArrayList<>();

    HeroProfile(Player p){
        uuid = p.getUniqueId().toString();
        name = p.getName();
        nick = "";
        firstJoined = Instant.now().toString();
        lastOnline = Instant.now().toString() ;//sets the time instant to now instead of a propper value as a placeholder for the first time. for initialization
        playtime = 0;
        Main.main.getLogger().log(Level.INFO,"Created new HeroProfile for "+name+" joined on "+firstJoined);
        Main.main.getLogger().log(Level.INFO,"Last online set to "+lastOnline+" to initialize the value.");
    }
    //HeroProfile Fetcher from config data
    HeroProfile(String p_UUID,String p_NAME,String p_NICK,String FIRST_JOINED, String LAST_ONLINE, long PLAYTIME){
        uuid = p_UUID;
        name = p_NAME;
        nick = p_NICK;
        firstJoined = FIRST_JOINED;
        lastOnline = LAST_ONLINE;
        playtime = PLAYTIME;
        Main.main.getLogger().log(Level.INFO,"Loaded HeroProfile for "+name+" from userdata. Player joined on "+FIRST_JOINED);
    }
    //TODO ONLY CREATE NEW PROFILE IF NOT ALREADY SAVED IN THE CONFIG. NOT ALL VALUES WILL BE SAVED IN SAID CONFIG
    //TODO PLAYTIME CALCULATION IN LEAVE EVENT AND RUNTIME WHILE ASKING (when getting playtime, sessiontime and playtime will be added.)
    public static HeroProfile createHeroProfile(Player p){
        HeroProfile profile = new HeroProfile(p);
        //WRITING PROFILE DATA TO CONFIG
        Config.set("userdata//"+profile.uuid,"name", profile.name);
        Config.set("userdata//"+profile.uuid,"nick", profile.nick);
        Config.set("userdata//"+profile.uuid,"firstJoined", profile.firstJoined);
        Config.set("userdata//"+profile.uuid,"lastOnline", profile.lastOnline);
        Config.set("userdata//"+profile.uuid,"playtime", profile.playtime);
        return profile;
    }
    //PROFILE GETTERS FROM ONLY CACHE
    public static HeroProfile getProfileByUUID(String uuid){

        for(HeroProfile profile : profiles){
            if(profile.getUUID().equals(uuid)){
                return profile;
            }
        }
        //TODO GET FROM FILE IF NOT IN CACHE HERE
        return null;
    }
    public static HeroProfile getProfileByName(String name){
        for(HeroProfile profile : profiles){
            if(profile.getName().equalsIgnoreCase(name)){
                return profile;
            }
        }
        //TODO GET FROM FILE IF NOT IN CACHE HERE
        return null;
    }
    //MASTER CACHING METHOD EXECUTED AT SERVERSTART
    //TODO EXECUTE IN onEnable METHOD (only change to this method when
    public static void initializePlayers(){

    }
    //TODO PROFILE GETTER FROM FILE (USE THIS IF THE CACHE GETS TOO BIG AND CACHING EVERYTHING IS TOO INEFFICIENT.)
    //GETTERS
    public String getUUID(){
        return uuid;
    }
    public String getName(){
        return name;
    }
    public String getFirstJoined(){
        return firstJoined;
    }
    public String getLastOnline(){
        return lastOnline;
    }
    public long getPlaytime(){
        return playtime;
    }

    //SETTERS (Updaters)
    public boolean updateName(String newName){
        String oldName = name;
        if(oldName.equalsIgnoreCase(newName)){
            return false;
        }
        Main.main.getLogger().log(Level.INFO,"new username for "+oldName+" found. It has been updated to "+newName+"! ");
        name = newName;
        Config.set("userdata//"+uuid,"name", newName);
        return true;
    }
    public boolean updateLastOnline(){
        //TODO put stuff here, UPDATE ON LEAVE
        return false;
    }
    public boolean updatePlaytime(){
        //TODO put stuff here, UPDATE ON LEAVE
        return false;
    }
    /*
                FUNCTIONALITY:
                -   CAN BE CALLED OFFLINE AND ONLINE. (OFFLINE DATA WILL NOT BE CACHED BUT DIRECTLY CALLED FROM THE FILESYSTEM)
                -   CONTAINS CURRENT AND IF OFFLINE LAST KNOWN PLAYER INFO.
                -   NOT ALL INFORMATION IS VISIBLE TO PLAYERS VIA COMMANDS, SOME ARE ONLY FOR MODERATION PURPOSES.

                STORED INFO:
                -   X playtime in millis(long)
                -   X last online date&time
                -   X join date&time

                -   X UUID (The Filename)
                -   X playername (CHECK IF IT CHANGED AND UPDATE IF NECCESSARY EVERYTIME A PLAYER JOINS)
                -   nickname

                -   installed mods and mod-loaders
                -   installed resourcepacks

                -   anticheat flags (multiple can be stored but summarized with a counter)
                -   ban status
                -   mute status
                -   moderation notes (multiple can be stored)
                -   open tickets


     */
}
