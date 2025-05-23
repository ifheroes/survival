package com.akabex86.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.main.Main;
import com.akabex86.utils.UuidFetcher;

public class CommandEnderchest implements CommandExecutor, TabCompleter {
    public CommandEnderchest(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;

            if(args.length == 0){
                p.openInventory(p.getEnderChest());
                return true;
            }
            if(p.hasPermission("survival.enderchest.mod")){
                for(Player p2:Bukkit.getOnlinePlayers()){
                    if(args[0].equalsIgnoreCase(p2.getName())){
                        p.openInventory(p2.getEnderChest());
                        return true;
                    }
                }

                if(UuidFetcher.getUUID(args[0].toLowerCase())!=null){
                    OfflinePlayer p2 = Bukkit.getOfflinePlayer(UUID.fromString(UuidFetcher.getUUID(args[0])));

                    p.sendMessage("Der Spieler ist Offline.");
                    return true;
                }
                //TODO OPEN OTHERS ENDERCHESTs + Extended enderchests
                p.sendMessage("Spieler nicht gefunden.");
                return true;
            }
            p.openInventory(p.getEnderChest());
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("survival.enderchest.mod")){
                for(Player online:Bukkit.getOnlinePlayers()){
                    TabComplete.add(online.getName());
                }
            }
        }
        return TabComplete;
    }
}
