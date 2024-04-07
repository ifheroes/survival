package com.akabex86.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import java.util.regex.Pattern;

public class Events_Sign {

    //TODO command sign system fuer spielerfreischaltung und andere serverseiteige features.

    //farbige schilder fuer farbige schilder halt...
    //TODO add graadient support
    public static void onSign(SignChangeEvent e){
        Player p = e.getPlayer();
        String regexPattern = "&([0-9a-fk-or])"; //Detects characters from a-f, k-o and numbers from 0-9
        for(int i=0;i<4;i++){
            if(e.getLine(i).contains("&")){
                String line = e.getLine(i);
                line = line.replaceAll(regexPattern,"§$1");
                e.setLine(i,line);
            }
        }
    }
}