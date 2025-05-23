package com.akabex86.utils;

import com.akabex86.main.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //TODO THIS IS UNSTABLE
    public static void sendAdvancement(Player receiver, String message){
        PacketContainer advancement = new PacketContainer(PacketType.Play.Server.ADVANCEMENTS);
        advancement.getStrings().write(0, message);
        try{
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver,advancement);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String dateFormat(Instant inst){
        //TODO Make it work with CEST
        //ZoneId zoneId = ZoneId.of("UTC+2");
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return formatter.format(inst);
    }
    public static String dateFormat(long timemillis,String regex){
        SimpleDateFormat sdf = new SimpleDateFormat(regex);
        return sdf.format(new Date(timemillis));
    }
    public static String colorFormat(String text){

        String regexPattern = "&([0-9a-fk-or])"; //Detects characters from a-f, k-o and numbers from 0-9
        return text.replaceAll(regexPattern,"§$1");
    }
    public static String colorFormatRGB(String text){
        String regexPattern = "&#([0-9a-f]{6})"; //DETECTS HEX PATTERN
        return text.replaceAll(regexPattern,"§#$1");
    }
    public static String colorGradientRGB(String text){

        String regexv2 = "&<(?:[0-9a-f]{6}/[0-9a-f]{6}>.*?</>)";
        //&<ffffff/000000>WeirdText </>
        //TODO ABOVE SHOULD BE DETECTABLE through regex v2
        Pattern p = Pattern.compile(regexv2);
        Matcher m = p.matcher(text);

        //List<String> matches = new ArrayList<String>();//THE STRINGS THAT NEED TO BE REPLACED
        StringBuilder textEffect = new StringBuilder();

        while(m.find()) {
            String match =m.group();
            //matches.add(match);
            int time = 0;
            //GETS HEX STRINGS AND TURNS THEM INTO DECIMAL INTEGERS
            String color1 = match.substring(2, 8);
            int r1 = Integer.parseInt(color1.substring(0, 2), 16);
            int g1 = Integer.parseInt(color1.substring(2, 4), 16);
            int b1 = Integer.parseInt(color1.substring(4, 6), 16);

            String color2 = match.substring(9, 15);
            int r2 = Integer.parseInt(color2.substring(0, 2), 16);
            int g2 = Integer.parseInt(color2.substring(2, 4), 16);
            int b2 = Integer.parseInt(color2.substring(4, 6), 16);

            String cleanedMatch = match.substring(16, match.length()-3);
            if(cleanedMatch.startsWith("<k>")) {
                textEffect.append("§k");
                cleanedMatch = cleanedMatch.replaceFirst("<k>","");
            }
            if(cleanedMatch.startsWith("<l>")) {
                textEffect.append("§l");
                cleanedMatch = cleanedMatch.replaceFirst("<l>","");
            }
            if(cleanedMatch.startsWith("<m>")) {
                textEffect.append("§m");
                cleanedMatch = cleanedMatch.replaceFirst("<m>","");
            }
            if(cleanedMatch.startsWith("<n>")) {
                textEffect.append("§n");
                cleanedMatch = cleanedMatch.replaceFirst("<n>","");
            }
            if(cleanedMatch.startsWith("<o>")) {
                textEffect.append("§o");
                cleanedMatch = cleanedMatch.replaceFirst("<o>","");
            }
            int sampleSize = cleanedMatch.length();
            //TODO BLEND COLORS AND MAP COLORS TO CHAR
            //for color1: bsp: samplesize=15=100% 14=?%
            //100:samplesize*sample_inv == der gesuchte % wert

            int sample_inverted = sampleSize;
            int sample_positive = 1;

            int pct_1;
            int pct_2;

            int r1_temp;
            int g1_temp;
            int b1_temp;

            int r2_temp;
            int g2_temp;
            int b2_temp;

            int r;
            int g;
            int b;

            String r_hex;
            String g_hex;
            String b_hex;

            StringBuilder formatedMatch = new StringBuilder();
            for(int i=0;i<sampleSize;i++) {

                pct_1 = 100/sampleSize*sample_inverted;
                r1_temp = r1/100*pct_1;
                g1_temp = g1/100*pct_1;
                b1_temp = b1/100*pct_1;

                pct_2 = 100/sampleSize*sample_positive;
                r2_temp = r2/100*pct_2;
                g2_temp = g2/100*pct_2;
                b2_temp = b2/100*pct_2;

                r=r1_temp+r2_temp;
                g=g1_temp+g2_temp;
                b=b1_temp+b2_temp;

                r_hex = String.format("%02x", r);
                g_hex = String.format("%02x", g);
                b_hex = String.format("%02x", b);

                formatedMatch.append("§#").append(r_hex).append(g_hex).append(b_hex).append(textEffect).append(cleanedMatch.charAt(i));

                sample_positive++;
                sample_inverted--;

            }

            text = text.replace(match, formatedMatch);

        }
        return text;
    }
    public static Boolean isSameLocation(Location loc1,Location loc2){
        if (loc1 == null && loc2 == null) {
            return true;
        }
        if (loc1 == null || loc2 == null) {
            return false;
        }
        return loc1.getWorld().equals(loc2.getWorld()) &&
                loc1.getBlockX() == loc2.getBlockX() &&
                loc1.getBlockY() == loc2.getBlockY() &&
                loc1.getBlockZ() == loc2.getBlockZ();
    }
    public static Location stringToLocation(String str){
        String slocs[] = str.split("\\,");
        String X = slocs[0];
        String Y = slocs[1];
        String Z = slocs[2];
        String j = slocs[3];
        String p = slocs[4];
        String w = slocs[5];
        Location loc = new Location(Bukkit.getWorld(w),Double.valueOf(X), Double.valueOf(Y), Double.valueOf(Z), Float.valueOf(j), Float.valueOf(p));
        return loc;
    }
    public static String locationToString(Location loc){
        String locX = String.valueOf(loc.getX());
        String locY = String.valueOf(loc.getY());
        String locZ = String.valueOf(loc.getZ());
        String locj = String.valueOf(loc.getYaw());
        String locp = String.valueOf(loc.getPitch());
        String w = loc.getWorld().getName();
        String spc = ",";
        String Spawn = locX+spc+locY+spc+locZ+spc+locj+spc+locp+spc+w;
        return Spawn;
    }
    //URL FORMATER
    public static String[] splitMessage_removeURL(String message) {
        // Regular expression to match URLs
        String urlRegex = "https?://\\S+";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(message);

        // Find URLs and store their indices
        int[] urlIndices = new int[message.length() + 1];
        int indexCount = 0;
        while (matcher.find()) {
            urlIndices[indexCount++] = matcher.start();
            urlIndices[indexCount++] = matcher.end();
        }

        // Create an array to store URL and non-URL parts
        String[] parts = new String[indexCount / 2 + 1];

        // Extract URL and non-URL parts
        int startIndex = 0;
        for (int i = 0; i < indexCount; i += 2) {
            parts[i / 2] = message.substring(startIndex, urlIndices[i]);
            startIndex = urlIndices[i + 1];
        }

        // Add the last non-URL part
        if (startIndex < message.length()) {
            parts[indexCount / 2] = message.substring(startIndex);
        }

        return parts;
    }
    public static String[] splitMessage(String message) {
        // Regular expression to match URLs
        String urlRegex = "https?://\\S+";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(message);

        // List to store URL and non-URL parts
        ArrayList<String> result = new ArrayList<>();

        // Find URLs and split the message
        int lastIndex = 0;
        while (matcher.find()) {
            // Add non-URL part before the URL
            if (matcher.start() > lastIndex) {
                result.add(message.substring(lastIndex, matcher.start()));
            }

            // Add the URL part
            result.add(matcher.group());

            // Update the last index
            lastIndex = matcher.end();
        }

        // Add the remaining non-URL part
        if (lastIndex < message.length()) {
            result.add(message.substring(lastIndex));
        }

        // Convert the list to an array
        String[] parts = new String[result.size()];
        parts = result.toArray(parts);

        return parts;
    }
    public static TextComponent urlFormat(String msg) {

        String[] parts = splitMessage(msg);

        //for(String part:parts) Bukkit.broadcastMessage(part);

        ArrayList<TextComponent> textComponents = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {
            TextComponent component = new TextComponent(parts[i]);

            if (isURL(parts[i])) {
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, parts[i]));
                component.setColor(ChatColor.BLUE);
            }

            textComponents.add(component);
        }
        return merge(textComponents.toArray(new TextComponent[0]));
    }
    private static boolean isURL(String part) {
        // Implement your logic to check if the part is a URL
        // For simplicity, this example assumes that the part starts with "http"
        return part.startsWith("https://");
    }
    private static TextComponent merge(TextComponent[] textComponents){
        TextComponent combinedTextComponent = textComponents[0];
        for(int i=1;i<textComponents.length;i++){
            combinedTextComponent.addExtra(textComponents[i]);
        }
        return combinedTextComponent;
    }
}
