package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.mariokartfighter.dao.CharacterRepoFile;
import com.revature.mariokartfighter.dao.ItemRepoFile;
import com.revature.mariokartfighter.dao.PlayerRepoFile;
import com.revature.mariokartfighter.models.Player;

public class GameServiceTest {
	GameService gameService;
	PlayerService playerService;
	String playerID;
	
	@Before
	public void setupDummyData() {
		gameService = new GameService(new PlayerRepoFile(), new CharacterRepoFile(), new ItemRepoFile());
		playerService = new PlayerService(new PlayerRepoFile());
		playerID = playerService.generatePlayerID();
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

}
