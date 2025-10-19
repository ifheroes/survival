package com.akabex86.features.skilllevel.listeners.Combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.akabex86.features.skilllevel.PlayerSkills;
import com.akabex86.features.skilllevel.Skill;
import com.akabex86.features.skilllevel.SkillCategory;
import com.akabex86.features.skilllevel.SkillManager;

public class DamageEntityListener implements Listener{

	/*
	 * tmp
	 */
    static final double MAX_DAMAGE = 10;

    public static double calculateDamage(double damage, int level) {
       return damage + (MAX_DAMAGE - damage) * Math.pow((double) level / Skill.MAXLEVEL, 2);
    }
	
	@EventHandler
	public void damageEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player player) {
			PlayerSkills skillTree = SkillManager.getSkills(player);
			Skill combatSkill = skillTree.get(SkillCategory.COMBAT);
			
			int level = combatSkill.getLevel();
			double damage = event.getDamage();
			double newDamage = calculateDamage(damage, level);
			event.setDamage(newDamage);
		}
	}
	
}
