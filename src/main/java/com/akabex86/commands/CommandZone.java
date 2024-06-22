package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.objects.Cuboid;
import com.akabex86.utils.UuidFetcher;
import com.akabex86.utils.Zone;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Location;
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
            if(!p.getWorld().getName().equalsIgnoreCase(Zone._mainWorld)){
                p.sendMessage("§cDas erstellen von Zonen ist nur in der Hauptwelt erlaubt!");
                return true;
            }
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
                            //TODO ERROR HANDLING - IMPLEMENT ZONE CREDITS
                            //ZONE CREATOR
                            int result = Zone.create(UuidFetcher.getUUID(p.getName()),selection);
                            switch (result) {
                                case 0 ->
                                    //SUCCESS
                                        p.sendMessage("§aZone erstellt!");
                                case 1 -> {
                                    //REGION ALREADY EXISTS
                                    p.sendMessage("");
                                    p.sendMessage("§eWillst du deine bestehende Zone überschreiben?");
                                    TextComponent message = new TextComponent();
                                    TextComponent accept = new TextComponent("Überschreiben");
                                    accept.setColor(ChatColor.GREEN);
                                    accept.setBold(true);
                                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/zone update"));
                                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("/zone update")));
                                    message.addExtra(accept);
                                    p.sendMessage("§8(§a/zone update§8)");
                                    p.spigot().sendMessage(message);
                                }
                                case 2 ->
                                    //REGION MANAGER NOT FOUND
                                        p.sendMessage("§cZonenmanager nicht gefunden!");
                                case 3 ->
                                    //LOCATION DATA NOT FOUND
                                        p.sendMessage("§cFehlerhafte Positionsdaten!");
                                case 4 ->
                                    //SELECTION TOO LARGE
                                        p.sendMessage("§cAuswahl zu groß! (Max.7000)");
                                case 5 ->
                                    //SELECTION TOO SMALL
                                        p.sendMessage("§cAuswahl zu klein! (Min.10x10)");
                                case 6 ->
                                    //ZONE INTERSECTION
                                        p.sendMessage("§cZone überschneidet sich mit einer oder mehreren anderen Zonen.");
                                default ->
                                    //UNKNOWN ERROR
                                        p.sendMessage("§cUnbekannter Fehler!");
                            }
                            return true;
                        }
                    }
                    p.sendMessage("§cDu must erst eine Zone festlegen.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("update")){
                    Cuboid selection = Zone.ZoneCache.get(p.getName());
                    if(selection != null){
                        if(selection.isValid()){
                            //TODO ERROR HANDLING - IMPLEMENT ZONE CREDITS

                            Zone.delete("zone_"+p.getName());
                            int result = Zone.create(UuidFetcher.getUUID(p.getName()),selection);
                            switch (result) {
                                case 0 ->
                                    //SUCCESS
                                        p.sendMessage("§aZonen Update erfolgreich!");
                                case 1 -> {
                                    //REGION ALREADY EXISTS
                                    p.sendMessage("§4FEHLER §c- zone wurde nicht entfernt");
                                }
                                case 2 ->
                                    //REGION MANAGER NOT FOUND
                                        p.sendMessage("§cZonenmanager nicht gefunden!");
                                case 3 ->
                                    //LOCATION DATA NOT FOUND
                                        p.sendMessage("§cFehlerhafte Positionsdaten!");
                                case 4 ->
                                    //SELECTION TOO LARGE
                                        p.sendMessage("§cAuswahl zu groß! (Max.7000)");
                                case 5 ->
                                    //SELECTION TOO SMALL
                                        p.sendMessage("§cAuswahl zu klein! (Min.10x10)");
                                case 6 ->
                                    //ZONE INTERSECTION
                                        p.sendMessage("§cZone überschneidet sich mit einer oder mehreren anderen Zonen.");
                                default ->
                                    //UNKNOWN ERROR
                                        p.sendMessage("§cUnbekannter Fehler!");
                            }
                            return true;
                        }
                    }
                    p.sendMessage("§cDu must erst eine Zone festlegen.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("delete")){
                    //DELETES THE ZONE A PLAYER IS CURRENTLY STANDING ON
                    //TODO ADD SECOND ARG FOR MULTIPLE ZONES
                    if(Zone.delete("zone_"+p.getName())){
                        p.sendMessage("§aZone gelöscht.");
                        return true;
                    }
                    p.sendMessage("§cDie Zone gehört dir nicht oder es konnte keine Zone gefunden werden.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("info")){
                    //TODO SHOW INFO ABOUT THE ZONE A PLAYER IS CURRENTLY STANDING ON
                    Location loc = p.getLocation();
                    if(Zone.hasZoneAt(loc)){
                        ProtectedRegion reg = Zone.getZoneAt(loc);
                        String id = reg.getId();
                        String owner = id.replaceFirst("zone_","");
                        List<BlockVector2> pts = reg.getPoints();

                        p.sendMessage("\n§aZone von "+owner);
                        int num=1;
                        for(BlockVector2 bv:pts){
                            int x=bv.getBlockX();
                            int z=bv.getBlockZ();
                            p.sendMessage("§8- §e"+num+".Eckpunkt: [X:"+x+" Z:"+z+"]");
                            num++;
                        }
                        p.sendMessage("§8- §emehr parameter bald verfügbar.");
                        return true;
                    }
                    p.sendMessage("§cDu befindest dich in keiner Zone.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    //TODO LISTS ALL ZONES OF A PLAYER
                    return true;
                }
                if(args[0].equalsIgnoreCase("help")){
                    p.sendMessage(" ");
                    p.sendMessage("§8- §7/zone tool§r §eWandelt einen Stick zum Zauberstab um.");
                    p.sendMessage("§8- §7/zone claim§r §eClaime deine Zone mit diesem Befehl");
                    p.sendMessage("§8- §7/zone update§r §eUpdate deine Zone mit diesem Befehl");
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
            TabComplete.add("update");
            TabComplete.add("info");
            TabComplete.add("delete");
        }
        return TabComplete;
    }
}
