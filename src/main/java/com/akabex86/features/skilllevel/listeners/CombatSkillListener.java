package com.akabex86.features.skilllevel.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.akabex86.features.skilllevel.SkillCategory;
import com.akabex86.features.skilllevel.SkillManager;
import com.akabex86.features.skilllevel.Skills;
import com.akabex86.features.skilllevel.skills.SkillCombat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class CombatSkillListener implements Listener{

	@EventHandler
	public void killEntity(EntityDeathEvent event) {
		if(!(event.getDamageSource().getCausingEntity() instanceof Player player)) return;
		
		Skills skillTree = SkillManager.getSkills(player);
		SkillCombat combatSkill = (SkillCombat) skillTree.get(SkillCategory.COMBAT);
		long xp = combatSkill.getXP();
		xp += 10;
		combatSkill.setXP(xp);
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6Combat: §a%s§6xp".formatted(xp)));
		
		SkillManager.updateSkills(null, skillTree);
		
	}
}
