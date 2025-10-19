package com.akabex86.features.skilllevel;

import java.util.Locale;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class SkillUpdateVisualizier {

	public void visualizeSkillUpdate(Player player, SkillUpdateResult result, SkillCategory category) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, createTextComponent(result, category));
	}
	
	private TextComponent createTextComponent(SkillUpdateResult result, SkillCategory category) {
		int oldXp = result.oldXp();
		
		int newXp = result.newXp();
		int newLevel = result.newLevel();
		
		TextComponent message = new TextComponent(String.format(Locale.GERMANY ,"%s %d - %5,d/%5,dXP", category.getName(), newLevel, oldXp, newXp));
		message.setColor(category.getColor());
		return message;
	}
	
}
