package com.revature.mariokartfighter.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter.dao.IPlayableCharacterRepo;
import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.dao.IMatchRecordRepo;
import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.dao.db.PlayableCharacterRepoDB;
import com.revature.mariokartfighter.dao.db.ItemRepoDB;
import com.revature.mariokartfighter.dao.db.MatchRecordRepoDB;
import com.revature.mariokartfighter.dao.db.PlayerRepoDB;
import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;
import com.revature.mariokartfighter.service.PlayableCharacterService;
import com.revature.mariokartfighter.service.ConnectionService;
import com.revature.mariokartfighter.service.GameService;
import com.revature.mariokartfighter.service.ItemService;
import com.revature.mariokartfighter.service.PlayerService;
import com.revature.mariokartfighter.service.ValidationService;

public class MainMenu {
	private static final Logger logger = LogManager.getLogger(MainMenu.class); 
	
	ConnectionService connectionService;
	
	IPlayerRepo playerRepo;
	IPlayableCharacterRepo characterRepo;
	IItemRepo itemRepo;
	IMatchRecordRepo matchRecordRepo;
	
	private PlayerService playerService;
	private PlayableCharacterService characterService;
	private ItemService itemService;
	private ValidationService validationService;
	private GameService gameService;
	private String currPlayerID;
	
	private void setUp() {
		connectionService = new ConnectionService();
		logger.info("created new connection service");
		
		playerRepo = new PlayerRepoDB(connectionService);
		characterRepo = new PlayableCharacterRepoDB(connectionService);
		itemRepo = new ItemRepoDB(connectionService);
		matchRecordRepo = new MatchRecordRepoDB(connectionService);
		logger.info("created new repo objects");
		
		playerService = new PlayerService(playerRepo);
		characterService = new PlayableCharacterService(characterRepo);
		itemService = new ItemService(itemRepo);
		validationService = new ValidationService();
		gameService = new GameService(playerRepo, characterRepo, itemRepo,
				matchRecordRepo);
		logger.info("created new service objects");
	}
	
	public void mainMenu() {	
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
						String inputtedPassword = "";
						do {
							System.out.println("Enter a password (4-24 characters):");
							inputtedPassword = validationService.getValidString();
						} while(inputtedPassword.length() < 4 || inputtedPassword.length() > 24); 					
						currPlayerID = playerService.createNewPlayer(inputtedID, inputtedPassword);
						loggedIn = true;
						break;
					}
				}
			} else if (optionNumber == 2) {
				//find player in database
				System.out.println("Enter your player ID to login:");
				String inputID = validationService.getValidString();
				if (playerService.checkPlayerExists(inputID)) {
					String inputtedPassword = "";
					do {
						System.out.println("Enter a password (4-24 characters):");
						inputtedPassword = validationService.getValidString();
					} while(!playerService.checkPassword(inputID, inputtedPassword));
					loggedIn = true;
					currPlayerID = inputID;
					logger.info("player successfully logged in");
				} else {
					System.out.println("ID does not exist...try again "
							+ "(type 'exit' to go back to main menu)");
				}
			} else if (optionNumber == 0) {
				logger.info("player exited game");
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
			System.out.println("[4] Fight Menu");
			System.out.println("[5] Matches Menu");
			System.out.println("[0] Exit the program");
			
			optionNumber2 = validationService.getValidInt();		
			
			if (optionNumber2 == 1) {
				//print player level and rank
				playerService.printPlayerInfo(currPlayerID);
			} else if (optionNumber2 == 2) {
				logger.info("player entered character menu");
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
						characterService.printAllCharacters();
						break;
					case 2:
						characterService.printSomeCharacters(
								playerService.getPlayerObject(currPlayerID).getLevel());
						break;
					case 3:
						System.out.println("Enter character's name:");
						String nameInput = validationService.getValidString();
						characterService.printCharacterInfo(nameInput);
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
						logger.info("player exited character menu");
						break;
					default:
						System.out.println("Invalid option...Redirecting to Main Menu");
					}
					System.out.println(" ");
				} while (characterOption != 6);
			} else if (optionNumber2 == 3) {
				logger.info("player entered item menu");
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
						itemService.printAllItems();
						break;
					case 2:
						itemService.printSomeItems(
								playerService.getPlayerObject(currPlayerID).getLevel());
						break;
					case 3:
						System.out.println("Enter item's name:");
						String nameInput = validationService.getValidString();
						itemService.printItemInfo(nameInput);
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
						logger.info("player exited item menu");
						break;
					default:
						System.out.println("Invalid option...Redirecting to Main Menu");
					}
					System.out.println(" ");
				} while (itemOption != 6);
			} else if (optionNumber2 == 4) {
				logger.info("player entered fight menu");
				int fightOption = -1;
				Player thisPlayer;
				Player player1;
				Player player2;
				do {
					System.out.println("---FIGHT MENU---");
					System.out.println("[1] Fight a Bot");
					System.out.println("[2] Fight a Random Player");
					System.out.println("[3] Choose a Player to Fight");
					System.out.println("[4] Back to Main Menu");
					
					fightOption = validationService.getValidInt();
					
					switch(fightOption) {
					case 1:
						//check if player has selected an item and character
						thisPlayer = playerService.getPlayerObject(currPlayerID);
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
						Item randomItem = gameService.chooseRandomItem(botLevel, randomCharacter.getType());
						Bot newBot = gameService.createNewBot(botLevel, randomCharacter, randomItem);
						
						gameService.botFight(newBot, currPlayerID);
						break;
					case 2:
						//check if player has selected an item and character
						thisPlayer = playerService.getPlayerObject(currPlayerID);
						if (thisPlayer.getSelectedCharacter() == null 
								|| thisPlayer.getSelectedItem() == null) {
							System.out.println("You can't fight yet because you haven't selected "
									+ "a character and item.");
							System.out.println("Redirecting to main menu...");
							continue;
						}
						
						System.out.println("Selecting opponent...");
						player1 = playerService.getPlayerObject(currPlayerID);
						player2 = playerService.chooseClosestPlayer(player1);
						
						//make sure a player to fight was chosen
						if (player2.getSelectedCharacter() == null)  {
							System.out.println("No players available to fight.");
							System.out.println("Redirecting to main menu...");
						} else {
							System.out.println("Opponent is " + player2.getPlayerID());
							playerService.printPlayerInfo(player2.getPlayerID());
							
							gameService.playerFight(player1, player2);
						}
						break;
					case 3:
						//check if player has selected an item and character
						thisPlayer = playerService.getPlayerObject(currPlayerID);
						if (thisPlayer.getSelectedCharacter() == null 
								|| thisPlayer.getSelectedItem() == null) {
							System.out.println("You can't fight yet because you haven't selected "
									+ "a character and item.");
							System.out.println("Redirecting to main menu...");
							continue;
						}

						player1 = playerService.getPlayerObject(currPlayerID);
						
						System.out.println("Players available to fight:");
						playerService.printPlayersToFight(currPlayerID);
						
						System.out.println("Enter the playerID of the player you want to fight:");
						String player2ID = validationService.getValidString();
						do {
							if(playerService.checkPlayerExists(player2ID)) {
								break;
							}
							player2ID = validationService.getValidString();
						} while (!playerService.checkPlayerExists(player2ID));
						
						player2 = playerService.getPlayerObject(player2ID);
						
						System.out.println("Opponent is " + player2.getPlayerID());
						playerService.printPlayerInfo(player2.getPlayerID());
						
						gameService.playerFight(player1, player2);
						break;
					case 4:
						logger.info("player exited fight menu");
						break;
					default:
						System.out.println("Invalid option...Redirecting to Main Menu");
					}
				} while (fightOption != 4);		
			} else if (optionNumber2 == 5)  {
				logger.info("player entered matches menu");
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
						logger.info("player exited matches menu");
						break;
					default:
						System.out.println("Invalid option...Redirecting to Main Menu");
					}
					System.out.println(" ");
				} while (printMatchesOption != 3);
			} else if (optionNumber2 == 0) {
				System.out.println("Thanks for playing!");
				logger.info("player exited game");
				System.exit(0);
			} else {
				System.out.println("Invalid option number");
			}
		} while (optionNumber2 != 0);
	}
}
