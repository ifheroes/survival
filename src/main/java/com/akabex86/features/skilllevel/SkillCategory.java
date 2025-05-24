package com.akabex86.features.skilllevel;

import com.akabex86.features.skilllevel.skills.SkillAgriculture;
import com.akabex86.features.skilllevel.skills.SkillCollector;
import com.akabex86.features.skilllevel.skills.SkillCombat;
import com.akabex86.features.skilllevel.skills.SkillMining;

public enum SkillCategory {

	AGRICULTURE("Agriculture", SkillAgriculture.class), MINING("Mining", SkillMining.class), COMBAT("Combat", SkillCombat.class), COLLECTOR("Collector", SkillCollector.class);
	
	final String name;
	final Class<? extends ISkill> skill;
	
	SkillCategory(String name, Class<? extends ISkill> skill) {
		this.name = name;
		this.skill = skill;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Class<? extends ISkill> getSkillClass() {
		return this.skill;
	}
}
