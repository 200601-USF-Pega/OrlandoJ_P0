package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.mariokartfighter.dao.db.PlayerRepoDB;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class PlayerServiceTest {
	PlayerService playerService;
	ConnectionService connectionService;
	
	@Before
	public void setupNeededClasses() {
		connectionService = new ConnectionService();
		playerService = new PlayerService(new PlayerRepoDB(connectionService));
	}
	
	@Test
	public void checkPlayerExistsShouldReturnFalse() {
		assertFalse(playerService.checkPlayerExists("ps-test02"));
	}
	
	@Test
	public void createNewPlayerShouldReturnPlayerIDIfAdded() {
		String playerID = playerService.createNewPlayer("ps-test01", "password");
		assertEquals("ps-test01", playerID);
	}

	@Test
	public void createNewPlayerShouldAddPlayerToRepo() {
		assertFalse(playerService.checkPlayerExists("ps-test03"));
		playerService.createNewPlayer("ps-test03", "password");
		assertTrue(playerService.checkPlayerExists("ps-test03"));
	}
	
	@Test
	public void getPlayerObjectShouldReturnCorrectPlayerObject() {
		//String thisPlayerID = playerService.createNewPlayer("ps-test04");
		Player thisPlayer = playerService.getPlayerObject("admin001");
		PlayableCharacter adminChar = new PlayableCharacter("dk001", "power", "donkey kong",
				100, 35.0, 20.0, 2);
		Item adminItem = new Item("sp001", "star power", "power", 4, 10, 15.0, 15.0);
		Player testPlayer = new Player("admin001", 4, 400, 0, 0, adminChar, adminItem);
		assertTrue(thisPlayer.equals(testPlayer));
	}
	
	@Ignore	//will never happen bc admin players exist
	@Test
	public void chooseClosestPlayerShouldReturnEmptyPlayerObjectIfNoPlayersHaveSetCharacters() {
		String thisPlayerID = playerService.createNewPlayer("ps-test04", "password");
		Player player1 = playerService.getPlayerObject(thisPlayerID);
		Player player2 = playerService.chooseClosestPlayer(player1);
		assertEquals(null, player2.getSelectedCharacter());
	}
	
	@Test
	public void chooseClosestPlayerShouldChoosePlayerObject() {
		String thisPlayerID = playerService.createNewPlayer("ps-test04", "password");
		Player thisPlayer = playerService.getPlayerObject(thisPlayerID);
		Player closestPlayer = playerService.chooseClosestPlayer(thisPlayer);
		assertEquals(closestPlayer.getClass(), Player.class);
	}
	
	@After
	public void cleanUp() {
		//remove players added for testing (anything with ps-test%)
		playerService.removeTestPlayers("ps-test");
		
		try {
			connectionService.getConnection().close();
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
