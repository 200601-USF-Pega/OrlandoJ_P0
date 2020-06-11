package com.revature.mariokartfighter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.revature.mariokartfighter.models.MatchRecord;

public class MatchRecordRepoDB implements IMatchRecordRepo {
	Connection connection;
	
	public MatchRecordRepoDB() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://ruby.db.elephantsql.com:5432", 
					"brdzdjzb", "l7Lh2FHoFuFdz4Gf1h5j0-9LSj78BeJ8");
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public MatchRecord addMatchRecord(MatchRecord match) {
		try {			
			PreparedStatement insertMatchRecord = connection.prepareStatement(
					"INSERT INTO matchRecord ("
					+ "matchID, timeOfMatch, player1ID, player2ID, player2IsBot, winnerIsPlayer1) "
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			PreparedStatement insertPlayerMatchRecord = connection.prepareStatement(
					"INSERT INTO playerMatchRecord ("
					+ "matchID, playerID, characterID, itemID) "
					+ "VALUES (?, ?, ?, ?)");

			insertMatchRecord.setString(1, match.getMatchID());
			insertMatchRecord.setTimestamp(2, match.getTimeOfMatch());
			insertMatchRecord.setString(3, match.getPlayer1ID());
			insertMatchRecord.setString(4, match.getPlayer2ID());
			insertMatchRecord.setBoolean(5, match.isPlayer2IsBot());
			if (match.getWinnerID().equals(match.getPlayer1ID())) {
				insertMatchRecord.setBoolean(6, true);				
			} else {
				insertMatchRecord.setBoolean(6, false);	
			}
	
			insertPlayerMatchRecord.setString(1, match.getMatchID());
			insertPlayerMatchRecord.setString(2, match.getPlayer1ID());
			insertPlayerMatchRecord.setString(3, match.getPlayer1CharacterID());
			insertPlayerMatchRecord.setString(4, match.getPlayer1ItemID());
			
			insertMatchRecord.executeUpdate();
			insertPlayerMatchRecord.executeUpdate();
			
			insertPlayerMatchRecord.setString(2, match.getPlayer2ID());
			insertPlayerMatchRecord.setString(3, match.getPlayer2CharacterID());
			insertPlayerMatchRecord.setString(4, match.getPlayer2ItemID());
			insertPlayerMatchRecord.executeUpdate();
			
			return match;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MatchRecord> getAllMatches() {
		// TODO Auto-generated method stub
		return null;
	}

}
