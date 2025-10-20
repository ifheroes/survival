package com.akabex86.features.skilllevel.listeners.Combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.akabex86.features.skilllevel.PlayerSkills;
import com.akabex86.features.skilllevel.Skill;
import com.akabex86.features.skilllevel.SkillCategory;
import com.akabex86.features.skilllevel.SkillManager;
import com.akabex86.features.skilllevel.SkillUpdateResult;
import com.akabex86.features.skilllevel.SkillUpdateVisualizier;

public class CombatSkillXpListener extends SkillUpdateVisualizier implements Listener{
	
	@EventHandler
	public void killEntity(EntityDeathEvent event) {
		if(!(event.getDamageSource().getCausingEntity() instanceof Player player)) return;
		
		PlayerSkills skillTree = SkillManager.getSkills(player);
		Skill combatSkill = skillTree.get(SkillCategory.COMBAT);
	
		int gainedXp = event.getDroppedExp();
		SkillUpdateResult result = combatSkill.addXp(gainedXp);
		
		visualizeSkillUpdate(player, result, SkillCategory.COMBAT);
		SkillManager.updatePlayerSkills(player.getUniqueId(), skillTree);	
	}
}
