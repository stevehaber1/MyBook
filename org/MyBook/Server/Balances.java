package org.MyBook.Server;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Balances {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	void UpdateAccountBalances()
	{
		ArrayList<Wagers_DAO> outstandingWagers = SQLiteJDBC.GetWagerList();
		
		for(Wagers_DAO wager : outstandingWagers){
			log.info("Parsing results for WagerID "+wager.getWagerID());
			Match_DAO matchInfo = SQLiteJDBC.ExecuteSelectGetMatchRow("SELECT * FROM Matches WHERE matchID='"+wager.getMatchID()+"'");
			MatchResults_DAO matchResultsInfo = SQLiteJDBC.ExecuteSelectGetMatchResultsRow("SELECT * FROM Results WHERE matchDate='"+matchInfo.getMatchDate()+"' AND ((team1='"+matchInfo.getTeam1()+"' AND team2='"+matchInfo.getTeam2()+"') OR (team1='"+matchInfo.getTeam2()+"' AND team2='"+matchInfo.getTeam1()+"'))");
			
			switch(wager.getAttribute()){
			case("over"):
				//Over needs to calculate if the match total was > than the sum of team1 + team2 scores
				int team1Score = Integer.parseInt(matchResultsInfo.getTeam1Score());
				int team2Score = Integer.parseInt(matchResultsInfo.getTeam2Score());
				int total = team1Score + team2Score;
				
				break;
			case("under"):
				break;
			case("mlTeam1"):
				break;
			case("mlTeam2"):
				break;
			case("spreadTeam1"):
				break;
			case("spreadTeam2"):
				break;
				
			}
			
		}
		
	}
}
