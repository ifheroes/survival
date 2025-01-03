package com.akabex86.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.akabex86.main.Main;
import com.akabex86.objects.Cuboid;
import com.akabex86.utils.UuidFetcher;
import com.akabex86.utils.Zone;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CommandZone implements CommandExecutor, TabCompleter {
	public CommandZone(Main main) {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		/* 
		 * TODO COMPLETE REFACTOR - 
		 * Remove edit mode and switch to a simpler, item-based selection system with fewer commands.
		 * Add zone credits instead of the predefined 100 blocks squared.
		 * Right-click and left-click with region tool allow for the following actions:
		 *   - Left-click or right-click within WG region: do nothing; show zone info and borders of the clicked zone in red.
		 *   - Left-click or right-click outside region (separate actions: left == pos1, right == pos2).
		 *     Alternatively, use /zone <pos1,pos2> as a region selector.
		 *   - /zone create|claim: After you've made your selection, creates a new zone within the selected area.
		 *     The ID will be generated starting from playername_1 and counting up.
		 *   - /zone edit: Edit existing zones.
		 *
		 * TODO
		 * Misc features:
		 *   - Flags
		 *   - Custom name (must be unique)
		 *   - Trusted members
		 *   - Warp (instead of player warps)
		 */

		if (!(sender instanceof Player)) return false;

		Player p = (Player) sender;
		if (checkZoneWorld(p)) return true;
		if (args.length != 1) 	return true;

		if (Arrays.asList("wand", "tool", "claimtool", "zauberstab").contains(args[0])) return convertStickOfPlayer(p);
		if (Arrays.asList("claim", "erstellen").contains(args[0])) return createZone(p);
		if (args[0].equalsIgnoreCase("update")) return updateZone(p);
		if (args[0].equalsIgnoreCase("delete")) return deleteZone(p);
		if (args[0].equalsIgnoreCase("info")) return getZoneInfo(p);
			
		if (args[0].equalsIgnoreCase("list")) {
			// TODO LISTS ALL ZONES OF A PLAYER
			return true;
		}
		
		if (args[0].equalsIgnoreCase("help")) {
			p.sendMessage(" ");
			p.sendMessage("§8- §7/zone tool§r §eWandelt einen Stick zum Zauberstab um.");
			p.sendMessage("§8- §7/zone claim§r §eClaime deine Zone mit diesem Befehl");
			p.sendMessage("§8- §7/zone update§r §eUpdate deine Zone mit diesem Befehl");
			p.sendMessage("§8- §7/zone delete§r §eEntfernt die Zone auf der du dich befindest");
			p.sendMessage("§8- §7/zone info§r §eZeigt Zoneninformationen an");
			p.sendMessage(" ");
			p.sendMessage("§8- §7/zone help§r §eZeige diese Seite");
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
		return (sender instanceof Player) ? Arrays.asList("tool", "claim", "update", "info", "delete") : Collections.emptyList();
	}

	private boolean checkZoneWorld(Player player) {
		if (!player.getWorld().getName().equalsIgnoreCase(Zone._mainWorld)) {
			player.sendMessage("§cDas erstellen von Zonen ist nur in der Hauptwelt erlaubt!");
			return true;
		}
		return false;
	}

	private boolean sendPlayerZoneOverrideRequest(Player player) {
		player.sendMessage("");
		player.sendMessage("§eWillst du deine bestehende Zone überschreiben?");
		TextComponent message = new TextComponent();
		TextComponent accept = new TextComponent("Überschreiben");
		accept.setColor(ChatColor.GREEN);
		accept.setBold(true);
		accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/zone update"));
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("/zone update")));
		message.addExtra(accept);
		player.sendMessage("§8(§a/zone update§8)");
		player.spigot().sendMessage(message);
		return true;
	}

	private boolean convertStickOfPlayer(Player player) {
		Optional.ofNullable(player.getInventory().getItemInMainHand()).filter(item -> item.getType() == Material.STICK)
				.ifPresentOrElse(item -> {
					Zone.createWand(item);
					player.sendMessage(" ");
					player.sendMessage("§eDein Stick wurde zum Zauberstab!");
					player.sendMessage("§eSetze position 1 mit Linksklick und position 2 mit einen Rechtsklick");
					player.sendMessage("§eBestaetige deine Auswahl mit '/zone claim' wenn du fertig bist.");
					player.sendMessage("§eMit '/zone help' siehst du alle wichtigen Befehle für das Zonensystem.");
				}, () -> player.sendMessage("§cBitte lege einen Stick in die Hand und versuche es erneut."));
		return true;
	}

	private boolean createZone(Player player) {
		Cuboid selection = Zone.ZoneCache.get(player.getName());
		if (selection == null) {
			player.sendMessage("§cDu must erst eine Zone festlegen."); return true;
		}
		if (!selection.isValid()) return true;
		
		// TODO ERROR HANDLING - IMPLEMENT ZONE CREDITS
		// ZONE CREATOR
		switch (Zone.create(UuidFetcher.getUUID(player.getName()), selection)) {
			case REGIONCREATED -> player.sendMessage("§aZone erstellt!");
			case ALLREADYEXISTS -> sendPlayerZoneOverrideRequest(player);
			case ZONEMANAGERNOTFOUND -> player.sendMessage("§cZonenmanager nicht gefunden!");
			case INVALIDLOCATION -> player.sendMessage("§cFehlerhafte Positionsdaten!");
			case SELECTIONTOLARGE -> player.sendMessage("§cAuswahl zu groß! (Max.7000)");
			case SELECTIONTOSMALL -> player.sendMessage("§cAuswahl zu klein! (Min.10x10)");
			case ZONEINTERSECTION -> player.sendMessage("§cZone überschneidet sich mit einer oder mehreren anderen Zonen.");
		default -> player.sendMessage("§cUnbekannter Fehler!");
		}
		return true;
	}
	
	private boolean updateZone(Player player) {
		// TODO ERROR HANDLING - IMPLEMENT ZONE CREDITS
		Optional.ofNullable(Zone.ZoneCache.get(player.getName()))
			.filter(Cuboid::isValid)
			.ifPresentOrElse(sel -> {
				Zone.delete("zone_" + player.getName());
				switch (Zone.create(UuidFetcher.getUUID(player.getName()), sel)) {
				case REGIONCREATED -> player.sendMessage("§aZonen Update erfolgreich!");
				case ALLREADYEXISTS -> player.sendMessage("§4FEHLER §c- zone wurde nicht entfernt");
				case ZONEMANAGERNOTFOUND -> player.sendMessage("§cZonenmanager nicht gefunden!");
				case INVALIDLOCATION -> player.sendMessage("§cFehlerhafte Positionsdaten!");
				case SELECTIONTOLARGE -> player.sendMessage("§cAuswahl zu groß! (Max.7000)");
				case SELECTIONTOSMALL -> player.sendMessage("§cAuswahl zu klein! (Min.10x10)");
				case ZONEINTERSECTION -> player.sendMessage("§cZone überschneidet sich mit einer oder mehreren anderen Zonen.");
				default -> player.sendMessage("§cUnbekannter Fehler!");
				}
			}, () -> player.sendMessage("§cDu must erst eine Zone festlegen."));
		return true;
	}
	
	private boolean deleteZone(Player player) {
		// DELETES THE ZONE A PLAYER IS CURRENTLY STANDING ON
		// TODO ADD SECOND ARG FOR MULTIPLE ZONES
		if (Zone.delete("zone_" + player.getName())) {
			player.sendMessage("§aZone gelöscht.");
		} else {
			player.sendMessage("§cDie Zone gehört dir nicht oder es konnte keine Zone gefunden werden.");
		}
		return true;
	}
	
	private boolean getZoneInfo(Player player) {
		// TODO SHOW INFO ABOUT THE ZONE A PLAYER IS CURRENTLY STANDING ON
		if(!Zone.hasZoneAt(player.getLocation())) {
			player.sendMessage("§cDu befindest dich in keiner Zone.");
			return true;
		}
		
		ProtectedRegion reg = Zone.getZoneAt(player.getLocation());
		String id = reg.getId();
		String owner = id.replaceFirst("zone_", "");
		AtomicInteger num = new AtomicInteger(1);
		reg.getPoints().forEach(bv -> player.sendMessage("§8- §e %s.Eckpunkt: [X:%s Z:%s]".formatted(num.getAndIncrement(), bv.getBlockX(), bv.getBlockZ())));
			
		player.sendMessage("\n§aZone von " + owner);
		player.sendMessage("§8- §emehr parameter bald verfügbar.");
		return true;
	}
}
