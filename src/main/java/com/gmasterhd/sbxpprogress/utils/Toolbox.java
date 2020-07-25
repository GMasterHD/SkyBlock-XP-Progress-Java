package com.gmasterhd.sbxpprogress.utils;

import com.gmasterhd.sbxpprogress.SkyBlockXPProgress;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;
import java.util.stream.Collectors;

public class Toolbox {
	public static String getUUIDByUsername(String username) {
		return APIRequests.request("https://api.mojang.com/users/profiles/minecraft/" + username).get("id").getAsString();
	}
	
	public static String getSkyBlockProfileID(String username, String name) {
		JsonObject playerStats = APIRequests.request("https://api.hypixel.net/player?key=" + SkyBlockXPProgress.apiKey + "&uuid=" + getUUIDByUsername(username));
		List<String> keys = new ArrayList();
		JsonObject profiles = playerStats.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("SkyBlock").getAsJsonObject("profiles");
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(profiles.getAsString());
		JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
		for(Map.Entry<String, JsonElement> entry : entries) {
			keys.add(entry.getKey());
		}
		for(String k : keys) {
			String cuteName = profiles.getAsJsonObject(k).get("cute_name").getAsString();
			System.out.println(k + " ProfileName: " + cuteName);
			if(cuteName.equals(name)) {
				return k;
			}
		}
		
		return "";
	}
	public static String getSkyBlockProfileID(String username) {
		JsonObject playerStats = APIRequests.request("https://api.hypixel.net/player?key=" + SkyBlockXPProgress.apiKey + "&uuid=" + getUUIDByUsername(username));
		List<String> keys = new ArrayList();
		JsonObject player = playerStats.getAsJsonObject("player");
		JsonObject stats = player.getAsJsonObject("stats");
		JsonObject SkyBlock = stats.getAsJsonObject("SkyBlock");
		System.out.println("OBJECT: " + stats.toString());
		JsonObject profiles = SkyBlock.getAsJsonObject("profiles");
		Set<Map.Entry<String, JsonElement>> entries = profiles.entrySet();//will return members of your object
		for(Map.Entry<String, JsonElement> entry : entries) {
			System.out.println("Key: " + entry.getKey());
			keys.add(entry.getKey());
		}
		
		System.out.println(profiles.getAsJsonObject(keys.get(0)).get("cute_name").getAsString());
		return profiles.getAsJsonObject(keys.get(0)).get("profile_id").getAsString();
	}
	
	public static JsonObject getSkyBlockStats(String username) {
		System.out.println("Username: " + username + ", Key: " + SkyBlockXPProgress.apiKey + ", Profile: " + getSkyBlockProfileID(username));
		
		JsonObject skyblockstats = APIRequests.request("https://api.hypixel.net/skyblock/profile?key=" + SkyBlockXPProgress.apiKey + "&profile=" + getSkyBlockProfileID(username));
		JsonObject profile = skyblockstats.getAsJsonObject("profile");
		JsonObject members = profile.getAsJsonObject("members");
		JsonObject member = members.getAsJsonObject(getUUIDByUsername(username).replace("-", ""));
		
		System.out.println("Member: " + member.toString());
		return member;
	}
	public static JsonObject getSkyBlockStats(String username, String profileName) {
		JsonObject skyblockstats = APIRequests.request("https://api.hypixel.net/skyblock/profile?key=" + SkyBlockXPProgress.apiKey + "&profile=" + getSkyBlockProfileID(username, profileName));
		JsonObject profile = skyblockstats.getAsJsonObject("profile");
		JsonObject members = profile.getAsJsonObject("members");
		JsonObject member = members.getAsJsonObject(getUUIDByUsername(username).replace("-", ""));
		
		System.out.println("Member: " + member.toString());
		return member;
	}
	
	public static float getSkillAVG(SkillXP... skills) {
		float totalLvl = 0;
		
		for(SkillXP skill: skills) {
			totalLvl += skill.level;
		}
		
		return totalLvl / skills.length;
	}
}
