package org.MyBook.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteJDBC {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	public static void ExecuteInsertUpdate(String SQL)
	{
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
	      c.setAutoCommit(false);
	      log.log(Level.CONFIG, "Opened database successfully");
	      
	      
	      stmt = c.createStatement();
	      int results = stmt.executeUpdate(SQL);
	      
	      log.info(SQL + " executed successfully. "+results+ " rows updated");
	      
	      stmt.close();
	      c.commit();
	      c.close();
	      
	      log.log(Level.CONFIG, "Closed database successfully");
	    } catch ( Exception e ) {
	      log.info(e.toString());
	    }
	    
	}
	
	public static Match_DAO ExecuteSelectGetMatchRow(String SQL)
	{
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
	      c.setAutoCommit(false);
	      log.log(Level.CONFIG, "Opened database successfully");
	      
	      
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery(SQL);
	      int rowCount = 0;
	      Match_DAO a = new Match_DAO();
	      
	      while ( rs.next() ) {
	    	  int matchID = rs.getInt("matchID");
	          String  matchDate = rs.getString("matchDate");
	          String  team1 = rs.getString("team1");
	          String  team2 = rs.getString("team2");
	          String  mlTeam1 = rs.getString("mlTeam1");
	          String  mlTeam2 = rs.getString("mlTeam2");
	          String  overUnder = rs.getString("overunder");
	          String  overUnder_OverLine = rs.getString("overUnder_OverLine");
	          String  overUnder_UnderLine = rs.getString("overUnder_UnderLine");
	          String  spreadTeam1 = rs.getString("spreadTeam1");
	          String  spreadTeam2 = rs.getString("spreadTeam2");
	          String  spreadTeam1ML = rs.getString("spreadTeam1ML");
	          String  spreadTeam2ML = rs.getString("spreadTeam2ML");
	          
	          
	          a.setMatchDate(matchDate);
	          a.setMatchID(matchID);
	          a.setMlTeam1(mlTeam1);
	          a.setMlTeam2(mlTeam2);
	          a.setOverUnder(overUnder);
	          a.setOverUnder_OverLine(overUnder_OverLine);
	          a.setOverUnder_UnderLine(overUnder_UnderLine);
	          a.setSpreadTeam1(spreadTeam1);
	          a.setSpreadTeam1ML(spreadTeam1ML);
	          a.setSpreadTeam2(spreadTeam2);
	          a.setSpreadTeam2ML(spreadTeam2ML);
	          a.setTeam1(team1);
	          a.setTeam2(team2);
	          
	          rowCount++;
	       }
	      
	      log.info(SQL + " executed successfully. ");
	      
	      rs.close();
	      stmt.close();
	      c.commit();
	      c.close();
	      
	      log.log(Level.CONFIG, "Closed database successfully");
	      if(rowCount == 1){
	    	  return a;
	      }
	      else {
	    	  return null;
	      }
	      
	    } catch ( Exception e ) {
	      log.info(e.toString());
	      return null;
	    }
	    
	}
	
	public static MatchResults_DAO ExecuteSelectGetMatchResultsRow(String SQL)
	{
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
	      c.setAutoCommit(false);
	      log.log(Level.CONFIG, "Opened database successfully");
	      
	      
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery(SQL);
	      int rowCount = 0;
	      MatchResults_DAO a = new MatchResults_DAO();
	      
	      while ( rs.next() ) {
	    	  //CREATE TABLE Results (resultsID integer primary key AUTOINCREMENT, matchDate varchar(20), team1 varchar(20), team2 varchar(20), team1Score varchar(20), team2Score varchar(20));
	    	  int matchResultID = rs.getInt("resultsID");
	          String matchDate = rs.getString("matchDate");
	          String team1 = rs.getString("team1");
	          String team2 = rs.getString("team2");
	          String team1Score = rs.getString("team1Score");
	          String team2Score = rs.getString("team2Score");
	          
	          a.setMatchResultID(matchResultID);
	          a.setMatchDate(matchDate);
	          a.setTeam1(team1);
	          a.setTeam1Score(team1Score);
	          a.setTeam2(team2);
	          a.setTeam2Score(team2Score);
	          
	          rowCount++;
	       }
	      
	      log.info(SQL + " executed successfully. ");
	      
	      rs.close();
	      stmt.close();
	      c.commit();
	      c.close();
	      
	      log.log(Level.CONFIG, "Closed database successfully");
	      if(rowCount == 1){
	    	  return a;
	      }
	      else {
	    	  return null;
	      }
	      
	    } catch ( Exception e ) {
	      log.info(e.toString());
	      return null;
	    }
	    
	}
	
	public static int SelectGetRowCount(String SQL)
	{
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
	      c.setAutoCommit(false);
	      log.log(Level.CONFIG, "Opened database successfully");
	      
	      
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery(SQL);
	      int rowCount = 0;
	            
	      while ( rs.next() ) {
	    	  
	          
	          rowCount++;
	       }
	      
	      log.info(SQL + " executed successfully. ");
	      
	      rs.close();
	      stmt.close();
	      c.commit();
	      c.close();
	      
	      log.log(Level.CONFIG, "Closed database successfully");
	      return rowCount;
	      
	    } catch ( Exception e ) {
	      log.info(e.toString());
	      return -1;
	    }
	    
	}
	
	public static ArrayList<Wagers_DAO> GetWagerList(){
		
		ArrayList<Wagers_DAO> wagers = new ArrayList<Wagers_DAO>();
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
	      c.setAutoCommit(false);
	      log.log(Level.CONFIG, "Opened database successfully");
	      
	      
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery("SELECT wagerID, matchID, amount, attribute, paidYet, userID FROM wagers WHERE paidYet='false'");
	      @SuppressWarnings("unused")
	      int rowCount = 0;
	      
	      
	      while ( rs.next() ) {
	    	  Wagers_DAO a = new Wagers_DAO();
	    	  
	    	  int wagerID = rs.getInt("wagerID");
	          int matchID = rs.getInt("matchID");
	          int userID = rs.getInt("userID");
	          double amount = rs.getDouble("amount");
	          String attribute = rs.getString("attribute");
	          boolean paidYet = rs.getBoolean("paidYet");
	          
	          a.setAmount(amount);
	          a.setAttribute(attribute);
	          a.setMatchID(matchID);
	          a.setPaidYet(paidYet);
	          a.setWagerID(wagerID);
	          a.setUserID(userID);
	         	          
	          rowCount++;
	          wagers.add(a);
	       }
	      
	      log.info("Wager selection executed successfully. ");
	      
	      rs.close();
	      stmt.close();
	      c.commit();
	      c.close();
	      
	      log.log(Level.CONFIG, "Closed database successfully");
	      
	    } catch ( Exception e ) {
	      log.info(e.toString());
	    }
	    return wagers;
		
		
	}
		
	
	
	
}
