package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Tpa;
import com.comphenix.protocol.utility.MinecraftReflection;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandPlayerinfo implements CommandExecutor{
    public CommandPlayerinfo(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;
            if(args.length >= 1){
                if(Bukkit.getPlayer(args[0]) != null){
                    Player target = Bukkit.getPlayer(args[0]);

                    p.sendMessage("§l---Spielerinfo["+target.getName()+"]---");
                    p.sendMessage("UUID: §e"+target.getUniqueId().toString());
                    p.sendMessage("Ping: §e"+target.getPing());
                    p.sendMessage("Client: §e"+PlaceholderAPI.setPlaceholders(target,"%clientdetector_client_name% ver.%clientdetector_client_version%"));
                    if(PlaceholderAPI.setPlaceholders(target,"%clientdetector_bedrock_player%").equalsIgnoreCase("true")){
                        p.sendMessage("Bedrock Client: §cJA");
                    }else{
                        p.sendMessage("Bedrock Client: §7NEIN");
                    }

                    if(PlaceholderAPI.setPlaceholders(target,"%clientdetector_client_name%").equalsIgnoreCase("Forge")){
                        p.sendMessage("Forge Mod Loader: §cJA");
                        p.sendMessage("Erkannte Forge Mods: §e"+PlaceholderAPI.setPlaceholders(target,"%clientdetector_forge_list%"));
                    }else{
                        p.sendMessage("Forge Mod Loader: §7NEIN");
                    }
                    if(PlaceholderAPI.setPlaceholders(target,"%clientdetector_client_name%").equalsIgnoreCase("Labymod")){
                        p.sendMessage("Labymod: §cJA");
                        p.sendMessage("Erkannte Labymod Addons: §e"+PlaceholderAPI.setPlaceholders(target,"%clientdetector_labymod_addons%"));
                    }else{
                        p.sendMessage("Labymod: §7NEIN");
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
