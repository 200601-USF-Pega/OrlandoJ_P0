package com.revature.mariokartfighter.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.revature.mariokartfighter.dao.ICharacterRepo;
import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.dao.IMatchRecordRepo;
import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.MatchRecord;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class GameService {
	IPlayerRepo playerRepo;
	ICharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	
	public GameService (IPlayerRepo playerRepo, ICharacterRepo characterRepo, IItemRepo itemRepo,
			IMatchRecordRepo matchRecordRepo) {
		this.playerRepo = playerRepo;
		this.characterRepo = characterRepo;
		this.itemRepo = itemRepo;
		this.matchRecordRepo = matchRecordRepo;
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
	
	public void botFight(Bot bot, String playerID) {
		//find player info
		Player currentPlayer;
		List<Player> retrievedPlayers = playerRepo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if (playerID.equals(p.getPlayerID())) {
				currentPlayer = p;
				break;
			}
		}
		
		//TODO simulate fight
		String winnerID;
		
		
		//TODO save to repo
		MatchRecord newMatch = new MatchRecord(this.generateMatchID(), playerID, bot.getID(), 
				);
		matchRecordRepo.addMatchRecord(newMatch);
	}
	
	public void playerFight(String player1ID) {
		//TODO choose player with closest level
		String player2ID;
		
		//TODO simulate player fight
		
		//TODO save to repo
		
	}
	
	public String generateMatchID() {
		return UUID.randomUUID().toString();
	}
}
