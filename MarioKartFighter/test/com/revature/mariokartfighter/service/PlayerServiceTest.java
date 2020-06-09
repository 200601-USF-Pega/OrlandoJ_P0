package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.mariokartfighter.dao.PlayerRepoFile;

public class PlayerServiceTest {
	PlayerService playerService;
	
	@Before
	public void setupDummyData() {
		playerService = new PlayerService(new PlayerRepoFile());
	}
	
	@Test
	public void testCheckPlayerExists() {
		assertFalse(playerService.checkPlayerExists("000"));
	}
	
	@Test
	public void createNewPlayerShouldAddPlayerToRepo() {
		assertFalse(playerService.checkPlayerExists("jorlando989"));
		String newPlayerID = playerService.createNewPlayer("jorlando989");
		assertTrue(playerService.checkPlayerExists("jorlando989"));
	}
}
