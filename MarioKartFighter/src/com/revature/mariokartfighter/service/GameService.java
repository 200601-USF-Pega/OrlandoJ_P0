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
				//check if player has unlocked
				Player player = null;
				List<Player> retrievedPlayers = playerRepo.getAllPlayers();
				for (Player p : retrievedPlayers) {
					if (playerID.equals(p.getPlayerID())) {
						player = p;
						break;
					}
				}
				if (player != null && player.getLevel() >= c.getUnlockAtLevel()) {
					playerRepo.assignCharacterToPlayer(c, playerID);				
				} else if (player != null) {
						System.out.println("You haven't unlocked this character yet.");	
						System.out.println("**Earn more XP by playing matches**");
						return false;
				} else {
					System.out.println("player does not exist");
					return false;
				}
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
				//check if player has unlocked
				Player player = null;
				List<Player> retrievedPlayers = playerRepo.getAllPlayers();
				for (Player p : retrievedPlayers) {
					if (playerID.equals(p.getPlayerID())) {
						player = p;
						break;
					}
				}
				if (player != null && player.getLevel() >= i.getUnlockAtLevel()) {
					playerRepo.assignItemToPlayer(i, playerID);					
				} else if (player != null) {
						System.out.println("You haven't unlocked this item yet.");	
						System.out.println("**Earn more XP by playing matches**");
						return false;
				} else {
					System.out.println("player does not exist");
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public PlayableCharacter chooseRandomCharacter(int level) {
		List<PlayableCharacter> retrievedCharacters = characterRepo.getSomeCharacters(level);
		Random random = new Random();
		int n = random.nextInt(retrievedCharacters.size()-1);
		return retrievedCharacters.get(n);
	}
	
	public Item chooseRandomItem(int level) {
		List<Item> retrievedItems = itemRepo.getSomeItems(level);
		Random random = new Random();
		int n = random.nextInt(retrievedItems.size()-1);
		return retrievedItems.get(n);
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
					//strength is player attack - opponent defense
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
					playerRepo.updateAfterFight(true, p.getPlayerID());
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
	
	public void playerFight(String player1ID, Player player1, Player player2) {
		List<Player> retrievedPlayers = playerRepo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if (player1ID.equals(p.getPlayerID())) {
				player1 = p;
			} 
		}
		
		//simulate fight		
		PlayableCharacter player1Char = player1.getSelectedCharacter();
		PlayableCharacter player2Char = player2.getSelectedCharacter();
		Item player1Item = player1.getSelectedItem();
		Item player2Item = player2.getSelectedItem();
		int player1Health = player1.getSelectedCharacter().getMaxHealth();
		int player2Health = player2.getSelectedCharacter().getMaxHealth();
		
		while(player2Health > 0 && player1Health > 0) {
			//strength is player attack - opponent defense
			double botStrength = player2Char.getAttackStat() + player2Item.getBonusToAttack()
				- (player1Char.getDefenseStat() + player1Item.getBonusToDefense());
			double playerStrength = player1Char.getAttackStat() + player1Item.getBonusToAttack()
				- (player2Char.getDefenseStat() + player2Item.getBonusToDefense());
			
			player2Health -= playerStrength;
			if(player2Health <= 0) {
				break;
			}
			player1Health -= botStrength;
		}
		
		String winnerID;
		if(player2Health < player1Health) {
			winnerID = player1.getPlayerID();
			playerRepo.updateAfterFight(true, player1.getPlayerID());
		} else {
			winnerID = player2.getPlayerID();
			playerRepo.updateAfterFight(true, player2.getPlayerID());
		}
		
		//save to repo
		MatchRecord newMatch = new MatchRecord(this.generateMatchID(), player1ID, 
				player1.getSelectedCharacter().getCharacterID(), 
				player1.getSelectedItem().getItemID(),
				player2.getPlayerID(), player2.getSelectedCharacter().getCharacterID(),
				player2.getSelectedItem().getItemID(), false, winnerID);
		matchRecordRepo.addMatchRecord(newMatch);
	}
	
	public String generateMatchID() {
		return UUID.randomUUID().toString();
	}
}
