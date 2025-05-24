package com.akabex86.features.skilllevel.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.akabex86.features.skilllevel.SkillCategory;

/**
 * This event represents a passive skill level update.
 * It only allows modification of the target level (levelTo),
 * while the original level (levelFrom) remains immutable.
 */
public class SkillUpdateEvent extends Event implements Cancellable{

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int originalLevel;
    private final long originalXP;
    private int targetLevel;
    private long targetXP;
    private SkillCategory skillCategory;
    
    public SkillUpdateEvent(Player player, SkillCategory category, int originalLevel, int targetLevel, long originalXP, long targetXP) {
        this.player = player;
        this.originalLevel = originalLevel;
        this.targetLevel = targetLevel;
        this.originalXP = originalXP;
        this.targetXP = targetXP;
    }

    public Player getPlayer() {
        return this.player;
    }

    public SkillCategory getCategory() {
        return this.skillCategory;
    }

    public int getOriginalLevel() {
        return this.originalLevel;
    }

    public int getTargetLevel() {
        return this.targetLevel;
    }

    public long getOriginalXP() {
    	return this.originalXP;
    }
    
    public long getTargetXP() {
    	return this.targetXP;
    }
    
    public void setTargetLevel(int level) {
        this.targetLevel = level;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {
		// TODO Auto-generated method stub
		
	}
}
