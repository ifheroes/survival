package com.akabex86.listeners;

import com.akabex86.main.Main;
import com.akabex86.utils.HeroProfile;
import com.akabex86.utils.Tpa;
import com.akabex86.utils.UuidFetcher;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.HashMap;
import java.util.logging.Level;

public class Events_Join_Leave {
    public static void onLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        HeroProfile profile = HeroProfile.getProfileByUUID(UuidFetcher.getUUID(p.getName()));
        if(profile == null){
            //TODO DEBUGGER: CHECK IF A NEW PROFILE IS CREATED EVERYTIME OR IS LOADED SUCCESSFULLY.
            Main.main.getLogger().log(Level.INFO,"DEBUG: PROFILE FOR "+p.getName()+" NOT FOUND! CREATING A NEW ONE...");
            profile = HeroProfile.createHeroProfile(p);
        }
        profile.updateName(p.getName());
        UuidFetcher.updateMappings();
    }
    public static void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        HeroProfile profile = HeroProfile.getProfileByUUID(UuidFetcher.getUUID(p.getName()));
        profile.updateJoinTime();
    }
    public static void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        //TODO TEST THIS (works so far)
        Tpa.clearSentRequests(p);

        HeroProfile profile = HeroProfile.getProfileByUUID(UuidFetcher.getUUID(p.getName()));
        if(profile == null){
            Main.main.getLogger().log(Level.INFO,"DEBUG: PROFILE FOR "+p.getName()+" NOT FOUND! CREATING A NEW ONE...");
            profile = HeroProfile.createHeroProfile(p);
        }
        profile.updateLastOnline();
        profile.updatePlaytime();
        Main.main.getLogger().log(Level.INFO,"DEBUG: PLAYER LEFT "+p.getName()+" WAS ONLINE FOR "+profile.getSessionTime());

    }
}