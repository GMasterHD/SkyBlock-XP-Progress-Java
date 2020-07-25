package com.gmasterhd.sbxpprogress.utils;

import java.util.ArrayList;
import java.util.List;

public class SkillXP {
	public int level;
	public double progress;
	public int nextLevel;
	
	public SkillXP(int level, double progress, int nextLevel) {
		this.level = level;
		this.progress = progress;
		this.nextLevel = nextLevel;
	}
	
	public static int combatXPPerLevel[] = {
			0,
			50,
			125,
			300,
			500,
			750,
			1000,
			1500,
			2000,
			3500,
			5000,
			7500,
			10000,
			15000,
			20000,
			30000,
			50000,
			75000,
			100000,
			200000,
			300000,
			400000,
			500000,
			600000,
			700000,
			800000,
			900000,
			1000000,
			1100000,
			1200000,
			1300000,
			1400000,
			1500000,
			1600000,
			1700000,
			1800000,
			1900000,
			2000000,
			2100000,
			2200000,
			2300000,
			2400000,
			2500000,
			2600000,
			2750000,
			2900000,
			3100000,
			3400000,
			3700000,
			4000000
	};
	
	public static SkillXP getLevelByXP(double xp) {
		int level = 0;
		double exp = xp;
		int nextLevel = 0;
		double lastEXP = 0;
		for(int i : combatXPPerLevel) {
			exp -= i;
			if(exp >= 0) {
				lastEXP = exp;
				++level;
				nextLevel = i;
			}
		}
		if(level == 50) {
			nextLevel = 0;
		}
		
		return new SkillXP(level, lastEXP, nextLevel);
	}
	public static int getXPByRequiredXP(int requiredXP, int xp) {
		int out = 0;
		for(int x = 0; x < combatXPPerLevel.length; ++x) {
			System.out.println("Out: " + out);
			
			if(combatXPPerLevel[x] == requiredXP) {
				x = 9999;
			}
			
			if(x < combatXPPerLevel.length) {
				out += combatXPPerLevel[x];
			}
		}
		System.out.println("Out: " + out);
		System.out.println("XP: " + (xp + out));
		
		return (out + xp);
	}
	
	@Override
	public String toString() {
		return "Level: " + level + ", Progress: " + progress + ", nextLevel: " + nextLevel;
	}
}
