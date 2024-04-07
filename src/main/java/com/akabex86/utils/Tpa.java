package com.akabex86.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Tpa {
    //TODO UPGRADE TO UUID BASED SYSTEM
    //PLAYERS CAN ONLY SEND ONE REQUEST AT A TIME BUT RECEIVE MULTIPLE

    private static HashMap<Player,Player> requests = new HashMap<>();
    public static void sendRequest(Player from, Player to){
        //overwrites old request
        if(requests.containsKey(from)){
            requests.remove(from);
        }
        requests.put(from, to);
    }
    public static Player getReceiver(Player sender){
        return requests.get(sender);
    }
    public static Player getRequestor(Player receiver){
        for(Player key:requests.keySet()){
            if(requests.get(key).getName().equalsIgnoreCase(receiver.getName())){
                return key;
            }
        }
        return null;
    }
    public static Boolean hasRequested(Player sender){
        if(requests.containsKey(sender)){
            return true;
        }
        return false;
    }
    public static Boolean hasRequest(Player receiver){
        for(Player key:requests.keySet()){
            if(requests.get(key).getName().equalsIgnoreCase(receiver.getName())){
                return true;
            }
        }
        return false;
    }
    public static void clearSentRequests(Player player){
        if(requests.containsKey(player)){
            requests.remove(player);
        }
    }
    public static void clearReceivedRequests(Player player){
        for(Player key:requests.keySet()){
            if(requests.get(key).getName().equalsIgnoreCase(player.getName()))requests.remove(key);
        }
    }
    public static void clearAllRequests(Player player){
        if(requests.containsKey(player)){
            requests.remove(player);
        }
        for(Player key:requests.keySet()){
            if(requests.get(key).getName().equalsIgnoreCase(player.getName()))requests.remove(key);
        }
    }
}
