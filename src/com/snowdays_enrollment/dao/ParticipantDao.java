//Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

package com.snowdays_enrollment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;

/**
* ParticipantDao is the class that performs actions on the table Participant of the database
* Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/
* @author Luca Barazzuol
*/
public class ParticipantDao {
	
	// commons logging references
	static Logger log = Logger.getLogger(ParticipantDao.class.getName());

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
    public ParticipantDao(Connection c){
    	connection = c;
    }
    
    /**
     * Constructor with no parameters
     * It initializes the connection to the database
     * 
     */
    public ParticipantDao(){
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
     * Add a record to table 
     * 
     * @param anId_group A group id
     * @param aRecord A participant
     */
    public void addRecord(int anId_group, Participant aRecord) {
    	log.trace("START");
        try {
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into participants(participant_name,"
                    		+ "participant_surname, participant_group_id, participant_gender, participant_friday_program,"
                    		+ "participant_intolerance, participant_t_shirt_size, participant_rental_option_id, "
                    		+ "participant_birthday, participant_id, participant_approved, participant_photo, participant_student_card) " +
                    					"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)");
            preparedStatement.setInt(10, aRecord.getId());
            preparedStatement.setString(1, aRecord.getFname());
            preparedStatement.setString(2, aRecord.getLname());
            preparedStatement.setInt(3, aRecord.getId_group());
            preparedStatement.setString(4, aRecord.getGender());
            preparedStatement.setInt(5, aRecord.getFridayProgram());
            preparedStatement.setString(6, aRecord.intolerancesToString());
            preparedStatement.setString(7, aRecord.getTShirtSize());
            preparedStatement.setInt(8, aRecord.getRentalOption());         
            preparedStatement.setString(9, aRecord.getDate_of_birth());
            preparedStatement.setBoolean(11, aRecord.isApproved());
            preparedStatement.setString(12, aRecord.getPhoto());
            preparedStatement.setString(13, aRecord.getStudentID());
            log.debug(preparedStatement.toString());
        	log.debug("addRecord Execute Update");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            GroupDao gDao = new GroupDao();
            gDao.updateActualParticipantNr(anId_group, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Delete a record using its id
     * 
     * @param id It is the id of the record to delete
     */
    public void deleteRecord(int id) {
    	log.trace("START");
        try {
        	PreparedStatement preparedStatement = connection
             		.prepareStatement("delete from registrations where registration_participant_id=?");
            preparedStatement.setInt(1, id);
            log.debug(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement = connection
                    .prepareStatement("delete from participants where participant_id=?");
            preparedStatement.setInt(1, id);
         
            GroupDao gDao = new GroupDao();
            log.debug("group id: "+getRecordById(id).getId_group());
            gDao.updateActualParticipantNr(getRecordById(id).getId_group(), -1);
            System.out.println("ecco qui");
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
    public void updateRecord(Participant aRecord) {
    	log.debug("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update participants " +
                    					"set " +
                    					"participant_group_id=?, " +
                    					"participant_name=?, " +
                    					"participant_surname=?, " +
                    					"participant_email=?, " +
                    					"participant_birthday=?," +
                      					"participant_approved=?, " +
                    					"participant_friday_program=?, "
                    					+ "participant_gender=?,"
                    					+ "participant_intolerance=?,"
                    					+ "participant_rental_option_id=?,"
                    					+ "participant_photo=?,"
                    					+ "participant_t_shirt_size=?,"
                    					+ "participant_student_card=?"+
                            			"where participant_id=?");
            log.debug(aRecord.getDate_of_birth()); 
            preparedStatement.setInt(1, aRecord.getId_group());
            preparedStatement.setString(2, aRecord.getFname());
            preparedStatement.setString(3, aRecord.getLname());
            preparedStatement.setString(4, aRecord.getEmail());            
            preparedStatement.setString(5, aRecord.getDate_of_birth());
            preparedStatement.setBoolean(6, aRecord.isApproved());
            preparedStatement.setInt(7, aRecord.getFridayProgram());
            preparedStatement.setString(8, aRecord.getGender());
            preparedStatement.setString(9, aRecord.intolerancesToString());
            preparedStatement.setInt(10, aRecord.getRentalOption());
            preparedStatement.setString(11, aRecord.getPhoto());
            preparedStatement.setString(12, aRecord.getTShirtSize());
            preparedStatement.setString(13, aRecord.getStudentID());
            preparedStatement.setInt(14, aRecord.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Returns the list of all records stored
     * 
     * @return List<User> List of objects Participant
     */
    public List<Participant> getAllRecords() {
        log.trace("START");
    	List<Participant> records = new ArrayList<Participant>();
    	GroupDao gDao = new GroupDao();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from participants");
            while (rs.next()) {
                Participant record = new Participant();
                record.setId(rs.getInt("participant_id"));
                record.setId_group(rs.getInt("participant_group_id"));
                record.setFname(rs.getString("participant_name"));
                record.setLname(rs.getString("participant_surname"));
                record.setEmail(rs.getString("participant_email"));
                record.setDate_of_birth(rs.getString("participant_birthday"));
                record.setApproved(rs.getBoolean("participant_approved"));
                record.setGender(rs.getString("participant_gender"));
                record.setFridayProgram(rs.getInt("participant_friday_program"));
//                record.setIntolerances(strings);
                record.setTShirtSize(rs.getString("participant_t_shirt_size"));
                record.setRentalOption(rs.getInt("participant_rental_option_id"));
                record.setPhoto(rs.getString("participant_photo"));
                record.setRegistrationTime(rs.getString("participant_registration_time"));
                record.setStudentID(rs.getString("participant_student_card"));
                Group g = gDao.getRecordById(record.getId_group());
                record.setGroupName(g.getName());
                records.add(record);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return records;
        }
    	log.trace("END");
        return records;


    }
    
    /**
     * Returns the list of all records stored in table associated with a group
     * 
     * @param anId_group
     * @return List<User> List of objects Participant
     */
    public List<Participant> getAllRecordsById_group(int anId_group) {
        log.trace("START");
    	List<Participant> records = new ArrayList<Participant>();
    	GroupDao gDao = new GroupDao();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from participants where participant_group_id=?");
            preparedStatement.setInt(1, anId_group);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                Participant record = new Participant();
                record.setId(rs.getInt("participant_id"));
                record.setId_group(rs.getInt("participant_group_id"));
                record.setFname(rs.getString("participant_name"));
                record.setLname(rs.getString("participant_surname"));
//                record.setEmail(rs.getString("participant_email"));
                record.setDate_of_birth(rs.getString("participant_birthday"));
//                record.setApproved(rs.getBoolean("participant_approved"));
                record.setGender(rs.getString("participant_gender"));
                record.setFridayProgram(rs.getInt("participant_friday_program"));
//                record.setIntolerances(strings);
                record.setTShirtSize(rs.getString("participant_t_shirt_size"));
                record.setRentalOption(rs.getInt("participant_rental_option_id"));
//                record.setPhotoURL(rs.getString("participant_photo"));
//                record.setRegistrationTime(rs.getString("participant_registration_time"));
                Group g = gDao.getRecordById(record.getId_group());
                record.setGroupName(g.getName());
                records.add(record);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return records;
    }
    
    /**
     * Returns the record passing its id
     * 
     * @param id Identifier of the record to get
     * @return Participant An object Participant
     */
    public Participant getRecordById(int id) {
    	log.trace("START");
    	Participant record = new Participant();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from participants where participant_id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                record.setId(rs.getInt("participant_id"));
                record.setId_group(rs.getInt("participant_group_id"));
                log.debug("id group: "+record.getId_group());
                record.setFname(rs.getString("participant_name"));
                record.setLname(rs.getString("participant_surname"));   
                record.setDate_of_birth(rs.getString("participant_birthday"));
                record.setApproved(rs.getBoolean("participant_approved"));
                record.setRegistrationTime(rs.getString("participant_registration_time"));
                record.setGender(rs.getString("participant_gender"));
                record.setFridayProgram(rs.getInt("participant_friday_program"));
                record.setTShirtSize(rs.getString("participant_t_shirt_size"));
                record.setRentalOption(rs.getInt("participant_rental_option_id"));
                record.setPhoto(rs.getString("participant_photo"));
                record.setPhoto(rs.getString("participant_student_card"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return record;
    }
    
    /**
     * Return a list of user that can modify the record identified by the passed id_participant 
     * 
     * @param anId_participant an id of a participant
     * @return List<Integer> List of id
     */
    public List<Integer>  canBeChangedBy(int anId_participant) {
    	log.trace("START");
    	log.debug("participant id: "+anId_participant);
        List<Integer> listOfId = new ArrayList<Integer>();
        try {
        	
        	//look for group_referent
            PreparedStatement preparedStatement1 = connection
                    .prepareStatement("SELECT snowdays_enrollment.groups.group_referent_id, snowdays_enrollment.groups.group_id " +
                    					" FROM participants, snowdays_enrollment.groups" +
                    					" WHERE participants.participant_group_id = snowdays_enrollment.groups.group_id" +
                    					" AND participants.participant_id = ?");
            preparedStatement1.setInt(1, anId_participant);
            log.debug(preparedStatement1.toString());
            ResultSet rs1 = preparedStatement1.executeQuery();

            if (rs1.next()) {
                listOfId.add(rs1.getInt("group_referent_id"));
                log.debug("id_group_referent: " + rs1.getInt("group_referent_id"));
                log.debug("id_group: " + rs1.getInt("group_id"));
            }
           //looks for group managers
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("SELECT user_id " +
                    					" FROM snowdays_enrollment.users, snowdays_enrollment.roles" +
                    					" WHERE snowdays_enrollment.users.user_username = snowdays_enrollment.roles.user_username" +
                    					" AND snowdays_enrollment.roles.role_name = 'group_manager'");
            log.debug(preparedStatement2.toString());
            ResultSet rs2 = preparedStatement2.executeQuery();
            while (rs2.next()) {
            	log.debug("there are admins");
                listOfId.add(rs2.getInt("user_id"));
                log.debug("id_admin: " + rs2.getInt("user_id"));
            }
            
            //look for admins
            PreparedStatement preparedStatement3= connection
                    .prepareStatement("SELECT user_id " +
                    					" FROM snowdays_enrollment.users, snowdays_enrollment.roles" +
                    					" WHERE snowdays_enrollment.users.user_username = snowdays_enrollment.roles.user_username" +
                    					" AND snowdays_enrollment.roles.role_name = 'admin'");
            log.debug(preparedStatement3.toString());
            ResultSet rs3 = preparedStatement3.executeQuery();
            while (rs3.next()) {
            	log.debug("there are admins");
                listOfId.add(rs3.getInt("user_id"));
                log.debug("id_admin: " + rs3.getInt("user_id"));
            }
            
            for (int  i = 0; i < listOfId.size(); i++){
            	log.debug("listOfId[" + i + "]: " + listOfId.get(i));
            }
            
            rs1.close();
            rs3.close();
            preparedStatement1.close();
            preparedStatement3.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	return listOfId;
    }
    
    public void approve(int id, boolean b){
    	log.trace("START");
    	 try {
             PreparedStatement preparedStatement = connection.
                     prepareStatement("update participants set participant_approved=? where participant_id=?");
             preparedStatement.setBoolean(1, b);
             preparedStatement.setInt(2, id);
             preparedStatement.executeUpdate();
             preparedStatement.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     	log.trace("END");
    }
    
    public int getIDByParticipant(String name, String surname, int groupID){
    	log.trace("START");
    	int result = -1;
   	 try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select participant_id from participants where participant_name=? and participant_surname=? and participant_group_id=?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, groupID);
            ResultSet rs = preparedStatement.executeQuery();
            rs.beforeFirst();
            if(rs.next()){
            	result = rs.getInt("participant_id");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	return result;
    }
    
    public int getProgramID(String program){
    	log.trace("START");
    	int res = -1;
    	try{
    		PreparedStatement preparedStatement = connection.
    				prepareStatement("select program_id from programs where program_description=?");
    		preparedStatement.setString(1, program);
    		ResultSet rs = preparedStatement.executeQuery();
    		rs.beforeFirst();
    		if(rs.next()){
    			res = rs.getInt("program_id");
    			preparedStatement.close();
    	}
    } catch (SQLException e) {
        e.printStackTrace();
    }
	log.trace("END");
	return res;
    }
    
    public int getRentalOptionID(String option){
    	log.trace("START");
    	int res = -1;
    	try{
    		PreparedStatement preparedStatement = connection.
    				prepareStatement("select rent_option_id from rental_options where rent_option_description=?");
    		preparedStatement.setString(1, option);
    		ResultSet rs = preparedStatement.executeQuery();
    		rs.beforeFirst();
    		if(rs.next()){
    			res = rs.getInt("rent_option_id");
    			preparedStatement.close();
    	}
    } catch (SQLException e) {
        e.printStackTrace();
    }
	log.trace("END");
	return res;
    }
    
    public String getProgramByID(int id){
    	log.trace("START");
    	String result = null;
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("select program_description from programs where program_id=?");
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		if(rs.next()){
    			result = rs.getString("program_description");
    		}
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    	return result;
    }
    
    public String getRentalByID(int id){
    	log.trace("START");
    	String result = null;
    	try{
    		PreparedStatement stmt = connection
    				.prepareStatement("select rent_option_description from rental_options where rent_option_id=?");
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		rs.beforeFirst();
    		if(rs.next()){
    			result = rs.getString("rent_option_description");
    		}
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    	return result;
    }
    
}