package com.akabex86.features.skilllevel.listeners;

import com.akabex86.features.skilllevel.PlayerSkills;
import com.akabex86.features.skilllevel.Skill;
import com.akabex86.features.skilllevel.SkillCategory;
import com.google.gson.Gson;

public class Test {

	public static void main(String[] args) {
		String json = "{skillLevel\":\"{\"map\":{\"COMBAT\":{\"level\":2,\"xp\":103}}}}";
		
		String secondJson = "{\"map\":{\"COMBAT\":{\"level\":2,\"xp\":106}}}";
		
		
		new Gson().fromJson(secondJson, PlayerSkills.class);
		
		PlayerSkills playerSkills = new PlayerSkills();
		
		Skill combat = playerSkills.get(SkillCategory.COMBAT);
		combat.setLevel(2);
		combat.setXp(106);
		
		
		System.out.println(new Gson().toJson(playerSkills));
	}
	
}
