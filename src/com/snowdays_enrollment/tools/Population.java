package com.snowdays_enrollment.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.test.dao.DbConfig;
import com.snowdays_enrollment.test.dao.UserDaoTest;

/**
* Class used to Populate DB with basic record set
* 
* @author Luca Barazzuol
*/
public class Population {
	
	// commons logging references
	static Logger log = Logger.getLogger(Population.class.getName());
	
	// JDBC driver name and database URL
	static String DB_JDBC_DRIVER;  
	static String DB_URL;
	//  Database credentials
	static String DB_USER;
	static String DB_PASSWORD;
	
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;;
	
	/**
	* Constructor
	*
	*/
	public Population(){
		DbConfig dbc = new DbConfig();
		DB_JDBC_DRIVER = dbc.getDB_JDBC_DRIVER();
		DB_URL = dbc.getDB_URL();
		DB_USER = dbc.getDB_USER();
		DB_PASSWORD = dbc.getDB_PASSWORD();
		
	}
	
	/**
	* Execute the population of the DB
	*
	*/
	public void doPopulation(){
		log.debug("START");
		
		log.debug("DB_JDBC_DRIVER: " + DB_JDBC_DRIVER);
		log.debug("DB_URL: " + DB_URL);
		log.debug("DB_USER: " + DB_USER);
		log.debug("DB_PASSWORD: " + DB_PASSWORD);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	    	
	    	String sql;
// #####################################################################################################	    	
	    	sql = "DELETE FROM ems.group;";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = "DELETE FROM ems.event;";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = "DELETE FROM ems.user;";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = "DELETE FROM ems.user_role;";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);

// #####################################################################################################
	    	// the field role of the table ems.user is not anymore used. 
	    	//It is still used to avoid major changes on the script code
	    	
	    	//ADD USERS
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user(fname,lname,date_of_birth,email,PASSWORD,role)" +
	    			" VALUES ('Luca', 'Be', '19910101','lucabelles@gmail.com' ,'password','admin');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	    	
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user_role(ROLE_NAME, email)" +
	    			" VALUES ('admin', 'lucabelles@gmail.com');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	    	

	    	sql = 	"INSERT"+
	    			" INTO ems.user(fname,lname,date_of_birth,email,PASSWORD,role)" +
	    			" VALUES ('Luca', 'Ba', '19710703','luca.barazzuol@gmail.com' ,'password','event_mng');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user_role(ROLE_NAME, email)" +
	    			" VALUES ('event_mng', 'luca.barazzuol@gmail.com');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user(fname,lname,date_of_birth,email,PASSWORD,role)" +
	    			" VALUES ('Alex', 'Stan','19910202','alexstannumberone@gmail.com' ,'password','group_mng');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user_role(ROLE_NAME, email)" +
	    			" VALUES ('group_mng', 'alexstannumberone@gmail.com');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
// #####################################################################################################	    	
	    	
	    	//ADD EVENTS
	    	
	    	sql = 	"INSERT"+
	    			" INTO event(id_manager,NAME,description,START,END,enrollment_start,enrollment_end) " +
	    			" VALUES (" +
	    				" (SELECT id " + 
	    					" FROM ems.user, ems.user_role" +  
	    					" WHERE ems.user.email = ems.user_role.email"+ 
	    					" AND ems.user_role.ROLE_NAME = 'event_mng' LIMIT 1),"+
	    					" 'event1', 'description1', '20991231', '20991231', '20991231', '20991231');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	    	

	    	sql = 	"INSERT"+
	    			" INTO event(id_manager,NAME,description,START,END,enrollment_start,enrollment_end) " +
	    			" VALUES (" +
	    				" (SELECT id " + 
	    					" FROM ems.user, ems.user_role" +  
	    					" WHERE ems.user.email = ems.user_role.email"+ 
	    					" AND ems.user_role.ROLE_NAME = 'event_mng' LIMIT 1),"+
	    					" 'event2', 'description2', '20991231', '20991231', '20991231', '20991231');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	
	    	
	    	sql = 	"INSERT"+
	    			" INTO event(id_manager,NAME,description,START,END,enrollment_start,enrollment_end) " +
	    			" VALUES (" +
	    				" (SELECT id " + 
	    					" FROM ems.user, ems.user_role" +  
	    					" WHERE ems.user.email = ems.user_role.email"+ 
	    					" AND ems.user_role.ROLE_NAME = 'event_mng' LIMIT 1),"+
	    					" 'event3', 'descriptio3', '20991231', '20991231', '20991231', '20991231');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	    	
// #####################################################################################################	    	
	    	sql = 	"INSERT" +
	    			" INTO ems.group(id_event,id_group_referent,ems.group.name,max_group_number)"+ 
	    			" VALUES (" +
	    				" (SELECT id" + 
	    				" FROM event" + 
	    				" LIMIT 1" +
	    				" )"+
	    				",(SELECT id"+ 
	    				" FROM ems.user, ems.user_role"+ 
	    				" WHERE ems.user.email = ems.user_role.email" + 
	    				" AND ems.user_role.ROLE_NAME = 'group_mng' LIMIT 1"+
	    				" )" +
	    				" ,'Group001'" +
	    				" , 5);";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	
	    	
	    	sql = 	"INSERT" +
	    			" INTO ems.group(id_event,id_group_referent,ems.group.name,max_group_number)"+ 
	    			" VALUES (" +
	    				" (SELECT id" + 
	    				" FROM event" + 
	    				" LIMIT 1" +
	    				" )"+
	    				",(SELECT id"+ 
	    				" FROM ems.user, ems.user_role"+ 
	    				" WHERE ems.user.email = ems.user_role.email" + 
	    				" AND ems.user_role.ROLE_NAME = 'group_mng' LIMIT 1"+
	    				" )" +
	    				" ,'Group002'" +
	    				" , 5);";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = 	"INSERT" +
	    			" INTO ems.group(id_event,id_group_referent,ems.group.name,max_group_number)"+ 
	    			" VALUES (" +
	    				" (SELECT id" + 
	    				" FROM event" + 
	    				" LIMIT 1" +
	    				" )"+
	    				",(SELECT id"+ 
	    				" FROM ems.user, ems.user_role"+ 
	    				" WHERE ems.user.email = ems.user_role.email" + 
	    				" AND ems.user_role.ROLE_NAME = 'group_mng' LIMIT 1"+
	    				" )" +
	    				" ,'Group003'" +
	    				" , 5);";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
// #####################################################################################################	    	   	
	    	sql = 	"INSERT" +
	    			" INTO ems.participant(id_group,fname,lname,email,date_of_birth)"+ 
	    			" VALUES (" +
	    				" (SELECT id" + 
	    				" FROM ems.group" + 
	    				" LIMIT 1" +
	    				" )"+
	    	    		" ,'Pluto'" +
	    	    		" ,'Disney'" +
	    	    		" ,'luca.barazzuol@gmail.com'" +
	    	    		" , '19990101'" +	    	
	    				" );";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = 	"INSERT" +
	    			" INTO ems.participant(id_group,fname,lname,email,date_of_birth)"+ 
	    			" VALUES (" +
	    				" (SELECT id" + 
	    				" FROM ems.group" + 
	    				" LIMIT 1" +
	    				" )"+
	    	    		" ,'Mariella'" +
	    	    		" ,'Dongiovanni'" +
	    	    		" ,'luca.barazzuol@eidosoft.biz'" +
	    	    		" , '19890101'" +
						" );";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = 	"INSERT" +
	    			" INTO ems.participant(id_group,fname,lname,email,date_of_birth)"+ 
	    			" VALUES (" +
	    				" (SELECT id" + 
	    				" FROM ems.group" + 
	    				" LIMIT 1, 1" +
	    				" )"+
	    	    		" ,'Pinco'" +
	    	    		" ,'Pallo'" +
	    	    		" ,'lukeb@gmx.de'" +
	    	    		" , '19900101'" +
	    				" );";
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
// #####################################################################################################	    	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally{
	        //finally block used to close resources
	        try{
	           if(stmt!=null)
	              conn.close();
	        }catch(SQLException se){
	        }// do nothing
	        try{
	           if(conn!=null)
	              conn.close();
	        }catch(SQLException se){
	           se.printStackTrace();
	        }//end finally try
	    }
	}
	
	
	/**
	 * Main method to test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population p = new Population();
		p.doPopulation();
	}

}
