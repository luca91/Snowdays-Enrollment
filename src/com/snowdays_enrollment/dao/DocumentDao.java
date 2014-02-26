package com.snowdays_enrollment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.model.Badge;

/**
 * 
 * @author Luca Bellettati
 *
 */
public class DocumentDao {
	
	static Logger log = Logger.getLogger(DocumentDao.class.getName());
	
	/**
	 * @uml.property  name="connection"
	 */
	private Connection connection;

	/**
	 * It creates a new object with a given connection.
	 * @param c Connection
	 */
	public DocumentDao(Connection c) {
		connection = c;
	}
	
	/**
	 * It creates a new object setting the context.
	 */
	public DocumentDao(){
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
	 * It retrieves the data from the database in order to fill the fields of the badge.
	 * @param id int
	 * @return Badge - The badge object
	 */
	public Badge getDataFromDB(int id){
		log.trace("START");
		Badge aBadge = new Badge();
		try{
			 PreparedStatement preparedStatement = connection.prepareStatement("select  fname, lname, id_group from participant where id=?");
			 preparedStatement.setInt(1, id);
			 ResultSet rs = preparedStatement.executeQuery();
			 if(rs.next()){
				aBadge.setFirstName(rs.getString("fname"));
				aBadge.setLastName(rs.getString("lname"));
				aBadge.setGroup(rs.getInt("id_group"));
			 }
			 preparedStatement.close();
			 rs.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		log.trace("END");
		return aBadge;
	}

}
