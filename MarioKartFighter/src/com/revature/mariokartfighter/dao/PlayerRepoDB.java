package com.revature.mariokartfighter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class PlayerRepoDB implements IPlayerRepo {
	Connection connection;
	
	public PlayerRepoDB(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Player addPlayer(Player player) {
		try {			
			PreparedStatement playerInsert = connection.prepareStatement(
					"INSERT INTO player VALUES (?, ?, ?, ?, ?, ?, ?)");
			playerInsert.setString(1, player.getPlayerID());
			playerInsert.setInt(2, player.getLevel());
			playerInsert.setInt(3, player.getXpEarned());
			playerInsert.setInt(4, player.getNumberOfMatches());
			playerInsert.setInt(5, player.getNumberOfWins());
			playerInsert.setString(6, player.getSelectedCharacter().getCharacterID());
			playerInsert.setString(7, player.getSelectedItem().getItemID());
			
			playerInsert.executeUpdate();
			
			return player;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Player> getAllPlayers() {
		try {
			if(this.connection == null) {
				System.out.println("connection null");
			}
			PreparedStatement getPlayers = connection.prepareStatement(
					"SELECT * FROM players;");
			ResultSet playersRS = getPlayers.executeQuery();
			
			PreparedStatement getPlayersCharacter = connection.prepareStatement(
					"SELECT * FROM character WHERE characterID = ?;");
			PreparedStatement getPlayersItem = connection.prepareStatement(
					"SELECT * FROM item WHERE itemID = ?;");
			
			List<Player> retrievedPlayers = new ArrayList<Player>();
			
			while(playersRS.next()) {
				getPlayersCharacter.setString(1, playersRS.getString("selectedCharacterID"));
				ResultSet playerCharRS = getPlayersCharacter.executeQuery();
				
				getPlayersItem.setString(1, playersRS.getString("selectedItemID"));
				ResultSet playerItemRS = getPlayersCharacter.executeQuery();
				
				PlayableCharacter playerCharacter = null;
				Item playerItem = null;
				while(playerCharRS.next()) {
					playerCharacter = new PlayableCharacter(playersRS.getString("selectedCharacterID"), 
						playerCharRS.getString("type"),
						playerCharRS.getString("name"),	
						playerCharRS.getInt("maxHealth"), 
						playerCharRS.getDouble("attackStat"), 
						playerCharRS.getDouble("defenseStat"), 
						playerCharRS.getInt("unlockAtLevel"));
				}
				while(playerItemRS.next()) {
					playerItem = new Item(playersRS.getString("selectedItemID"), 
						playerItemRS.getString("name"), 
						playerItemRS.getString("typeThatCanUse"), 
						playerItemRS.getInt("unlockAtLevel"), 
						playerItemRS.getInt("bonusToHealth"), 
						playerItemRS.getDouble("bonusToAttack"), 
						playerItemRS.getDouble("bonusToDefense"));
				}
				
				if (playerCharacter != null && playerItem != null) {
					Player newPlayer = new Player(
						playersRS.getString("playerID"),
						playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
						playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatches"),
						playerCharacter, playerItem);	
				
					retrievedPlayers.add(newPlayer);
				} else {
					System.out.println("no character or item selected for player");
				}
				return retrievedPlayers;
			}	
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Player>();
	}

	@Override
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID) {
		try {			
			PreparedStatement getPlayers = connection.prepareStatement(
					"UPDATE player "
					+ "SET selectedCharacterID = ? "
					+ "WHERE playerID = ?;");
			getPlayers.setString(1, character.getCharacterID());
			getPlayers.setString(2, playerID);
			
			getPlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void assignItemToPlayer(Item item, String playerID) {
		try {			
			PreparedStatement getPlayers = connection.prepareStatement(
					"UPDATE player "
					+ "SET selectedItemID = ? "
					+ "WHERE playerID = ?;");
			getPlayers.setString(1, item.getItemID());
			getPlayers.setString(2, playerID);
			
			getPlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void updateAfterFight(boolean wonMatch, String playerID) {
		try {
			List<Player> allPlayers = this.getAllPlayers();
			Player currentPlayer = null;
			for (Player p : allPlayers) {
				if (p.getPlayerID().equals(playerID)) {
					if (wonMatch) {
						p.setXpEarned(p.getXpEarned() + 100);					
					} else {
						p.setXpEarned(p.getXpEarned() + 50);	
					}
					//check for level up
					if(p.getXpEarned() >= (p.getLevel()*100)+1) {
						p.setLevel(p.getLevel()+1);
						System.out.println("Congratulations! You leveled up!");
						System.out.println("You are now level " + p.getLevel() + ".");
					}
					currentPlayer = p;
					break;
				}
			}
			if(currentPlayer == null) {
				throw new SQLException("player does not exist");
			}
			
			PreparedStatement updatePlayer = connection.prepareStatement(
					"UPDATE player "
					+ "SET xpEarned = ?, xpLevel = ?"
					+ "WHERE playerID = ?;");
			
			updatePlayer.setInt(1, currentPlayer.getXpEarned());
			updatePlayer.setInt(2, currentPlayer.getLevel());
			updatePlayer.setString(3, currentPlayer.getPlayerID());
			
			updatePlayer.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public int getPlayerRank(String playerID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
