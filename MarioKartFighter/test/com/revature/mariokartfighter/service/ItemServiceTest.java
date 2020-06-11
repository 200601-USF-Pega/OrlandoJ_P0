package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.mariokartfighter.dao.db.ItemRepoDB;

public class ItemServiceTest {
	ItemService itemService;
	ConnectionService connectionService;
	
	@Before
	public void setupNeededClasses() {
		connectionService = new ConnectionService();
		itemService = new ItemService(new ItemRepoDB(connectionService));
	}
	
	@Test
	public void checkItemExistsShouldReturnFalse() {
		assertFalse(itemService.checkItemExists("is-test02"));
	}
	
	@Test
	public void generateItemIDShouldReturnUniqueString() {
		String generatedID = itemService.generateItemID();
		assertTrue(generatedID.length() > 3 && generatedID.length() < 64);
		assertFalse(itemService.checkItemExists(generatedID));
	}

	@Ignore	//need to get around user input
	@Test
	public void createNewItemShouldAddItemToRepo() {
		String itemID = itemService.createNewItem();
		assertTrue(itemService.checkItemExists(itemID));
		itemService.removeTestItems(itemID);
	}
	
	@After
	public void cleanUp() {
		try {
			connectionService.getConnection().close();
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
