package com.akabex86.features.skilllevel.listeners.Mining;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.akabex86.features.skilllevel.PlayerSkills;
import com.akabex86.features.skilllevel.Skill;
import com.akabex86.features.skilllevel.SkillCategory;
import com.akabex86.features.skilllevel.SkillManager;
import com.akabex86.features.skilllevel.SkillUpdateResult;
import com.akabex86.features.skilllevel.SkillUpdateVisualizier;

public class MiningSkillXpListener extends SkillUpdateVisualizier implements Listener{
	
	@EventHandler
	public void mineRessources(BlockBreakEvent event) {
		if(!Mining.ORELIST.contains(event.getBlock().getBlockData().getMaterial())) return;
		
		Player player = event.getPlayer();
		PlayerSkills playerSkills = SkillManager.getSkills(player);
		Skill mining = playerSkills.get(SkillCategory.MINING);
		
		int xp = event.getExpToDrop();
		SkillUpdateResult result = mining.addXp(xp);
		
		visualizeSkillUpdate(player, result, SkillCategory.MINING);
		SkillManager.updatePlayerSkills(player.getUniqueId(), playerSkills);
	}
	
}
