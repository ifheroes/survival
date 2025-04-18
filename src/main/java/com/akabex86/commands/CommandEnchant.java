package com.akabex86.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.akabex86.main.Main;

public class CommandEnchant implements CommandExecutor, TabCompleter {
    public CommandEnchant(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;

            if(args.length == 0||args.length ==1)return false;
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            if(!itemInMainHand.getType().isAir()){
                try{
                    int number = Integer.parseInt(args[1]);
                    if(number <= 0){
                        p.sendMessage("Fehler: Zahl ist kleiner oder gleich 0.");
                        return true;
                    }
                } catch (NumberFormatException e){
                    p.sendMessage("Fehler: "+args[1]+" ist keine Zahl.");
                    return true;
                }
                String enchantment = "";

                List<Enchantment> enchants = Arrays.stream(Enchantment.values()).toList();
                for(Enchantment e:enchants){
                    if(e.toString().toLowerCase().contains(args[0].toLowerCase())){
                        itemInMainHand.addUnsafeEnchantment(e,Integer.parseInt(args[1]));
                        p.sendMessage("Verzauberung ["+enchantment.toUpperCase()+" lvl."+args[1]+"] wurde dem Item in deiner Haupthand ["+itemInMainHand.getType()+"] gegeben.");
                        return true;
                    }
                }
                p.sendMessage("Fehler: Unbekannte Verzauberung ["+args[0].toUpperCase()+"]");
                return true;
            }
            p.sendMessage("Fehler: Du hast kein Item in deiner Hand.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player){

            Player p = (Player)sender;
            //TODO FIX THIS
            String pattern = ".*minecraft:([^,]+),.*";
            Pattern regex = Pattern.compile(pattern);
            for(String enchantment:Arrays.stream(Enchantment.values()).map(Enchantment::toString).toList()){
                Matcher matcher = regex.matcher(enchantment);
                if(matcher.matches())TabComplete.add(matcher.group(1));
            }
        }
        return TabComplete;
    }
}
