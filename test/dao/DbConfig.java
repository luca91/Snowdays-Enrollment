package com.snowdays_enrollment.test.dao;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
* Class used to get configuration for DB connection from file DB_Config.properties
* 
* @author Luca Barazzuol
*/
public class DbConfig {
	
	// commons logging references
	static Logger log = Logger.getLogger(GroupDaoTest.class.getName());
	


	/**
	 * @uml.property  name="dB_URL"
	 */
	private String DB_URL;
	/**
	 * @uml.property  name="dB_JDBC_DRIVER"
	 */
	private String DB_JDBC_DRIVER;
	/**
	 * @uml.property  name="dB_USER"
	 */
	private String DB_USER;
	/**
	 * @uml.property  name="dB_PASSWORD"
	 */
	private String DB_PASSWORD;
	
	/**
	* Constructor
	* 
	*/
	public DbConfig(){		
		log.debug("START");
		ResourceBundle rb= ResourceBundle.getBundle("DB_Config");
		DB_JDBC_DRIVER = rb.getString("DB_JDBC_DRIVER");
		DB_URL = rb.getString("DB_URL");
		DB_USER = rb.getString("DB_USER");
		DB_PASSWORD = rb.getString("DB_PASSWD");
		log.debug("END");
	}
	
	/**
	 * @return
	 * @uml.property  name="dB_URL"
	 */
	public String getDB_URL() {
		return DB_URL;
	}

	/**
	 * @param dB_URL
	 * @uml.property  name="dB_URL"
	 */
	public void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}

	/**
	 * @return
	 * @uml.property  name="dB_JDBC_DRIVER"
	 */
	public String getDB_JDBC_DRIVER() {
		return DB_JDBC_DRIVER;
	}

	/**
	 * @param dB_JDBC_DRIVER
	 * @uml.property  name="dB_JDBC_DRIVER"
	 */
	public void setDB_JDBC_DRIVER(String dB_JDBC_DRIVER) {
		DB_JDBC_DRIVER = dB_JDBC_DRIVER;
	}

	/**
	 * @return
	 * @uml.property  name="dB_USER"
	 */
	public String getDB_USER() {
		return DB_USER;
	}

	/**
	 * @param dB_USER
	 * @uml.property  name="dB_USER"
	 */
	public void setDB_USER(String dB_USER) {
		DB_USER = dB_USER;
	}

	/**
	 * @return
	 * @uml.property  name="dB_PASSWORD"
	 */
	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	/**
	 * @param dB_PASSWORD
	 * @uml.property  name="dB_PASSWORD"
	 */
	public void setDB_PASSWORD(String dB_PASSWORD) {
		DB_PASSWORD = dB_PASSWORD;
	}
}
