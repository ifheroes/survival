package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.WorldHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWorld implements CommandExecutor {

    public CommandWorld(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player)sender;
        if(args.length == 1){
            //commands: /world info /world list
            if(args[0].equalsIgnoreCase("info")){
                //Shows info of current world
                World w = p.getWorld();
                p.sendMessage("Welten infos:\n" +
                        "NAME: "+w.getName()+"\n" +
                        "SEED: "+w.getSeed()+"\n" +
                        "SPAWN: "+w.getSpawnLocation()+"\n" +
                        "ENVIRONMENT: "+w.getEnvironment()+"\n" +
                        "WORLDTYPE(DEPRECATED): "+w.getWorldType()+"\n" +
                        "");
                return true;
            }
            if(args[0].equalsIgnoreCase("list")){
                StringBuilder worldlist = new StringBuilder();
                for(World w: Bukkit.getWorlds()){
                    worldlist.append("\n"+w.getName());
                }
                p.sendMessage("§eWelten§7:§8"+worldlist.toString());
                return true;
            }
            if(args[0].equalsIgnoreCase("test")){
                //Creates a testworld with the seed "0"
                WorldHandler.createWorld("testworld", World.Environment.NORMAL, WorldType.FLAT, (long)0);
            }
            //returning true returns the usage.
            return true;
        }
        return false;
    }
}
