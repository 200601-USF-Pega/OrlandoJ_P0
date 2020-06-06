package com.revature.mariokartfighter.service;

import java.util.List;
import java.util.Random;

import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.models.Player;

public class PlayerService {
	IPlayerRepo repo;
	
	public PlayerService (IPlayerRepo repo) {
		this.repo = repo;
	}
	
	public String createNewPlayer() {
		Player newPlayer = new Player(this.generatePlayerID());
		repo.addPlayer(newPlayer);
		return newPlayer.getPlayerID();
	}
	
	public String generatePlayerID() {
		String AlphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz"; 
		
		//choose a random length up to 12 characters for the playerID
		Random random = new Random();
		int n = random.nextInt(11) + 1;
				
		StringBuilder sb = new StringBuilder(n); 
		do {
	        for (int i = 0; i < n; i++) { 
	            // generate a random number between 
	            // 0 to AlphaNumericString variable length 
	            int index = (int)(AlphabetString.length()  * Math.random()); 
	  
	            // add Character one by one in end of sb 
	            sb.append(AlphabetString .charAt(index)); 
	        } 
		} while(checkPlayerExists(sb.toString()));
		
		return sb.toString();
	}

	public void getPlayers() {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			System.out.println(p);
		}
	}
	
	public void getPlayerInfo(String playerID) {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				System.out.println("\tLevel: " + p.getLevel());
				System.out.println("\tRank: ");
				return;
			}
		}
		//System.out.println("Player does not exist");
	}
	
	public boolean checkPlayerExists(String playerID) {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				return true;
			}
		}
		return false;
	}
}
