package com.revature.mariokartfighter.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	
	@Test
	public void constructorShouldInitializeValuesProperly() {
		Player newPlayer = new Player("abcd");
		assertEquals("abcd", newPlayer.getPlayerID());
		assertEquals(1, newPlayer.getLevel());
		assertEquals(0, newPlayer.getXpEarned());
		assertEquals(0, newPlayer.getNumberOfWins());
		assertEquals(0, newPlayer.getPlayerID());
	}

}
