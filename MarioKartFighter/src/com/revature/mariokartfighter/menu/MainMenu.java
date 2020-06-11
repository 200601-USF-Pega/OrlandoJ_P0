package com.revature.mariokartfighter.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.mariokartfighter.dao.CharacterRepoDB;
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
	private static final Logger logger = LogManager.getLogger("MinMenu"); 
	
	private PlayerService playerService = new PlayerService(new PlayerRepoDB());
	private CharacterService characterService = new CharacterService(new CharacterRepoDB());
	private ItemService itemService = new ItemService(new ItemRepoDB());
	private ValidationService validationService = new ValidationService();
	private GameService gameService = new GameService(new PlayerRepoDB(), new CharacterRepoDB(),
			new ItemRepoDB(), new MatchRecordRepoDB());
	private String currPlayerID;
	
	public void mainMenu() {
		logger.info("---begin logging---");
		
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
				System.out.println("[2] Get Character Info");
				System.out.println("[3] Set My Character");
				System.out.println("[4] Create Custom Character");
				System.out.println("[5] Back to Main Menu");
				
				int characterOption = validationService.getValidInt();
				
				switch (characterOption) {
				case 1:
					characterService.getAllCharacters();
					break;
				case 2:
					System.out.println("Enter character's name:");
					String nameInput = validationService.getValidString();
					characterService.getCharacterInfo(nameInput);
					break;
				case 3:
					System.out.println("Enter character's name:");
					boolean created = false;
					do {
						String charNameInput = validationService.getValidString();					
						created = gameService.setCharacter(charNameInput, currPlayerID);
					} while (!created);
					break;
				case 4:
					characterService.createNewCharacter();
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid option...Redirecting to Main Menu");
				}
				
			} else if (optionNumber2 == 3) {
				int itemOption = validationService.getValidInt();
				
				System.out.println("---ITEM MENU---");
				System.out.println("[1] List All Items");
				System.out.println("[2] Get Item Info");
				System.out.println("[3] Set My Item");
				System.out.println("[4] Create Custom Item");
				System.out.println("[5] Back to Main Menu");
				
				switch (itemOption) {
				case 1:
					itemService.getAllItems();
					break;
				case 2:
					System.out.println("Enter item's name:");
					String nameInput = validationService.getValidString();
					itemService.getItemInfo(nameInput);
					break;
				case 3:
					System.out.println("Enter item's name:");
					boolean created = false;
					do {
						String itemNameInput = validationService.getValidString();					
						created = gameService.setItem(itemNameInput, currPlayerID);
					} while (!created);
					break;
				case 4:
					itemService.createNewItem();
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid option...Redirecting to Main Menu");
				}
				
			} else if (optionNumber2 == 4) {
				//ask for level of bot
				System.out.println("What level bot would you like to fight?");
				int botLevel = validationService.getValidInt();
				PlayableCharacter randomCharacter = gameService.chooseRandomCharacter(botLevel);
				Item randomItem = gameService.chooseRandomItem(botLevel);
				Bot newBot = new Bot(botLevel, randomCharacter, randomItem);
				
				gameService.botFight(newBot, currPlayerID);
				
			} else if (optionNumber2 == 5) {
				Player player1 = playerService.getPlayerObject(currPlayerID);
				Player player2 = playerService.chooseClosestPlayer(player1);
				gameService.playerFight(currPlayerID, player1, player2);
				
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
