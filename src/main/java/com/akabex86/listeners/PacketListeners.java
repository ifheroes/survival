package com.akabex86.listeners;

import com.akabex86.main.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.entity.Player;

import java.util.List;

public class PacketListeners {
    public static void loadPacketListeners(ProtocolManager man){
        //blocks explosions (test)
        man.addPacketListener(new PacketAdapter(Main.main, ListenerPriority.NORMAL, PacketType.Play.Server.EXPLOSION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                //EXPERIMENTAL!!! blocks all explosions from being sent to the client by the server
                //event.setCancelled(true);
            }
        });

        man.addPacketListener(new PacketAdapter(Main.main, ListenerPriority.NORMAL, PacketType.Play.Client.CUSTOM_PAYLOAD) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                //EXPERIMENTAL!!! GETS CUSTOM PAYLOAD DATA FROM THE CLIENT COMMENTED OUT STUFF TRIGGERS ERRORS!!!
                /*
                Player p = event.getPlayer();
                PacketContainer pc = event.getPacket();
                PacketType type = pc.getType();
                List<String> stringData = pc.getStrings().getValues();
                String handle = pc.getHandle().toString();
                Main.main.getLogger().info("Server received packet by "+p.getName()+" of type "+type.name());
                Main.main.getLogger().info("- Handle: "+handle);

                 */
            }
        });
        man.addPacketListener(new PacketAdapter(Main.main, ListenerPriority.NORMAL, PacketType.Play.Client.CLIENT_COMMAND) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                //EXPERIMENTAL!!! GETS CLIENT COMMAND DATA FROM THE CLIENT COMMENTED OUT STUFF TRIGGERS ERRORS!!!
                /*

                Player p = event.getPlayer();

                PacketContainer pc = event.getPacket();
                PacketType type = pc.getType();
                String handle = pc.getHandle().toString();
                List<String> stringData = pc.getStrings().getValues();
                String _stringData = "";

                for(String s:stringData){
                    _stringData = _stringData.concat(","+s);
                }

                _stringData = _stringData.replaceFirst(",", "");
                Main.main.getLogger().info("Server received packet by "+p.getName()+" of type "+type.name());
                Main.main.getLogger().info("- Handle: "+handle);

                 */
            }
        });
        man.addPacketListener(new PacketAdapter(Main.main, ListenerPriority.NORMAL, PacketType.Play.Server.LOGIN) {
            @Override
            public void onPacketSending(PacketEvent event) {
                //EXPERIMENTAL!!! GETS SERVER LOGIN DATA FROM THE CLIENT
                //COMMENTED OUT STUFF TRIGGERS ERRORS!!!
                /*
                Player p = event.getPlayer();

                PacketContainer pc = event.getPacket();
                PacketType type = pc.getType();
                String handle = pc.getHandle().toString();

                List<String> stringData = pc.getStrings().getValues();
                String _stringData = "";

                for(String s:stringData){
                    _stringData = _stringData.concat(","+s);
                }

                _stringData = _stringData.replaceFirst(",", "");
                Main.main.getLogger().info("Server sent packet to "+p.getName()+" of type "+type.name());
                Main.main.getLogger().info("- Handle: "+handle);
                Main.main.getLogger().info("- StringData: "+_stringData);
                 */
            }
        });
        //block tab completions
        /*
        man.addPacketListener(new PacketAdapter(
                Main.main,
                ListenerPriority.NORMAL,
                PacketType.Play.Server.TAB_COMPLETE
        ) {
            @Override
            public void onPacketSending(PacketEvent e) {
                PacketContainer packet = e.getPacket();
                String[] completions = e.getPacket().getStringArrays().read(0);
                //Ersetzt die Tabcompletions durch einen leeren String array.
                e.getPacket().getStringArrays().write(0, new String[]{});
            }
        });
         */
    }
}
