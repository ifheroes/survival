package com.akabex86.features.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Location;

import com.akabex86.features.FeatureComponent;
import com.akabex86.features.FeaturePlugin;
import com.akabex86.features.home.commands.CommandDelhome;
import com.akabex86.features.home.commands.CommandHome;
import com.akabex86.features.home.commands.CommandHomes;
import com.akabex86.features.home.commands.CommandSethome;
import com.akabex86.features.home.commands.CommandSpyhome;
import com.akabex86.utils.Config;
import com.akabex86.utils.Utils;

@FeatureComponent(owner = "Akabex86, FrauJulian, I_Dev", version = "1.0")
public class HomeManager extends FeaturePlugin{

	@Override
	public void onLoad() {
		CommandHome home = new CommandHome();
        getCommand("home").setExecutor(home);
        getCommand("home").setTabCompleter(home);

        CommandSpyhome spyhome = new CommandSpyhome();
        getCommand("spyhome").setExecutor(spyhome);

        CommandHomes homes = new CommandHomes();
        getCommand("homes").setExecutor(homes);
        getCommand("homes").setTabCompleter(homes);

        CommandSethome sethome = new CommandSethome();
        getCommand("sethome").setExecutor(sethome);
        getCommand("sethome").setTabCompleter(sethome);

        CommandDelhome delhome = new CommandDelhome();
        getCommand("delhome").setExecutor(delhome);
        getCommand("delhome").setTabCompleter(delhome);
	}
	
	private static final String FILEPATH = "userdata//%uuid%";
	private static final String FILEPATHHOMES = "userdata//%uuid%homes.";
	
	private static final String HOMEKEY = "homes.";
	
	//TODO home system auf caching updaten um Filesystem calls zu verringern.
	public static Optional<Location> getHome(String uuid, String homeName) {
		String path = getHomePath(uuid);
	    String locationString = Config.getString(path, homeName);

	    return Optional.ofNullable(locationString)
	    				.map(Utils::stringToLocation);
	}

    public static void setHome(String uuid, String homeName, Location loc){
    	String path = getPath(uuid);
    	String locationString = Utils.locationToString(loc);
    	
        Config.set(path, homeName, locationString);
    }
    
    public static void delHome(String uuid, String homeName){
    	String path = getPath(uuid);
    	
        Config.delete(path,HOMEKEY+homeName);
    }
    
    public static boolean hasHomes(String uuid){
    	String path = getPath(uuid);
        return Config.getKeys(path, true).stream()
                     .anyMatch(key -> key.startsWith(HOMEKEY));
    }
    
    public static List<String> getHomes(String uuid){
        String path = getPath(uuid);
        
        return Config.getKeys(path, true).stream()
        		.filter(key -> key.startsWith(HOMEKEY))
        		.map(key -> key.replaceFirst(HOMEKEY, ""))
        		.toList();
    }
	
    
    private static String getPath(String uuid) {
    	return FILEPATH.replace("%uuid%", uuid);
    }
    
    private static String getHomePath(String uuid) {
    	return FILEPATHHOMES.replace("%uuid%", uuid);
    }
}
