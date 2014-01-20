package com.snowdays_enrollment.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {
	
	private int maxParticipantsPerGroup;
	private int maxExternals;
	private int maxInternals;
	private Map<String, String> badgeFiles;
	private Map<String, Country> cs = new HashMap<String, Country>();
	
	public void setMaxParticipantsPerGroup(int nr){
		maxParticipantsPerGroup = nr;
	}
	
	public int getMaxParticipantsPerGroups(){
		return this.maxParticipantsPerGroup;
	}
	
	public void setMaxExternals(int nr){
		maxInternals = nr;
	}
	
	public int getMaxExternals(){
		return this.maxExternals;
	}
	
	public void setMaxInternals(int nr){
		maxInternals = nr;
	}
	
	public int getMaxInternals(){
		return this.maxInternals;
	}
	
	public void addBadgeFile(String type, String path){
		badgeFiles.put(type, path);
	}
	
	public List<String> getBadgeFiles(){
		return (List<String>) badgeFiles.values();
	}
	
	public String getBadgeFile(String type){
		return badgeFiles.get(type);
	}
	
	public void setCountry(Country c){
		cs.put(c.getName(), c);
	}
	
	public Country getCountry(String name){
		return cs.get(name);
	}
	
	public void setCountries(List<Country> l){
		for(Country c: l.toArray(new Country[]{})){
			System.out.println(c.getName());
			cs.put(c.getName(), c);
		}
	}
}