package org.MyBook.Server;

public class Match {
	private String matchDate;
	
	private int matchID;
	
	public int getMatchID() {
		return matchID;
	}
	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	private String team1;
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getMlTeam1() {
		return mlTeam1;
	}
	public void setMlTeam1(String mlTeam1) {
		this.mlTeam1 = mlTeam1;
	}
	public String getMlTeam2() {
		return mlTeam2;
	}
	public void setMlTeam2(String mlTeam2) {
		this.mlTeam2 = mlTeam2;
	}
	public String getSpreadTeam1() {
		return spreadTeam1;
	}
	public void setSpreadTeam1(String spreadTeam1) {
		this.spreadTeam1 = spreadTeam1;
	}
	public String getSpreadTeam2() {
		return spreadTeam2;
	}
	public void setSpreadTeam2(String spreadTeam2) {
		this.spreadTeam2 = spreadTeam2;
	}
	public String getOverUnder() {
		return overUnder;
	}
	public void setOverUnder(String overUnder) {
		this.overUnder = overUnder;
	}
	public String getOverUnder_OverLine() {
		return overUnder_OverLine;
	}
	public void setOverUnder_OverLine(String overUnder_OverLine) {
		this.overUnder_OverLine = overUnder_OverLine;
	}
	public String getOverUnder_UnderLine() {
		return overUnder_UnderLine;
	}
	public void setOverUnder_UnderLine(String overUnder_UnderLine) {
		this.overUnder_UnderLine = overUnder_UnderLine;
	}
	private String team2;
	
	private String mlTeam1;
	private String mlTeam2;
	
	private String spreadTeam1;
	private String spreadTeam2;
	
	private String overUnder;
	private String overUnder_OverLine;
	private String overUnder_UnderLine;
	
	private String spreadTeam1ML;
	public String getSpreadTeam1ML() {
		return spreadTeam1ML;
	}
	public void setSpreadTeam1ML(String spreadTeam1ML) {
		this.spreadTeam1ML = spreadTeam1ML;
	}
	public String getSpreadTeam2ML() {
		return spreadTeam2ML;
	}
	public void setSpreadTeam2ML(String spreadTeam2ML) {
		this.spreadTeam2ML = spreadTeam2ML;
	}
	private String spreadTeam2ML;
	
	public String toString(){
		return "\n\tTeam 1: "+this.getTeam1()+"\n\tTeam 2: "+this.getTeam2()+"\n\tML Team 1: "+this.getMlTeam1()+"\n\tML Team 2: "+this.getMlTeam2()+
				"\n\tSpread Team 1: "+this.getSpreadTeam1()+ " ("+this.getSpreadTeam1ML()+")\n\tSpread Team 2: "+this.getSpreadTeam2()+" ("+this.getSpreadTeam2ML()+
				")\n\tOver/Under: "+this.getOverUnder()+ " (o: "+this.getOverUnder_OverLine()+", u: "+this.getOverUnder_UnderLine()+")";
	}
	
	
}
