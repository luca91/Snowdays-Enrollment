package com.snowdays_enrollment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.model.Country;
import com.snowdays_enrollment.model.Settings;

public class SettingsDao {
	
static Logger log = Logger.getLogger(SettingsDao.class.getName());
	
	/**
	 * @uml.property  name="connection"
	 */
	private Connection connection;

	/**
	 * It creates a new object with a given connection.
	 * @param c Connection
	 */
	public SettingsDao(Connection c) {
		connection = c;
	}
	
	/**
	 * It creates a new object setting the context.
	 */
	public SettingsDao(){
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
	
	public void addSettings(String name, String description){
		log.trace("START");
		System.out.println("Value: "+ description);
		try{
			if(getSetting(name) == null){
				PreparedStatement stmt = connection
						.prepareStatement("insert into settings values(?,?)");
				stmt.setString(1, name);
				stmt.setString(2, description);
				log.debug(stmt.toString());
				stmt.executeUpdate();
				stmt.close();
			}
			else{
				System.out.println("Update");
				updateSetting(name, description);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		log.trace("END");
	}
	
	public String getSetting(String name){
		log.trace("START");
		String result = null;
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select setting_value from settings where setting_name=?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			if(rs.next()){
				result = rs.getString("setting_value");
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
	
	public Settings getAllSettings(){
		log.trace("START");
		Settings result = new Settings();
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select * from settings");
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			while(rs.next()){
				if(!(rs.getString("setting_name").equals("all_blocked")))
					setAllSettings(rs.getString("setting_name"),Integer.parseInt(rs.getString("setting_value")), result);
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
	
	public Map<String, Integer> getPeoplePerCountry(){
		log.trace("START");
		Map<String, Integer> result = new HashMap<String, Integer>();
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select * from countries");
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			while(rs.next()){
				result.put(rs.getString("country_name"), rs.getInt("country_max_people"));
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
	
	public List<Country> getAllCountries(){
		log.trace("START");
		List<Country> result = new ArrayList<Country>();
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select * from countries");
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			while(rs.next()){
				Country c = new Country();
				c.setName(rs.getString("country_name"));
				c.setMaxPeople(rs.getInt("country_max_people"));
				c.setActualPeople(rs.getInt("country_actual_people"));
				System.out.println(c.getName());
				result.add(c);
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
	
	public void setPeoplePerCountry(Map<String, Integer> list){
		log.trace("START");
		String[] keys =  list.keySet().toArray(new String[] {});
		try{
			PreparedStatement stmt = null; 
			for(int i = 0; i < list.size(); i++){
				stmt = connection
					.prepareStatement("update countries set country_max_people=? where country_name=?");
				stmt.setInt(1, list.get(keys[i]));
				stmt.setString(2, keys[i]);
				stmt.executeUpdate();
			}
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void updateSetting(String name, String value){
		log.trace("START");
		try{
			PreparedStatement stmt = connection
					.prepareStatement("update settings set setting_value=? where setting_name=?");
			stmt.setString(1, value);
			stmt.setString(2, name);
			stmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void setAllSettings(String name, int value, Settings s){
		switch (name){
		case "maxexternals":
			s.setMaxExternals(value);
			break;
		case "maxinternals":
			s.setMaxInternals(value);
			break;
		case "maxpergroup":
			s.setMaxParticipantsPerGroup(value);
			break;
		}
	}

}
