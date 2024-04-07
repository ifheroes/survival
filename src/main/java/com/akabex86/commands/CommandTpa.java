package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Home;
import com.akabex86.utils.Tpa;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTpa implements CommandExecutor {
    public CommandTpa(Main main){

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (sender instanceof Player){
            //TODO TPTOGGLE
            Player p = (Player)sender;
            if(args.length >= 1){
                if(Bukkit.getPlayer(args[0]) != null){
                    if(Bukkit.getPlayer(args[0]).getName().equals(p.getName())){
                        p.sendMessage("jaa genaaau schick dir nur selbst eine Anfrage du dödl du...");
                        return true;
                    }
                    Player p2 = Bukkit.getPlayer(args[0]);
                    Tpa.sendRequest(p,p2);
                    p.sendMessage("Du hast dem  Spieler "+p2.getName()+" eine TPA anfrage gesendet.");
                    p2.sendMessage("Der Spieler "+p.getName()+" hat dir eine Teleportanfrage gesendet.");

                    TextComponent message = new TextComponent();

                    TextComponent accept = new TextComponent("Annehmen");
                    accept.setColor(ChatColor.GREEN);
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("hover_accept")));


                    TextComponent or = new TextComponent(" oder ");

                    TextComponent deny = new TextComponent("Ablehnen");
                    deny.setColor(ChatColor.RED);
                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
                    deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("hover_deny")));

                    message.addExtra(accept);
                    message.addExtra(or);
                    message.addExtra(deny);

                    p2.spigot().sendMessage(message);

                    return true;
                }
                p.sendMessage("Fehler: Spieler ["+args[0].toUpperCase()+"] nicht gefunden.");
                return true;
            }
        }
        return false;
    }
}
