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
					if (player.getSelectedItem() != null && !player.getSelectedCharacter().getType().equals(
							player.getSelectedItem().getTypeThatCanUse())) {		
						System.out.println("Item type no longer compatible with character type!!");
						System.out.println("Unsetting selected item...");
						player.setSelectedItem(null);
						playerRepo.assignItemToPlayer(null, playerID);
					}
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
			if (itemName.equals(i.getItemName())) {
				//check if player has unlocked
				Player player = null;
				List<Player> retrievedPlayers = playerRepo.getAllPlayers();
				for (Player p : retrievedPlayers) {
					if (playerID.equals(p.getPlayerID())) {
						player = p;
						break;
					}
				}

				if (player != null) {
					//check if selectedCharacter can use
					 if (player.getLevel() < i.getUnlockAtLevel()) {
						System.out.println("You haven't unlocked this item yet.");	
						System.out.println("**Earn more XP by playing matches**");
						return false;
					} else if (!player.getSelectedCharacter().getType().equals(i.getTypeThatCanUse())) {		
						System.out.println("Item type not compatible with character type!!");
						System.out.println("\titem type: " + i.getTypeThatCanUse());
						System.out.println("\tcharacter type: " + player.getSelectedCharacter().getType());
						return false;
					} else {
						playerRepo.assignItemToPlayer(i, playerID);
					}
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
	
	public Bot createNewBot(int botLevel, PlayableCharacter randomCharacter, Item randomItem ) {
		Bot newBot = new Bot(botLevel, randomCharacter, randomItem);
		playerRepo.addBot(newBot);
		return newBot;
	}
	
	public void botFight(Bot bot, String playerID) {
		//find player info
		List<Player> retrievedPlayers = playerRepo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if (playerID.equals(p.getPlayerID())) {		
				PlayableCharacter playerChar = p.getSelectedCharacter();
				PlayableCharacter botChar = bot.getSelectedCharacter();
				Item playerItem = p.getSelectedItem();
				Item botItem = bot.getSelectedItem();
				int playerHealth = p.getSelectedCharacter().getMaxHealth();
				int botHealth = bot.getSelectedCharacter().getMaxHealth();
				
				System.out.println("Player 1 Character:");
				System.out.println(playerChar.getInfoString());
				System.out.println("Player 1 Item:");
				System.out.println(playerItem.getInfoString());
				
				System.out.println("Bot Character:");
				System.out.println(botChar.getInfoString());
				System.out.println("Bot Item:");
				System.out.println(botItem.getInfoString());
				
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
					System.out.println("Player 1 wins!!");
				} else {
					winnerID = bot.getID();
					System.out.println("Bot wins!!");
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
				
		PlayableCharacter player1Char = player1.getSelectedCharacter();
		PlayableCharacter player2Char = player2.getSelectedCharacter();
		Item player1Item = player1.getSelectedItem();
		Item player2Item = player2.getSelectedItem();
		int player1Health = player1.getSelectedCharacter().getMaxHealth();
		int player2Health = player2.getSelectedCharacter().getMaxHealth();
		
		System.out.println("Player 1 Character:");
		System.out.println(player1Char.getInfoString());
		System.out.println("Player 1 Item:");
		System.out.println(player1Item.getInfoString());
		
		System.out.println("Player 2 Character:");
		System.out.println(player2Char.getInfoString());
		System.out.println("Player 2 Item:");
		System.out.println(player2Item.getInfoString());
		
		while(player2Health > 0 && player1Health > 0) {
			//strength is player attack - opponent defense
			double player1Strength = player2Char.getAttackStat() + player2Item.getBonusToAttack()
				- (player1Char.getDefenseStat() + player1Item.getBonusToDefense());
			double player2Strength = player1Char.getAttackStat() + player1Item.getBonusToAttack()
				- (player2Char.getDefenseStat() + player2Item.getBonusToDefense());
			
			player2Health -= player1Strength;
			if(player2Health <= 0) {
				break;
			}
			player1Health -= player2Strength;
		}
		
		String winnerID;
		boolean levelUp1 = false;
		if(player2Health < player1Health) {
			winnerID = player1.getPlayerID();
			levelUp1 = playerRepo.updateAfterFight(true, player1.getPlayerID());
			playerRepo.updateAfterFight(false, player2.getPlayerID());
			System.out.println("Player 1 wins!!");
		} else {
			winnerID = player2.getPlayerID();
			levelUp1 = playerRepo.updateAfterFight(false, player1.getPlayerID());
			playerRepo.updateAfterFight(true, player2.getPlayerID());
			System.out.println("Player 2 wins!!");
		}
		
		if (levelUp1) {
			System.out.println("Congratulations! You leveled up!");
			System.out.println("You are now level " + player1.getLevel() + ".");
		}
		
		//save to repo
		MatchRecord newMatch = new MatchRecord(this.generateMatchID(), player1ID, 
				player1.getSelectedCharacter().getCharacterID(), 
				player1.getSelectedItem().getItemID(),
				player2.getPlayerID(), player2.getSelectedCharacter().getCharacterID(),
				player2.getSelectedItem().getItemID(), false, winnerID);
		matchRecordRepo.addMatchRecord(newMatch);
	}
	
	public void printAllMatches() {
		System.out.println(String.format("%-25s|%-20s|%-20s|%-20s|%-25s|%-20s|%-20s|%-20s\n", 
				"timeOfMatch", "player1ID", "player1CharacterID", "player1ItemID", "player2ID", 
				"player2CharacterID", "player2ItemID", "winnerID"));
		List<MatchRecord> allMatches = matchRecordRepo.getAllMatches();
		for (MatchRecord mr : allMatches) {
			System.out.println(mr);
		}
	}
	
	public void printPlayerMatches(String playerID) {
		System.out.println(String.format("%-25s|%-20s|%-20s|%-20s|%-25s|%-20s|%-20s|%-20s\n", 
				"timeOfMatch", "player1ID", "player1CharacterID", "player1ItemID", "player2ID", 
				"player2CharacterID", "player2ItemID", "winnerID"));
		List<MatchRecord> allMatches = matchRecordRepo.getPlayerMatches(playerID);
		for (MatchRecord mr : allMatches) {
			System.out.println(mr);
		}
	}
	
	public String generateMatchID() {
		return UUID.randomUUID().toString();
	}
}
