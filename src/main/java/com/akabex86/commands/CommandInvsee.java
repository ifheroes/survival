package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import com.akabex86.utils.Tpa;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandInvsee implements CommandExecutor {
    public CommandInvsee(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //TODO PERMISSION TO BLOCK INVSEE
        if (sender instanceof Player){
            Player p = (Player)sender;
            if(args.length >= 1){
                if(Bukkit.getPlayer(args[0]) != null){
                    if(Bukkit.getPlayer(args[0]).getName().equals(p.getName())){
                        p.sendMessage("macht halt keinen sinn den eigenen inventar aufzumachen...");
                        return true;
                    }
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if (p2 != null) {
                        p.openInventory(p2.getInventory());
                        return true;
                    }
                    p.sendMessage("Der Spieler ist nicht mehr online...");
                    return true;
                }
                p.sendMessage("Fehler: Spieler ["+args[0].toUpperCase()+"] nicht gefunden.");
                return true;
            }
        }
        return false;
    }
}
