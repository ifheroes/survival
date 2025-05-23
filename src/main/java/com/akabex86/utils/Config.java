package com.akabex86.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import com.akabex86.main.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    public static void createMainFolder(){
        File ufile = new File ("plugins//survival");
        if(!ufile.isDirectory()){
            ufile.mkdir();
            System.out.println("[Survival] Plugin Ordner erstellt!");
        }
    }
    public static void createFolder(String name){
        File ufile = new File ("plugins//survival//"+name);
        if(!ufile.isDirectory()){
            ufile.mkdir();
            System.out.println("[Survival] Ordner '"+name+"' erstellt!");
        }
    }
    public static void checkConfig(){
        File file = new File("plugins//survival//config.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config konnte nicht gefunden werden!");
            }
        }
    }
    public static void checkFile(String fileName){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config konnte nicht gefunden werden!");
            }
        }
    }

    //Getter
    public static List<String> getFilenames(String directory){
        Path dir = Paths.get(directory);
        List<String> filenames;
        try {
            filenames = Files.list(dir).filter(Files::isRegularFile).map(file -> file.toString()).toList();
        }catch (IOException e){
            Main.main.getLogger().log(Level.SEVERE,"Gratulation. Du hast es geschafft eine IOException zu triggern! :D");
            throw new RuntimeException(e);
        }
        return filenames;
    }
    public static Set<String> getKeys(String fileName,Boolean includeHierarchy){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            return new HashSet<>();
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getKeys(includeHierarchy);
        }
    }
    public static int getInteger(String fileName,String Key){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            return 0;
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getInt(Key);
        }
    }
    public static long getLong(String fileName,String Key){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            return 0L;
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getLong(Key);
        }
    }
    public static Boolean getBoolean(String fileName,String Key){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            return null;
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getBoolean(Key);
        }
    }
    public static String getString(String fileName,String Key){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            Main.main.getLogger().log(Level.SEVERE,"- method=getString&file=["+file.toString()+"] Key:["+Key+"]");
            Main.main.getLogger().log(Level.SEVERE,"- fileName parameter=["+fileName+"]");
            return null;
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getString(Key);
        }
    }
    public static List<String> getStringList(String fileName,String Key){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            return null;
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getStringList(Key);
        }
    }
    public static Location getLocation(String fileName,String key){
    	
        if(Config.getString(fileName,key)!=null){
        	
            return Utils.stringToLocation(Config.getString(fileName,key));
        }else{
            return null;
        }
    }
    //Setter
    public static void set(String fileName,String Key,Object Value){
        File file = new File("plugins//survival//"+fileName+".yml");
        Main.main.getLogger().log(Level.INFO,"plugins//survival//"+fileName+".yml wird gelesen...");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
            Main.main.getLogger().log(Level.INFO,"plugins//survival//"+fileName+".yml wird erstellt...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                Main.main.getLogger().log(Level.SEVERE,"Gratulation. Du hast es geschafft eine IOException zu triggern! :D");
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(Key, Value);
        try {
            cfg.save(file);
        }catch (IOException e){
            Main.main.getLogger().log(Level.SEVERE,"Fehler! Config Inhalt konnte nicht gespeichert werden!");
        }
    }
    public static void delete(String fileName,String Key){
        File file = new File("plugins//survival//"+fileName+".yml");
        if(!file.exists()){
            Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set(Key, null);
            try {
                cfg.save(file);
            }catch (IOException e){
                Main.main.getLogger().log(Level.SEVERE,"[Survival] Fehler! Config Inhalt konnte nicht gespeichert werden!");
            }
        }
    }
}
