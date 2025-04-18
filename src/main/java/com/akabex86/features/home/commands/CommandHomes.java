package com.akabex86.features.home.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.home.HomeManager;
import com.akabex86.utils.UuidFetcher;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CommandHomes implements CommandExecutor, TabCompleter {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //TODO /homes <spieler> fuer mods
        if (sender instanceof Player){
            Player p = (Player)sender;
            if(args.length>=1&&p.hasPermission("survival.homes.mod")){
                if(UuidFetcher.isFetched(args[0].toLowerCase())){
                    String UUID = UuidFetcher.getUUID(args[0]);
                    String name = UuidFetcher.getName(UUID).toUpperCase();
                    if(!HomeManager.hasHomes(UUID)){
                        p.sendMessage("Fehler: "+name+" hat noch kein Home gesetzt.");
                        return true;
                    }
                    p.sendMessage("Homes von "+name+":");
                    for(String home: HomeManager.getHomes(UUID)){

                        TextComponent message = new TextComponent();

                        TextComponent dash = new TextComponent("- ");

                        TextComponent _home = new TextComponent(home);
                        _home.setColor(ChatColor.YELLOW);
                        _home.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spyhome "+name+" "+home));
                        _home.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("Teleport zum home '"+home+"'")));

                        message.addExtra(dash);
                        message.addExtra(_home);

                        p.spigot().sendMessage(message);
                    }
                    return true;
                }
                p.sendMessage("Fehler: Spieler ["+args[0].toUpperCase()+"] nicht gefunden.");
                return true;
            }
            String UUID = p.getUniqueId().toString();
            if(!HomeManager.hasHomes(UUID)){
                p.sendMessage("Fehler: Du hast noch kein Home gesetzt.");
                return true;
            }
            p.sendMessage("Homes:");
            for(String home: HomeManager.getHomes(UUID)){

                TextComponent message = new TextComponent();

                TextComponent dash = new TextComponent("- ");

                TextComponent _home = new TextComponent(home);
                _home.setColor(ChatColor.YELLOW);
                _home.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home "+home));
                _home.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("Teleport zum home '"+home+"'")));

                message.addExtra(dash);
                message.addExtra(_home);

                p.spigot().sendMessage(message);
                //p.sendMessage("- "+home);
            }
            return true;
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>(); //JUST RETURNS EMPTY LIST

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("survival.homes.mod")){
                for(Player online:Bukkit.getOnlinePlayers()){
                    TabComplete.add(online.getName());
                }
            }
        }
        //todo only block tab complete for non admins/mods. mods can access others homes by using /homes <user>
        return TabComplete;
    }
}
