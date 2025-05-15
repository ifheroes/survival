package com.akabex86.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.akabex86.commands.CommandBook;
import com.akabex86.commands.CommandBroadcast;
import com.akabex86.commands.CommandDelhome;
import com.akabex86.commands.CommandDelwarp;
import com.akabex86.commands.CommandEnchant;
import com.akabex86.commands.CommandEnderchest;
import com.akabex86.commands.CommandHome;
import com.akabex86.commands.CommandHomes;
import com.akabex86.commands.CommandInvsee;
import com.akabex86.commands.CommandPlayerinfo;
import com.akabex86.commands.CommandSethome;
import com.akabex86.commands.CommandSetwarp;
import com.akabex86.commands.CommandSpawn;
import com.akabex86.commands.CommandSpyhome;
import com.akabex86.commands.CommandTicket;
import com.akabex86.commands.CommandTpa;
import com.akabex86.commands.CommandTpaccept;
import com.akabex86.commands.CommandTpdeny;
import com.akabex86.commands.CommandWarp;
import com.akabex86.commands.CommandWarps;
import com.akabex86.commands.CommandZone;
import com.akabex86.features.FeatureManager;
import com.akabex86.features.dailyrewards.LoginTracker;
import com.akabex86.features.dailyrewards.commands.CheckTrack;
import com.akabex86.listeners.PacketListeners;
import com.akabex86.listeners._EventLoader;
import com.akabex86.utils.Config;
import com.akabex86.utils.UuidFetcher;
import com.akabex86.utils.Warp;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class Main extends JavaPlugin {

    public static Main main;
    Logger logger = this.getLogger();
    
    private ProtocolManager protocolManager;
    private FeatureManager featureManager;
    
    private static String featuresPackage = "com.akabex86.features";
    
    @Override
    public void onEnable() {
        //TODO create a messages.yml file for editing if it doesnt exist already.
        super.onEnable();
        main = this;

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null){
            //Bukkit.getPluginManager().registerEvents(this, this);
            //TODO REFERING TO https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Hook-into-PlaceholderAPI
            getLogger().warning("PlaceholderAPI nicht gefunden! Plugin texte werden eventuell fehlerhaft dargestellt!");
        }

        protocolManager = ProtocolLibrary.getProtocolManager();

        loadConfig();

        loadCommands();
        loadListeners();
        PacketListeners.loadPacketListeners(protocolManager);

        Warp.loadWarps();//Only works properly if the file exists, FIX THAT!
        UuidFetcher.updateMappings();
        
        /*
         * Feature Manager
         */
        
        logger.log(Level.INFO, "Loading Features:");
        featureManager = new FeatureManager(featuresPackage);
        featureManager.initializeFeatures();
        logger.log(Level.INFO, "All Features loaded!");
        
        getCommand("checkLastLogin").setExecutor(new CommandExecutor() {
			
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				sender.sendMessage(LoginTracker.getLastLoginMilis((Player) sender)+"");
				return true;
			}
		});
        
        logger.log(Level.INFO,"Survival System aktiviert!");
    }
    @Override
    public void onDisable() {
        super.onDisable();
        logger.log(Level.INFO,"Survival System deaktiviert!");
    }
    private void loadListeners(){
        new _EventLoader(this);
    }
    private void loadCommands(){

        //HOME COMMANDS
        CommandHome home = new CommandHome(this);
        getCommand("home").setExecutor(home);
        getCommand("home").setTabCompleter(home);

        CommandSpyhome spyhome = new CommandSpyhome(this);
        getCommand("spyhome").setExecutor(spyhome);

        CommandHomes homes = new CommandHomes(this);
        getCommand("homes").setExecutor(homes);
        getCommand("homes").setTabCompleter(homes);

        CommandSethome sethome = new CommandSethome(this);
        getCommand("sethome").setExecutor(sethome);
        getCommand("sethome").setTabCompleter(sethome);

        CommandDelhome delhome = new CommandDelhome(this);
        getCommand("delhome").setExecutor(delhome);
        getCommand("delhome").setTabCompleter(delhome);

        //WARP COMMANDS
        CommandSpawn spawn = new CommandSpawn(this);
        getCommand("spawn").setExecutor(spawn);
        getCommand("spawn").setTabCompleter(spawn);

        CommandWarp warp = new CommandWarp(this);
        getCommand("warp").setExecutor(warp);
        getCommand("warp").setTabCompleter(warp);

        CommandWarps warps = new CommandWarps(this);
        getCommand("warps").setExecutor(warps);
        getCommand("warps").setTabCompleter(warps);

        CommandSetwarp setwarp = new CommandSetwarp(this);
        getCommand("setwarp").setExecutor(setwarp);
        getCommand("setwarp").setTabCompleter(setwarp);

        CommandDelwarp delwarp = new CommandDelwarp(this);
        getCommand("delwarp").setExecutor(delwarp);
        getCommand("delwarp").setTabCompleter(delwarp);

        //TPA COMMANDS
        CommandTpa tpa = new CommandTpa(this);
        getCommand("tpa").setExecutor(tpa);

        CommandTpaccept tpaccept = new CommandTpaccept(this);
        getCommand("tpaccept").setExecutor(tpaccept);
        getCommand("tpaccept").setTabCompleter(tpaccept);

        CommandTpdeny tpdeny = new CommandTpdeny(this);
        getCommand("tpdeny").setExecutor(tpdeny);
        getCommand("tpdeny").setTabCompleter(tpdeny);

        //MOD TOOLS
        CommandPlayerinfo pinfo = new CommandPlayerinfo(this);
        getCommand("playerinfo").setExecutor(pinfo);

        CommandTicket ticket = new CommandTicket(this);
        getCommand("ticket").setExecutor(ticket);
        getCommand("ticket").setTabCompleter(ticket);

        CommandZone zone = new CommandZone(this);
        getCommand("zone").setExecutor(zone);
        getCommand("zone").setTabCompleter(zone);

        CommandEnchant enchant = new CommandEnchant(this);
        getCommand("unsafeenchant").setExecutor(enchant);
        getCommand("unsafeenchant").setTabCompleter(enchant);

        CommandBook book = new CommandBook(this);
        getCommand("book").setExecutor(book);
        getCommand("book").setTabCompleter(book);

        CommandEnderchest enderchest = new CommandEnderchest(this);
        getCommand("enderchest").setExecutor(enderchest);
        getCommand("enderchest").setTabCompleter(enderchest);

        CommandInvsee invsee = new CommandInvsee(this);
        getCommand("invsee").setExecutor(invsee);

        CommandBroadcast bc= new CommandBroadcast(this);
        getCommand("broadcast").setExecutor(bc);
        getCommand("broadcast").setTabCompleter(bc);
       

    }
    
    private void loadConfig(){
        Config.createMainFolder();
        Config.createFolder("kits");
        Config.createFolder("userdata");
    }
}
