package org.MyBook.Server;

import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MatchResults {
	
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	void UpdateMatchResults(){
		
		try {
	         Document doc = Jsoup.connect(ServerInstance.props.getNFLResultsURL()).get();
	         
	         Element nflWeekElement = doc.getElementsByClass("dates").select("ul").get(0).select(".current").get(0);
	         String nflWeek = nflWeekElement.text().split("k")[0]+"k "+ nflWeekElement.text().split("k")[1];
	         
	         System.out.println(nflWeek);
	         
	         Elements tablesElements = doc.getElementsByClass("match");

	         for(int z=0;z<tablesElements.size();z++){
	        	 Element tableElements = tablesElements.get(z);
	        	 	        	 
	        	 String[] resultsarr = tableElements.text().split(" ");
	        	 if(resultsarr[0].equals("Final")){
	        		 if(SQLiteJDBC.SelectGetRowCount("SELECT * FROM Results WHERE matchDate='"+nflWeek+"' AND ((team1='"+resultsarr[1]+"' AND team2='"+resultsarr[3]+"') OR (team1='"+resultsarr[3]+"' AND team2='"+resultsarr[1]+"'))") <= 0){
	        			 SQLiteJDBC.ExecuteInsertUpdate("INSERT INTO Results (matchDate, team1, team2, team1Score, team2Score) VALUES ('"+nflWeek+"', '"+resultsarr[1]+"', '"+resultsarr[3]+"', '"+resultsarr[2]+"', '"+resultsarr[4]+"')");
	        		 }
	        	 }
	        	 
	         }
		}
		catch(Exception e)
		{
			log.info(e.toString()+e.getStackTrace());
		}
		
	}
}
