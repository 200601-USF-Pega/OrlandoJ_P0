package com.revature.mariokartfighter.menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter.dao.CharacterRepoDB;
import com.revature.mariokartfighter.dao.ICharacterRepo;
import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.dao.IMatchRecordRepo;
import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.dao.ItemRepoDB;
import com.revature.mariokartfighter.dao.MatchRecordRepoDB;
import com.revature.mariokartfighter.dao.PlayerRepoDB;
import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;
import com.revature.mariokartfighter.service.CharacterService;
import com.revature.mariokartfighter.service.GameService;
import com.revature.mariokartfighter.service.ItemService;
import com.revature.mariokartfighter.service.PlayerService;
import com.revature.mariokartfighter.service.ValidationService;

public class MainMenu {
	private static final Logger logger = LogManager.getLogger("MainMenu"); 
	
	Connection connection;
	
	IPlayerRepo playerRepo = new PlayerRepoDB(connection);
	ICharacterRepo characterRepo = new CharacterRepoDB(connection);
	IItemRepo itemRepo = new ItemRepoDB(connection);
	IMatchRecordRepo matchRecordRepo = new MatchRecordRepoDB(connection);
	
	private PlayerService playerService = new PlayerService(playerRepo);
	private CharacterService characterService = new CharacterService(characterRepo);
	private ItemService itemService = new ItemService(itemRepo);
	private ValidationService validationService = new ValidationService();
	private GameService gameService = new GameService(playerRepo, characterRepo, itemRepo,
			matchRecordRepo);
	private String currPlayerID;
	
	public void mainMenu() {
		logger.info("---begin logging---");
		
		//make connection manager
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://ruby.db.elephantsql.com:5432/brdzdjzb", 
					"brdzdjzb", "l7Lh2FHoFuFdz4Gf1h5j0-9LSj78BeJ8");
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}		
		
		System.out.println("WELCOME TO MARIO KART FIGHTER!");
		System.out.println("Please choose an option:");
		System.out.println("[1] New Player");
		System.out.println("[2] Returning Player");
		System.out.println("[0] Exit the program");
		
		int optionNumber = validationService.getValidInt();
		
		boolean loggedIn = false;
		do {
			if (optionNumber == 1) {
				System.out.println("Enter a username (4-24 characters):");
				String inputtedID = validationService.getValidString();
				while (!loggedIn) {
					if (inputtedID.length() > 24 || inputtedID.length() < 4) {
						System.out.println("username wrong length...try again");												
					} else if(playerService.checkPlayerExists(inputtedID)) {
						inputtedID = validationService.getValidString();
						System.out.println("ID already taken...try again");
					} else {
						currPlayerID = playerService.createNewPlayer(inputtedID);
						loggedIn = true;
						break;
					}
				}
			} else if (optionNumber == 2) {
				//find player in database
				System.out.println("Enter your player ID to login:");
				System.out.println("** IDs are case-sensitive **");
				String inputID = validationService.getValidString();
				if (playerService.checkPlayerExists(inputID)) {
					loggedIn = true;
					currPlayerID = inputID;
				} else if (inputID.toLowerCase().equals("exit")){
					System.exit(0);
				} else {
					System.out.println("ID does not exist...try again (type 'exit' to go quit program)");
				}
			} else if (optionNumber == 0) {
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
		} while (!loggedIn);
		
		System.out.println("Welcome Player " + currPlayerID);
		int optionNumber2;
		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] View my Level and Rank");
			System.out.println("[2] Character Menu");
			System.out.println("[3] Item Menu");
			System.out.println("[4] Fight a Bot");
			System.out.println("[5] Fight a Player");
			System.out.println("[6] View Record of my Matches");
			System.out.println("[0] Exit the program");
			
			optionNumber2 = validationService.getValidInt();		
			
			if (optionNumber2 == 1) {
				//print player level and rank
				playerService.getPlayerInfo(currPlayerID);				
			} else if (optionNumber2 == 2) {

				System.out.println("---CHARACTER MENU---");
				System.out.println("[1] List All Characters");
				System.out.println("[2] List Unlocked Characters");
				System.out.println("[3] Get Character Info");
				System.out.println("[4] Set My Character");
				System.out.println("[5] Create Custom Character");
				System.out.println("[6] Back to Main Menu");
				
				int characterOption = validationService.getValidInt();
				
				switch (characterOption) {
				case 1:
					characterService.getAllCharacters();
					break;
				case 2:
					characterService.getSomeCharacters(
							playerService.getPlayerObject(currPlayerID).getLevel());
					break;
				case 3:
					System.out.println("Enter character's name:");
					String nameInput = validationService.getValidString();
					characterService.getCharacterInfo(nameInput);
					break;
				case 4:
					System.out.println("Enter character's name:");
					boolean created = false;
					do {
						String charNameInput = validationService.getValidString();					
						created = gameService.setCharacter(charNameInput, currPlayerID);
					} while (!created);
					break;
				case 5:
					characterService.createNewCharacter();
					break;
				case 6:
					break;
				default:
					System.out.println("Invalid option...Redirecting to Main Menu");
				}
				
			} else if (optionNumber2 == 3) {
				int itemOption = validationService.getValidInt();
				
				System.out.println("---ITEM MENU---");
				System.out.println("[1] List All Items");
				System.out.println("[2] List Unlocked Items");
				System.out.println("[3] Get Item Info");
				System.out.println("[4] Set My Item");
				System.out.println("[5] Create Custom Item");
				System.out.println("[6] Back to Main Menu");
				
				switch (itemOption) {
				case 1:
					itemService.getAllItems();
					break;
				case 2:
					itemService.getSomeItems(
							playerService.getPlayerObject(currPlayerID).getLevel());
					break;
				case 3:
					System.out.println("Enter item's name:");
					String nameInput = validationService.getValidString();
					itemService.getItemInfo(nameInput);
					break;
				case 4:
					System.out.println("Enter item's name:");
					boolean created = false;
					do {
						String itemNameInput = validationService.getValidString();					
						created = gameService.setItem(itemNameInput, currPlayerID);
					} while (!created);
					break;
				case 5:
					itemService.createNewItem();
					break;
				case 6:
					break;
				default:
					System.out.println("Invalid option...Redirecting to Main Menu");
				}
				
			} else if (optionNumber2 == 4) {
				//check if player has selected an item and character
				Player thisPlayer = playerService.getPlayerObject(currPlayerID);
				if (thisPlayer.getSelectedCharacter() == null 
						|| thisPlayer.getSelectedItem() == null) {
					System.out.println("You can't fight yet because you haven't selected "
							+ "a character and item.");
					System.out.println("Redirecting to main menu...");
					continue;
				}
				
				//ask for level of bot
				System.out.println("What level bot would you like to fight?");
				int botLevel = validationService.getValidInt();
				PlayableCharacter randomCharacter = gameService.chooseRandomCharacter(botLevel);
				Item randomItem = gameService.chooseRandomItem(botLevel);
				Bot newBot = new Bot(botLevel, randomCharacter, randomItem);
				
				gameService.botFight(newBot, currPlayerID);
				
			} else if (optionNumber2 == 5) {
				//check if player has selected an item and character
				Player thisPlayer = playerService.getPlayerObject(currPlayerID);
				if (thisPlayer.getSelectedCharacter() == null 
						|| thisPlayer.getSelectedItem() == null) {
					System.out.println("You can't fight yet because you haven't selected "
							+ "a character and item.");
					System.out.println("Redirecting to main menu...");
					continue;
				}
				
				Player player1 = playerService.getPlayerObject(currPlayerID);
				Player player2 = playerService.chooseClosestPlayer(player1);
				
				//make sure a player to fight was chosen
				if (player2.getSelectedCharacter() == null)  {
					System.out.println("No players available to fight.");
					System.out.println("Redirecting to main menu...");
				} else {
					gameService.playerFight(currPlayerID, player1, player2);
				}
			} else if (optionNumber2 == 6) {
				//TODO print record of matches
				
			} else if (optionNumber2 == 0) {
				System.out.println("Thanks for playing!");
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
			
		} while (optionNumber2 != 0);
	}
}
