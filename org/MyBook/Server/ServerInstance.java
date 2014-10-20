package org.MyBook.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.Timer;

public class ServerInstance {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	public static ServerConfigurationProperties props;
	
	@SuppressWarnings("unused")
	private ArrayList<Match_DAO> nflmatches;
	
	private OddParser oddParser;
	private MatchResults matchResults;
	private Balances accountBalances;
	
	public ServerInstance(org.MyBook.Server.ServerConfigurationProperties configProps){
		log.info("Starting server...");
		props = configProps;
		log.info("Beginning configuration of server");
		
		nflmatches = new ArrayList<Match_DAO>();
		oddParser = new OddParser();
		matchResults = new MatchResults();
		accountBalances = new Balances();
		
		StartupProcess();
		StartDaemon();
		
		log.info("Server startup complete.");
	}
	
	private void StartDaemon() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				oddParser.parseESPNNFLMatchesTable();
				matchResults.UpdateMatchResults();
				accountBalances.UpdateAccountBalances();
			}

		}, props.getUpdateInterval(),props.getUpdateInterval());
		
	}
	
	private void StartupProcess()
	{
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("dyn/Config.properties"));
		} catch (IOException e) {
			log.info("Unable to locate configuration file.");
		}
		
		for(String odd : props.getOddsToProcess())
		{
			log.info("Set odds URL to "+properties.getProperty(odd+"OddsURL") + " for sport "+odd);
			switch(odd){
				case "NFL":
					props.setNFLOddsURL(properties.getProperty(odd+"OddsURL"));
					props.setNFLOddsURLType(properties.getProperty(odd+"OddsURLType"));
					props.setNFLResultsURL(properties.getProperty(odd+"ResultsURL"));
					log.info("Downloading initial NFL Odds Information from "+ props.getNFLOddsURLType());
					
					
					if(props.getNFLOddsURLType().equals("ESPN")) { nflmatches = oddParser.parseESPNNFLMatchesTable(); }
					matchResults.UpdateMatchResults();
					
					break;
				case "NBA":
					//props.setNBAOddsURL(properties.getProperty(odd+"OddsURL"));
					//props.setNBAOddsURLType(properties.getProperty(odd+"OddsURLType"));
					//if(props.getNBAOddsURLType() == "ESPN") { LoadESPN(); }
					break;
				default:
					log.info("Unable to find sport "+odd + " in configuration.");
			}
			
			
		}
		
		props.setUpdateInterval(Integer.parseInt(properties.getProperty("UpdateInterval")));
		log.info("Update interval has been set to "+props.getUpdateInterval() + " for all odds.");
		
		
	}
	
	
}
