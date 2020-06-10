package com.revature.mariokartfighter.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class PlayerRepoFile implements IPlayerRepo {
	private String filepath= "src/resources/Player.txt";
	
	@Override
	public synchronized Player addPlayer(Player player) {
		List<Player> currentPlayers = this.getAllPlayers();
		try {
			ObjectOutputStream objectOutputStream = 
					new ObjectOutputStream(new FileOutputStream(filepath));
			currentPlayers.add(player);
			objectOutputStream.writeObject(currentPlayers);
			objectOutputStream.close();
			return player;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Player> getAllPlayers() {
		try {
			ObjectInputStream inputStream = 
					new ObjectInputStream(new FileInputStream(filepath));
			List<Player> retrievedPlayers = (ArrayList<Player>) inputStream.readObject();
			inputStream.close();
			return retrievedPlayers;
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			//Just in case Player class is not found
			e.printStackTrace();
		} 
		
		return new ArrayList<Player>();
	}

	@Override
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID) {
		List<Player> currentPlayers = this.getAllPlayers();
		for (Player p : currentPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				p.setSelectedCharacter(character);
				break;
			}
		}
		
		try {
			ObjectOutputStream objectOutputStream = 
					new ObjectOutputStream(new FileOutputStream(filepath));
			objectOutputStream.writeObject(currentPlayers);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void assignItemToPlayer(Item item, String playerID) {
		List<Player> currentPlayers = this.getAllPlayers();
		for (Player p : currentPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				p.setSelectedItem(item);
				break;
			}
		}
		
		try {
			ObjectOutputStream objectOutputStream = 
					new ObjectOutputStream(new FileOutputStream(filepath));
			objectOutputStream.writeObject(currentPlayers);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAfterFight(boolean wonMatch, String playerID) {
		List<Player> currentPlayers = this.getAllPlayers();
		for (Player p : currentPlayers) {
			if (p.getPlayerID().equals(playerID)) {
				if (wonMatch) {
					p.setXpEarned(p.getXpEarned() + 100);					
				} else {
					p.setXpEarned(p.getXpEarned() + 50);	
				}
				
				//check for level up
				if(p.getXpEarned() >= (p.getLevel()*100)+1) {
					p.setLevel(p.getLevel()+1);
					System.out.println("Congratulations! You leveled up!");
					System.out.println("You are now level " + p.getLevel() + ".");
				}
				
				break;
			}
		}
		try {
			ObjectOutputStream objectOutputStream = 
					new ObjectOutputStream(new FileOutputStream(filepath));
			objectOutputStream.writeObject(currentPlayers);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
