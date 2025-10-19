package com.akabex86.features.skilllevel.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.akabex86.features.skilllevel.PlayerSkills;
import com.akabex86.features.skilllevel.Skill;
import com.akabex86.features.skilllevel.SkillCategory;
import com.akabex86.features.skilllevel.SkillManager;
import com.akabex86.features.skilllevel.SkillUpdateResult;
import com.akabex86.features.skilllevel.SkillUpdateVisualizier;

public class CombatSkillListener extends SkillUpdateVisualizier implements Listener{

	@EventHandler
	public void killEntity(EntityDeathEvent event) {
		if(!(event.getDamageSource().getCausingEntity() instanceof Player player)) return;
		
		PlayerSkills skillTree = SkillManager.getSkills(player);
		Skill combatSkill = skillTree.get(SkillCategory.COMBAT);
		SkillUpdateResult result = combatSkill.addXp(10);
		
		visualizeSkillUpdate(player, result, SkillCategory.COMBAT);
		SkillManager.updatePlayerSkills(player.getUniqueId(), skillTree);	
	}
	
	
	
	
}
