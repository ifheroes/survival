package com.akabex86.objects;

import com.akabex86.utils.Zone;
import com.sk89q.worldedit.math.BlockVector3;
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

    public BlockVector3 getBv1() {
        return BlockVector3.at(loc1.getBlockX(),loc1.getWorld().getMaxHeight(),loc1.getBlockZ());
    }
    public BlockVector3 getBv2() {
        return BlockVector3.at(loc2.getBlockX(),loc2.getWorld().getMinHeight(),loc2.getBlockZ());
    }
    public int getBlocks_2D(){
        int dx = Math.abs(loc1.getBlockX() - loc2.getBlockX())+1;
        int dz = Math.abs(loc1.getBlockZ() - loc2.getBlockZ())+1;
        return dx*dz;
    }
    public int getBlocks_3D(){ //UNTESTED!!!
        int dx = Math.abs(loc1.getBlockX() - loc2.getBlockX())+1;
        int dy = Math.abs(loc1.getBlockY() - loc2.getBlockY())+1;
        int dz = Math.abs(loc1.getBlockZ() - loc2.getBlockZ())+1;
        return dx*dy*dz;
    }
    //Setters
    public void setLoc1(Location loc){
        this.loc1=loc;
    }
    public void setLoc2(Location loc){
        this.loc2=loc;
    }
    public boolean isValid(){
        if(loc1!=null&&loc2!=null){
            return true;
        }
        return false;
    }
    //HELPER METHODS

}
