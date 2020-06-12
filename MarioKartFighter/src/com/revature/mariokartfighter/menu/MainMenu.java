package com.revature.mariokartfighter.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter.dao.ICharacterRepo;
import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.dao.IMatchRecordRepo;
import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.dao.db.CharacterRepoDB;
import com.revature.mariokartfighter.dao.db.ItemRepoDB;
import com.revature.mariokartfighter.dao.db.MatchRecordRepoDB;
import com.revature.mariokartfighter.dao.db.PlayerRepoDB;
import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;
import com.revature.mariokartfighter.service.CharacterService;
import com.revature.mariokartfighter.service.ConnectionService;
import com.revature.mariokartfighter.service.GameService;
import com.revature.mariokartfighter.service.ItemService;
import com.revature.mariokartfighter.service.PlayerService;
import com.revature.mariokartfighter.service.ValidationService;

public class MainMenu {
	private static final Logger logger = LogManager.getLogger("MainMenu"); 
	
	ConnectionService connectionService;
	
	IPlayerRepo playerRepo;
	ICharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	
	private PlayerService playerService;
	private CharacterService characterService;
	private ItemService itemService;
	private ValidationService validationService;
	private GameService gameService;
	private String currPlayerID;
	
	private void setUp() {
		connectionService = new ConnectionService();
		
		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new CharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);
		
		playerService = new PlayerService(playerRepo);
		characterService = new CharacterService(characterRepo);
		itemService = new ItemService(itemRepo);
		validationService = new ValidationService();
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
	}
	
	public void mainMenu() {
		logger.info("---begin logging---");	
		setUp();
		
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
				String inputID = validationService.getValidString();
				if (playerService.checkPlayerExists(inputID)) {
					loggedIn = true;
					currPlayerID = inputID;
				} else if (inputID.toLowerCase().equals("exit")){
					continue;
				} else {
					System.out.println("ID does not exist...try again "
							+ "(type 'exit' to go back to main menu)");
				}
			} else if (optionNumber == 0) {
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
		} while (!loggedIn);
		
		System.out.println("Welcome Player " + currPlayerID);
		int optionNumber2 = -1;
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
				playerService.printPlayerInfo(currPlayerID);				
			} else if (optionNumber2 == 2) {
				int characterOption = -1;
				do {
					System.out.println("---CHARACTER MENU---");
					System.out.println("[1] List All Characters");
					System.out.println("[2] List Unlocked Characters");
					System.out.println("[3] Get Character Info");
					System.out.println("[4] Set My Character");
					System.out.println("[5] Create Custom Character");
					System.out.println("[6] Back to Main Menu");
					
					characterOption = validationService.getValidInt();
					
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
						boolean created = false;
						do {
							System.out.println("Enter character's name:");
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
					System.out.println(" ");
				} while (characterOption != 6);
			} else if (optionNumber2 == 3) {
				int itemOption = -1;
				do {
					System.out.println("---ITEM MENU---");
					System.out.println("[1] List All Items");
					System.out.println("[2] List Unlocked Items");
					System.out.println("[3] Get Item Info");
					System.out.println("[4] Set My Item");
					System.out.println("[5] Create Custom Item");
					System.out.println("[6] Back to Main Menu");
					
					itemOption = validationService.getValidInt();
					
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
						boolean created = false;
						do {
							System.out.println("Enter item's name:");
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
					System.out.println(" ");
				} while (itemOption != 6);
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
				Bot newBot = gameService.createNewBot(botLevel, randomCharacter, randomItem);
				
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
				
				System.out.println("Selecting opponent...");
				Player player1 = playerService.getPlayerObject(currPlayerID);
				Player player2 = playerService.chooseClosestPlayer(player1);
				
				//make sure a player to fight was chosen
				if (player2.getSelectedCharacter() == null)  {
					System.out.println("No players available to fight.");
					System.out.println("Redirecting to main menu...");
				} else {
					System.out.println("Opponent is " + player2.getPlayerID());
					playerService.printPlayerInfo(player2.getPlayerID());
					
					gameService.playerFight(currPlayerID, player1, player2);
				}
			} else if (optionNumber2 == 6) {
				int printMatchesOption = -1;
				do {
					System.out.println("---MATCHES MENU---");
					System.out.println("[1] List All Matches");
					System.out.println("[2] List My Matches");
					System.out.println("[3] Back to Main Menu");
					
					printMatchesOption = validationService.getValidInt();
					
					switch (printMatchesOption) {
					case 1:
						gameService.printAllMatches();
						break;
					case 2:
						gameService.printPlayerMatches(currPlayerID);
						break;
					case 3:
						break;
					default:
						System.out.println("Invalid option...Redirecting to Main Menu");
					}
					System.out.println(" ");
				} while (printMatchesOption != 6);
			} else if (optionNumber2 == 0) {
				System.out.println("Thanks for playing!");
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
			System.out.println(" ");
		} while (optionNumber2 != 0);
	}
}
