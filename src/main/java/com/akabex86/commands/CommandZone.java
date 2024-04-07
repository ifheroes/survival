package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.EditMode;
import com.akabex86.utils.Warp;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandZone implements CommandExecutor, TabCompleter {
    public CommandZone(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //TODO COMPLETE REFACTOR -
        // REMOVE EDIT MODE AND ADD
        //TODO add zone credits instead of the predefined 100 blocks squared
        if (sender instanceof Player){
            Player p = (Player)sender;
            int minsize = 50;
            if(args.length == 1){

                if(     args[0].equalsIgnoreCase("create")||
                        args[0].equalsIgnoreCase("claim")||
                        args[0].equalsIgnoreCase("erstellen")||
                        args[0].equalsIgnoreCase("bearbeiten")||
                        args[0].equalsIgnoreCase("edit"))
                {
                    //enables the edit mode if a stick has been found in the inventory
                    if(p.getInventory().contains(Material.STICK)){
                        //todo check if player is already in edit mode
                        p.sendMessage(" ");
                        p.sendMessage("§eDu hast den Zonen Bearbeitungsmodus betreten! schreibe 'exit' in den chat um diesen zu Verlassen.");
                        p.sendMessage("§eSetze position 1 mit Linksklick und position 2 mit einen Rechtsklick");
                        p.sendMessage("§eBestaetige deine Auswahl mit 'claim' wenn du fertig bist.");
                        p.sendMessage("§eMit 'help' siehst du alle wichtigen befehle des Bearbeitungsmodus.");
                        EditMode.addEditor(p.getName());//ADDS PLAYERS TO EDITOR
                        return true;
                    }
                    p.sendMessage("Bitte lege einen Stick in die Hand und versuche es erneut.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("trust")){
                    return true;
                }
                if(args[0].equalsIgnoreCase("help")){
                    //TODO ZEIGE ZONEN HILFE AN
                    return true;
                }
                return false;
            }
            //CREATE HELP DELETE (wird spaeter geadded:MODIFY)
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player){
            Player p = (Player)sender;
            //ARGUMENT 1
            TabComplete.add("create");
            TabComplete.add("edit");
            TabComplete.add("delete");
            TabComplete.add("trust");
        }
        return TabComplete;
    }
}
