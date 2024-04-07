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

public class CommandDelwarp implements CommandExecutor, TabCompleter {
    public CommandDelwarp(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;

            if(args.length == 0)return false;

            if(Warp.getWarp(args[0].toLowerCase())!=null){
                Warp.delWarp(args[0].toLowerCase());
                p.sendMessage("Warp ["+args[0]+"] erfolgreich entfernt.");
                return true;
            }
            p.sendMessage("Fehler: Warp ["+args[0]+"] nicht gefunden.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player){

            Player p = (Player)sender;

            TabComplete.addAll(Warp.getWarps().keySet());
        }
        return TabComplete;
    }
}
