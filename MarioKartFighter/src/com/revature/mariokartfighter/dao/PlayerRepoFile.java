package com.revature.mariokartfighter.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.models.Player;

public class PlayerRepoFile implements IPlayerRepo {
	private String filepath= "src/resources/Player.txt";
	
	@Override
	public Player addPlayer(Player player) {
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

}
