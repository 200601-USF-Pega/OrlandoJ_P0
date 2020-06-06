package com.revature.mariokartfighter.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ItemTest {

	@Test
	public void constructorShouldInitializeValuesProperly() {
		Item newItem = new Item("abcde", "speed", 1, 0, 3.0, 1.0);
		assertEquals("abcd", newItem.getItemID());
		assertEquals("speed", newItem.getTypeThatCanUse());
		assertEquals(3.0, newItem.getBonusToAttack(), 0);
		assertEquals(1.0, newItem.getBonusToDefense(), 0);
		assertEquals(0, newItem.getBonusToHealth());
	}
	
}
