//Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

package com.snowdays_enrollment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.User;



/**
* GroupDao is the class that performs actions on the Group of the database
* Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/
* @author Luca Barazzuol
*/
public class GroupDao {
	
	// commons logging references
	static Logger log = Logger.getLogger(GroupDao.class.getName());

    /**
	 * @uml.property  name="connection"
	 */
    private Connection connection;

    /**
     * Constructor with a Connection as parameter
     * used by JUnit
     * 
     * @param  c A connection object used to access database by test units
     */
    public GroupDao(Connection c){
    	connection = c;
    }
    
    /**
     * Constructor with no parameters
     * It initializes the connection to the database
     * 
     */
    public GroupDao() {
        Context initialContext;
		try {
			initialContext = new InitialContext();
	        Context envContext;
			try {
				envContext = (Context)initialContext.lookup("java:/comp/env");
		        DataSource ds = (DataSource)envContext.lookup("jdbc/snowdays_enrollment");
		        connection = ds.getConnection();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    

    /**
     * Add a record to the table
     * 
     * @param id_event The event for which the group is created
     * @param aRecord A record
     */
    public void addRecord(Group aRecord) {
    	log.trace("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into snowdays_enrollment.groups(group_id, group_name, group_referent_id, "
                    		+ "group_country, group_is_blocked, group_actual_participants_number, "
                    		+ "group_badge_type, group_saturday, group_max_participants) "
                    		+ "values(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, aRecord.getId());
            preparedStatement.setString(2, aRecord.getName());
            preparedStatement.setInt(3, aRecord.getGroupReferentID());
            preparedStatement.setString(4, aRecord.getCountry());
            preparedStatement.setBoolean(5, aRecord.isBlocked());
            preparedStatement.setInt(6, aRecord.getActualParticipantNumber());
            preparedStatement.setString(7, aRecord.getBadgeType());
            preparedStatement.setString(8, aRecord.getSnowvolley());
            preparedStatement.setInt(9, aRecord.getGroupMaxNumber());
            UserDao uDao = new UserDao();
            User u = uDao.getUserById(aRecord.getGroupReferentID());
            setAssignedReferent(u.getUsername());
            u.setGroup(aRecord.getName());
            uDao.updateUser(u);
        	log.debug("add record");
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Delete a record from table using its id
     * 
     * @param id It is the id of the record to delete
     */
    public void deleteRecord(int id) {
    	log.trace("START");
        try {
        	Group g = getRecordById(id);
        	UserDao uDao = new UserDao();
        	User u = uDao.getUserById(g.getGroupReferentID());
        	PreparedStatement preparedStatement = connection
        			.prepareStatement("delete from registrations where registration_participant_group_id=?");
        	preparedStatement.setInt(1, id);
        	preparedStatement.executeUpdate();
        	preparedStatement = connection
             		.prepareStatement("delete from participants where participant_group_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection
                    .prepareStatement("delete from snowdays_enrollment.groups where group_id=?");
            preparedStatement.setInt(1, id);
            log.debug(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update users set user_group = NULL where user_id=?");
            preparedStatement.setInt(1, u.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update roles set group_assigned = FALSE where user_username=?");
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }


    
    /**
     * Update the fields of a record
     * 
     * @param aRecord The record to update
     */
    public void updateRecord(Group aRecord) {
    	log.trace("START");
    	log.debug(aRecord.toString());
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update snowdays_enrollment.groups set "
                    		+ "group_name=?, group_referent_id=?, "
                    		+ "group_max_participants=?, group_country=?, group_is_blocked=?, group_actual_participants_number=?, "
                    		+ "group_first_participant_registered_id=?, group_badge_type=?, group_saturday=? " +
                            "where group_id=?");
            
            preparedStatement.setInt(10, aRecord.getId());
            preparedStatement.setString(1, aRecord.getName());
            preparedStatement.setInt(2, aRecord.getGroupReferentID());
            preparedStatement.setInt(3, aRecord.getGroupMaxNumber());
            preparedStatement.setString(4, aRecord.getCountry());
            preparedStatement.setBoolean(5, aRecord.isBlocked());
            preparedStatement.setInt(6, aRecord.getActualParticipantNumber());
            preparedStatement.setInt(7, aRecord.getFirstParticipantRegistered());
            preparedStatement.setString(8, aRecord.getBadgeType());
            preparedStatement.setString(9, aRecord.getSnowvolley());
        	log.debug("Update done");
        	preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }
    
    /**
     * Returns the list of all records stored in the table Group
     * 
     * 
     * @return List<Group> List of objects Group
     */
    public List<Group> getAllRecords() {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
    	UserDao uDao = new UserDao();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from snowdays_enrollment.groups");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("group_id"));
                aRecord.setName(rs.getString("group_name"));    
                aRecord.setGroupReferentID(rs.getInt("group_referent_id"));
                aRecord.setGroupMaxNmber(rs.getInt("group_max_participants"));
                aRecord.setCountry(rs.getString("group_country"));
                aRecord.setBlocked(rs.getBoolean("group_is_blocked"));
                aRecord.setActualParticipantNumber(rs.getInt("group_actual_participants_number"));
                aRecord.setBadgeType(rs.getString("group_badge_type"));
                aRecord.setSnowvolley(rs.getString("group_saturday"));
                User u = uDao.getUserById(aRecord.getGroupReferentID());
                aRecord.setGroupReferentData(u.getFname() + " " + u.getLname());
                aRecord.setFirstParticipantRegisteredID(rs.getInt("group_first_participant_registered_id"));
                list.add(aRecord);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return list;
    }
    /**
     * Returns the list of all records stored in the table Group editable by a id_group_referent
     * 
     * @param id_group_referent user id to which belong the groups
     * @return List<Group> List of objects Group
     */
    public List<Group> getRecordsByGroupReferentID(int groupReferentID) {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from groups" + 
                    					" where group_referent_id = " + groupReferentID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("group_id"));
                aRecord.setName(rs.getString("group_name"));    
                aRecord.setGroupReferentID(rs.getInt("group_referent_id"));
                aRecord.setGroupMaxNmber(rs.getInt("group_max_participants"));
                aRecord.setCountry(rs.getString("group_country"));
                aRecord.setBlocked(rs.getBoolean("group_is_blocked"));
                aRecord.setActualParticipantNumber(rs.getInt("group_actual_participants_number"));
                aRecord.setBadgeType(rs.getString("group_badge_type"));
                aRecord.setSnowvolley(rs.getString("group_saturday"));
                list.add(aRecord);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return list;
    } 
    
    /**
     * Returns the record passing its id
     * 
     * @param id Identifier of the record to get
     * @return group An object Group
     */
    public Group getRecordById(int id) {
    	log.trace("START");
    	Group aRecord = new Group();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from snowdays_enrollment.groups where group_id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	 aRecord.setId(rs.getInt("group_id"));
                 aRecord.setName(rs.getString("group_name"));    
                 aRecord.setGroupReferentID(rs.getInt("group_referent_id"));
                 aRecord.setGroupMaxNmber(rs.getInt("group_max_participants"));
                 aRecord.setCountry(rs.getString("group_country"));
                 aRecord.setBlocked(rs.getBoolean("group_is_blocked"));
                 aRecord.setActualParticipantNumber(rs.getInt("group_actual_participants_number"));
                 aRecord.setBadgeType(rs.getString("group_badge_type"));
                 aRecord.setSnowvolley(rs.getString("group_saturday"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return aRecord;
    }
    
    /**
     * Return a list of user that can modify the groups identified by the passed id 
     * 
     * @param anId_group an id of a group
     * @return List<Integer> List of id of users 
     */
    public List<Integer>  canBeChangedBy(int anId_group) {
    	log.trace("START");
        List<Integer> listOfId = new ArrayList<Integer>();
        try {
        	
        	//look for group_referent
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT group_referent_id " +
                    					" FROM snowdays_enrollment.groups" +
                    					" WHERE group_id = ?");
            preparedStatement.setInt(1, anId_group);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	log.debug("id_group_referent: " + rs.getInt("group_referent_id"));
                listOfId.add(rs.getInt("group_referent_id"));
            }
            
            //look admins
            preparedStatement = connection
                    .prepareStatement("SELECT id " +
                    					" FROM snowdays_enrollment.users, snowdays_enrollment.roles" +
                    					" WHERE snowdays_enrollment.users.user_username = snowdays_enrollment.roles.user_username" +
                    					" AND snowdays_enrollment.roles.role_name = 'admin'");
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	log.debug("id_admin:" + rs.getInt("user_id"));
                listOfId.add(rs.getInt("user_id"));
            }
            
            for (int  i = 0; i < listOfId.size(); i++){
            	log.debug("listOfId: " + listOfId.get(i));
            }
            rs.close();
            preparedStatement.close();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	return listOfId;
    }
    
    /**
     * Return the number of participant enrolled in the group 
     * 
     * @param anId_group an id of a group
     * @return int number of actual participant enrolled in the group 
     */
    public int  getNrEnrolledParticipant(int anId_group) {
    	log.trace("START");
    	int nrEnrolledParticipant = 0;
        try {	
        	//look for group_referent
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT group_actual_participants_number" +
                    					" FROM groups" +
                    					" WHERE group_id = ?");
            preparedStatement.setInt(1, anId_group);
            ResultSet rs = preparedStatement.executeQuery();

            
            
            if (rs.next()) {
            	nrEnrolledParticipant = rs.getInt("group_actual_participants_number");
            	log.debug("nrEnrolledParticipant: " + nrEnrolledParticipant);

            }
            rs.close();
            preparedStatement.close();

                      
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	return nrEnrolledParticipant;
    }    
    
    public void approve(int id, boolean b){
    	log.trace("START");
   	 try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("update groups set group_approved=? where group_id=?");
            preparedStatement.setBoolean(1, b);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }
    
    public void setAssignedReferent(String username){
    	log.trace("START");
      	 try {
               PreparedStatement preparedStatement = connection.
                       prepareStatement("update roles set group_assigned=? where user_username=?");
               preparedStatement.setBoolean(1, true);
               preparedStatement.setString(2, username);
               preparedStatement.executeUpdate();
               preparedStatement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       	log.trace("END");
    }
    
    public int getActualParticipantByGroup(int id){
    	log.trace("START");
    	int res = 0;
    	try {
    		PreparedStatement stmt = connection
    				.prepareStatement("select group_actual_participants_number from groups where group_id=?");
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		if(rs.next())
    			res = rs.getInt("group_actual_participants_number");
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    	return res;
    }
    
    public void updateActualParticipantNr(int id, int update){
    	log.trace("START");
    	log.debug("id: "+id);
    	log.debug("update: "+update);
    	try{
    		int oldNr = getActualParticipantByGroup(id);
    		PreparedStatement stmt = connection
    				.prepareStatement("update groups set group_actual_participants_number=? where group_id=?");
    		if(update > 0){
    			oldNr += 1;
    			stmt.setInt(1, oldNr);
    			System.out.println(oldNr);
    		}
    		else{
    			oldNr -= 1;
    			stmt.setInt(1, oldNr);
    			System.out.println(oldNr);
    		}
    		stmt.setInt(2, id);
    		log.debug(stmt.toString());
    		stmt.executeUpdate();
    		stmt.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    }
    
    public int getGroupByIDGroupReferent(int id){
    	log.trace("START");
    	int res = -1;
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("select group_id from groups where group_referent_id=?");
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		if(rs.next()){
    			res = rs.getInt("group_id");
    			rs.close();
    			stmt.close();
    		}
    	}
    	
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	return res;
    }
    
    public ArrayList<String> getAllGroupsNames(){
    	log.trace("START");
    	ArrayList<String> res = new ArrayList<String>();
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("select group_name from groups");
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		while(rs.next()){
    			res.add(rs.getString("group_name"));
    		}
    		rs.close();
			stmt.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    		return res;
    }
    
    public Group getGroupByName(String name){
    	log.trace("START");
    	UserDao uDao = new UserDao();
    	Group aRecord = new Group();
    	try {
    		PreparedStatement stmt = connection
    				.prepareStatement("select * from groups where group_name=?");
    		stmt.setString(1, name);
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		if(rs.next()){
                aRecord.setId(rs.getInt("group_id"));
                aRecord.setName(rs.getString("group_name"));    
                aRecord.setGroupReferentID(rs.getInt("group_referent_id"));
                aRecord.setGroupMaxNmber(rs.getInt("group_max_participants"));
                aRecord.setCountry(rs.getString("group_country"));
                aRecord.setBlocked(rs.getBoolean("group_is_blocked"));
                aRecord.setActualParticipantNumber(rs.getInt("group_actual_participants_number"));
                aRecord.setBadgeType(rs.getString("group_badge_type"));
                aRecord.setSnowvolley(rs.getString("group_saturday"));
                User u = uDao.getUserById(aRecord.getGroupReferentID());
                aRecord.setGroupReferentData(u.getFname() + " " + u.getLname());
                aRecord.setFirstParticipantRegisteredID(rs.getInt("group_first_participant_registered_id"));
    		}
    		rs.close();
    		stmt.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    	return aRecord;
    }
    
    public void setFirstRegistered(int id, int groupID){
    	log.trace("START");
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("update groups set group_first_participant_registered_id=? where group_id=?");
    		stmt.setInt(1, id);
    		stmt.setInt(2, groupID);
    		stmt.executeUpdate();
    		stmt.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    }
    
    public ArrayList<String> getCountries(){
    	log.trace("START");
    	ArrayList<String> result = new ArrayList<String>();
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("select country_name from countries");
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		while(rs.next()) {
				result.add(rs.getString("country_name"));
			}
    		rs.close();
    		stmt.close();
    	}
    	catch(SQLException e){
			e.printStackTrace();
		}
    	log.trace("END");
		return result;
    }
    
    public void blockAll(boolean block){
    	log.trace("START");
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("update groups set group_is_blocked=?");
    		stmt.setBoolean(1, block);
    		stmt.executeUpdate();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    }
    
    public void updateMaxParticipants(int nr){
    	log.trace("START");
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("update groups set group_max_participants=?");
    		stmt.setInt(1, nr);
    		stmt.executeUpdate();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    }
}