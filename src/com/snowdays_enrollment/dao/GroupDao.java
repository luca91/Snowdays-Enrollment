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
		        DataSource ds = (DataSource)envContext.lookup("jdbc/ems");
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
    public void addRecord(int id_event, Group aRecord) {
    	log.trace("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into ems.group(id_event,id_group_referent, name, max_group_number,blocked) values (?,?, ?, ?, ? )");
            preparedStatement.setInt(1, id_event);
            preparedStatement.setInt(2, aRecord.getId_group_referent());
            preparedStatement.setString(3, aRecord.getName());
            preparedStatement.setInt(4, aRecord.getMax_group_number());
            preparedStatement.setBoolean(5, aRecord.isBlocked());
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
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from ems.group where id=?");
            preparedStatement.setInt(1, id);
            log.debug(preparedStatement.toString());
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
                    .prepareStatement("update ems.group set id_event=?, id_group_referent=?, name=?, max_group_number=?,blocked=? " +
                            "where id=?");
            
            preparedStatement.setInt(1, aRecord.getId_event());
            preparedStatement.setInt(2, aRecord.getId_group_referent());
            preparedStatement.setString(3, aRecord.getName());
            preparedStatement.setInt(4, aRecord.getMax_group_number());
            preparedStatement.setBoolean(5, aRecord.isBlocked());
            
            preparedStatement.setInt(6, aRecord.getId());
            preparedStatement.executeUpdate();
        	log.debug("update done");
        	
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Returns the list of all records stored in the table Group and associated with an event
     * 
     * @param id_event Event to which belong the groups
     * @return List<Group> List of objects Group
     */
    public List<Group> getAllRecordsById_event(int id_event) {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from ems.group where id_event=?");
            preparedStatement.setInt(1, id_event);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));                
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
     * Returns the list of all records stored in the table Group and associated with an event and an event_manager
     * 
     * @param idManager id manager for the event
     * @param id_event id of an event
     * @return List<Group> List of objects Group
     */
    public List<Group> getAllRecordsById_manager(int idManager, int id_event) {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * " +
                    				" from ems.group, ems.event " +
                    				" where ems.group.id_event=ems.event.id" +
                    				" and ems.event.id_manager=?" +
                    				" and ems.group.id_event = ?");
            preparedStatement.setInt(1, idManager);
            preparedStatement.setInt(2, id_event);
            log.debug(preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));                
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
     * Returns the list of all records stored in the table Group and associated with an id_group_referent
     * 
     * @param id_group_referent an it of an id_group_referent
     * @return List<Group> List of objects Group
     */
    public List<Group> getAllRecordsById_group_referent(int id_group_referent) {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * " +
                    				" from ems.group" +
                    				" where ems.group.id_group_referent=?");
            preparedStatement.setInt(1, id_group_referent);
            log.debug(preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));                
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
     * Returns the list of all records stored in the table Group
     * 
     * 
     * @return List<Group> List of objects Group
     */
    public List<Group> getAllRecords() {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from ems.group");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));                
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
    public List<Group> getRecordsById_group_referent(int id_group_referent) {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from ems.group" + 
                    					" where ems.group.id_group_referent = " + id_group_referent);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));                
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
     * Returns the list of all records stored in the table Group editable by a id_manager
     * 
     * @param id_manager Id manager to which belong the groups
     * @return List<Group> List of objects Group
     */
    public List<Group> getRecordsById_manager(int id_manager) {
        log.trace("START");
    	List<Group> list = new ArrayList<Group>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * " +
                    					" from ems.group, ems.event" + 
                    					" where ems.group.id_event = ems.event.id" +
                    					" and ems.event.id_manager = " + id_manager);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Group aRecord = new Group();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));                
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
                    prepareStatement("select * from ems.group where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_event(rs.getInt("id_event"));
                aRecord.setId_group_referent(rs.getInt("id_group_referent"));
                aRecord.setName(rs.getString("name"));
                aRecord.setMax_group_number(rs.getInt("max_group_number"));
                aRecord.setBlocked(rs.getBoolean("blocked"));
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
                    .prepareStatement("SELECT id_group_referent, id_event " +
                    					" FROM ems.group" +
                    					" WHERE id = ?");
            preparedStatement.setInt(1, anId_group);
            ResultSet rs = preparedStatement.executeQuery();

            int id_event = 0;
            if (rs.next()) {
            	log.debug("id_group_referent: " + rs.getInt("id_group_referent"));
                listOfId.add(rs.getInt("id_group_referent"));
                id_event = rs.getInt("id_event");
            }

            //look for id_event
            preparedStatement = connection
                    .prepareStatement("SELECT id_manager " +
                    					" FROM event" +
                    					" WHERE id = ?");
            preparedStatement.setInt(1, id_event);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	log.debug("id_manager: " + rs.getInt("id_manager"));
                listOfId.add(rs.getInt("id_manager"));
            }
            
            //look admins
            preparedStatement = connection
                    .prepareStatement("SELECT id " +
                    					" FROM ems.user, ems.user_role" +
                    					" WHERE ems.user.email = ems.user_role.email" +
                    					" AND ems.user_role.ROLE_NAME = 'admin'");
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	log.debug("id_admin:" + rs.getInt("id"));
                listOfId.add(rs.getInt("id"));
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
                    .prepareStatement("SELECT count(*) AS count" +
                    					" FROM participant" +
                    					" WHERE id_group = ?");
            preparedStatement.setInt(1, anId_group);
            ResultSet rs = preparedStatement.executeQuery();

            
            
            if (rs.next()) {
            	nrEnrolledParticipant = rs.getInt("count");
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
}