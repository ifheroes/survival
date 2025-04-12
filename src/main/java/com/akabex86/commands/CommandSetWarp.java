package com.akabex86.commands;

import com.akabex86.utils.Regex;
import com.akabex86.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandSetWarp implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
        //TODO home setzen limitieren auf 5 homes max. und location limits setzen.
        if (sender instanceof Player player){
            if(args.length == 0){
                player.sendMessage("Fehler: Bitte gib einen Namen für deinen Warp an.");
                return false;
            }

            if (Regex.containsIllegalCharacters(args[0])){
                player.sendMessage("Fehler: nur Buchstaben, Unterstriche und Zahlen sind erlaubt!");
                return true;
            }

            if( Warp.SetWarp(args[0].toLowerCase(),player.getLocation())){
                //TODO teste ob methode ausgefuehrt werden kann.
                player.sendMessage("Warp ["+args[0].toUpperCase()+"] gesetzt!");
                return true;
            }

            player.sendMessage("Warp ["+args[0].toUpperCase()+"] existiert bereits!");

            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
        return new ArrayList<>();
    }
}
