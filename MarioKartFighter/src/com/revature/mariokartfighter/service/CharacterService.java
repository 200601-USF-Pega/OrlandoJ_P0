package com.revature.mariokartfighter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.revature.mariokartfighter.dao.ICharacterRepo;
import com.revature.mariokartfighter.models.PlayableCharacter;

public class CharacterService {
	ValidationService validation;
	ICharacterRepo repo;
	
	public CharacterService (ICharacterRepo repo) {
		this.repo = repo;
		this.validation = new ValidationService();
	}
	
	public String createNewCharacter() {
		String type;
		String name;
		int maxHealth;
		double attackStat;
		double defenseStat;
		int unlockAtLevel;
		
		//TODO validate input
		System.out.println("Name:");
		name = validation.getValidString();
		
		//check type is allowed
		boolean gotAllowedType = false;
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add("skill");
		allowedTypes.add("all-around");
		allowedTypes.add("speed");
		allowedTypes.add("power");
		
		System.out.println("Type (choose from skill, all-around, speed, power):");
		do {
			type = validation.getValidString();
			if (allowedTypes.contains(type.toLowerCase())) {
				gotAllowedType = true;
				break;
			} else {
				System.out.println("Not a valid character type...try again");
			}
		} while (gotAllowedType);
		
		System.out.println("Max Health:");
		maxHealth = validation.getValidInt();
		
		System.out.println("Attack Stat:");
		attackStat = validation.getValidDouble();
		
		System.out.println("Defense Stat:");
		defenseStat = validation.getValidDouble();
		
		//choose unlock level based on stats
		unlockAtLevel = (int)(maxHealth%10 + attackStat + defenseStat)/3;
		
		String characterID = generateCharacterID();
		PlayableCharacter newCharacter = new PlayableCharacter(characterID, type, name, maxHealth, attackStat, defenseStat, unlockAtLevel);
		repo.addCharacter(newCharacter);
		return characterID;
	}
	
	public String generateCharacterID() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
		
		//choose a random length up to 64 characters 
		Random random = new Random();
		int n = random.nextInt(63) + 1;
				
		StringBuilder sb;
		do {
			sb = new StringBuilder(n); 
	        for (int i = 0; i < n; i++) { 
	            int index = (int)(alphaNumericString.length()  * Math.random()); 
	            sb.append(alphaNumericString.charAt(index)); 
	        } 
		} while(checkCharacterExists(sb.toString()));
		
		return sb.toString();
	}
	
	public void getAllCharacters() {
		List<PlayableCharacter> retrievedCharacters = repo.getAllCharacters();
		for(PlayableCharacter c : retrievedCharacters) {
			System.out.println(c);
		}
	}
	
	public void getSomeCharacters(int level) {
		List<PlayableCharacter> retrievedCharacters = repo.getSomeCharacters(level);
		for(PlayableCharacter c : retrievedCharacters) {
			System.out.println(c);
		}
	}
	
	public boolean checkCharacterExists(String characterID) {
		List<PlayableCharacter> retrievedCharacters = repo.getAllCharacters();
		for(PlayableCharacter c : retrievedCharacters) {
			if (c.getCharacterID().equals(characterID)) {
				return true;
			}
		}
		return false;
	}
	
	public void getCharacterInfo(String characterName) {
		List<PlayableCharacter> retrievedCharacters = repo.getAllCharacters();
		for(PlayableCharacter c : retrievedCharacters) {
			if (c.getCharacterName().equals(characterName)) {
				System.out.println(c.getInfoString());
				return;
			}
		}
	}
	
	public void removeTestCharacters(String testName) {
		repo.removeCharacters(testName);
	}
}

