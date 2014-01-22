package com.snowdays_enrollment.model;

import java.util.ArrayList;
import java.sql.Date;

/**
* User is the JavaBean representing the record of the table Participant
* 
* @author Luca Barazzuol
*/
public class Participant {

	/**
	 * @uml.property  name="id"
	 */
	private int id;
	/**
	 * @uml.property  name="id_group"
	 */
	private int id_group;
	/**
	 * @uml.property  name="fname"
	 */
	private String fname;
	/**
	 * @uml.property  name="lname"
	 */
	private String lname;
	/**
	 * @uml.property  name="email"
	 */
	private String email;
	/**
	 * @uml.property  name="date_of_birth"
	 */
	private String date_of_birth;
	/**
	 * @uml.property  name="approved"
	 */
	private boolean approved;
	private int fridayProgram;
	private String intolerances;
	private String tShirtSize;
	private int rentalOption;
	private String birthday;
	private String photo;
	private String registrationTime;
	private String gender;
	private String groupName;
	private String studentID;
	
	
	
	//Getters and Setters
	/**
	 * @return
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return
	 * @uml.property  name="id_group"
	 */
	public int getId_group() {
		return id_group;
	}
	/**
	 * @param id_group
	 * @uml.property  name="id_group"
	 */
	public void setId_group(int id_group) {
		this.id_group = id_group;
	}
	/**
	 * @return
	 * @uml.property  name="fname"
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname
	 * @uml.property  name="fname"
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return
	 * @uml.property  name="lname"
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname
	 * @uml.property  name="lname"
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return
	 * @uml.property  name="email"
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email
	 * @uml.property  name="email"
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return
	 * @uml.property  name="date_of_birth"
	 */
	public String getDate_of_birth() {
		return date_of_birth;
	}
	/**
	 * @param date_of_birth
	 * @uml.property  name="date_of_birth"
	 */
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	/**
	 * @return
	 * @uml.property  name="approved"
	 */
	public boolean isApproved() {
		return approved;
	}
	/**
	 * @param approved
	 * @uml.property  name="approved"
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public void setFridayProgram(int program){
		this.fridayProgram = program;
	}
	
	public int getFridayProgram(){
		return this.fridayProgram;
	}
	
	public void setIntolerances(String intol){
		intolerances = intol;
	}
	
	public String getIntolerances(){
		return this.intolerances;
	}
	
	public void setTShirtSize(String c){
		tShirtSize = c;
	}
	
	public String getTShirtSize(){
		return this.tShirtSize;
	}
	
	public void setRentalOption(int opt){
		rentalOption = opt;
	}
	
	public int getRentalOption(){
		return this.rentalOption;
	}
		
	public void setBirthday(String d){
		birthday = d;
	}
	
	public String getBirthday(){
		return this.birthday;
	}
	
	public void setPhoto(String url){
		photo = url;
	}
	
	public String getPhoto(){
		return this.photo;
	}
	
	public void setRegistrationTime(String d){
		registrationTime = d;
	}
	
	public String getRegistrationTime(){
		return this.registrationTime;
	}
	
	public void setGender(String g){
		gender = g;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String intolerancesToString(){
		return intolerances.toString();
	}
	
	public void setGroupName(String name){
		groupName = name;
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public void setStudentID(String id){
		studentID = id;
	}
	
	public String getStudentID(){
		return this.studentID;
	}
}
