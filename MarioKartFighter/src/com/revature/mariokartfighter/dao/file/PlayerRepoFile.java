package com.revature.mariokartfighter.dao.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.revature.mariokartfighter.dao.IPlayerRepo;
import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class PlayerRepoFile implements IPlayerRepo {
	private String filepath= "src/resources/Player.txt";
	
	@Override
	public synchronized Player addPlayer(Player player, String password) {
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
			e.printStackTrace();
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
	public boolean updateAfterFight(boolean wonMatch, String playerID) {
		boolean leveledUp = false;
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
					leveledUp = true;
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
		return leveledUp;
	}

	@Override
	public int getPlayerRank(String playerID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removePlayers(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Bot addBot(Bot bot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String,String> getAllPlayersWithPasswords() {
		// TODO Auto-generated method stub
		return null;
	}

}
