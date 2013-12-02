package com.snowdays_enrollment.model;

/**
* User is the JavaBean representing the record of the table Group
* 
* @author Luca Barazzuol
*/
public class Group {

	/**
	 * @uml.property  name="id"
	 */
	private int id;
	/**
	 * @uml.property  name="id_event"
	 */
	private int id_event;
	/**
	 * @uml.property  name="id_group_referent"
	 */
	private int id_group_referent;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="max_group_number"
	 */
	private int max_group_number;
	/**
	 * @uml.property  name="blocked"
	 */
	private boolean blocked;

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
	 * @uml.property  name="id_event"
	 */
	public int getId_event() {
		return id_event;
	}
	/**
	 * @param id_event
	 * @uml.property  name="id_event"
	 */
	public void setId_event(int id_event) {
		this.id_event = id_event;
	}

	/**
	 * @return
	 * @uml.property  name="id_group_referent"
	 */
	public int getId_group_referent() {
		return id_group_referent;
	}
	/**
	 * @param id_group_referent
	 * @uml.property  name="id_group_referent"
	 */
	public void setId_group_referent(int id_group_referent) {
		this.id_group_referent = id_group_referent;
	}

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 * @uml.property  name="max_group_number"
	 */
	public int getMax_group_number() {
		return max_group_number;
	}
	/**
	 * @param max_group_number
	 * @uml.property  name="max_group_number"
	 */
	public void setMax_group_number(int max_group_number) {
		this.max_group_number = max_group_number;
	}

	/**
	 * @return
	 * @uml.property  name="blocked"
	 */
	public boolean isBlocked() {
		return blocked;
	}
	/**
	 * @param blocked
	 * @uml.property  name="blocked"
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", id_event=" + id_event + ", id_group_referent=" + id_group_referent 
                + ", max_group_number=" + max_group_number + ", blocked=" + blocked  +"]";
    } 
	
	
}
