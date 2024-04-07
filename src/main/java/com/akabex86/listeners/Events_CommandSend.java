package com.akabex86.listeners;

import com.akabex86.main.Main;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;

public class Events_CommandSend {
    //zur info: JA, das ist due klasse in der die befehlsliste an den spieler gesendet wird (für tab completion.)
    public static void onCommandSend(PlayerCommandSendEvent e){
        Player p = e.getPlayer();

        e.getCommands().remove(";");

        ArrayList<String> commandlist = new ArrayList<>(e.getCommands());
        if(!p.hasPermission("survival.showcommandlist")){

            //BLOCKS ALL WORLDEDIT DOUBLE SLASH COMMANDS
            //AND ALL COMMANDS CONTAINING A PLUGIN SPECIFIER (e.g. bukkit:help)
            for(String cmd:commandlist){
                if(cmd.contains(":")||cmd.startsWith("/")){
                    e.getCommands().remove(cmd);
                }
            }
            //TODO CONFIG ENTRY FOR THIS TO MAKE IT EXTENDABLE
            //make a removeall based on a list collected from a config file
            e.getCommands().remove("br");
            e.getCommands().remove("brush");
            e.getCommands().remove("co");
            e.getCommands().remove("dmap");
            e.getCommands().remove("dmarker");
            e.getCommands().remove("dynmap");
            e.getCommands().remove("dynmapexp");
            e.getCommands().remove("hd");
            e.getCommands().remove("holo");
            e.getCommands().remove("hologram");
            e.getCommands().remove("holograms");
            e.getCommands().remove("lp");
            e.getCommands().remove("luckperms");
            e.getCommands().remove("none");
            e.getCommands().remove("papi");
            e.getCommands().remove("perm");
            e.getCommands().remove("permission");
            e.getCommands().remove("permissions");
            e.getCommands().remove("perms");
            e.getCommands().remove("placeholderapi");
            e.getCommands().remove("plan");
            e.getCommands().remove("planbungee");
            e.getCommands().remove("planp");
            e.getCommands().remove("planproxy");
            e.getCommands().remove("planvelocity");
            e.getCommands().remove("region");
            e.getCommands().remove("regions");
            e.getCommands().remove("rg");
            e.getCommands().remove("toggleplace");
            e.getCommands().remove("tool");
            e.getCommands().remove("we");
            e.getCommands().remove("worldedit");
            e.getCommands().remove("fastasyncworldedit");
            e.getCommands().remove("fawe");
            e.getCommands().remove("foxaddition");
            e.getCommands().remove("callback");
            e.getCommands().remove("god");
            e.getCommands().remove("ungod");
            e.getCommands().remove("heal");
            e.getCommands().remove("lpv");
            e.getCommands().remove("luckpermsvelocity");
            e.getCommands().remove("slay");
            e.getCommands().remove("themis");
            //TODO ADD NEW FILTER COMMANDS
        }

        Main.main.getLogger().info("[Debug] Downloading Command list for "+p.getName());
        //Main.main.getLogger().info("[Debug] Commands: "+String.join(", ", e.getCommands()));
    }
}