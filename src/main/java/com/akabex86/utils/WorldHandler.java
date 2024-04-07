package com.akabex86.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class WorldHandler {

    public static void createWorld(String w_Name,Environment w_environment,WorldType w_type,Long w_Seed){

        WorldCreator wc = WorldCreator.name(w_Name);

        if(w_environment!=null)wc.environment(w_environment);
        if(w_type!=null)wc.type(w_type);
        if(w_Seed!=null)wc.seed(w_Seed);

        Bukkit.broadcastMessage("§8§oGeneriere Welt: "+w_Name+" ...");
        Bukkit.createWorld(wc);
        Bukkit.broadcastMessage("§8§oAbgeschlossen! Mit §r§7§o/world tp "+w_Name+"§r§8§o kannst du dich hinteleportieren!");
        //TODO WRITE TO CONFIG AND LOAD WORLDS ON STARTUP, LOAD WORLD IF ALREADY EXISTS!

        HashMap<String,String> worldData = new HashMap<>();
        if(w_environment != null)worldData.put("environment", w_environment.toString());
        if(w_type != null)worldData.put("type", w_type.toString());
        if(w_Seed != null)worldData.put("seed", w_Seed.toString());

        File file = new File("plugins//iHS//worlds.yml");
        if(!file.exists()){
            System.out.println("[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if(cfg.getConfigurationSection("worlds")!=null){
                ConfigurationSection sec = cfg.getConfigurationSection("worlds");
                sec.set(w_Name, worldData);
            }else{
                ConfigurationSection sec = cfg.createSection("worlds");
                sec.set(w_Name, worldData);
            }
            try {
                cfg.save(file);
            }catch (IOException e){
                System.out.println("[Survival] Fehler! Config Inhalt konnte nicht gespeichert werden!");
            }
        }
    }
    public static void unloadWorld(String w_Name){
        //Config.delete("worlds", "worlds."+w_Name);
    }
    public static void LoadWorlds(){
        File file = new File("plugins//iHS//worlds.yml");
        if(!file.exists()){
            System.out.println("[Survival] Fehler! Config Inhalt konnte nicht gefunden werden da diese nicht existiert!");
        }else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection sec = cfg.getConfigurationSection("worlds");
            try {
                for(String key:sec.getKeys(true)){
                    //IF KEY IS NOT A SUBKEY
                    if(!key.contains(".")){
                        //System.out.println("[DEBUG] Key:"+key);

                        HashMap<String,String> worldData = new HashMap<>();

                        for(String subkey:sec.getConfigurationSection(key).getKeys(false)){
                            //LOAD VALUES INTO worldData HashMap!
                            //System.out.println("[DEBUG] SubKey:"+subkey+" Value:"+cfg.getString("worlds."+key+"."+subkey));
                            worldData.put(subkey, cfg.getString("worlds."+key+"."+subkey));
                        }
                        WorldCreator wc = WorldCreator.name(key);
                        if(worldData.containsKey("environment")){
                            if(worldData.get("environment").equalsIgnoreCase("NORMAL")){
                                wc.environment(Environment.NORMAL);
                            }
                            if(worldData.get("environment").equalsIgnoreCase("NETHER")){
                                wc.environment(Environment.NETHER);
                            }
                            if(worldData.get("environment").equalsIgnoreCase("THE_END")){
                                wc.environment(Environment.THE_END);
                            }
                        }
                        if(worldData.containsKey("type")){
                            if(worldData.get("type").equalsIgnoreCase("AMPLIFIED")){
                                wc.type(WorldType.AMPLIFIED);
                            }
                            if(worldData.get("type").equalsIgnoreCase("FLAT")){
                                wc.type(WorldType.FLAT);
                            }
                            if(worldData.get("type").equalsIgnoreCase("LARGE_BIOMES")){
                                wc.type(WorldType.LARGE_BIOMES);
                            }
                            if(worldData.get("type").equalsIgnoreCase("NORMAL")){
                                wc.type(WorldType.NORMAL);
                            }
                        }
                        if(worldData.containsKey("seed")){
                            Long seed = Long.valueOf(worldData.get("seed"));
                            wc.seed(seed);
                        }
                        Bukkit.createWorld(wc);
                    }
                }
            }catch(NullPointerException e){
                System.out.println("[Survival] Es wurden noch keine Welten geladen!");
            }
            try {
                cfg.save(file);
            }catch (IOException e){
                System.out.println("[Survival] Fehler! Config Inhalt konnte nicht gespeichert werden!");
            }
        }
    }
}
