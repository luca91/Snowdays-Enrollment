package com.snowdays_enrollment.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Settings {
	
	private int maxParticipantsPerGroup;
	private int maxExternals;
	private int maxInternals;
	private Map<String, String> badgeFiles;
	private Map<String, Integer> peoplePerCountry;
	private String startExternalsEnrollment;
	
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
	
	public void setPeoplePerCountry(String country, int nr){
		peoplePerCountry.put(country, nr);
	}
	
	public int getPeoplePerCountry(String country){
		return peoplePerCountry.get(country);
	}
	
	public void setAllPeoplePerCountries(Map<String, Integer> m){
		peoplePerCountry = m;
	}
	
	public void setStartExternalsEnrollment(String date){
		startExternalsEnrollment = date;
	}
	
	public String getStartExternalsEnrollment(){
		return this.startExternalsEnrollment;
	}
	
	public void parseValues(Map<String, String> toParse){
		String[] keys = (String[]) toParse.keySet().toArray();
		for(int i = 0; i < toParse.size(); i++){
			String name =  (String) Array.get(keys, i);
			String prefix = name.substring(0,4);
			if(!prefix.equals("badge") || !prefix.equals("countr")){
				switch (name){
				case "maxParticipantsPerGroup":
					setMaxParticipantsPerGroup(Integer.parseInt(toParse.get(name)));
					break;
				case "maxExternals":
					setMaxExternals(Integer.parseInt(toParse.get(name)));
					break;
				case "maxInternals":
					setMaxInternals(Integer.parseInt(toParse.get(name)));
					break;
				case "startExternalsEnrollment":
					setStartExternalsEnrollment(toParse.get(name));
					break;
				}
			}
			else if (prefix.equals("badge")){
				prefix = name.substring(5, name.length()-1);
				addBadgeFile(prefix, toParse.get("badge"+prefix));
			}
			else{
				prefix = name.substring(7, name.length()-1);
				setPeoplePerCountry(prefix, Integer.parseInt(toParse.get(name.substring(0, 6)+prefix)));
			}
		}
	}
}