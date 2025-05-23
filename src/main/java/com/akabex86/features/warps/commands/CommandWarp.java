package com.akabex86.features.warps.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.warps.WarpManager;

public class CommandWarp implements CommandExecutor, TabCompleter {
	
	private WarpManager warpManager;
	
    public CommandWarp(){
    	this.warpManager = WarpManager.getInstance();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 0){
                p.sendMessage("Fehler: Bitte gib einen Namen für deinen Warp an.");
                return true;
            }
            if(warpManager.getWarp(args[0].toLowerCase())==null){
                p.sendMessage("Fehler: Warp ["+args[0]+"] nicht gefunden.");
                return true;
            }
            p.teleport(warpManager.getWarp(args[0].toLowerCase()));
            p.sendMessage("Teleportiere zum Warp ["+args[0]+"] ...");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player p){
            //TODO: Add logic to this
            TabComplete.addAll(warpManager.getWarps().keySet());
        }
        return TabComplete;
    }
}