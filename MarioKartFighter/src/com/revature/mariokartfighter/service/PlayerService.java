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
		//check if player exists in main menu
		Player newPlayer = new Player(inputtedID);
		Player addedPlayer = repo.addPlayer(newPlayer);
		return addedPlayer.getPlayerID();
	}

	public void printPlayers() {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			System.out.println(p);
		}
	}
	
	public void printPlayersToFight(String playerID) {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				continue;
			} else if (p.getSelectedCharacter() == null || p.getSelectedItem() == null)  {
				continue;
			}
			System.out.println(p);
		}
	}
	
	public void printPlayerInfo(String playerID) {
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for(Player p : retrievedPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				System.out.println("\tLevel: " + p.getLevel());
				System.out.println("\tXP: " + p.getXpEarned());
				int rank  = repo.getPlayerRank(playerID);
				System.out.println("\tRank: " + rank + "/" + retrievedPlayers.size());
				if (p.getSelectedCharacter() == null) {					
					System.out.println("\tSelected Character: <None selected>");
				} else {
					System.out.println("\tSelected Character: " + p.getSelectedCharacter().getCharacterName());
				}
				if (p.getSelectedItem() == null ) {
					System.out.println("\tSelected Item: <None selected>");					
				} else {
					System.out.println("\tSelected Item: " + p.getSelectedItem().getItemName());
				}
				return;
			}
		}
	}
	
	public Player chooseClosestPlayer(Player player1) {
		//choose player with closest level
		int xpDiff = 1000000;
		Player player2 = new Player("");
		List<Player> retrievedPlayers = repo.getAllPlayers();
		for (Player p : retrievedPlayers) {
			if(Math.abs(p.getXpEarned() - player1.getXpEarned()) < xpDiff) {
				//check if player2 has selected an item and character
				if (p.getSelectedCharacter() == null || p.getSelectedItem() == null 
						|| p.getPlayerID().equals(player1.getPlayerID())) {
					continue;
				}
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
	
	public void removeTestPlayers(String testName) {
		repo.removePlayers(testName);
	}
}
