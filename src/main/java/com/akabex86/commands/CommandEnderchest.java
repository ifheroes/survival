package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.UuidFetcher;
import com.akabex86.utils.Warp;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
