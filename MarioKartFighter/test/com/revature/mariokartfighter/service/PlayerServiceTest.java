package com.revature.mariokartfighter.service;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.mariokartfighter.dao.PlayerRepoDB;
import com.revature.mariokartfighter.models.Player;

public class PlayerServiceTest {
	PlayerService playerService;
	Connection connection;
	
	@BeforeClass
	public void setupNeededClasses() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://ruby.db.elephantsql.com:5432/brdzdjzb", 
					"brdzdjzb", "l7Lh2FHoFuFdz4Gf1h5j0-9LSj78BeJ8");
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		playerService = new PlayerService(new PlayerRepoDB(connection));
	}
	
	@Test
	public void testCheckPlayerExists() {
		assertFalse(playerService.checkPlayerExists("ps-test02"));
	}
	
	@Test
	public void createNewPlayerShouldReturnPlayerIDIfAdded() {
		String playerID = playerService.createNewPlayer("ps-test01");
		assertEquals("ps-test01", playerID);
	}

	@Test
	public void createNewPlayerShouldAddPlayerToRepo() {
		assertFalse(playerService.checkPlayerExists("ps-test03"));
		playerService.createNewPlayer("ps-test03");
		assertTrue(playerService.checkPlayerExists("ps-test03"));
	}
	
	@Test
	public void getPlayerObjectShouldReturnCorrectPlayerObject() {
		String thisPlayerID = playerService.createNewPlayer("ps-test04");
		Player thisPlayer = playerService.getPlayerObject(thisPlayerID);
		assertEquals(thisPlayer, new Player("ps-test04", 1, 0, 0, 0, null, null));
	}
	
	@Ignore	//will never happen bc admin players exist
	@Test
	public void chooseClosestPlayerShouldReturnEmptyPlayerObjectIfNoPlayersHaveSetCharacters() {
		String thisPlayerID = playerService.createNewPlayer("ps-test04");
		Player player1 = playerService.getPlayerObject(thisPlayerID);
		Player player2 = playerService.chooseClosestPlayer(player1);
		assertEquals(null, player2.getSelectedCharacter());
	}
	
	@Test
	public void chooseClosestPlayerShouldChoosePlayerObject() {
		String thisPlayerID = playerService.createNewPlayer("ps-test04");
		Player thisPlayer = playerService.getPlayerObject(thisPlayerID);
		Player closestPlayer = playerService.chooseClosestPlayer(thisPlayer);
		assertEquals(closestPlayer.getClass(), Player.class);
	}
	
	@AfterClass
	public void cleanUp() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		//remove players added for testing (anything with ps-test%)
		
	}
}
