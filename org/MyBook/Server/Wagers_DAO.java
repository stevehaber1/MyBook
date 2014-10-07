package org.MyBook.Server;

public class Wagers_DAO {
	private int wagerID;
	private int matchID;
	private double amount;
	private String attribute;
	private boolean paidYet;
	public int getWagerID() {
		return wagerID;
	}
	public void setWagerID(int wagerID) {
		this.wagerID = wagerID;
	}
	public int getMatchID() {
		return matchID;
	}
	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public boolean isPaidYet() {
		return paidYet;
	}
	public void setPaidYet(boolean paidYet) {
		this.paidYet = paidYet;
	}
	private int userID;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
