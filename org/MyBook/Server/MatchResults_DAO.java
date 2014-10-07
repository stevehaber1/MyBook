package org.MyBook.Server;

public class MatchResults_DAO {
	private int matchResultID;
	public int getMatchResultID() {
		return matchResultID;
	}
	public void setMatchResultID(int matchResultID) {
		this.matchResultID = matchResultID;
	}
	private String matchDate;
	private String team1;
	private String team2;
	private String team1Score;
	private String team2Score;
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
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
	public String getTeam1Score() {
		return team1Score;
	}
	public void setTeam1Score(String team1Score) {
		this.team1Score = team1Score;
	}
	public String getTeam2Score() {
		return team2Score;
	}
	public void setTeam2Score(String team2Score) {
		this.team2Score = team2Score;
	}
	
}
