package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.objects.Cuboid;
import com.akabex86.utils.UuidFetcher;
import com.akabex86.utils.Zone;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandZone implements CommandExecutor, TabCompleter {
    public CommandZone(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        //TODO COMPLETE REFACTOR -
        // remove edit mode and switch to a simpler, item based selection system with less commands
        // add zone credits instead of the predefined 100 blocks squared
        // right click and left click with region tool allows for the following actions:
        // - leftclick or rightclick within wg region, do nothing, show zone info and borders of clicked zone in red
        // - leftclick or rightclick outside region (seperate actions (left==pos1 right==pos2) (alternatively /zone <pos1,pos2> ) region selector
        // /zone create|claim after you've made your selection creates a new zone within the selected area (id will be generated starting from playername_1 counting up)
        // /zone edit

        //TODO
        // misc features (flags, customname(muss unique sein), trusted members, warp(statt playerwarps)


        if (sender instanceof Player){
            Player p = (Player)sender;
            int minsize = 50;
            if(args.length == 1){

                if(     args[0].equalsIgnoreCase("wand")||
                        args[0].equalsIgnoreCase("tool")||
                        args[0].equalsIgnoreCase("claimtool")||
                        args[0].equalsIgnoreCase("zauberstab"))
                {
                    if(p.getInventory().contains(Material.STICK)){
                        if(p.getInventory().getItemInMainHand().getType() == Material.STICK){
                            Zone.createWand(p.getInventory().getItemInMainHand());
                            p.sendMessage(" ");
                            p.sendMessage("§eDein Stick wurde zum Zauberstab!");
                            p.sendMessage("§eSetze position 1 mit Linksklick und position 2 mit einen Rechtsklick");
                            p.sendMessage("§eBestaetige deine Auswahl mit '/zone claim' wenn du fertig bist.");
                            p.sendMessage("§eMit '/zone help' siehst du alle wichtigen Befehle für das Zonensystem.");
                            return true;
                        }
                    }
                    p.sendMessage("§cBitte lege einen Stick in die Hand und versuche es erneut.");
                    return true;
                }
                if(     args[0].equalsIgnoreCase("claim")||
                        args[0].equalsIgnoreCase("erstellen"))
                {
                    Cuboid selection = Zone.ZoneCache.get(p.getName());
                    if(selection != null){
                        if(selection.isValid()){
                            //p.sendMessage("§aRegion erstellt! (nicht wirklich aber mal so als platzhalter)");
                            int result = Zone.create(UuidFetcher.getUUID(p.getName()),selection);
                            switch(result){
                                case 0:
                                    //SUCCESS
                                    p.sendMessage("§4DEBUG §aRegion erstellt!");
                                    break;
                                case 1:
                                    //REGION ALREADY EXISTS
                                    p.sendMessage("§4DEBUG §cRegion existiert bereits!");
                                    break;
                                case 2:
                                    //REGION MANAGER NOT FOUND
                                    break;
                                case 3:
                                    //LOCATION DATA NOT FOUND
                                    break;
                                default:
                                    //UNKNOWN ERROR
                                    break;
                            }

                            return true;
                        }
                    }
                    p.sendMessage("§cDu must erst eine Zone festlegen.");
                    //COMMAND FOR CREATION AFTER 2 POINTS HAVE BEEN SET
                    //TODO ERROR HANDLING LIKE INTERSECTIONS, TOO SMALL, NOT ENOUGH CREDITS, TOO LARGE
                    return true;
                }
                if(args[0].equalsIgnoreCase("info")){
                    //TODO zeigt informationen ueber eine zone an auf der
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    //TODO listet alle zonen eines spielers auf
                    return true;
                }
                if(args[0].equalsIgnoreCase("trust")){
                    //TODO remove and add general /trust command
                    return true;
                }
                if(args[0].equalsIgnoreCase("help")){
                    p.sendMessage(" ");
                    p.sendMessage("§8- §7/zone tool§r §eWandelt einen Stick zum Zauberstab um.");
                    p.sendMessage("§8- §7/zone claim§r §eClaime deine Zone mit diesem Befehl");
                    p.sendMessage("§8- §7/zone delete§r §eEntfernt die Zone auf der du dich befindest");
                    p.sendMessage("§8- §7/zone info§r §eZeigt Zoneninformationen an");
                    p.sendMessage(" ");
                    p.sendMessage("§8- §7/zone help§r §eZeige diese Seite");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if (sender instanceof Player){
            Player p = (Player)sender;
            //ARGUMENT 1
            //TODO UPDATE TAB COMPLETE
            TabComplete.add("tool");
            TabComplete.add("claim");
            TabComplete.add("info");
            TabComplete.add("delete");
        }
        return TabComplete;
    }
}
