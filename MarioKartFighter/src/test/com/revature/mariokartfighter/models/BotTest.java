package test.com.revature.mariokartfighter.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;

public class BotTest {

	@Test
	public void constructorShouldInitializeValuesProperly() {
		PlayableCharacter botCharacter = new PlayableCharacter(
				"dk002", "skill", "diddy kong", 100, 2.0, 2.0, 1);
		Item botItem = new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0);
		Bot newBot = new Bot(2, botCharacter, botItem);
		
		assertEquals(botCharacter, newBot.getSelectedCharacter());
		assertEquals(botItem, newBot.getSelectedItem());
		assertEquals(2, newBot.getLevel());
		assertNotEquals(null, newBot.getBotID());
	}
	@Test
	public void testEqualsOverride() {
		PlayableCharacter botCharacter = new PlayableCharacter(
				"dk002", "skill", "diddy kong", 100, 2.0, 2.0, 1);
		Item botItem = new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0);
		Bot bot1 = new Bot(2, botCharacter, botItem);
		Bot bot2 = new Bot(2, botCharacter, botItem);
		assertTrue(bot1.equals(bot2));
	}
	
	@Test
	public void generateBotIDShouldReturnStringWithLevelAndTime() {
		PlayableCharacter botCharacter = new PlayableCharacter(
				"dk002", "skill", "diddy kong", 100, 2.0, 2.0, 1);
		Item botItem = new Item("b001", "banana", "skill", 1, 0, 3.0, 1.0);
		Bot newBot = new Bot(2, botCharacter, botItem);
		assertTrue((newBot.getBotID().contains("_")));
		assertEquals("bot_", newBot.getBotID().substring(0,4));
	}
	
}
