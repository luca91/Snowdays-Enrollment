package com.snowdays_enrollment.model;

public class RegistrationUniBz {
	
	private String time;
	private Participant p;
	private boolean insertionComplete;
	
	private void setTime(String time){
		this.time = time;
	}
	
	private String getTime(){
		return this.time;
	}
	
	private void setParticipant(Participant p){
		this.p = p;
	}
	
	private Participant getPartcipant(){
		return this.p;
	}
	
	private void setInsertionComplete(boolean b){
		insertionComplete = b;
	}
	
	public boolean getInsertionComplete(){
		return this.insertionComplete;
	}

}
