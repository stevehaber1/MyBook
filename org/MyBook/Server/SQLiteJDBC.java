package org.MyBook.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
	
	public static int ExecuteSelectGetRowCount(String SQL)
	{
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
	      c.setAutoCommit(false);
	      log.log(Level.CONFIG, "Opened database successfully");
	      
	      
	      stmt = c.createStatement();
	      ResultSet results = stmt.executeQuery(SQL);
	      int rowCount = 0;
	      while ( results.next() ) {
	          rowCount++;
	       }
	      log.info(SQL + " executed successfully. ");
	      
	      results.close();
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
	
	public static Match GetMatchFromSQL(String SQL)
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
	      
	      Match a = new Match();
	      
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
	          
	          
	       }
	      
	      log.info(SQL + " executed successfully. ");
	      
	      
	      
	      rs.close();
	      stmt.close();
	      c.commit();
	      c.close();
	      
	      
	      
	      log.log(Level.CONFIG, "Closed database successfully");
	      
	      if (rowCount > 1)
	      {
	    	  log.info("More than one game with the identical criteria was found. Returning null");
	    	  return null;
	      }
	      
	      return a;
	      
	    } catch ( Exception e ) {
	      log.info(e.toString());
	      return null;
	    }
	
	}
	
}
