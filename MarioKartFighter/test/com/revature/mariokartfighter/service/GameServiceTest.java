package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.mariokartfighter.dao.file.CharacterRepoFile;
import com.revature.mariokartfighter.dao.file.ItemRepoFile;
import com.revature.mariokartfighter.dao.file.MatchRecordRepoFile;
import com.revature.mariokartfighter.dao.file.PlayerRepoFile;
import com.revature.mariokartfighter.models.Player;

public class GameServiceTest {
	GameService gameService;
	PlayerService playerService;
	String playerID;
	ConnectionService connectionService;
	
	@Before
	public void setupNeededClasses() {
		connectionService = new ConnectionService();
	}
	
	@Before
	public void setupDummyData() {
		gameService = new GameService(new PlayerRepoFile(), new CharacterRepoFile(), 
				new ItemRepoFile(), new MatchRecordRepoFile());
		playerService = new PlayerService(new PlayerRepoFile());
		playerID = "testingplayer";
	}
	
	@Test
	public void setCharacterWhenCharacterDoesntExist() {
		assertFalse(gameService.setCharacter("xyz", playerID));
	}
	
	@Test
	public void setCharacterShouldChangePlayerInfoInRepo() {
		gameService.setCharacter("Donkey Kong", playerID);
		List<Player> retrievedPlayers = playerService.repo.getAllPlayers();
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
	public void testBotFight() {
		
	}
	
	@Test
	public void testPlayerFight() {
		
	}

}
