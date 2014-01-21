//Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

package com.snowdays_enrollment.model;

import java.io.Serializable;

/**
* User is the JavaBean representing the record of the table User
* 
* @author Luca Barazzuol
*/
public class User implements Serializable {
   
	private static final long serialVersionUID = 1L;

	/**
	 * @uml.property  name="id"
	 */
	private int id;
	/**
	 * @uml.property  name="fname"
	 */
	private String fname;
	/**
	 * @uml.property  name="lname"
	 */
	private String lname;
	/**
	 * @uml.property  name="date_of_birth"
	 */
	private String date_of_birth;
	/**
	 * @uml.property  name="password"
	 */
	private String password;
	/**
	 * @uml.property  name="email"
	 */
	private String email;
	/**
	 * @uml.property  name="role"
	 */
	private String role;
	/**
	 * @uml.property name="username"
	 */
	private String username;
	private String group;
	
	public User(){ }
	
	public User(String name, String surname, String dateOfBirth, String password, String email, String role){
		fname = name;
		lname = surname;
		date_of_birth = dateOfBirth;
		this.password = password;
		this.email = email;
		this.role = role;
		
	}
	
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
	 * @uml.property  name="password"
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password
	 * @uml.property  name="password"
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @uml.property  name="role"
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role
	 * @uml.property  name="role"
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	@Override
    public String toString() {
        return "User [id=" + id + ", fname=" + fname
                + ", lname=" + lname + ", password=" + password + ", email="
                + email + ", " + "role=" + role +"]";
    }
	
	public void setGroup(String groupName){
		group = groupName;
	}
	
	public String getGroup(){
		return this.group;
	}
}
