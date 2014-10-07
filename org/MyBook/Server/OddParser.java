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
	
		
	ArrayList<Match_DAO> parseESPNNFLMatchesTable(){
		ArrayList<Match_DAO> matchList = new ArrayList<Match_DAO>();
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
	            
	            Match_DAO a = new Match_DAO();
	            
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
	            
	            
	            Match_DAO matchToCompare = SQLiteJDBC.ExecuteSelectGetMatchRow("SELECT * FROM Matches WHERE matchDate='"+a.getMatchDate()+"' AND team1 = '"+a.getTeam1()+"' AND team2= '"+a.getTeam2()+"'");
	            
	            if(matchToCompare != null)
	            {
	            	//row object was found in the database - lets compare it and run an update if its different
	            	//matchToCompare is the old object (stored in db)
	            	//a is the new object (geneted just before)
	            	boolean updatesMade = false;
	            	if(!a.getMlTeam1().equals(matchToCompare.getMlTeam1())) { updatesMade=true; log.info("Update found in the Team 1 ML for the "+a.getTeam1()+"/"+a.getTeam2()+" match."); SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET mlTeam1='"+a.getMlTeam1()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getMlTeam2().equals(matchToCompare.getMlTeam2())) { updatesMade=true; log.info("Update found in the Team 2 ML for the "+a.getTeam1()+"/"+a.getTeam2()+" match.");  SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET mlTeam2='"+a.getMlTeam2()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getOverUnder().equals(matchToCompare.getOverUnder()))  { updatesMade=true; log.info("Update found in the OverUnder for the "+a.getTeam1()+"/"+a.getTeam2()+" match."); SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET overunder='"+a.getOverUnder()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getOverUnder_OverLine().equals(matchToCompare.getOverUnder_OverLine()))  { updatesMade=true; log.info("Update found in the Over Line for the "+a.getTeam1()+"/"+a.getTeam2()+" match."); SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET overUnder_OverLine='"+a.getOverUnder_OverLine()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getOverUnder_UnderLine().equals(matchToCompare.getOverUnder_UnderLine())) { updatesMade=true; log.info("Update found in the Under Line for the "+a.getTeam1()+"/"+a.getTeam2()+" match.");  SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET overUnder_UnderLine='"+a.getOverUnder_UnderLine()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getSpreadTeam1().equals(matchToCompare.getSpreadTeam1()))  { updatesMade=true; log.info("Update found in the Team 1 Spread for the "+a.getTeam1()+"/"+a.getTeam2()+" match."); SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET spreadTeam1='"+a.getSpreadTeam1()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getSpreadTeam2().equals(matchToCompare.getSpreadTeam2()))  { updatesMade=true; log.info("Update found in the Team 2 Spread for the "+a.getTeam1()+"/"+a.getTeam2()+" match."); SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET spreadTeam2='"+a.getSpreadTeam2()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getSpreadTeam1ML().equals(matchToCompare.getSpreadTeam1ML()))  { updatesMade=true; log.info("Update found in the ML Spread for Team 1 for the "+a.getTeam1()+"/"+a.getTeam2()+" match."); SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET spreadTeam1ML='"+a.getSpreadTeam1ML()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	if(!a.getSpreadTeam2ML().equals(matchToCompare.getSpreadTeam2ML())) { updatesMade=true; log.info("Update found in the ML Spread for Team 2 for the "+a.getTeam1()+"/"+a.getTeam2()+" match.");  SQLiteJDBC.ExecuteInsertUpdate("UPDATE Matches SET spreadTeam2ML='"+a.getSpreadTeam2ML()+"' WHERE matchID="+matchToCompare.getMatchID()); }
	            	
	            	if(!updatesMade)log.info("No updates made to the "+a.getTeam1()+"/"+a.getTeam2()+" match.");
	            	
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
