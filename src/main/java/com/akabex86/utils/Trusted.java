package com.akabex86.utils;

import java.util.List;

public class Trusted {
    public static List<String> getTrusted(String UUID){
        //GETS ALL TRUSTED PLAYERS FROM UUID
        return null;
    }
    public static void addtrusted(String UUID,String UUID_target){

    }
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
}
