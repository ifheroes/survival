package com.akabex86.listeners;

import com.akabex86.utils.Regex;
import com.akabex86.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Events_Chat {
    public static void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String UUID = p.getUniqueId().toString();
        String message = e.getMessage();
        if(Regex.isOutOfExtendedASCIIRange(message)){
            TextComponent no = new TextComponent("Das dürfen sie nicht.");
            no.setColor(ChatColor.RED);
            no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text(":(")));
            p.spigot().sendMessage(no);
            //p.sendMessage("§cDas dürfen sie nicht.");
            e.setCancelled(true);
            return;
        }
        if (p.hasPermission("survival.chatcolor")) message = Utils.colorFormat(message);
        if (p.hasPermission("survival.chatcolor.rgb")) message = Utils.colorFormatRGB(message);
        if (p.hasPermission("survival.chatcolor.gradient")) message = Utils.colorGradientRGB(message);
        if (p.hasPermission("survival.chat.placeholders")) message = PlaceholderAPI.setPlaceholders(p,message);

        String prefix = Utils.colorFormatRGB(Utils.colorFormat(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%%player_name%%luckperms_suffix%")));

        TextComponent formatedMessage = new TextComponent(prefix);
        TextComponent msg = new TextComponent(message);
        if (p.hasPermission("survival.chat.url"))msg = Utils.urlFormat(message);
        formatedMessage.addExtra(msg);
        Bukkit.spigot().broadcast(formatedMessage);
        //Main.main.getLogger().log(Level.INFO,"[CHAT]"+p.getName()+": "+message);
        Bukkit.getServer().getConsoleSender().sendMessage("§e[Chat] §r"+p.getName()+": "+message);
        e.setCancelled(true);
    }
}