package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Tpa;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTpdeny implements CommandExecutor, TabCompleter {
    public CommandTpdeny(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;
            //IF PLAYER HAS A PENDING REQUEST
            if(Tpa.hasRequest(p)){
                Player requestor = Tpa.getRequestor(p);
                p.sendMessage("Teleportanfrage abgelehnt.");
                requestor.sendMessage(p.getName()+" mag dich nicht.");
                //TODO CLEAN THIS
                Tpa.clearSentRequests(requestor);
                return true;
            }
            p.sendMessage("Du hast keine Teleport Anfragen.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender,Command command,String s,String[] strings) {
        return new ArrayList<>();
    }
}
