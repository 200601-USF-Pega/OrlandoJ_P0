package com.revature.mariokartfighter.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;
import com.revature.mariokartfighter.service.ConnectionService;

public class PlayerRepoDB implements IPlayerRepo {
	ConnectionService connectionService;
	
	public PlayerRepoDB(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	@Override
	public Player addPlayer(Player player) {
		try {			
			PreparedStatement playerInsert = connectionService.getConnection().prepareStatement(
					"INSERT INTO player VALUES (?, ?, ?, ?, ?, ?, ?)");
			playerInsert.setString(1, player.getPlayerID());
			playerInsert.setInt(2, player.getLevel());
			playerInsert.setInt(3, player.getXpEarned());
			playerInsert.setInt(4, player.getNumberOfMatches());
			playerInsert.setInt(5, player.getNumberOfWins());
			
			if(player.getSelectedCharacter() != null) {				
				playerInsert.setString(6, player.getSelectedCharacter().getCharacterID());
			} else {
				playerInsert.setString(6, null);
			}
			if(player.getSelectedItem() != null) {				
				playerInsert.setString(7, player.getSelectedItem().getItemID());
			} else { 
				playerInsert.setString(7, null);
			}				
			
			playerInsert.execute();
			
			return player;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Bot addBot(Bot bot) {
		try {			
			PreparedStatement botInsert = connectionService.getConnection().prepareStatement(
					"INSERT INTO player VALUES (?, ?, ?, ?, ?, ?, ?)");
			botInsert.setString(1, bot.getBotID());
			botInsert.setInt(2, bot.getLevel());
			botInsert.setInt(3, 0);
			botInsert.setInt(4, 0);
			botInsert.setInt(5, 0);
			
			if(bot.getSelectedCharacter() != null) {				
				botInsert.setString(6, bot.getSelectedCharacter().getCharacterID());
			} else {
				botInsert.setString(6, null);
			}
			if(bot.getSelectedItem() != null) {				
				botInsert.setString(7, bot.getSelectedItem().getItemID());
			} else { 
				botInsert.setString(7, null);
			}				
			
			botInsert.execute();
			
			return bot;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Player> getAllPlayers() {
		try {
			if(this.connectionService == null) {
				System.out.println("connection null");
			}
			//get all players that are not bots
			PreparedStatement getPlayers = connectionService.getConnection().prepareStatement(
					"SELECT * FROM player WHERE playerID NOT LIKE ?;");
			getPlayers.setString(1, "bot_%");
			ResultSet playersRS = getPlayers.executeQuery();
			
			PreparedStatement getPlayersCharacter = connectionService.getConnection().prepareStatement(
					"SELECT * FROM playablecharacter WHERE characterID = ?;");
			PreparedStatement getPlayersItem = connectionService.getConnection().prepareStatement(
					"SELECT * FROM item WHERE itemID = ?;");
			
			List<Player> retrievedPlayers = new ArrayList<Player>();
			
			while(playersRS.next()) {
				getPlayersCharacter.setString(1, playersRS.getString("selectedCharacterID"));
				ResultSet playerCharRS = getPlayersCharacter.executeQuery();
				
				getPlayersItem.setString(1, playersRS.getString("selectedItemID"));
				ResultSet playerItemRS = getPlayersItem.executeQuery();
				
				PlayableCharacter playerCharacter = null;
				Item playerItem = null;
				while(playerCharRS.next()) {
					playerCharacter = new PlayableCharacter(playersRS.getString("selectedCharacterID"), 
						playerCharRS.getString("characterType"),
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
				
				Player newPlayer;
				if (playerCharacter != null && playerItem != null) {
					newPlayer = new Player(
						playersRS.getString("playerID"),
						playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
						playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
						playerCharacter, playerItem);	
				} else if(playerCharacter != null) {
					newPlayer = new Player(
							playersRS.getString("playerID"),
							playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
							playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
							playerCharacter, null);	
				} else {
					newPlayer = new Player(
							playersRS.getString("playerID"),
							playersRS.getInt("xpLevel"), playersRS.getInt("xpEarned"),
							playersRS.getInt("numberOfWins"), playersRS.getInt("numberOfMatchesPlayed"),
							null, null);	
				}
				retrievedPlayers.add(newPlayer);
			}	
			return retrievedPlayers;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Player>();
	}

	@Override
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID) {
		try {			
			PreparedStatement getPlayers = connectionService.getConnection().prepareStatement(
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
			PreparedStatement getPlayers = connectionService.getConnection().prepareStatement(
					"UPDATE player "
					+ "SET selectedItemID = ? "
					+ "WHERE playerID = ?;");
			if (item == null) {
				getPlayers.setString(1, null);
			} else {
				getPlayers.setString(1, item.getItemID());
			}
			getPlayers.setString(2, playerID);
			
			getPlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateAfterFight(boolean wonMatch, String playerID) {
		boolean leveledUp = false;
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
						leveledUp = true;
					}
					currentPlayer = p;
					break;
				}
			}
			if(currentPlayer == null) {
				throw new SQLException("player does not exist");
			}
			
			PreparedStatement updatePlayer = connectionService.getConnection().prepareStatement(
					"UPDATE player "
					+ "SET xpEarned = ?, xpLevel = ?"
					+ "WHERE playerID = ?;");
			
			updatePlayer.setInt(1, currentPlayer.getXpEarned());
			updatePlayer.setInt(2, currentPlayer.getLevel());
			updatePlayer.setString(3, currentPlayer.getPlayerID());
			
			updatePlayer.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return leveledUp;
	}

	@Override
	public int getPlayerRank(String playerID) {
		try {
			List<Player> allPlayers = this.getAllPlayers();
			Player currentPlayer = null;
			for (Player p : allPlayers) {
				if (p.getPlayerID().equals(playerID)) {
					currentPlayer = p;
					break;
				}
			}
			if(currentPlayer == null) {
				throw new SQLException("player does not exist");
			}
			
			PreparedStatement getPlayersSorted = connectionService.getConnection().prepareStatement(
					"SELECT * "
					+ "FROM player "
					+ "WHERE playerid NOT LIKE ? "
					+ "ORDER BY xpLevel DESC, xpEarned DESC;");
			getPlayersSorted.setString(1, "bot_%");
			ResultSet playerSortedRS = getPlayersSorted.executeQuery();
			int rank = 1;
			while (playerSortedRS.next()) {
				//look for player and count how many people theyre below
				if(playerSortedRS.getString("playerID").equals(playerID)) {
					return rank;
				}
				rank++;
			}
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void removePlayers(String name) {
		try {
			PreparedStatement removePlayers = connectionService.getConnection().prepareStatement(
					"DELETE FROM player "
					+ "WHERE playerID LIKE ?");
			removePlayers.setString(1, name+'%');
			removePlayers.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
