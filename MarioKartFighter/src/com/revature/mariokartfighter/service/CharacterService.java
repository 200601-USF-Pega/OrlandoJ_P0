package com.revature.mariokartfighter.service;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.revature.mariokartfighter.dao.ICharacterRepo;
import com.revature.mariokartfighter.models.PlayableCharacter;

public class CharacterService {
	Scanner input = new Scanner(System.in);
	ICharacterRepo repo;
	
	public CharacterService (ICharacterRepo repo) {
		this.repo = repo;
	}
	
	public void createNewCharacter() {
		String type;
		String name;
		int maxHealth;
		double attackStat;
		double defenseStat;
		int unlockAtLevel;
		
		//TODO validate input
		System.out.println("Name:");
		name = input.nextLine();
		
		System.out.println("Type:");
		type = input.nextLine();
		
		System.out.println("Max Health:");
		maxHealth = input.nextInt();
		input.nextLine();
		
		System.out.println("Attack Stat:");
		attackStat = input.nextDouble();
		input.nextLine();
		
		System.out.println("Defense Stat:");
		defenseStat = input.nextDouble();
		input.nextLine();
		
		//choose unlock level based on stats
		unlockAtLevel = (int)(maxHealth%10 + attackStat + defenseStat)/3;
		
		PlayableCharacter newCharacter = new PlayableCharacter(type, name, maxHealth, attackStat, defenseStat, unlockAtLevel);
		repo.addCharacter(newCharacter);
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
}

