package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTicket implements CommandExecutor, TabCompleter {
    public CommandTicket(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("create")){
                    //message with buttons for further creation
                    return true;
                }
                if(args[0].equalsIgnoreCase("delete")){
                    //message with buttons for further creation
                    return true;
                }
                if(args[0].equalsIgnoreCase("status")){
                    //message with buttons for further creation
                    return true;
                }
                if(args[0].equalsIgnoreCase("close")){
                    //TODO ONLY FOR TEAM MEMBERS!!!
                    //message with buttons for further creation
                    return true;
                }
            }
            if(args.length >= 2){
                if(args[0].equalsIgnoreCase("create")){
                    //message with buttons for further creation
                    return true;
                }
                if(args[0].equalsIgnoreCase("delete")){
                    //message with buttons for further creation
                    return true;
                }
                if(args[0].equalsIgnoreCase("status")){
                    //message with buttons for further creation
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player){

            Player p = (Player)sender;
            if(args.length == 0){
                TabComplete.add("erstellen");//startet den ticket creator (step by step anleitung)
                TabComplete.add("entfernen");
                //(als spieler) entfernt das eigene ticket.
                //(als mod) erlaubt fuer extra argument um tickets von spielernm zu entfernen
                //braucht eine bestaetigung mit '/ticket entfernen <ticket> confirm' (kein autocomplete fuer den confirm befehl)

                TabComplete.add("status");//zeigt den status des erstellten tickets an. (spieler koennen nur ein ticket laufend haben.)
                if(p.hasPermission("survival.tickets.mod")){
                    TabComplete.add("close");//ticket als geloest markieren.
                    TabComplete.add("list");//listet alle offenen tickets auf
                }
                return TabComplete;
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("erstellen")){
                    TabComplete.add("Chunksystem");//fuer das chunk/zone system
                    TabComplete.add("PLayerreport");
                    TabComplete.add("Bugreport");
                    TabComplete.add("Sonstige");
                    //TODO erlaube das erstellen neuer kategorien in der config file
                }
                if(args[0].equalsIgnoreCase("entfernen")){
                    if(p.hasPermission("survival.tickets.mod")){
                        //TODO THIS NEEDS A LIST OF ALL EXISTING TICKETS(for mods)
                        TabComplete.add("exampleTicket1<entfernen>");
                        TabComplete.add("exampleTicket2<entfernen>");
                        TabComplete.add("exampleTicket3<entfernen>");
                    }
                }
                return TabComplete;
            }
        }
        return TabComplete;
    }
}
