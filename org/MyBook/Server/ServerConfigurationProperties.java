package org.MyBook.Server;

public class ServerConfigurationProperties {
	private String NBAOddsURL;

	public String getNBAOddsURL() {
		return NBAOddsURL;
	}

	public void setNBAOddsURL(String nBAOddsURL) {
		NBAOddsURL = nBAOddsURL;
	}
	
	private String[] OddsToProcess;

	public String[] getOddsToProcess() {
		return OddsToProcess;
	}

	public void setOddsToProcess(String[] oddsToProcess) {
		OddsToProcess = oddsToProcess;
	}
	
	private String NFLOddsURL;

	public String getNFLOddsURL() {
		return NFLOddsURL;
	}

	public void setNFLOddsURL(String nFLOddsURL) {
		NFLOddsURL = nFLOddsURL;
	}
	
	private String NFLOddsURLType;
	private String NBAOddsURLType;

	public String getNFLOddsURLType() {
		return NFLOddsURLType;
	}

	public void setNFLOddsURLType(String nFLOddsURLType) {
		NFLOddsURLType = nFLOddsURLType;
	}

	public String getNBAOddsURLType() {
		return NBAOddsURLType;
	}

	public void setNBAOddsURLType(String nBAOddsURLType) {
		NBAOddsURLType = nBAOddsURLType;
	}
	
	private int UpdateInterval;

	public int getUpdateInterval() {
		return UpdateInterval;
	}

	public void setUpdateInterval(int updateInterval) {
		UpdateInterval = updateInterval;
	}
	private String NFLResultsURL;

	public String getNFLResultsURL() {
		return NFLResultsURL;
	}

	public void setNFLResultsURL(String nFLResultsURL) {
		NFLResultsURL = nFLResultsURL;
	}
	
}
