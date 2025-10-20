package com.akabex86.features.skilllevel.listeners.Mining;

import java.util.Collection;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.akabex86.features.skilllevel.PlayerSkills;
import com.akabex86.features.skilllevel.Skill;
import com.akabex86.features.skilllevel.SkillCategory;
import com.akabex86.features.skilllevel.SkillManager;

public class BreakBlockListener implements Listener{

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		if(!Mining.ORELIST.contains(event.getBlock().getBlockData().getMaterial())) return;
		
		PlayerSkills skillTree = SkillManager.getSkills(event.getPlayer());
		Skill mining = skillTree.get(SkillCategory.MINING);
		
		int level = mining.getLevel();
		

        event.setDropItems(false);
		
		Block block = event.getBlock();
		World world = block.getLocation().getWorld();
        Collection<ItemStack> drops = block.getDrops(event.getPlayer().getInventory().getItemInMainHand());
        drops.forEach(item -> {
        	item.setAmount(item.getAmount()*level);
        	world.dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), item);
        });
        
        
        
	}
}
