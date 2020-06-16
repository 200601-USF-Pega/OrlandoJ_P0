package test.com.revature.mariokartfighter.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class PlayerTest {
	
	@Test
	public void constructorShouldInitializeValuesProperly() {
		Player newPlayer = new Player("abcd");
		assertEquals("abcd", newPlayer.getPlayerID());
		assertEquals(1, newPlayer.getLevel());
		assertEquals(0, newPlayer.getXpEarned());
		assertEquals(0, newPlayer.getNumberOfWins());
		assertEquals(0, newPlayer.getNumberOfMatches());
		assertEquals(null, newPlayer.getSelectedCharacter());
		assertEquals(null, newPlayer.getSelectedItem());
	}
	
	@Test
	public void testEqualsOverride() {
		Player player1 = new Player("player0", 2, 250, 3, 4, 
				new PlayableCharacter("dk002", "skill", "diddy kong", 100, 2.0, 2.0, 1),
				new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0));
		Player player2 = new Player("player0", 2, 250, 3, 4, 
				new PlayableCharacter("dk002", "skill", "diddy kong", 100, 2.0, 2.0, 1),
				new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0));
		assertTrue(player1.equals(player2));
	}
	
}
