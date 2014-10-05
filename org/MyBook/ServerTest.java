package org.MyBook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ServerTest {

	/**
	 * @param args
	 */
	
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("dyn/Config.properties"));
		} catch (IOException e) {
			log.info("Unable to locate configuration file.");
		}
		org.MyBook.Server.ServerConfigurationProperties config = new org.MyBook.Server.ServerConfigurationProperties();
		config.setOddsToProcess(properties.getProperty("OddsToProcess").split(","));
		
		
		
		org.MyBook.Server.ServerInstance servInstance = new org.MyBook.Server.ServerInstance(config);
		
		
	}

}
