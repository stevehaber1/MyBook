package org.MyBook.Server;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Balances {
	public enum PosNeg
	{
		POSITIVE, NEGATIVE
	}
	
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	void UpdateAccountBalances()
	{
		ArrayList<Wagers_DAO> outstandingWagers = SQLiteJDBC.GetWagerList();
		
		for(Wagers_DAO wager : outstandingWagers){
			log.info("Wager "+wager.getWagerID()+" - Parsing results.");
			Match_DAO matchInfo = SQLiteJDBC.ExecuteSelectGetMatchRow("SELECT * FROM Matches WHERE matchID='"+wager.getMatchID()+"'");
			MatchResults_DAO matchResultsInfo = SQLiteJDBC.ExecuteSelectGetMatchResultsRow("SELECT * FROM Results WHERE matchDate='"+matchInfo.getMatchDate()+"' AND ((team1='"+matchInfo.getTeam1()+"' AND team2='"+matchInfo.getTeam2()+"') OR (team1='"+matchInfo.getTeam2()+"' AND team2='"+matchInfo.getTeam1()+"'))");
			
			//Over needs to calculate if the match total was > than the sum of team1 + team2 scores
			int team1Score = Integer.parseInt(matchResultsInfo.getTeam1Score());
			int team2Score = Integer.parseInt(matchResultsInfo.getTeam2Score());
			int matchTotal = team1Score + team2Score;
			
			log.info("Wager "+wager.getWagerID()+ " - Parsing "+wager.getAttribute());
			
			String useOverUnderML = SQLiteJDBC.GetConfigValue("UseOverUnderMoneyline");
			String useSpreadML = SQLiteJDBC.GetConfigValue("UseSpreadMoneyline");
			
			PosNeg t1SpreadType = null,t2SpreadType = null, overUnderLine = null,mlTeam1 = null, mlTeam2 = null, t1spreadML=null, t2spreadML=null;
			if(matchInfo.getSpreadTeam1().charAt(0)=='+')t1SpreadType = PosNeg.POSITIVE;
			if(matchInfo.getSpreadTeam1().charAt(0)=='-')t1SpreadType = PosNeg.NEGATIVE;
			if(matchInfo.getSpreadTeam2().charAt(0)=='+')t2SpreadType = PosNeg.POSITIVE;
			if(matchInfo.getSpreadTeam2().charAt(0)=='-')t2SpreadType = PosNeg.NEGATIVE;
			if(matchInfo.getOverUnder_OverLine().charAt(0) == '+')overUnderLine = PosNeg.POSITIVE;
			if(matchInfo.getOverUnder_OverLine().charAt(0) == '-')overUnderLine = PosNeg.NEGATIVE;
			if(matchInfo.getMlTeam1().charAt(0) == '+')mlTeam1 = PosNeg.POSITIVE;
			if(matchInfo.getMlTeam1().charAt(0) == '-')mlTeam1 = PosNeg.NEGATIVE;
			if(matchInfo.getMlTeam2().charAt(0) == '+')mlTeam2 = PosNeg.POSITIVE;
			if(matchInfo.getMlTeam2().charAt(0) == '-')mlTeam2 = PosNeg.NEGATIVE;
			if(matchInfo.getSpreadTeam1ML().charAt(0) == '+')t1spreadML = PosNeg.POSITIVE;
			if(matchInfo.getSpreadTeam1ML().charAt(0) == '-')t1spreadML = PosNeg.NEGATIVE;
			if(matchInfo.getSpreadTeam2ML().charAt(0) == '+')t2spreadML = PosNeg.POSITIVE;
			if(matchInfo.getSpreadTeam2ML().charAt(0) == '-')t2spreadML = PosNeg.NEGATIVE;
			
			
			switch(wager.getAttribute()){
			
			case("over"):
				
				
				if(matchTotal > Double.parseDouble(matchInfo.getOverUnder())){
					log.info("Wager "+wager.getWagerID()+" - Match was over, and user "+wager.getUserID()+" took the over. Winner.");
					//Winner logic goes here
					if(useOverUnderML.equals("true")){
						if(overUnderLine == PosNeg.POSITIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getOverUnder_OverLine().substring(1)))/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance+gainlooseamt));
						}
						if(overUnderLine == PosNeg.NEGATIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getOverUnder_OverLine().substring(1)))/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance+gainlooseamt));
						}
					}
					
					
				}else if(matchTotal < Double.parseDouble(matchInfo.getOverUnder())){
					log.info("Wager "+wager.getWagerID()+" - Match was under, and user "+wager.getUserID()+" took the over. Loser.");
					//Looser logic goes here
					if(useOverUnderML.equals("true")){
						if(overUnderLine == PosNeg.POSITIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getOverUnder_OverLine().substring(1)))/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
						}
						if(overUnderLine == PosNeg.NEGATIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getOverUnder_OverLine().substring(1)))/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
						}
					}
					
				}else if(matchTotal == Double.parseDouble(matchInfo.getOverUnder())){
					log.info("Wager "+wager.getWagerID()+" - Match was equal to over/under. It's a push.");
					//Do nothing
				}
			
				break;
				
				
				
				
				
			case("under"):
				//Over needs to calculate if the match total was > than the sum of team1 + team2 scores
				team1Score = Integer.parseInt(matchResultsInfo.getTeam1Score());
				team2Score = Integer.parseInt(matchResultsInfo.getTeam2Score());
				matchTotal = team1Score + team2Score;
				
				if(matchTotal > Double.parseDouble(matchInfo.getOverUnder())){
					log.info("Wager "+wager.getWagerID()+ " - Match was over, and user "+wager.getUserID()+" took the under. Loser.");
					//Looser logic goes here
					if(useOverUnderML.equals("true")){
						if(overUnderLine == PosNeg.POSITIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * 100)/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
						}
						if(overUnderLine == PosNeg.NEGATIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getOverUnder_UnderLine().substring(1)))/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
						}
					}
					
				}else if(matchTotal < Double.parseDouble(matchInfo.getOverUnder())){
					log.info("Wager "+wager.getWagerID()+" - Match was under, and user "+wager.getUserID()+" took the under. Winner.");
					//Winner logic goes here
					if(useOverUnderML.equals("true")){
						if(overUnderLine == PosNeg.POSITIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getOverUnder_UnderLine().substring(1)))/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance + gainlooseamt));
						}
						if(overUnderLine == PosNeg.NEGATIVE){
							double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
							double gainlooseamt = (wager.getAmount() * 100)/100;
							log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance + gainlooseamt));
						}
					}
					
				}else if(matchTotal == Double.parseDouble(matchInfo.getOverUnder())){
					log.info("Wager "+wager.getWagerID()+" - Match was equal to over/under. It's a push.");
				}
				break;
				
				
				
				
				
			case("mlTeam1"):
				if(team1Score > team2Score){
					log.info("Wager "+wager.getWagerID()+" - User won his moneyline bet");
					//Winner logic goes here
					if(mlTeam1 == PosNeg.POSITIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getMlTeam1().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance + gainlooseamt));
					}
					if(mlTeam1 == PosNeg.NEGATIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * 100)/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance + gainlooseamt));
					}
					
				} else if(team1Score < team2Score){
					log.info("Wager "+wager.getWagerID()+" - User lost his moneyline bet");
					//Looser logic goes here
					if(mlTeam1 == PosNeg.POSITIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * 100)/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
					}
					if(mlTeam1 == PosNeg.NEGATIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getMlTeam1().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
					}
					
				}
				break;
				
				
				
				
			case("mlTeam2"):
				
				if(team1Score > team2Score){
					log.info("Wager "+wager.getWagerID()+" - User lost his moneyline bet");
					//Looser logic goes here
					if(mlTeam2 == PosNeg.POSITIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * 100)/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
					}
					if(mlTeam2 == PosNeg.NEGATIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getMlTeam2().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " lost. Balance updated to "+(balance - gainlooseamt));
					}
					
				} else if(team1Score < team2Score){
					log.info("Wager "+wager.getWagerID()+" - User won his moneyline bet");
					//Winner logic goes here
					if(mlTeam2 == PosNeg.POSITIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * Double.parseDouble(matchInfo.getMlTeam2().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance + gainlooseamt));
					}
					if(mlTeam2 == PosNeg.NEGATIVE){
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						double gainlooseamt = (wager.getAmount() * 100)/100;
						log.info("Wager "+wager.getWagerID()+" - ***"+gainlooseamt + " won. Balance updated to "+(balance + gainlooseamt));
					}
				}
				break;
				
				
				
				
				
			case("spreadTeam1"):
				double spreadValueT1 = Double.parseDouble(matchInfo.getSpreadTeam1().substring(1));
				boolean win1=false,push1=false;
				
				
				if(t1SpreadType == PosNeg.NEGATIVE && ((team2Score+spreadValueT1)<team1Score)){
					//favorite wins
					log.info("Wager "+wager.getWagerID()+" - "+team2Score+ " + " + matchInfo.getSpreadTeam1()+" < "+team1Score+". Favorite Covers Spread. Winner.");
					win1=true;
				}
				else if(t1SpreadType == PosNeg.NEGATIVE && ((team2Score + spreadValueT1)>team1Score)){
					//favorite looses
					log.info("Wager "+wager.getWagerID()+" - "+team2Score+ " + " + matchInfo.getSpreadTeam1()+" > "+team1Score+". Favorite Doesn't Cover Spread. Looser.");
					win1=false;
				}
				else if(t1SpreadType == PosNeg.POSITIVE && ((team1Score + spreadValueT1)> team2Score)){
					//under dog wins
					log.info("Wager "+wager.getWagerID()+" - "+team1Score+ " + " + matchInfo.getSpreadTeam1()+" > "+team2Score+". Underdog Beats Spread. Winner.");
					win1=true;
				}
				else if(t1SpreadType == PosNeg.POSITIVE && ((team1Score + spreadValueT1)<team2Score)){
					//under dog looses
					log.info("Wager "+wager.getWagerID()+" - "+team1Score+ " + " + matchInfo.getSpreadTeam1()+" < "+team2Score+". Underdog Doesn't Beat Spread. Looser.");
					win1=false;
				}
				else{
					//gotta be a push
					log.info("Wager "+wager.getWagerID()+" - "+"It's a push. Spread was "+matchInfo.getSpreadTeam1()+", T1Total = "+team1Score+", T2Total = "+team2Score);
					push1=true;
				}
			
				if(useSpreadML.equals("true")){
					//Balance logic
					double amtGainLost1 =0.0;
					if(win1 && t1spreadML == PosNeg.POSITIVE){
						//Win by ML amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost1 = (wager.getAmount() * Double.parseDouble(matchInfo.getSpreadTeam1ML().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - User won "+amtGainLost1+". Balance updated to "+(amtGainLost1+balance));
					}
					else if(win1 && t1spreadML == PosNeg.NEGATIVE){
						//Win by wager amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost1 = wager.getAmount();
						log.info("Wager "+wager.getWagerID()+" - User won "+amtGainLost1+". Balance updated to "+(amtGainLost1+balance));
					}
					else if(!win1 && t1spreadML == PosNeg.POSITIVE){
						//Loose by wager amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost1 = wager.getAmount();
						log.info("Wager "+wager.getWagerID()+" - User lost "+amtGainLost1+". Balance updated to "+(balance-amtGainLost1));
					}
					else if(!win1 && t1spreadML == PosNeg.NEGATIVE){
						//Loose by ML amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost1 = (wager.getAmount() * Double.parseDouble(matchInfo.getSpreadTeam1ML().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - User lost "+amtGainLost1+". Balance updated to "+(balance-amtGainLost1));
					}else if(push1){
						//Nobody wins.
						log.info("Wager "+wager.getWagerID()+" - Was a push. No changes made.");
						
					}
				}
				
				
				
				
				
				break;
				
				
				
				
			case("spreadTeam2"):
				double spreadValueT2 = Double.parseDouble(matchInfo.getSpreadTeam1().substring(1));
				boolean win2=false,push2=false;

				if(t2SpreadType == PosNeg.NEGATIVE && ((team1Score+spreadValueT2)<team2Score)){
					//favorite wins
					log.info("Wager "+wager.getWagerID()+" - "+team1Score+ " + " + matchInfo.getSpreadTeam2()+" < "+team2Score+". Favorite Covers Spread. Winner.");
					win2=true;
				}
				else if(t2SpreadType == PosNeg.NEGATIVE && ((team1Score + spreadValueT2)>team2Score)){
					//favorite looses
					log.info("Wager "+wager.getWagerID()+" - "+team1Score+ " + " + matchInfo.getSpreadTeam2()+" > "+team2Score+". Favorite Doesn't Cover Spread. Looser.");
					win2=false;
				}
				else if(t2SpreadType == PosNeg.POSITIVE && ((team2Score + spreadValueT2)> team1Score)){
					//under dog wins
					log.info("Wager "+wager.getWagerID()+" - "+team2Score+ " + " + matchInfo.getSpreadTeam2()+" > "+team1Score+". Underdog Beats Spread. Winner.");
					win2=true;
				}
				else if(t2SpreadType == PosNeg.POSITIVE && ((team2Score + spreadValueT2)<team1Score)){
					//under dog looses
					log.info("Wager "+wager.getWagerID()+" - "+team2Score+ " + " + matchInfo.getSpreadTeam2()+" < "+team1Score+". Underdog Doesn't Beat Spread. Looser.");
					win2=false;
				}
				else{
					//gotta be a push
					log.info("Wager "+wager.getWagerID()+" - "+"It's a push. Spread was "+matchInfo.getSpreadTeam1()+", T1Total = "+team1Score+", T2Total = "+team2Score);
					push2=true;
				}
				
				
				if(useSpreadML.equals("true")){
					//Balance logic
					double amtGainLost2 =0.0;
					if(win2 && t2spreadML == PosNeg.POSITIVE){
						//Win by ML amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost2 = (wager.getAmount() * Double.parseDouble(matchInfo.getSpreadTeam2ML().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - User won "+amtGainLost2+". Balance updated to "+(amtGainLost2+balance));
					}
					else if(win2 && t2spreadML == PosNeg.NEGATIVE){
						//Win by wager amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost2 = wager.getAmount();
						log.info("Wager "+wager.getWagerID()+" - User won "+amtGainLost2+". Balance updated to "+(amtGainLost2+balance));
					}
					else if(!win2 && t2spreadML == PosNeg.POSITIVE){
						//Loose by wager amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost2 = wager.getAmount();
						log.info("Wager "+wager.getWagerID()+" - User lost "+amtGainLost2+". Balance updated to "+(balance-amtGainLost2));
					}
					else if(!win2 && t2spreadML == PosNeg.NEGATIVE){
						//Loose by ML amt
						double balance = SQLiteJDBC.GetUserAccountBalance(wager.getUserID());
						amtGainLost2 = (wager.getAmount() * Double.parseDouble(matchInfo.getSpreadTeam2ML().substring(1)))/100;
						log.info("Wager "+wager.getWagerID()+" - User lost "+amtGainLost2+". Balance updated to "+(balance-amtGainLost2));
					}else if(push2){
						//Nobody wins.
						log.info("Wager "+wager.getWagerID()+" - Was a push. No changes made.");
						
					}
				}
				
				break;
				
			}
			
		}
		
	}
}