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

public class CommandDelhome implements CommandExecutor, TabCompleter {
    public CommandDelhome(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;

            if(args.length == 0)return false;
            //TODO USE LOCAL UUID FETCHER
            if(Home.getHome(p.getUniqueId().toString(),args[0].toLowerCase())!=null){
                Home.delHome(p.getUniqueId().toString(),args[0].toLowerCase());
                p.sendMessage("Home ["+args[0].toUpperCase()+"] erfolgreich entfernt.");
                return true;
            }
            p.sendMessage("Fehler: Home ["+args[0].toUpperCase()+"] nicht gefunden.");
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
