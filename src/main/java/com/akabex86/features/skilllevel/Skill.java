package com.akabex86.features.skilllevel;

public class Skill {
	
	public static int MAXLEVEL = 15;
	
	private int level;
	private int xp;
	public static final String version = "1.0";
	
	public Skill() {
		this.level = 1;
		this.xp = 0;
	}
	
	public void setLevel(int amount) {
		this.level = amount;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setXp(int amount) {
		this.xp = amount;
		checkLevelUp();
	}
	
	public int getXp() {
		return this.xp;
	}
	
	public int addLevel(int level) {
		return this.level += level;
	}
	
	public SkillUpdateResult addXp(int amount) {
	    int oldXp = xp;
	    int oldLevel = level;
	    xp += amount;
	    if(level < MAXLEVEL) checkLevelUp();
	    return new SkillUpdateResult(oldLevel, level, oldXp, getXpForNextLevel());
	}
	
	private void checkLevelUp() {
        while (level < MAXLEVEL && xp >= getXpForNextLevel()) {
            this.level++;
        }
    }

	private int getXpForNextLevel() {
	    double r = 1.25;
	    return (int) (100 * ((Math.pow(r, level) - 1) / (r - 1)));
	}
	
    @Override
    public String toString() {
    	return String.format("Level: %,d | Xp: %,d | XpForNextLevel: %,d", level, xp, getXpForNextLevel());
    }
    
}
