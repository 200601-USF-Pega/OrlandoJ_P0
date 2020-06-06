package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.mariokartfighter.dao.PlayerRepoFile;

public class PlayerServiceTest {
	PlayerService playerService;
	String playerID;
	
	@Before
	public void setupDummyData() {
		playerService = new PlayerService(new PlayerRepoFile());
		playerID = playerService.generatePlayerID();
	}
	
	@Test
	public void testCheckPlayerExists() {
		assertFalse(playerService.checkPlayerExists("000"));
	}
	
	@Test
	public void createNewPlayerShouldAddPlayerToRepo() {
		String newPlayerID = playerService.createNewPlayer();
		assertTrue(playerService.checkPlayerExists(newPlayerID));
	}

}
