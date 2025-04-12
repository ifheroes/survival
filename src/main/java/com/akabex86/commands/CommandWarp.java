package com.akabex86.commands;

import com.akabex86.utils.Warp;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandWarp implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage("Fehler: Bitte gib einen Namen für deinen Warp an.");
                return true;
            }

            if (Warp.GetWarpLocation(args[0].toLowerCase()) == null) {
                player.sendMessage("Fehler: Warp [" + args[0] + "] nicht gefunden.");
                return true;
            }

            Location warpLocation = Warp.GetWarpLocation(args[0].toLowerCase());
            if (warpLocation.getWorld() == null) {
                player.sendMessage("Fehler: Die Welt für Warp [" + args[0] + "] existiert nicht.");
                return true;
            }

            player.teleport(warpLocation);
            player.sendMessage("Teleportiere zum Warp [" + args[0] + "] ...");

            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (sender instanceof Player) {
            tabComplete.addAll(Warp.GetWarps().keySet());
        }

        return tabComplete;
    }
}
