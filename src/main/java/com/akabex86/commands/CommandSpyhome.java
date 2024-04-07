package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import com.akabex86.utils.UuidFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSpyhome implements CommandExecutor{
    public CommandSpyhome(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            //String UUID = p.getUniqueId().toString();
            if(args.length >= 1){
                if(UuidFetcher.isFetched(args[0].toLowerCase())) {
                    String UUID = UuidFetcher.getUUID(args[0]);
                    String name = UuidFetcher.getName(UUID).toUpperCase();
                    if (!Home.hasHomes(UUID)) {
                        p.sendMessage("Fehler: " + name + " hat noch kein Home gesetzt.");
                        return true;
                    }
                    if (args.length == 1) {
                        if (Home.getHome(UUID, "home") == null) {
                            p.sendMessage("Fehler: Home nicht gefunden.");
                            return true;
                        }
                        p.teleport(Home.getHome(UUID, "home"));
                        p.sendMessage("Teleportiere zum Home von " + name + "...");
                        return true;
                    }
                    if (Home.getHome(UUID, args[1]) == null) {
                        p.sendMessage("Fehler: Home [" + args[1].toUpperCase() + "] nicht gefunden.");
                        return true;
                    }
                    p.teleport(Home.getHome(UUID, args[1]));
                    p.sendMessage("Teleportiere zum Home [" + args[1].toUpperCase() + "] ...");
                    return true;
                }
                p.sendMessage("Fehler: Spieler ["+args[0].toUpperCase()+"] nicht gefunden.");
                return true;
            }
        }
        return false;
    }
}
