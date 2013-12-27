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
	 * @uml.property  name="id_group_referent"
	 */
	private int groupReferentID;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="max_group_number"
	 */
	private int groupMaxNumber;
	/**
	 * @uml.property  name="blocked"
	 */
	private boolean blocked;
	private String country;
	private int actualParticipantNumber;
	private String badgeType;
	private int firstParticipantRegisteredID;
	private boolean snowvolley;
	private boolean isApprove;
	private String groupReferentData;

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
	 * @uml.property  name="id_group_referent"
	 */
	public int getGroupReferentID() {
		return groupReferentID;
	}
	/**
	 * @param id_group_referent
	 * @uml.property  name="id_group_referent"
	 */
	public void setGroupReferentID(int id_group_referent) {
		this.groupReferentID = id_group_referent;
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
	public int getGroupMaxNumber() {
		return this.groupMaxNumber;
	}
	/**
	 * @param max_group_number
	 * @uml.property  name="max_group_number"
	 */
	public void setGroupMaxNmber(int max_group_number) {
		this.groupMaxNumber = max_group_number;
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
        return "User [id=" + id + ", id_group_referent=" + groupReferentID 
                + ", max_group_number=" + groupMaxNumber + ", blocked=" + blocked  +"]";
    } 
	
	public void setCountry(String country){
		this.country = country;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	public void setActualParticipantNumber(int number){
		actualParticipantNumber = number;
	}
	
	public int getActualParticipantNumber(){
		return this.actualParticipantNumber;
	}
	
	public void setBadgeType(String type){
		badgeType = type;
	}
	
	public String getBadgeType(){
		return this.badgeType;
	}
	
	public void setFirstParticipantRegisteredID(int p){
		firstParticipantRegisteredID = p;
	}
	
	public int getFirstParticipantRegistered(){
		return this.firstParticipantRegisteredID;
	}
	
	public boolean getSnowvolley(){
		return snowvolley;
	}
	
	public void setSnowvolley(boolean flag){
		snowvolley = flag;
	}
	
	public void setIsApproved(boolean b){
		isApprove = b;
	}
	
	public boolean getIsApproved(){
		return this.isApprove;
	}
	
	public void setGroupReferentData(String nameAndSurname){
		groupReferentData = nameAndSurname;
	}
	
	public String getGroupReferentData(){
		return this.groupReferentData;
	}
	
}
