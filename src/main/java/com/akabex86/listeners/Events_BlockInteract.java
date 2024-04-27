package com.akabex86.listeners;

import com.akabex86.utils.Zone;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events_BlockInteract {

    public static void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(Zone.hasTool(p)){
            //Y IS ALWAYS LOWEST ON FIRST POS AND HIGHEST ON SECOND POS
            int blockx = e.getClickedBlock().getLocation().getBlockX();
            int blockz = e.getClickedBlock().getLocation().getBlockZ();
            Location clicked = e.getClickedBlock().getLocation();
            if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                p.sendMessage("FIRST POSITION SET! AT X"+blockx+" Z"+blockz);
                Zone.setPos1(p,clicked);
            }
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                p.sendMessage("SECOND POSITION SET! AT X"+blockx+" Z"+blockz);
            }
            e.setCancelled(true);
        }
    }
    public static void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(Zone.hasTool(p)){
            e.setCancelled(true);
        }
    }
    public static void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(Zone.hasTool(p)){
            e.setCancelled(true);
        }
    }
}
