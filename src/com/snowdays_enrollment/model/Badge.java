package com.snowdays_enrollment.model;

import java.awt.Image;

/**
 * This class contains the data for constructing the final badge for the events.
 * @author Luca Bellettati	
 *
 */
public class Badge {
	
	/**
	 * @uml.property  name="firstName"
	 */
	private String firstName;
	/**
	 * @uml.property  name="lastName"
	 */
	private String lastName;
	/**
	 * @uml.property  name="photo"
	 */
	private Image photo;
	/**
	 * @uml.property  name="group"
	 */
	private int group;

	/**
	 * Default constructor.
	 */
	public Badge() { }
	
	/**
	 * Constructor with given data.
	 * @param firstName String
	 * @param lastName String
	 * @param photo String
	 * @param group in
	 */
	public Badge(String firstName, String lastName, Image photo, int group){
		this.firstName = firstName;
		this.lastName = lastName;
		this.photo = photo;
		this.group = group;
	}
	
	/**
	 * It return the first name contained in this object.
	 * @return  String - the first name.
	 * @uml.property  name="firstName"
	 */
	public String getFirstName(){
		return this.firstName;
	}
	
	/**
	 * It sets the last name with the string passed. 
	 * @param fname  String - the first name to set
	 * @uml.property  name="firstName"
	 */
	public void setFirstName(String fname){
		firstName = fname;
	}
	
	/**
	 * It return the last name contained in this object.
	 * @return  String - the last name
	 * @uml.property  name="lastName"
	 */
	public String getLastName(){
		return this.lastName;
	}
	
	/**
	 * It sets the last name with the string passed.
	 * @param lname  String - the last name to set
	 * @uml.property  name="lastName"
	 */
	public void setLastName(String lname){
		lastName = lname;
	}
	
	/**
	 * It return the photo of the participant
	 * @return  Image - the photo of the badge
	 * @uml.property  name="photo"
	 */
	public Image getPhoto(){
		return this.photo;
	}
	
	/**
	 * It sets the photo with the one passed.
	 * @param photo  Image
	 * @uml.property  name="photo"
	 */
	public void setPhoto(Image photo){
		this.photo = photo;
	}
	
	/**
	 * It returns the group to which the person belong.
	 * @return  int - the group id
	 * @uml.property  name="group"
	 */
	public int getGroup(){
		return this.group;
	}
	
	/**
	 * It sets the group of the person with the int passed.
	 * @param group  int
	 * @uml.property  name="group"
	 */
	public void setGroup(int group){
		this.group = group;
	}

}
