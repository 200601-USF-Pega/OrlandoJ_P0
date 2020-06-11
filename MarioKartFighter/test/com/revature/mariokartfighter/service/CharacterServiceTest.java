package com.revature.mariokartfighter.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.mariokartfighter.dao.db.CharacterRepoDB;

public class CharacterServiceTest {
	CharacterService characterService;
	ConnectionService connectionService;
	
	@Before
	public void setupNeededClasses() {
		connectionService = new ConnectionService();
		characterService = new CharacterService(new CharacterRepoDB(connectionService));
	}
	
	@Test
	public void checkCharacterExistsShouldReturnFalse() {
		assertFalse(characterService.checkCharacterExists("cs-test02"));
	}
	
	@Test
	public void generateCharacterIDShouldReturnUniqueString() {
		String generatedID = characterService.generateCharacterID();
		assertTrue(generatedID.length() > 3 && generatedID.length() < 64);
		assertFalse(characterService.checkCharacterExists(generatedID));
	}

	@Ignore	//need to get around user input
	@Test
	public void createNewCharacterShouldAddCharacterToRepo() {
		String characterID = characterService.createNewCharacter();
		assertTrue(characterService.checkCharacterExists(characterID));
		characterService.removeTestCharacters(characterID);
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
