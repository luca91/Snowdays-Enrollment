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
		try{
			PreparedStatement stmt = connection
					.prepareStatement("insert into emails_internals values(?,?,?,?,?,?)");
			stmt.setString(1, ru.getEmail());
			stmt.setString(2, ru.getName());
			stmt.setString(3, ru.getSurname());
			stmt.setString(4, ru.getStatus());
			stmt.setString(5, ru.getGroup());
			stmt.setString(6, ru.getLink());
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public RegistrationUniBz getRegistrationByEmail(String email){
		RegistrationUniBz ru = null;
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select * from emails_internals where email=?");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				ru = new RegistrationUniBz();
				ru.setEmail(rs.getString("email"));
				ru.setName(rs.getString("name"));
				ru.setSurname(rs.getString("surname"));
				ru.setStatus(rs.getString("status"));
				ru.setGroup(rs.getString("group"));
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return ru;
	}
	
	public List<RegistrationUniBz> getAllRegistration(){
		List<RegistrationUniBz> result = new ArrayList<RegistrationUniBz>();
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select * from emails_internals");
			ResultSet rs = stmt.executeQuery();
			int position = 1;
			while(rs.next()){
				RegistrationUniBz ru = new RegistrationUniBz();
				ru.setEmail(rs.getString("email"));
				ru.setName(rs.getString("name"));
				ru.setSurname(rs.getString("surname"));
				ru.setStatus(rs.getString("status"));
				ru.setGroup(rs.getString("group"));
				ru.setPosition(position);
				position++;
				result.add(ru);
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	public void deleteRecord(String email){
		try{
			PreparedStatement stmt = connection
					.prepareStatement("delete from emails_internals where email=?");
			stmt.setString(1, email);
			stmt.executeUpdate();
			stmt = connection.prepareStatement("delete from participants where participant_email=?");
			stmt.setString(1, email);
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void submit(String email){
		try{
			PreparedStatement stmt = connection
					.prepareStatement("update emails_internals set status='submit' where email=?");
			stmt.setString(1, email);
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

}
