package com.akabex86.features.skilllevel;

import java.util.Locale;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
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
		int xpToNewLevel = result.xpToNextLevel();

		TextComponent message = new TextComponent(String.format(Locale.GERMANY, "%s Lvl. %d - %,d/%,d XP",
				category.getName(), 
				newLevel, 
				newXp, 
				xpToNewLevel));
		
		message = applyGradient(message, category.getBaseColor(), category.getSecondaryColor());

		return message;
	}

	public static TextComponent applyGradient(TextComponent base, ChatColor startColor, ChatColor endColor) {
        String text = base.getText();
        if (text == null || text.isEmpty()) return base;
        if (startColor == null || endColor == null) return base;

        java.awt.Color start = java.awt.Color.decode(startColor.getName());
        java.awt.Color end = java.awt.Color.decode(endColor.getName());

        TextComponent gradient = new TextComponent();

        int length = text.length();
        for (int i = 0; i < length; i++) {
            float ratio = (length == 1) ? 0f : (float) i / (float) (length - 1);
            java.awt.Color blended = blend(start, end, ratio);

            String hex = String.format("#%02x%02x%02x",
                    blended.getRed(), blended.getGreen(), blended.getBlue());
            ChatColor color = ChatColor.of(hex);

            TextComponent part = new TextComponent(String.valueOf(text.charAt(i)));
            part.setColor(color);

            // Formatierungen & Events übernehmen
            part.setBold(base.isBold());
            part.setItalic(base.isItalic());
            part.setUnderlined(base.isUnderlined());
            part.setStrikethrough(base.isStrikethrough());
            part.setObfuscated(base.isObfuscated());
            part.setClickEvent(base.getClickEvent());
            part.setHoverEvent(base.getHoverEvent());

            gradient.addExtra(part);
        }

        return gradient;
    }

    private static java.awt.Color blend(java.awt.Color c1, java.awt.Color c2, float ratio) {
        ratio = Math.min(1f, Math.max(0f, ratio));
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);
        return new java.awt.Color(r, g, b);
    }

}
