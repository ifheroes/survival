package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import com.akabex86.utils.Regex;
import com.akabex86.utils.UuidFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSethome implements CommandExecutor, TabCompleter {
    public CommandSethome(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //TODO home setzen limitieren auf 5 homes max. und location limits setzen.
        if (sender instanceof Player){
            Player p = (Player) sender;
            String UUID = p.getUniqueId().toString();
            int maxHomes = 10; //TODO MAKE MAXHOMES MODIFIABLE IN HEROPROFILE

            if(args.length == 0){
                //just the /sethome command
                //Home.setHome(p.getUniqueId().toString(),"home",p.getLocation());
                //p.sendMessage("Home gesetzt!");
                setHome(p,"home",maxHomes);
                return true;
            }
            if (Regex.containsIllegalCharacters(args[0])){
                p.sendMessage("Fehler: nur Buchstaben, Unterstriche und Zahlen sind erlaubt!");
                return true;
            }
            setHome(p,args[0],maxHomes);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
    private void setHome(Player p,String home,int maxHomes){
        String uuid = UuidFetcher.getUUID(p.getName());
        if(Home.hasHomes(uuid)){
            if (Home.getHomes(uuid).size() >= maxHomes) {
                if (Home.getHome(p.getUniqueId().toString(), home.toLowerCase()) != null) {
                    Home.setHome(p.getUniqueId().toString(), home.toLowerCase(), p.getLocation());
                    p.sendMessage("Home [" + home.toUpperCase() + "] gesetzt!");
                } else {
                    p.sendMessage("Du kannst nicht mehr als " + maxHomes + " homes erstellen.");
                }
                return;
            }
        }
        Home.setHome(p.getUniqueId().toString(), home.toLowerCase(), p.getLocation());
        p.sendMessage("Home [" + home.toUpperCase() + "] gesetzt!");
    }
}
