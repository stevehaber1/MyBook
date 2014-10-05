package org.MyBook.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OddParser {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	public ArrayList<Match> LoadESPNNFLOdds() {
		// TODO Auto-generated method stub
		ArrayList<Match> matches = new ArrayList<Match>();
		log.info("Initial download of NFL matches...");
		parseESPNNFLMatchesTable();
		return matches;
		
	}

	public ArrayList<Match> UpdateNFLData(ArrayList<Match> nflmatches) {
		log.info("Updating NLF matches with new information");
		
		return nflmatches;
		
	}
	
	private ArrayList<Match> parseESPNNFLMatchesTable(){
		ArrayList<Match> matchList = new ArrayList<Match>();
		try {
	         Document doc = Jsoup.connect(ServerInstance.props.getNFLOddsURL()).get();
	         
	         Element nflWeek = doc.select("option[selected]").get(0);
	         
	         System.out.println(nflWeek.text());
	         
	         Elements tableElements = doc.select("table");

	         //Elements tableHeaderEles = tableElements.select("thead tr th");
	         //System.out.println("headers");
	         //for (int i = 0; i < tableHeaderEles.size(); i++) {
	         //   System.out.println(tableHeaderEles.get(i).text());
	         //}
	         System.out.println();

	         Elements tableRowElements = tableElements.select(":not(thead) tr");

	         //this should normally start at 0, but the first row is blank in this instance
	         for (int i = 1; i < tableRowElements.size(); i++) {
	            Element row = tableRowElements.get(i);
	            Elements rowItems = row.select("td");
	            
	            Match a = new Match();
	            
	            a.setMatchDate(nflWeek.text());
	            
	            a.setTeam1(rowItems.get(0).text().split(" ")[0]);
	            a.setTeam2(rowItems.get(0).text().split(" ")[1]);
	            
	            a.setMlTeam1(rowItems.get(1).text().split(" ")[0]);
	            a.setMlTeam2(rowItems.get(1).text().split(" ")[1]);
	            
	            a.setSpreadTeam1(rowItems.get(2).text().split(" ")[0]);
	            a.setSpreadTeam1ML(rowItems.get(2).text().split(" ")[1].replaceAll("(\\(|\\))", ""));
	            
	            a.setSpreadTeam2(rowItems.get(2).text().split(" ")[2]);
	            a.setSpreadTeam2ML(rowItems.get(2).text().split(" ")[3].replaceAll("(\\(|\\))", ""));
	            
	            a.setOverUnder(rowItems.get(3).text().split(" ")[0]);
	            a.setOverUnder_OverLine(rowItems.get(3).text().split(" ")[2].replace(",",""));  
	            a.setOverUnder_UnderLine(rowItems.get(3).text().split(" ")[4].replace(")",""));
	            
	            if(SQLiteJDBC.ExecuteSelectGetRowCount("SELECT * FROM Matches WHERE matchDate='"+a.getMatchDate()+"' AND team1 = '"+a.getTeam1()+"' AND team2= '"+a.getTeam2()+"'") > 0)
	            {
	            	//Execute update
	            	Match matchToCompare = SQLiteJDBC.GetMatchFromSQL("SELECT * FROM Matches WHERE matchDate='"+a.getMatchDate()+"' AND team1 = '"+a.getTeam1()+"' AND team2= '"+a.getTeam2()+"'");
	            	
	            	if(!matchToCompare.getOverUnder().equals(a.getOverUnder())){
	            		SQLiteJDBC.ExecuteInsertUpdate(SQL)
	            	}
	            	
	            	log.info("Execute update");
	            }
	            else 
	            {
	            	//execute insert
	            	String SQL = "INSERT INTO Matches (matchDate, team1, team2, mlTeam1, mlTeam2, overunder, overUnder_OverLine, overUnder_UnderLine, spreadTeam1, spreadTeam2, spreadTeam1ML, spreadTeam2ML)" +
	            		" VALUES ('"+a.getMatchDate()+"', '"+a.getTeam1()+"', '"+a.getTeam2()+"', '"+a.getMlTeam1()+"', '"+a.getMlTeam2()+"', '"+a.getOverUnder()+"', '"+a.getOverUnder_OverLine()+"', '"+a.getOverUnder_UnderLine()+"', '"+a.getSpreadTeam1()+"', '"+a.getSpreadTeam2()+"', '"+a.getSpreadTeam1ML()+"', '"+a.getSpreadTeam2ML()+"')";
	            
	            	SQLiteJDBC.ExecuteInsertUpdate(SQL);
	            	
	            }
	            System.out.println(a);
	            //System.out.println("\t"+SQL);
	            
	            matchList.add(a);
	         }

	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		return matchList;
	}

}
