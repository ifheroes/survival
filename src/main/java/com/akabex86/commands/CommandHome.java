package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHome implements CommandExecutor, TabCompleter {
    public CommandHome(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            String UUID = p.getUniqueId().toString();
            if(!Home.hasHomes(UUID)){
                p.sendMessage("Fehler: Du hast noch kein Home gesetzt.");
                return true;
            }
            if(args.length == 0){
                if(Home.getHome(p.getUniqueId().toString(),"home")==null){
                    p.sendMessage("Fehler: Home nicht gefunden.");
                    return true;
                }
                p.teleport(Home.getHome(p.getUniqueId().toString(),"home"));
                p.sendMessage("Teleportiere zum Home...");
                return true;
            }
            if(Home.getHome(p.getUniqueId().toString(),args[0])==null){
                p.sendMessage("Fehler: Home ["+args[0].toUpperCase()+"] nicht gefunden.");
                return true;
            }
            p.teleport(Home.getHome(p.getUniqueId().toString().toLowerCase(),args[0]));
            p.sendMessage("Teleportiere zum Home ["+args[0].toUpperCase()+"] ...");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player){

            Player p = (Player)sender;

            TabComplete.addAll(Home.getHomes(p.getUniqueId().toString()));
        }
        return TabComplete;
    }
}
