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
			
			// TODO add MatchRecord to table
			insertMatchRecord.setString("matchID", x);
			insertMatchRecord.setString("timeOfMatch", x);
			insertMatchRecord.setString("player1ID", x);
			insertMatchRecord.setString("player2ID", x);
			insertMatchRecord.setString("player2IsBot", x);
			insertMatchRecord.setString("winnerIsPlayer1", x);
	
			insertMatchRecord.executeUpdate();
	
			// TODO add players to playerMatchRecord
			insertPlayerMatchRecord.setString("matchID", x);
			insertPlayerMatchRecord.setString("playerID", x);
			insertPlayerMatchRecord.setString("characterID", x);
			insertPlayerMatchRecord.setString("itemID", x);
			
			insertPlayerMatchRecord.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<MatchRecord> getAllMatches() {
		// TODO Auto-generated method stub
		return null;
	}

}
