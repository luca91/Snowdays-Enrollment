package com.snowdays_enrollment.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import com.snowdays_enrollment.model.RegistrationUniBz;

public class RegistrationUniBzDao {
	
static Logger log = Logger.getLogger(RegistrationExternalsDao.class.getName());
	
	private Connection connection;
	
	public RegistrationUniBzDao(Connection c){
		connection = c;
	}
	
	public RegistrationUniBzDao() {
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
	
	public void addRecord(RegistrationUniBz ru){
		
	}
	
	public List<String> getFacultiyServerByName(String name){
		log.trace("START");
		List<String> result = new ArrayList<String>();
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select faculty_server from faculties where faculty_name=?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			if(rs.next()){
				result.add(rs.getString("faculty_server"));
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

}
