package com.akabex86.listeners;

import com.akabex86.utils.HeroProfile;
import com.akabex86.utils.Tpa;
import com.akabex86.utils.UuidFetcher;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events_Join_Leave {
    //TODO FIX PROFILE ALWAYS BEING NULL FOR SOME REASON
    public static void onLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        HeroProfile profile = HeroProfile.getProfileByUUID(p.getUniqueId().toString());
        if(profile == null){
            //PROFILE IS NULL - CREATING NEW HEROPROFILE
            profile = HeroProfile.createHeroProfile(p);
        }
        profile.updateName(p.getName());
        UuidFetcher.updateMappings();
    }
    public static void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
    }
    public static void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        //TODO TEST THIS (works so far)
        Tpa.clearSentRequests(p);
    }
}