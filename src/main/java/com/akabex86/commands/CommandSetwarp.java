package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import com.akabex86.utils.Regex;
import com.akabex86.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSetwarp implements CommandExecutor, TabCompleter {
    public CommandSetwarp(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //TODO home setzen limitieren auf 5 homes max. und location limits setzen.
        if (sender instanceof Player){
            Player p = (Player) sender;

            if(args.length == 0){
                //p.sendMessage("Fehler: Bitte gib einen Namen für deinen Warp an.");
                return false;
            }
            if (Regex.containsIllegalCharacters(args[0])){
                p.sendMessage("Fehler: nur Buchstaben, Unterstriche und Zahlen sind erlaubt!");
                return true;
            }
            if( Warp.setWarp(args[0].toLowerCase(),p.getLocation())){
                //TODO teste ob methode ausgefuehrt werden kann.
                p.sendMessage("Warp ["+args[0].toUpperCase()+"] gesetzt!");
                return true;
            }
            p.sendMessage("Warp ["+args[0].toUpperCase()+"] existiert bereits!");
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
