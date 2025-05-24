package com.akabex86.features.skilllevel;

public interface ISkill {

	public int getLevel();
	public int getMaxLevel();
	
	public Long getXP();
	public Long getXPForNextLevel();
	
	public void setLevel(int level);
	public void setMaxLevel(int maxLevel);
	
	public void setXP(Long xp);
	public void setXPForNextLevel(Long xp);
	
	public SkillCategory getSkillCategory();
	
}
