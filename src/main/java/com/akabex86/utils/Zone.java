package com.akabex86.utils;

import java.util.List;

public class Zone {
    //THE ZONE SYSTEM IS TESTED SEPERATELY.
    //GENERAL STATEMENTS
    public static boolean isWithinZone(String UUID){
        //TODO BUILD ZONE DETECTOR
        return false;
    }
    public static List<String> getTrusted(){

        return null;
    }
    //EDITOR ACTIONS
    public static void create(String UUID){
        //TODO CHECK FOR INTERSECTIONS
        //TODO ADD FORMULA FOR ZONE CREDITS AND LIMIT ZONE AMOUNT AND SIZE TO SAID CREDITS. (1 Credit = 10 blocks^2(squared))
    }
    public static void update(String UUID){
        //TODO CHECK FOR INTERSECTIONS (IGNORE THE ZONE THAT IS BEING EDITED)
        //TODO ADD FORMULA FOR ZONE CREDITS AND LIMIT ZONE AMOUNT AND SIZE TO SAID CREDITS. (1 Credit = 10 blocks^2(squared))
    }
    public static boolean delete(String UUID){
        //TODO CHECK FOR INTERSECTIONS (IGNORE THE ZONE THAT IS BEING EDITED)
        return false;
    }public static boolean select(String UUID){
        //TODO SELECTS AN EXISTING ZONE (needed for the update and delete command)
        //ONLY POSSIBLE IF USER IS OWNER OF THE ZONE
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
}
