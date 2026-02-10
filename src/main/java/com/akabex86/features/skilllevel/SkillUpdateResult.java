package com.akabex86.features.skilllevel;

public record SkillUpdateResult(int oldLevel, int newLevel, int oldXp, int newXp, int xpToNextLevel) {
	
}
