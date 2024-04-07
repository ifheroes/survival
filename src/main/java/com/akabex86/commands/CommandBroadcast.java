package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandBroadcast implements CommandExecutor, TabCompleter {
    public CommandBroadcast(Main main) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String message = String.join(" ", args);
            for(Player all:Bukkit.getOnlinePlayers()){
                message = PlaceholderAPI.setPlaceholders(all,message);
                message=Utils.colorFormat(message);
                message=Utils.colorFormatRGB(message);
                all.sendMessage(message);
            }
            return true;
        }
        return false;
    }
    @Override
    public List<String> onTabComplete (CommandSender sender, Command command, String s, String[]strings){
        return new ArrayList<>();
    }
}
