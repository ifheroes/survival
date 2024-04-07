package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Warp;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandBook implements CommandExecutor, TabCompleter {
    public CommandBook(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //COMMAND TUT EH NICHT WSS ER SOLL. UMBAUEN ZUM BUECHER KIT SYSTEM (BUECHER IN FILES SPEICHERBAR MACHEN)
        if (sender instanceof Player){
            Player p = (Player)sender;

            if(args.length == 0||args.length ==1)return false;
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            if(!itemInMainHand.getType().isAir()&&itemInMainHand.getType() == Material.BOOK){
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
                        String[] _ench = e.toString().split(", ");
                        enchantment = _ench[1].replaceFirst("]","");
                        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);

                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)enchantedBook.getItemMeta();
                        meta.addStoredEnchant(e,Integer.parseInt(args[1]),false);
                        enchantedBook.setItemMeta(meta);
                        //itemInMainHand.addUnsafeEnchantment(e,Integer.parseInt(args[1]));
                        p.getInventory().setItemInMainHand(enchantedBook);
                        p.sendMessage("Du hast ["+enchantment+" lvl."+args[1]+"] dem Item in deiner Haupthand ["+itemInMainHand.getType()+"] gegeben.");
                        return true;
                    }
                }
                p.sendMessage("Fehler: Unbekannte Verzauberung ["+args[0].toUpperCase()+"]");
                return true;
            }
            p.sendMessage("Fehler: Du hast kein Buch in deiner Hand.");
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
