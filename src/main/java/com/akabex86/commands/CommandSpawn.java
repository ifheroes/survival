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

public class CommandSpawn implements CommandExecutor, TabCompleter {
    public CommandSpawn(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;
            if(Warp.getWarp("spawn")==null){
                p.sendMessage("Fehler: Spawnpunkt nicht gefunden.");
                return true;
            }
            p.teleport(Warp.getWarp("spawn"));
            p.sendMessage("Teleportiere zum Spawn ...");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
