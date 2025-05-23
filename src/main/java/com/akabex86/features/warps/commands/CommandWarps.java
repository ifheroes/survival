package com.akabex86.features.warps.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.features.warps.WarpManager;

public class CommandWarps implements CommandExecutor, TabCompleter {
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    	if(sender instanceof Player player) {
    		StringBuilder textMessage = new StringBuilder("Warps: ");
    		WarpManager.getWarps().keySet().forEach(warp -> textMessage.append(String.format("%n- %s", warp)));
    		player.sendMessage(textMessage.toString());
    		return true;
    	}
    	return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}