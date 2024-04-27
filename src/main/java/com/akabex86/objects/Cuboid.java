package com.akabex86.objects;

import org.bukkit.Location;

public class Cuboid {
    private Location loc1;
    private Location loc2;

    public Cuboid(){
        this.loc1=null;
        this.loc2=null;
    }
    //Getters
    public Location getLoc1(){
        return loc1;
    }
    public Location getLoc2(){
        return loc2;
    }
    //Setters
    public void setLoc1(Location loc){
        this.loc1=loc;
    }
    public void setLoc2(Location loc){
        this.loc2=loc;
    }
}
