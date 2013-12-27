package com.snowdays_enrollment.test.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.Population;

/**
 * Tests for {@link UserDao}.
 *
 * @author Luca Barazzuol
 */
@RunWith(JUnit4.class)
public class UserDaoTest {
	
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
	 * @uml.property  name="id_event"
	 */
	int id_event;

	/**
	 * @uml.property  name="md"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	MockData md = new MockData();
	
	/**
	 * Constructor
	 * 
	 */
	public UserDaoTest(){
		DbConfig dbc = new DbConfig();
		DB_JDBC_DRIVER = dbc.getDB_JDBC_DRIVER();
		DB_URL = dbc.getDB_URL();
		DB_USER = dbc.getDB_USER();
		DB_PASSWORD = dbc.getDB_PASSWORD();
	}
	
	/**
	 * Populate DB- executed before each test
	 * 
	 */
	@Before
	public void loadMockData() {
		log.debug("loadMockData() - START");
		md.createMock();
		
		id_manager = md.getId_manager();
		id_event = md.getId_event();
		
		log.debug("loadMockData() - END");
	}
	
	/**
	 * Remove Data from DB - executed after each test
	 * 
	 */
	@After
	public void removeMockData(){
		log.debug("removeMockData() - START");
		md.removeMock();
		log.debug("removeMockData() - END");
	}
	
	/**
	 * Repopulate DB - load the typical data on DB at the end of execution
	 * 
	 */
	@AfterClass
	public static void repopulate(){
		log.debug("repopulate() - START");
		Population p = new Population();
		p.doPopulation();
		log.debug("repopulate() - END");
	}
	
	/**
	 * Test adduser
	 * 
	 */
	@Test
    public void testAddUser() throws NamingException, ClassNotFoundException {
		log.debug("testAddUser() - START");
		
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		
		Class.forName("com.mysql.jdbc.Driver");
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	    	UserDao obj = new UserDao(conn);
	    	User record = new User();
	    	
	    	record.setFname(fname);
	    	record.setLname(lname);
	    	record.setDate_of_birth(date_of_birth);
	    	record.setEmail(email);
	    	record.setPassword(password);
	    	record.setRole(role);
	    	obj.addUser(record);
	    	
	    	String sql = 
	    			"SELECT COUNT(*) AS nr_rows" +
	    			" FROM user" +
	    			" WHERE fname = '" + fname + "';";
	    	
	    	stmt = conn.createStatement();
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	int  count = rs.getInt("nr_rows");
	    	
	    	Assert.assertEquals("failure - record not added - ", 1, count);
		} catch (SQLException e) {
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
		
		log.debug("testAddUser() - END");

    }
	
	/**
	 * Test deleteUser
	 * 
	 */
	@Test
    public void testDeleteUser() throws NamingException, ClassNotFoundException {
		log.debug("testDeleteUser() - START");
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			
	    	String sql = 
	    			"INSERT" +
	    			" INTO user(fname,lname,date_of_birth,email, password,role) " +
	    			" VALUES ('" + fname +"', '" + lname + "', '" + date_of_birth + "', '" +  email + "', '" + password + "', '" + role + "');";
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
			
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();
	    	int  last_id = rs.getInt("last_id");
	    	
	    	UserDao obj = new UserDao(conn);
	    	obj.deleteUser(last_id);
	    	
	    	sql = 	"SELECT COUNT(*) AS nr_rows" +
	    			" FROM user" +
	    			" WHERE id = " + last_id;
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();
	    	int  count = rs.getInt("nr_rows");
	    	
	    	Assert.assertEquals("failure - record not deleted", 0, count);
		} catch (SQLException e) {
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
		log.debug("testDeleteUser() - END");
    }
	
	/**
	 * Test getAllRecordWithRole
	 * 
	 */
	@Test
    public void testGetAllRecordWithRole() throws ClassNotFoundException {
		log.debug("START");	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			UserDao obj = new UserDao(conn);
	
			String role = "admin";
	    	List<User> list = obj.getAllRecordWithRole(role);
	    	log.debug("Select: " );
	    	String sql = 	
	    			"SELECT COUNT(*) AS nr_rows" +
	    			" FROM user, user_role" +
	    			" WHERE user.email = user_role.email" + 
	    			" AND user_role.ROLE_NAME = '" + role + "';";
	    	log.debug(sql);
	    	stmt = conn.createStatement();
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	int nr_rows = rs.getInt("nr_rows");
	    	
	    	log.debug("nr_rows: " + nr_rows);
	    	log.debug("list.size(): " + list.size());
	    	Assert.assertEquals("failure - getAllUsers returns a different list of record", nr_rows, list.size());
		} catch (SQLException e) {
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
    	
		log.debug("testGetAllUsers() - END");	
    }
	
	/**
	 * Test getAllUsers
	 * 
	 */
	@Test
    public void testGetAllUsers() throws ClassNotFoundException {
		log.debug("testGetAllUsers() - START");	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			UserDao obj = new UserDao(conn);
	
	    	List<User> list = obj.getAllUsers();
	
	    	String sql = 	
	    			"SELECT COUNT(*) AS nr_rows" +
	    			" FROM user, user_role" +
	    			" WHERE user.email = user_role.email";	    					;
	    	stmt = conn.createStatement();
	    	rs = stmt.executeQuery(sql);
	    	rs.next();
	    	int nr_rows = rs.getInt("nr_rows");
	    	
	    	log.debug("nr_rows: " + nr_rows);
	    	log.debug("list.size(): " + list.size());
	    	Assert.assertEquals("failure - getAllUsers returns a different list of record", nr_rows, list.size());
		} catch (SQLException e) {
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
    	
		log.debug("testGetAllUsers() - END");	
    }
	
	/**
	 * Test getUserByEmail
	 * 
	 */
	@Test
    public void testGetUserByEmail() throws ClassNotFoundException {
		log.debug("testGetUserById() - START");
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			
	    	String sql = 
	    			"INSERT" +
					" INTO user(fname,lname,date_of_birth,email, password,role) " +
	    			" VALUES ('" + fname +"', '" + lname + "', '" + date_of_birth + "', '" +  email + "', '" + password + "', '" + role + "');";
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);

	    	sql = 
	    			"INSERT" +
					" INTO user_role(ROLE_NAME, email) " +
	    			" VALUES ('" + role +"', '" + email + "');";
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);	    	
	    	
	    	UserDao obj = new UserDao(conn);
	
	    	User aRecord = obj.getUserByEmail(email);
	    	
	    	Assert.assertEquals("failure - record returned by email is different from record inserted", aRecord.getFname(), fname);
		} catch (SQLException e) {
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
		log.debug("testGetUserById() - END");
    }
	
	/**
	 * Test getUserById
	 * 
	 */
	@Test
    public void testGetUserById() throws ClassNotFoundException {
		log.debug("testGetUserById() - START");
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			
	    	String sql = 
	    			"INSERT" +
					" INTO user(fname,lname,date_of_birth,email, password,role) " +
	    			" VALUES ('" + fname +"', '" + lname + "', '" + date_of_birth + "', '" +  email + "', '" + password + "', '" + role + "');";
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	sql = 
	    			"INSERT" +
					" INTO user_role(ROLE_NAME, email) " +
	    			" VALUES ('" + role +"', '" + email + "');";
	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
			
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();
	    	int  last_id = rs.getInt("last_id");
	    	
	    	UserDao obj = new UserDao(conn);
	
	    	User aRecord = obj.getUserById(last_id);
	    	
	    	Assert.assertEquals("failure - record returned by ID is different from record inserted", aRecord.getFname(), fname);
		} catch (SQLException e) {
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
		log.debug("testGetUserById() - END");
    }	
	
	/**
	 * Test isUserValid
	 * 
	 */
	@Test
    public void testIsUserValid() throws ClassNotFoundException {
		log.debug("testUpdateUser() - START");
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			
	    	String sql = 
	    			"INSERT" +
					" INTO user(fname,lname,date_of_birth,email, password,role) " +
	    			" VALUES ('" + fname +"', '" + lname + "', '" + date_of_birth + "', '" +  email + "', '" + password + "', '" + role + "');";

	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
	    	UserDao dao = new UserDao(conn);
	    	
	    	//test if method checks correctly a valid user
	    	boolean isUserValid = dao.isUserValid(email, password);
	    	Assert.assertTrue("failure - user and password are not valid", isUserValid);
	    	
	    	
	    	String userNotValid = UUID.randomUUID().toString();
	    	String passwordNotValid = UUID.randomUUID().toString();
	    	
	    	//test if method checks correctly a valid user with invalid password
	    	isUserValid = dao.isUserValid(email, passwordNotValid);
	    	Assert.assertTrue("failre - method checks as valid an user with invalid password", !isUserValid);
	    	
	    	//test if method checks correctly an invalid user
	    	isUserValid = dao.isUserValid(userNotValid, passwordNotValid);
	    	Assert.assertTrue("faliure - method checks as avalis an invalid user", !isUserValid);	
	    	
	    	
	    	
		} catch (SQLException e) {
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
		log.debug("testUpdateUser() - END");
    }
	
	/**
	 * Test updatePassword
	 * 
	 */
	@Test
    public void testUpdatePassword() throws ClassNotFoundException {
		log.debug("START");
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			
	    	String sql = 
	    			"INSERT" +
					" INTO user(fname,lname,date_of_birth,email, password,role) " +
	    			" VALUES ('" + fname +"', '" + lname + "', '" + date_of_birth + "', '" +  email + "', '" + password + "', '" + role + "');";

	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
			
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();
	    	int  last_id = rs.getInt("last_id");
	    	
	    	log.debug(last_id);
	    	
	    	UserDao obj = new UserDao(conn);
	
	    	User aRecord = new User();
	    	

	    	String newPassword = "PasswordUpdated";

	    	
	    	aRecord.setId(last_id);
	    	
	    	
	    	obj.updatePassword(last_id, newPassword);
	    	
	    	sql = 	"SELECT *" +
	    			" FROM user" +
	    			" WHERE id = " + last_id;
	    	
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();

	    	String upPassword = rs.getString("password");
	    	
	    	Assert.assertEquals("failure - field password has not been correctly updated", newPassword, upPassword);
		} catch (SQLException e) {
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
		log.debug("testUpdateUser() - END");
    }
	
	/**
	 * Test pdateUser
	 * 
	 */
	@Test
    public void testUpdateUser() throws ClassNotFoundException {
		log.debug("testUpdateUser() - START");
		String fname = "FakeFname";
		String lname = "FakeLname";
		String date_of_birth = "20130416";
		String email = "FakeEmail";
		String password = "FakePassword";
		String role = "admin";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			
	    	String sql = 
	    			"INSERT" +
					" INTO user(fname,lname,date_of_birth,email, password,role) " +
	    			" VALUES ('" + fname +"', '" + lname + "', '" + date_of_birth + "', '" +  email + "', '" + password + "', '" + role + "');";

	    	stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
	    	
			
	    	sql = "SELECT LAST_INSERT_ID() AS last_id";
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();
	    	int  last_id = rs.getInt("last_id");
	    	
	    	log.debug(last_id);
	    	
	    	UserDao obj = new UserDao(conn);
	
	    	User aRecord = new User();
	    	
	    	String newFname = "FnameUpdated";
	    	String newLname = "LnameUpdated";
	    	String newDate_of_birth = "20330416";
	    	String newEmail = "EmailUpdated";
	    	String newPassword = "PasswordUpdated";
	    	String newRole = "admin";
	    	
	    	aRecord.setId(last_id);
	    	aRecord.setFname(newFname);
	    	aRecord.setLname(newLname);
	    	aRecord.setDate_of_birth(newDate_of_birth);
	    	aRecord.setEmail(newEmail);
	    	aRecord.setPassword(newPassword);
	    	aRecord.setRole(newRole);
	    	
	    	obj.updateUser(aRecord);
	    	
	    	sql = 	"SELECT *, DATE_FORMAT(date_of_birth,'%Y%m%d') AS date_of_birth_formatted" +
	    			" FROM user" +
	    			" WHERE id = " + last_id;
	    	
	    	rs = stmt.executeQuery(sql);
			
	    	rs.next();
	    	String upFname = rs.getString("fname");
	    	String upLname = rs.getString("lname");
	    	String upDate_of_birth = rs.getString("date_of_birth_formatted");
	    	String upEmail = rs.getString("email");
	    	String upPassword = rs.getString("password");
	    	String upRole = rs.getString("role");
	    	
	    	log.debug("###### " + upFname + " - " + upLname + " - " + upDate_of_birth + " - " + upEmail + " - " + upPassword + " - " + upRole);
	    	Assert.assertEquals("failure - field fname has not been correctly updated", newFname, upFname);
	    	Assert.assertEquals("failure - field lname has not been correctly updated", newLname, upLname);
	    	Assert.assertEquals("failure - field date_of_birth has not been correctly updated", newDate_of_birth, upDate_of_birth);
	    	Assert.assertEquals("failure - field email has not been correctly updated", newEmail, upEmail);
	    	Assert.assertEquals("failure - field password has not been correctly updated", newPassword, upPassword);
	    	Assert.assertEquals("failure - field role has not been correctly updated", newRole, upRole);
	    	
	    	
		} catch (SQLException e) {
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
		log.debug("testUpdateUser() - END");
    }
	

	
}
