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
            if(!p.getWorld().getName().equalsIgnoreCase(Zone._mainWorld)){
                return; //TODO check: does this work?
            }

            if(e.getAction() == Action.LEFT_CLICK_BLOCK){

                Location clicked = e.getClickedBlock().getLocation();

                int blockx = clicked.getBlockX();
                int blockz = clicked.getBlockZ();

                if(Zone.setPos1(p,clicked)){
                    if(Zone.hasPos2(p)){
                        //TODO BEI REGIONSUEBERSCHNEIDUNGEN FEHLER MELDEN & LOCAL VISUALIZER DER LAEUFT WENN MAN DEN ZAUBERSTAB IN DER HAND HAT
                        // - MINDEST GROESSE 10x10 BLOECKE!!!

                        int blocks = Zone.ZoneCache.get(p.getName()).getBlocks_2D();

                        if(blocks <= 7000){
                            p.sendMessage("§eErste Position gesetzt! X:§7"+blockx+"§e Z:§7"+blockz+"§e(§a"+blocks+"§8/§77000§e)");
                        }else{
                            p.sendMessage("§eErste Position gesetzt! X:§7"+blockx+"§e Z:§7"+blockz+"§e(§c"+blocks+"§8/§77000§e)");
                        }
                    }else{
                        p.sendMessage("§eErste Position gesetzt! X:§7"+blockx+"§e Z:§7"+blockz);
                    }
                }
            }
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK){

                Location clicked = e.getClickedBlock().getLocation();

                int blockx = clicked.getBlockX();
                int blockz = clicked.getBlockZ();

                if(Zone.setPos2(p,clicked)){
                    if(Zone.hasPos1(p)){
                        int blocks = Zone.ZoneCache.get(p.getName()).getBlocks_2D();

                        if(blocks <= 7000){
                            p.sendMessage("§eZweite Position gesetzt! X:§7"+blockx+"§e Z:§7"+blockz+"§e(§a"+blocks+"§8/§77000§e)");
                        }else{
                            p.sendMessage("§eZweite Position gesetzt! X:§7"+blockx+"§e Z:§7"+blockz+"§e(§c"+blocks+"§8/§77000§e)");
                        }
                    }else{
                        p.sendMessage("§eZweite Position gesetzt! X:§7"+blockx+"§e Z:§7"+blockz);
                    }
                }
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
