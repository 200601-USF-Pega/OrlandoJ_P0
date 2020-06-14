package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.mariokartfighter.dao.db.ItemRepoDB;
import com.revature.mariokartfighter.dao.db.MatchRecordRepoDB;
import com.revature.mariokartfighter.dao.db.PlayableCharacterRepoDB;
import com.revature.mariokartfighter.dao.db.PlayerRepoDB;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
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
				new PlayableCharacterRepoDB(connectionService), 
				new ItemRepoDB(connectionService), 
				new MatchRecordRepoDB(connectionService));
		playerService = new PlayerService(
				new PlayerRepoDB(connectionService));
		playerID = "admin002";
		gameService.setCharacter("peach", playerID);
		gameService.setItem("green shell", playerID);
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
				assertNotEquals("donkey kong", p.getSelectedCharacter().getCharacterName());
				break;
			}
		}
		gameService.setCharacter("donkey kong", playerID);
		retrievedPlayers = playerService.repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(p.getPlayerID().equals(playerID)) {
				assertEquals("donkey kong", p.getSelectedCharacter().getCharacterName());
				break;
			}
		}
	}
	
	@Test
	public void setItemWhenItemDoesntExistShouldReturnFalse() {
		assertFalse(gameService.setItem("xyz", playerID));
	}
	
	@Test
	public void setItemShouldReturnFalseIfCharacterIsNotUnlocked() {
		assertFalse(gameService.setCharacter("dry bones", "admin003"));
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
		//set character to one with matching type
		gameService.setCharacter("wario", playerID);
		
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
	public void chooseRandomCharacterShouldReturnACharacterAtOrBelowLevel() {
		PlayableCharacter randomCharacter = gameService.chooseRandomCharacter(2);
		assertTrue(randomCharacter.getUnlockAtLevel() <= 2);
	}
	
	@Test
	public void chooseRandomItemShouldReturnAnItemAtOrBelowLevelAndOfCorrectType() {
		Item randomItem = gameService.chooseRandomItem(1, "power");
		assertTrue(randomItem.getUnlockAtLevel() <= 1);
		assertEquals("power", randomItem.getTypeThatCanUse());
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
