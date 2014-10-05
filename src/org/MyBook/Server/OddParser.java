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
	            
	            
	            
	            System.out.println(a);
	         }

	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		return null;
	}

}
