package com.snowdays_enrollment.test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;


/**
* Class used to mock some data to test DAO classes
* 
* @author Luca Barazzuol
*/
public class MockData {
		
	// commons logging references
		static Logger log = Logger.getLogger(UserDaoTest.class.getName());
		
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
	 * @uml.property  name="id_manager"
	 */
	int id_manager;
	/**
	 * @uml.property  name="id_group_referent"
	 */
	int id_group_referent;
	/**
	 * @uml.property  name="id_event"
	 */
	int id_event;
	/**
	 * @uml.property  name="id_group"
	 */
	int id_group;

	/**
	 * Flushes the tables and then load some mock data
	 * 
	 */
	public void createMock(){
		
		DbConfig dbc = new DbConfig();
		DB_JDBC_DRIVER = dbc.getDB_JDBC_DRIVER();
		DB_URL = dbc.getDB_URL();
		DB_USER = dbc.getDB_USER();
		DB_PASSWORD = dbc.getDB_PASSWORD();
				
		String sql;
		try {
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//Open a connection
			log.debug("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			log.debug("Connected database successfully...");
			
			// ############# DELETE RECORDS - START #############
			log.debug("DELETE ALL RECORDS");
			
			stmt = conn.createStatement();

			sql =
					"DELETE FROM participant";
			stmt.executeUpdate(sql);
			
			sql =
					"DELETE FROM ems.group";
			stmt.executeUpdate(sql);
			
			sql =
					"DELETE FROM event";
			stmt.executeUpdate(sql);
			
			sql =
					"DELETE FROM user";
			stmt.executeUpdate(sql);
			
			sql =
					"DELETE FROM user_role";
			stmt.executeUpdate(sql);
			// ############# DELETE RECORDS - END #############			
			
			
			// ############# TABLE USER - START #############
			log.debug("Table USER");

			sql = 	
					"insert " +
					" into user(fname,lname,date_of_birth,email,password,role)" +
					" values ('Luca', 'Be', '19910101','lucabelles@gmail.com' ,'password','admin');";
			log.debug("Inserting record 1...");
			stmt.executeUpdate(sql);
			
	    	sql = 	"INSERT"+
	    			" INTO ems.user_role(ROLE_NAME, email)" +
	    			" VALUES ('admin', 'lucabelles@gmail.com');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	// ########################################### 			
			
			sql = 	"insert " +
					" into user(fname,lname,date_of_birth,email,password,role)" +
					" values ('Luca', 'Ba', '19710703','luca.barazzuol@gmail.com' ,'password','event_mng');";
			log.debug("Inserting record 2...");
			stmt.executeUpdate(sql);
			
			log.debug("Get id of dummy id_manager");
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	id_manager = rs.getInt("last_id");
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user_role(ROLE_NAME, email)" +
	    			" VALUES ('event_mng', 'luca.barazzuol@gmail.comm');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	// ###########################################    	
			
			sql = 	"insert " +
					" into user(fname,lname,date_of_birth,email,password,role)" +
					" values ('Alex', 'Stan','19910202','alexstannumberone@gmail.com' ,'password','group_mng');";
			log.debug("Inserting record 3...");
			stmt.executeUpdate(sql);
			
			log.debug("Get id of dummy id_group_referent");
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	id_group_referent = rs.getInt("last_id");
	    	
	    	
	    	sql = 	"INSERT"+
	    			" INTO ems.user_role(ROLE_NAME, email)" +
	    			" VALUES ('group_mng', 'alexstannumberone@gmail.comm');";	    	
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
			// ############# TABLE USER - END #############
			
			
			
			// ############# TABLE EVENT - START #############
			log.debug("TABLE EVENT");
			
/*			log.debug("Insert a dummy id_manager");
			sql =
					"insert " +
					" into user(fname,lname,date_of_birth,email,password,role)" +
					" values ('Roger', 'Penroese', '19910101','roger@hotmail.com' ,'password','event_mng');";
			stmt.executeUpdate(sql);
			
			log.debug("Get id of dummy id_manager");
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	id_manager = rs.getInt("last_id");*/
			
			sql = 	
					"insert " +
					" into event(id_manager,name,description,start,end,enrollment_start,enrollment_end)" + 
					" values (" + id_manager + ", 'event1','description1','20130507', '20130607', '20130430', '20130506' );";
			log.debug("Inserting record 1...");
			stmt.executeUpdate(sql);
			
			log.debug("Get id of dummy id_event");
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	id_event = rs.getInt("last_id");
			
			
			sql = 	
					"insert " +
					" into event(id_manager,name,description,start,end,enrollment_start,enrollment_end)" + 
					" values (" + id_manager + ", 'event2','description2','20130507', '20130607', '20130430', '20130506' );";
			log.debug("Inserting record 2...");
			stmt.executeUpdate(sql);
			
			sql = 	
					"insert " +
					" into event(id_manager,name,description,start,end,enrollment_start,enrollment_end)" + 
					" values (" + id_manager + ", 'event3','description3','20130507', '20130607', '20130430', '20130506' );";

			log.debug("Inserting record 3...");
			stmt.executeUpdate(sql);			
			// ############# TABLE EVENT - END #############
				
			// ############# TABLE GROUP - START #############	
			log.debug("TABLE GROUP");
			log.debug("Insert a dummy group");
			sql =
					"insert " +
					" into ems.group(id_event,id_group_referent,max_group_number,blocked)" +
					" values (" + id_event + "," + id_group_referent + ", 99999, false);";
			log.debug(sql);
			stmt.executeUpdate(sql);
			
			log.debug("Get id of dummy id_group");
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	id_group = rs.getInt("last_id");
			
			log.debug("Insert a second dummy group");
			sql =
					"insert " +
					" into ems.group(id_event,id_group_referent,max_group_number,blocked)" +
					" values (" + id_event + "," + id_group_referent + ", 999999, true);";
			stmt.executeUpdate(sql);
			// ############# TABLE GROUP - END #############			
			
			log.debug("Executed queries");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	    catch (SQLException e) {
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
	 * Remove Data
	 * 
	 */
	public void removeMock(){
		String sql;
		try {
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//Open a connection
			log.debug("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			log.debug("Connected database successfully...");

			//STEP 4: Execute a query
			log.debug("Create statement");
			stmt = conn.createStatement();

			sql =
					"DELETE FROM participant";
			stmt.executeUpdate(sql);
			
			sql =
					"DELETE FROM ems.group";
			stmt.executeUpdate(sql);
			
			sql =
					"DELETE FROM event";
			stmt.executeUpdate(sql);
			
			
			sql =
					"DELETE FROM user";
			stmt.executeUpdate(sql);	
			
			sql =
					"DELETE FROM user";
			stmt.executeUpdate(sql);	
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	    catch (SQLException e) {
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
	 * @return
	 * @uml.property  name="id_manager"
	 */
	public int getId_manager() {
		return id_manager;
	}

	/**
	 * @param id_manager
	 * @uml.property  name="id_manager"
	 */
	public void setId_manager(int id_manager) {
		this.id_manager = id_manager;
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
}
