package com.snowdays_enrollment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.RegistrationExternal;

public class RegistrationExternalsDao {
	
	static Logger log = Logger.getLogger(RegistrationExternalsDao.class.getName());
	
	private Connection connection;
	
	public RegistrationExternalsDao(Connection c){
		connection = c;
	}
	
	public RegistrationExternalsDao() {
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
	
	public void addRegistration(RegistrationExternal r){
		log.trace("START");
		GroupDao gDao = new GroupDao();
		Group g = gDao.getGroupByName(r.getGroupName());
        try {
           PreparedStatement stmt = connection
        		   .prepareStatement("insert into registrations(registration_participant_id,"
        		   		+ "registration_participant_group_id,"
        		   		+ "registration_participant_group_name, "
        		   		+ "registration_participant_registration_time) values(?,?,?,?)");
           stmt.setInt(1, r.getParticipantID());
           stmt.setInt(2, g.getId());
           stmt.setString(3, r.getGroupName());
           stmt.setString(4, r.getTime());
           stmt.executeUpdate();
           if(g.getFirstParticipantRegistered() != 0)
        	   gDao.setFirstRegistered(r.getParticipantID(), g.getId());
           stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
	}
	
	public List<RegistrationExternal> getAllRegistration(){
		log.trace("START");
		List<RegistrationExternal> result = new ArrayList<RegistrationExternal>();
		try {
           PreparedStatement stmt = connection
        		   .prepareStatement("select * from registrations order by registration_participant_registration_time");
           log.debug(stmt.toString());
           ResultSet rs = stmt.executeQuery();
           rs.beforeFirst();
           while(rs.next()){
        	   RegistrationExternal re = new RegistrationExternal();
        	   re.setGroupName(rs.getString("registration_participant_group_name"));
        	   re.setParticipantID(rs.getInt("registration_participant_id"));
        	   re.setGroupID(rs.getInt("registration_participant_group_id"));
        	   re.setTime(rs.getString("registration_participant_registration_time"));
        	   System.out.println(re.getGroupName());
        	   result.add(re);
           }
           rs.close();
           stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	return result;
	}
	
	public RegistrationExternal getRegistrationByParticipantID(int id){
		log.trace("START");
		RegistrationExternal re = new RegistrationExternal();
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select * from registrations where registration_participant_id=?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			if(rs.next()){
				re.setTime(rs.getString("registration_participant_registration_time"));
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		log.trace("END");
		return re;
	}
	
	public int getRegistrationsCount(){
		log.trace("START");
		int result = 0;
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select count(registration_id) from registrations");
			ResultSet rs = stmt.executeQuery();
			rs.beforeFirst();
			if(rs.next()){
				result = rs.getInt("count(registration_id)");
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
	
	public int[] getGroupsList(){
		int[] result = null;
		try{
			PreparedStatement stmt = connection
					.prepareStatement("select count(*) from (select distinct registration_participant_group_id from registrations) as res;");
			ResultSet rs = stmt.executeQuery();
			rs.first();
			result = new int[rs.getInt("count(*)")];
			stmt = connection
					.prepareStatement("select distinct registration_participant_group_id from "
							+ "(select * from registrations order by registration_participant_registration_time) as regs");
			rs = stmt.executeQuery();
			rs.beforeFirst();
			for(int i = 0; rs.next(); i++)
				result[i] = rs.getInt("registration_participant_group_id");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}

}
