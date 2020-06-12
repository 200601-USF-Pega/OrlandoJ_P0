package com.revature.mariokartfighter.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.dao.IMatchRecordRepo;
import com.revature.mariokartfighter.models.MatchRecord;
import com.revature.mariokartfighter.service.ConnectionService;

public class MatchRecordRepoDB implements IMatchRecordRepo {
	ConnectionService connectionService;
	
	public MatchRecordRepoDB(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	@Override
	public MatchRecord addMatchRecord(MatchRecord match) {
		try {			
			PreparedStatement insertMatchRecord = connectionService.getConnection().prepareStatement(
					"INSERT INTO matchRecord ("
					+ "matchID, timeOfMatch, player1ID, player2ID, player2IsBot, winnerIsPlayer1) "
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			PreparedStatement insertPlayerMatchRecord = connectionService.getConnection().prepareStatement(
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
		try {
			PreparedStatement getMatches = connectionService.getConnection().prepareStatement(
				"SELECT * "
				+ "FROM matchRecord, playerMatchRecord "
				+ "WHERE matchRecord.matchID = playerMatchRecord.matchID;");
			
			ResultSet matchesRS = getMatches.executeQuery();
			List<MatchRecord> allMatches = new ArrayList<MatchRecord>();
			
			while(matchesRS.next()) {
				String player1ID = matchesRS.getString("player1ID");
				String player1CharacterID = matchesRS.getString("characterID");
				String player1ItemID = matchesRS.getString("itemID");
				String winnerID;
				if (matchesRS.getBoolean("winnerIsPlayer1")) {
					winnerID = matchesRS.getString("player1ID");
				} else {
					winnerID = matchesRS.getString("player2ID");					
				}
				
				//read second row (contains info for player 2)
				matchesRS.next();
				
				MatchRecord newMatchRecord = new MatchRecord(
					matchesRS.getString("matchID"), 
					matchesRS.getTimestamp("timeOfMatch"),
					player1ID, 
					player1CharacterID, 
					player1ItemID, 
					matchesRS.getString("player2ID"), 
					matchesRS.getString("characterID"), 
					matchesRS.getString("itemID"), 
					matchesRS.getBoolean("player2IsBot"), 
					winnerID);
				
				allMatches.add(newMatchRecord);
			}
			return allMatches;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MatchRecord> getPlayerMatches(String playerID) {
		try {
			PreparedStatement getSomeMatches = connectionService.getConnection().prepareStatement(
				"SELECT * "
				+ "FROM matchRecord, playerMatchRecord "
				+ "WHERE matchRecord.matchID = playerMatchRecord.matchID "
				+ "AND matchRecord.player1ID = ? "
				+ "OR matchRecord.player2ID = ?;");
			getSomeMatches.setString(1, playerID);
			getSomeMatches.setString(2, playerID);
			
			ResultSet matchesRS = getSomeMatches.executeQuery();
			List<MatchRecord> allMatches = new ArrayList<MatchRecord>();
			
			while(matchesRS.next()) {
				String player1ID = matchesRS.getString("player1ID");
				String player1CharacterID = matchesRS.getString("characterID");
				String player1ItemID = matchesRS.getString("itemID");
				String winnerID;
				if (matchesRS.getBoolean("winnerIsPlayer1")) {
					winnerID = matchesRS.getString("player1ID");
				} else {
					winnerID = matchesRS.getString("player2ID");					
				}
				
				//read second row (contains info for player 2)
				matchesRS.next();
				
				MatchRecord newMatchRecord = new MatchRecord(
					matchesRS.getString("matchID"), 
					matchesRS.getTimestamp("timeOfMatch"),
					player1ID, 
					player1CharacterID, 
					player1ItemID, 
					matchesRS.getString("player2ID"), 
					matchesRS.getString("characterID"), 
					matchesRS.getString("itemID"), 
					matchesRS.getBoolean("player2IsBot"), 
					winnerID);
				
				allMatches.add(newMatchRecord);
			}
			return allMatches;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
