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

import com.snowdays_enrollment.model.User;


/**
* UserDao is the class that performs actions on the table User of the database
* Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/
* @author Luca Barazzuol
*/
public class UserDao {
	
	// commons logging references
	static Logger log = Logger.getLogger(UserDao.class.getName());

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
    public UserDao(Connection c){
    	connection = c;
    }
    
    /**
     * Constructor with no parameters
     * It initializes the connection to the database
     * 
     */
    public UserDao(){
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
     * Add a record to table User
     * 
     * @param user A user
     */
    public void addUser(User user) {
    	log.trace("START");
        try {
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users(user_name,user_surname,user_email,user_password,user_role,user_username) values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFname());
            preparedStatement.setString(2, user.getLname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setString(6, user.getUsername());
                        
        	log.debug("addUser Execute Update on table user");
            preparedStatement.executeUpdate();
            
            preparedStatement = connection
                    .prepareStatement("insert into roles(role_name,user_username) values (?, ?)");
            preparedStatement.setString(1, user.getRole());
            preparedStatement.setString(2, user.getUsername());


        	log.debug("addUser Execute Update on table user_role");
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Delete a record from table User using its id
     * 
     * @param id It is the id of the record to delete
     */
    public void deleteUser(int id) {
    	log.trace("START");
        try {
            User u = getUserById(id);
            
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from roles where user_username=?");
            preparedStatement.setString(1, u.getUsername());
            log.debug(preparedStatement.toString());
            preparedStatement.executeUpdate();
            
            preparedStatement = connection
                    .prepareStatement("delete from users where user_id=?");
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
     * Update the fields of a user
     * 
     * @param user The user to update
     */
    public void updateUser(User user) {
    	log.debug("START");
        try {
        	
        	User oldUser = getUserById(user.getId());
        	log.debug("oldUser.email: " +oldUser.getEmail());
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set user_name=?, user_surname=?, user_birthday=?, "
                    		+ "user_email=?, user_password=?, user_role=?, user_group=? " +
                            "where user_id=?");
            preparedStatement.setString(1, user.getFname());
            preparedStatement.setString(2, user.getLname());
            preparedStatement.setString(3, user.getDate_of_birth());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setString(7, user.getGroup());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
            
            preparedStatement = connection
                    .prepareStatement("update roles set role_name=?, user_username=? " +
                            "where user_username=?");
            preparedStatement.setString(1, user.getRole());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, oldUser.getUsername());
            log.debug(preparedStatement.toString());
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Update the password of a given user
     * 
     * @param id The id of the user
     * @param password New Password
     * @return boolean to signal that the password has been changed
     */
    public boolean updatePassword(int id, String password) {
    	log.debug("START");
        try {
        	     	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update user set password=? " +
                            "where id=?");
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            
            return true;
                       
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns the list of all users stored in table User
     * 
     * @return List<User> List of objects User
     */
    public List<User> getAllUsers() {
        log.trace("START");
    	List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users, roles WHERE users.user_username = roles.user_username");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFname(rs.getString("user_name"));
                user.setLname(rs.getString("user_surname"));
                user.setDate_of_birth(rs.getString("user_birthday"));
                user.setPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("user_email"));
                user.setRole(rs.getString("role_name"));
                user.setGroup(rs.getString("user_group"));
                users.add(user);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return users;
    }

    /**
     * Returns the list of all users with a particular role
     * 
     * @param aRole String that identifies a role
     * @return List<User> List of objects User
     */
    public List<User> getAllRecordWithRole(String aRole) {
        log.trace("START");
    	List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * " +
						" FROM " +
						" snowdays_enrollment.users, roles " +
						" WHERE users.user_role = '" + aRole +"' and users.user_username = roles.user_username and roles.group_assigned = false ";
            log.debug(sql);
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFname(rs.getString("user_name"));
                user.setLname(rs.getString("user_surname"));
                user.setDate_of_birth(rs.getString("user_birthday"));
                user.setPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("user_email"));
                user.setRole(rs.getString("user_role"));
                users.add(user);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return users;
    }
  
    /**
     * Returns the user passing its id
     * 
     * @param id Identifier of the user to get
     * @return user An object user
     */
    public User getUserById(int id) {
    	log.trace("START");
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM users, roles WHERE users.user_username = roles.user_username AND user_id =?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setFname(rs.getString("user_name"));
                user.setLname(rs.getString("user_surname"));
                user.setDate_of_birth(rs.getString("user_birthday"));
                user.setPassword(rs.getString("user_password"));
                user.setUsername(rs.getString("user_username"));
                user.setEmail(rs.getString("user_email"));
                user.setRole(rs.getString("role_name"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return user;
    }
    
    /**
     * Returns true if the passed email and password are valid - otherwise it returns false 
     * NOT USED ANYMORE
     * 
     * @param email Is the email of a user
     * @param password Is the password of a user
     * @return boolean User is authenticated or not
     */
    public boolean isUserValid(String email, String password) {
    	log.trace("START");
    	
    	boolean isValid = false;
    	
        try {
        	log.debug("Query to check validity of the user");
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from user where email=? and password=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
                 	
        	//User user = new User();
        	
        	if (rs.next()) {
                //user.setId(rs.getInt("id"));
                //user.setFname(rs.getString("fname"));
                //user.setLname(rs.getString("lname"));
                //user.setDate_of_birth(rs.getString("date_of_birth"));
                //user.setPassword(rs.getString("password"));
                //user.setEmail(rs.getString("email"));
  
            	isValid = true;
            }
        	
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	if (isValid){
    		log.debug("user is valid");
    	}
    	else {
    		log.debug("user is INvalid");
    	}
        return isValid;
    }
 
    /**
     * Returns the user passing its email
     * 
     * @param email Email of a user
     * @return user An object user
     */
    public User getUserByEmail(String email) {
    	log.trace("START");
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM users, roles WHERE users.user_email = roles.user_email AND users.user_email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            log.debug(preparedStatement.toString());
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFname(rs.getString("fname"));
                user.setLname(rs.getString("lname"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("ROLE_NAME"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return user;
    }   
    
    public User getUserByUsername(String username){
    	log.trace("START");
    	User user = new User();
    	try{
    		PreparedStatement userInfo = connection
    				.prepareStatement("SELECT * FROM users, roles WHERE users.user_username=? AND roles.user_username=?");
    		userInfo.setString(1, username);
    		userInfo.setString(2, username);
    		ResultSet rs = userInfo.executeQuery();
    		log.debug(userInfo.toString());
    		rs.beforeFirst();
//    		System.out.println(rs.getString("user_username"));
    		if(rs.next()){
    			user.setId(rs.getInt("user_id"));
    			user.setFname(rs.getString("user_name"));
    			user.setLname(rs.getString("user_surname"));
    			user.setDate_of_birth(rs.getString("user_birthday"));
                user.setPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("user_email"));
                user.setRole(rs.getString("user_role"));
                user.setUsername(rs.getString("user_username"));
                System.out.println("User (UserDao): " + user.getUsername());
    		}
    		rs.close();
    		userInfo.close();
    	}
    	catch (SQLException e){
    		e.printStackTrace();
    	}
    	log.trace("END");
    	return user;
    }
}