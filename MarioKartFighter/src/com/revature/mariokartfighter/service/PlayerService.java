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
	
	public String createNewPlayer(String inputtedID) {		
		Player newPlayer = new Player(inputtedID);
		repo.addPlayer(newPlayer);
		return newPlayer.getPlayerID();
	}
	
//	public String generatePlayerID() {
//		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//				+ "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
//		
//		//choose a random length up to 64 characters
//		Random random = new Random();
//		int n = random.nextInt(63) + 1;
//				
//		StringBuilder sb;
//		do {
//			sb = new StringBuilder(n); 
//	        for (int i = 0; i < n; i++) { 
//	            int index = (int)(alphaNumericString.length()  * Math.random()); 
//	            sb.append(alphaNumericString.charAt(index)); 
//	        } 
//		} while(checkPlayerExists(sb.toString()));
//		
//		return sb.toString();
//	}

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
