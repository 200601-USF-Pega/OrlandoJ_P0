package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.mariokartfighter.dao.db.CharacterRepoDB;
import com.revature.mariokartfighter.dao.db.ItemRepoDB;
import com.revature.mariokartfighter.dao.db.MatchRecordRepoDB;
import com.revature.mariokartfighter.dao.db.PlayerRepoDB;
import com.revature.mariokartfighter.models.Player;

public class GameServiceTest {
	GameService gameService;
	PlayerService playerService;
	String playerID;
	ConnectionService connectionService;
	
	@Before
	public void setupNeededClasses() {
		connectionService = new ConnectionService();
		gameService = new GameService(
				new PlayerRepoDB(connectionService),
				new CharacterRepoDB(connectionService), 
				new ItemRepoDB(connectionService), 
				new MatchRecordRepoDB(connectionService));
		playerService = new PlayerService(
				new PlayerRepoDB(connectionService));
		playerID = "admin002";
	}
	
	@Test
	public void setCharacterWhenCharacterDoesntExist() {
		assertFalse(gameService.setCharacter("xyz", playerID));
	}
	
	@Test
	public void setCharacterShouldReturnFalseIfCharacterIsNotUnlocked() {
		assertFalse(gameService.setCharacter("db001", "admin003"));
	}
	
	@Test
	public void setCharacterShouldChangePlayerInfoInRepo() {
		List<Player> retrievedPlayers = playerService.repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(p.getPlayerID().equals(playerID)) {
				assertNotEquals("Donkey Kong", p.getSelectedCharacter().getCharacterName());
				break;
			}
		}
		gameService.setCharacter("Donkey Kong", playerID);
		retrievedPlayers = playerService.repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(p.getPlayerID().equals(playerID)) {
				assertEquals("Donkey Kong", p.getSelectedCharacter().getCharacterName());
				break;
			}
		}
	}
	
	@Test
	public void setItemWhenItemDoesntExist() {
		assertFalse(gameService.setItem("xyz", playerID));
	}
	
	@Test
	public void setItemShouldReturnFalseIfCharacterIsNotUnlocked() {
		assertFalse(gameService.setCharacter("db001", "admin003"));
	}
	

	@Test
	public void setItemShouldChangePlayerInfoInRepo() {
		List<Player> retrievedPlayers = playerService.repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(p.getPlayerID().equals(playerID)) {
				assertNotEquals("blue shell", p.getSelectedItem().getItemName());
				break;
			}
		}
		gameService.setItem("blue shell", playerID);
		retrievedPlayers = playerService.repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(p.getPlayerID().equals(playerID)) {
				assertEquals("blue shell", p.getSelectedItem().getItemName());
				break;
			}
		}
	}
	
	@Test
	public void chooseRandomCharacterShouldReturnAnItemAtOrBelowLevel() {
		
	}
	
	@Test
	public void chooseRandomItemShouldReturnAnItemAtOrBelowLevel() {
		
	}
	
	@Test
	public void testBotFight() {
		
	}
	
	@Test
	public void testPlayerFight() {
		
	}

	@After
	public void cleanUp() {
		try {
			connectionService.getConnection().close();
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
