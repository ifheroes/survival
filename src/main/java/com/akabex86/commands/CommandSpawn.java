package com.akabex86.commands;

import com.akabex86.main.Main;
import com.akabex86.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandSpawn implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
        if (sender instanceof Player player) {
            if (Warp.GetWarpLocation("spawn") == null) {
                player.sendMessage("Fehler: Spawnpunkt nicht gefunden.");
                return true;
            }

            player.teleport(Warp.GetWarpLocation("spawn"));
            player.sendMessage("Teleportiere zum Spawn ...");

            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {
        return new ArrayList<>();
    }
}
