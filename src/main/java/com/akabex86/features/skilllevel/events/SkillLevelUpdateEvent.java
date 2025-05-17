package com.akabex86.features.skilllevel.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.akabex86.features.skilllevel.Skill;

/**
 * This event represents a passive skill level update.
 * It only allows modification of the target level (levelTo),
 * while the original level (levelFrom) remains immutable.
 *
 * TODO: Consider whether this event should be made cancellable.
 */
public class SkillLevelUpdateEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Skill skill;
    private final int originalLevel;
    private int targetLevel;

    public SkillLevelUpdateEvent(Player player, Skill skill, int originalLevel, int targetLevel) {
        this.player = player;
        this.skill = skill; 
        this.originalLevel = originalLevel;
        this.targetLevel = targetLevel;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public int getOriginalLevel() {
        return this.originalLevel;
    }

    public int getTargetLevel() {
        return this.targetLevel;
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
}
