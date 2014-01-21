package com.snowdays_enrollment.tools;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {
	
	private Connection connection;
	
	public DBConnection(){
		openConnection();
	}
	
	public void openConnection(){
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
	
	public Connection getConnection(){
		return connection;
	}
	
	public void closeConnection() throws SQLException{
		connection.close();
	}

}
