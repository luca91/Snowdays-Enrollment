package com.snowdays_enrollment.model;

public class Country {
	
	private String name;
	private int maxPeople;
	private int actualPeople;
	
	public void setName(String n){
		name = n;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setMaxPeople(int mp){
		maxPeople = mp;
	}
	
	public int getMaxPeople(){
		return this.maxPeople;
	}
	
	public void setActualPeople(int ap){
		actualPeople = ap;
	}
	
	public int getActualPeople(){
		return this.actualPeople;
	}

}
