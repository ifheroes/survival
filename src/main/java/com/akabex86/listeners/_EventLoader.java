package com.akabex86.listeners;

import com.akabex86.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;

public class _EventLoader implements Listener {


    public _EventLoader(Main plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler public void onSignChange(SignChangeEvent e){Events_Sign.onSign(e);}
    @EventHandler public void onChat(AsyncPlayerChatEvent e){Events_Chat.onChat(e);}
    @EventHandler public void onCommandSend(PlayerCommandSendEvent e){Events_CommandSend.onCommandSend(e);}

    @EventHandler public void onLogin(PlayerLoginEvent e){Events_Join_Leave.onLogin(e);}
    @EventHandler public void onJoin(PlayerJoinEvent e){Events_Join_Leave.onJoin(e);}
    @EventHandler public void onLeave(PlayerQuitEvent e){Events_Join_Leave.onLeave(e);}


    @EventHandler public void onInteract(PlayerInteractEvent e){Events_BlockInteract.onInteract(e);}
    @EventHandler public void onPlace(BlockPlaceEvent e){Events_BlockInteract.onPlace(e);}
    @EventHandler public void onBreak(BlockBreakEvent e){Events_BlockInteract.onBreak(e);}

    @EventHandler public void onVilAcqTrade(VillagerAcquireTradeEvent e){Events_Villager.onVillagerAcquireTrade(e);}
    @EventHandler public void onVilRepTrade(VillagerReplenishTradeEvent e){Events_Villager.onVillagerReplenishTrade(e);}

    /*
    @EventHandler public void onInventoryClose(InventoryCloseEvent e){Events_Inventory.onInventoryClose(e);}
    @EventHandler public void onInventoryDrag(InventoryDragEvent e){Events_Inventory.onInventoryDrag(e);}
    @EventHandler public void onInventoryClick(InventoryClickEvent e){Events_Inventory.onInventoryClick(e);}
    */

}
