package com.snowdays_enrollment.model;

public class RegistrationUniBz {
	
	private String email;
	private String name;
	private String surname;
	private String status;
	private String group;
	private String link;
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getSurname(){
		return this.surname;
	}
	
	public void setStatus(String s){
		status = s;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setGroup(String id){
		group = id;
	}
	
	public String getGroup(){
		return this.group;
	}
	
	public void setLink(String l){
		link = l;
	}
	
	public String getLink(){
		return this.link;
	}

}
