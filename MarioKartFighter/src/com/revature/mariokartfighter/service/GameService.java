package com.revature.mariokartfighter.service;

import java.util.List;
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
	
	public PlayableCharacter chooseRandomCharacter(int level) {
		
	}
	
	public Item chooseRandomItem(int level) {
		
	}
	
	public void botFight(Bot bot, String playerID) {
		//find player info
		List<Player> retrievedPlayers = playerRepo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if (playerID.equals(p.getPlayerID())) {
				//TODO simulate fight		
				PlayableCharacter playerChar = p.getSelectedCharacter();
				PlayableCharacter botChar = bot.getSelectedCharacter();
				Item playerItem = p.getSelectedItem();
				Item botItem = bot.getSelectedItem();
				int playerHealth = p.getSelectedCharacter().getMaxHealth();
				int botHealth = bot.getSelectedCharacter().getMaxHealth();
				
				while(botHealth > 0 && playerHealth > 0) {
					//every turn subtract other players attack - your defense from health
					double botStrength = botChar.getAttackStat() + botItem.getBonusToAttack()
						- (playerChar.getDefenseStat() + playerItem.getBonusToDefense());
					double playerStrength = playerChar.getAttackStat() + playerItem.getBonusToAttack()
						- (botChar.getDefenseStat() + botItem.getBonusToDefense());
					
					botHealth -= playerStrength;
					if(botHealth <= 0) {
						break;
					}
					playerHealth -= botStrength;
				}
				
				String winnerID;
				if(botHealth < playerHealth) {
					winnerID = p.getPlayerID();
				} else {
					winnerID = bot.getID();
				}
				
				//save to repo
				MatchRecord newMatch = new MatchRecord(this.generateMatchID(), playerID, 
						p.getSelectedCharacter().getCharacterID(), 
						p.getSelectedItem().getItemID(),
						bot.getID(), bot.getSelectedCharacter().getCharacterID(),
						bot.getSelectedItem().getItemID(), true, winnerID);
				matchRecordRepo.addMatchRecord(newMatch);
				break;
			}
		}
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
