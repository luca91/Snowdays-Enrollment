package com.snowdays_enrollment.model;

public class RegistrationExternal {
	
	private String groupName;
	private String time;
	private int participantID;
	private int groupID;
	
	public void setGroupName(String name){
		groupName = name;
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public void setParticipantID(int id){
		participantID = id;
	}
	
	public int getParticipantID(){
		return this.participantID;
	}
	
	public void setGroupID(int id){
		groupID = id;
	}
	
	public int getGroupID(){
		return this.groupID;
	}
}
