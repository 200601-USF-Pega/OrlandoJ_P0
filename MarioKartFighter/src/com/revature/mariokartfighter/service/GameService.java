package com.revature.mariokartfighter.service;

import java.util.List;

import com.revature.mariokartfighter.dao.ICharacterRepo;
import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;

public class GameService {
	IPlayerRepo playerRepo;
	ICharacterRepo characterRepo;
	IItemRepo itemRepo;
	
	public GameService (IPlayerRepo playerRepo, ICharacterRepo characterRepo, IItemRepo itemRepo) {
		this.playerRepo = playerRepo;
		this.characterRepo = characterRepo;
		this.itemRepo = itemRepo;
	}
	
	public boolean setCharacter(String characterName, String playerID) {
		//check if character exists
		List<PlayableCharacter> retrievedCharacters = characterRepo.getAllCharacters();
		for (PlayableCharacter c : retrievedCharacters) {
			if (characterName.equals(c.getCharacterName())) {
				playerRepo.assignCharacterToPlayer(c, playerID);
				return true;
			}
		}
		return false;
	}
	
	public boolean setItem(String itemName, String playerID) {
		//check if character exists
		List<Item> retrievedItems = itemRepo.getAllItems();
		for (Item i : retrievedItems) {
			if (itemName.equals(i.getName())) {
				playerRepo.assignItemToPlayer(i, playerID);
				return true;
			}
		}
		return false;
	}
}
