package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import com.akabex86.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandWarps implements CommandExecutor, TabCompleter {
    public CommandWarps(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;
            p.sendMessage("Warps:");
            for(String warp: Warp.getWarps().keySet()){
                p.sendMessage("- "+warp);
            }
            return true;
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
