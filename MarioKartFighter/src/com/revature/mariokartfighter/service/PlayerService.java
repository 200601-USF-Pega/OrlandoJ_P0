package com.revature.mariokartfighter.service;

import java.util.List;

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
	}
	
	public Player chooseClosestPlayer(Player player1) {
		//choose player with closest level
		int xpDiff = 1000000;
		Player player2 = new Player("closestplayer");
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(Math.abs(p.getXpEarned() - player1.getXpEarned()) < xpDiff) {
				player2 = p;
				xpDiff = Math.abs(p.getXpEarned() - player1.getXpEarned());
			} 
		}
		return player2;
	}
	
	public Player getPlayerObject(String playerID) throws RuntimeException {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				return p;
			}
		}
		throw new RuntimeException("player does not exist");
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
