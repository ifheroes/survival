package com.akabex86.utils;

import com.akabex86.main.Main;

import java.util.HashMap;
import java.util.logging.Level;

public class UuidFetcher {
    private static HashMap <String,String> nameToUUIDMap = new HashMap<>();
    private static HashMap <String,String> uuidToNameMap = new HashMap<>();
    //TODO MAKE THIS ONE PUBLIC AND USE THI INSTEAD OF updateMappings TO SAVE CYCLES
    private static void addMapping(String name, String UUID){
        nameToUUIDMap.put(name.toLowerCase(),UUID);
        uuidToNameMap.put(UUID,name.toLowerCase());

    }
    public static void updateMappings(){
        //TODO FIX getString data
        Main.main.getLogger().log(Level.INFO,"==========================================");
        Main.main.getLogger().log(Level.INFO,"               UUID MAPPINGS              ");
        for(String uuid:Config.getFilenames("plugins//iHS//userdata")){
            if(uuid.endsWith(".yml")){
                String uid = uuid.replaceAll(".yml","").replaceAll("plugins//iHS//userdata//","").replaceAll("plugins/iHS/userdata/","");
                if(Config.getString("userdata//"+uid,"name")!=null){
                    //TODO MAP UUIDS AND NAMES TOGETHER.
                    String name = Config.getString("userdata//"+uid,"name");
                    Main.main.getLogger().log(Level.INFO,"UUID OF "+name+" IS "+uid);
                    addMapping(name,uid);
                }
            }
        }
        Main.main.getLogger().log(Level.INFO,"==========================================");
    }
    public static boolean isFetched(String name){
        return nameToUUIDMap.containsKey(name);
    }
    public static String getUUID(String name){
        return nameToUUIDMap.get(name.toLowerCase());
    }
    public static String getName(String UUID){
        return uuidToNameMap.get(UUID);
    }
}
