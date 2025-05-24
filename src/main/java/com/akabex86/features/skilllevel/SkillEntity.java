package com.akabex86.features.skilllevel;

public abstract class SkillEntity implements ISkill{

	private int maxLevel, level;
	private long xp, xpForNextLevel;
	private SkillCategory skillCategory;
	
	public SkillEntity(SkillCategory skillCategory) {
		this.level = 1;
		this.maxLevel = 0;
		
		this.xp = 0;
		this.xpForNextLevel = 0;
		
		this.skillCategory = skillCategory;
	}
	
	@Override
	public int getLevel() {
		return this.level;
	}
	@Override
	public int getMaxLevel() {
		return this.maxLevel;
	}
	@Override
	public Long getXP() {
		return this.xp;
	}
	@Override
	public Long getXPForNextLevel() {
		return this.xpForNextLevel;
	}
	
	@Override
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	@Override
	public void setXP(Long xp) {
		this.xp = xp;
	}
	@Override
	public void setXPForNextLevel(Long xp) {
		this.xpForNextLevel = xp;
	}
	
	@Override
	public SkillCategory getSkillCategory() {
		return this.skillCategory;
	}
}
