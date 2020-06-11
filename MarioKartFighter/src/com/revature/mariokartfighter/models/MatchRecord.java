package com.revature.mariokartfighter.models;

import java.sql.Timestamp;

public class MatchRecord {
	private String matchID;
	private Timestamp timeOfMatch;
	private String player1ID;
	private String player1CharacterID;
	private String player1ItemID;
	private String player2ID;
	private String player2CharacterID;
	private String player2ItemID;
	private boolean player2IsBot;
	private String winnerID;
	
	public MatchRecord(String matchID, String player1id, String player1CharacterID,
			String player1ItemID, String player2id, String player2CharacterID, String player2ItemID,
			boolean player2IsBot, String winnerID) {
		this.matchID = matchID;
		this.timeOfMatch = new Timestamp(System.currentTimeMillis());
		player1ID = player1id;
		this.player1CharacterID = player1CharacterID;
		this.player1ItemID = player1ItemID;
		player2ID = player2id;
		this.player2CharacterID = player2CharacterID;
		this.player2ItemID = player2ItemID;
		this.player2IsBot = player2IsBot;
		this.winnerID = winnerID;
	}

	public MatchRecord(String matchID, Timestamp timeOfMatch, String player1id, String player1CharacterID,
			String player1ItemID, String player2id, String player2CharacterID, String player2ItemID,
			boolean player2IsBot, String winnerID) {
		this.matchID = matchID;
		this.timeOfMatch = timeOfMatch;
		player1ID = player1id;
		this.player1CharacterID = player1CharacterID;
		this.player1ItemID = player1ItemID;
		player2ID = player2id;
		this.player2CharacterID = player2CharacterID;
		this.player2ItemID = player2ItemID;
		this.player2IsBot = player2IsBot;
		this.winnerID = winnerID;
	}
	
	public String getMatchID() {
		return matchID;
	}

	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}

	public Timestamp getTimeOfMatch() {
		return timeOfMatch;
	}

	public void setTimeOfMatch(Timestamp timeOfMatch) {
		this.timeOfMatch = timeOfMatch;
	}

	public String getPlayer1ID() {
		return player1ID;
	}

	public void setPlayer1ID(String player1id) {
		player1ID = player1id;
	}

	public String getPlayer1CharacterID() {
		return player1CharacterID;
	}

	public void setPlayer1CharacterID(String player1CharacterID) {
		this.player1CharacterID = player1CharacterID;
	}

	public String getPlayer1ItemID() {
		return player1ItemID;
	}

	public void setPlayer1ItemID(String player1ItemID) {
		this.player1ItemID = player1ItemID;
	}

	public String getPlayer2ID() {
		return player2ID;
	}

	public void setPlayer2ID(String player2id) {
		player2ID = player2id;
	}

	public String getPlayer2CharacterID() {
		return player2CharacterID;
	}

	public void setPlayer2CharacterID(String player2CharacterID) {
		this.player2CharacterID = player2CharacterID;
	}

	public String getPlayer2ItemID() {
		return player2ItemID;
	}

	public void setPlayer2ItemID(String player2ItemID) {
		this.player2ItemID = player2ItemID;
	}

	public boolean isPlayer2IsBot() {
		return player2IsBot;
	}

	public void setPlayer2IsBot(boolean player2IsBot) {
		this.player2IsBot = player2IsBot;
	}

	public String getWinnerID() {
		return winnerID;
	}

	public void setWinnerID(String winnerID) {
		this.winnerID = winnerID;
	}
	
	
}
