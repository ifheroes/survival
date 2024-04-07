package com.akabex86.utils;

import com.akabex86.main.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EditMode {
    private static List<String> editors = new ArrayList<>();

    public static List<String> getEditors(){
        return editors;
    }
    public static Boolean isEditor(String username){
        return editors.contains(username);
    }
    public static void addEditor(String username){
        editors.add(username);
        Main.getPlugin(Main.class).getLogger().info("Added "+username+ " to editors.");
    }
    public static void removeEditor(String username){
        editors.remove(username);
        Main.getPlugin(Main.class).getLogger().info("Removed "+username+ " from editors.");
    }
}
