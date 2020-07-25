package com.gmasterhd.sbxpprogress;

import com.gmasterhd.sbxpprogress.utils.SkillXP;
import com.gmasterhd.sbxpprogress.utils.Toolbox;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkyBlockXPProgress {
	public static String apiKey;
	public static String name;
	public static String file;
	
	public static int slayerXP_need;
	
	public static int[] levels = new int[] {
			0,
			0,
			0,
			0,
			0,
			0,
			0,
			0,
			0
	};
	
	public static void main(String[] args) {
		for(String arg: args) {
			if(arg.startsWith("apiKey:")) {
				apiKey = arg.substring(arg.indexOf(':') + 1);
			} else if(arg.startsWith("name:")) {
				name = arg.substring(arg.indexOf(':') + 1);
			} else if(arg.startsWith("alchemy:")) {
				levels[0] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("combat:")) {
				levels[1] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("enchanting:")) {
				levels[2] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("farming:")) {
				levels[3] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("fishing:")) {
				levels[4] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("foraging:")) {
				levels[5] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("mining:")) {
				levels[6] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("taming:")) {
				levels[7] = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			} else if(arg.startsWith("file:")) {
				file = arg.substring(arg.indexOf(':') + 1);
			} else if(arg.startsWith("slayerXP:")) {
				slayerXP_need = Integer.parseInt(arg.substring(arg.indexOf(':') + 1));
			}
		}
		
		System.out.println("Name: " + name);
		System.out.println("apiKey: " + apiKey);
		
		levels[8] = (levels[0] + levels[1] + levels[2] + levels[3] + levels[4] + levels[5] + levels[6] + levels[7]) / 8;
		
		System.out.println("APIKey: " + apiKey);
		
		JsonObject sbStats = Toolbox.getSkyBlockStats(name);
		
		int slayerXP = 0;

		SkillXP alchemy = SkillXP.getLevelByXP(sbStats.get("experience_skill_alchemy").getAsDouble());
		SkillXP combat = SkillXP.getLevelByXP(sbStats.get("experience_skill_combat").getAsDouble());
		SkillXP enchanting = SkillXP.getLevelByXP(sbStats.get("experience_skill_enchanting").getAsDouble());
		SkillXP farming = SkillXP.getLevelByXP(sbStats.get("experience_skill_farming").getAsDouble());
		SkillXP fishing = SkillXP.getLevelByXP(sbStats.get("experience_skill_fishing").getAsDouble());
		SkillXP foraging = SkillXP.getLevelByXP(sbStats.get("experience_skill_foraging").getAsDouble());
		SkillXP mining = SkillXP.getLevelByXP(sbStats.get("experience_skill_mining").getAsDouble());
		SkillXP taming = SkillXP.getLevelByXP(sbStats.get("experience_skill_taming").getAsDouble());
		
		JsonObject slayers = sbStats.getAsJsonObject("slayer_bosses");
		slayerXP = slayers.getAsJsonObject("zombie").get("xp").getAsInt() + slayers.getAsJsonObject("spider").get("xp").getAsInt() + slayers.getAsJsonObject("wolf").get("xp").getAsInt();
		
		System.out.println("AVG: " + Toolbox.getSkillAVG(alchemy, combat, enchanting, farming, fishing, foraging, mining, taming));
		
		List<String> lines = new ArrayList<>();
		lines.add("Alchemy: " + alchemy.level + " / " + levels[0]);
		lines.add("Combat: " + combat.level + " / " + levels[1]);
		lines.add("Enchanting: " + enchanting.level + " / " + levels[2]);
		lines.add("Farming: " + farming.level + " / " + levels[3]);
		lines.add("Fishing: " + fishing.level + " / " + levels[4]);
		lines.add("Foraging: " + foraging.level + " / " + levels[5]);
		lines.add("Mining: " + mining.level + " / " + levels[6]);
		lines.add("Taming: " + taming.level + " / " + levels[7]);
		lines.add("");
		lines.add("AVG: " + Toolbox.getSkillAVG(alchemy, combat, enchanting, farming, fishing, foraging, mining, taming) + " / " + levels[8]);
		
		String slayerXPString = "" + (float)slayerXP / (float)slayerXP_need * 100;
		slayerXPString = slayerXPString.substring(0, slayerXPString.indexOf(".") + 3);
		
		lines.add("SlayerXP: " + slayerXP + " / " + (int)slayerXP_need + " (" + slayerXPString + "%)");
		
		for(String s: lines) {
			System.out.println(s);
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			
			for(String str: lines) {
				writer.write(str + System.lineSeparator());
			}
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
