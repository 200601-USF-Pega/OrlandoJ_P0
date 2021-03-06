package com.revature.mariokartfighter.dao.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.dao.IPlayableCharacterRepo;
import com.revature.mariokartfighter.models.PlayableCharacter;

public class PlayableCharacterRepoFile implements IPlayableCharacterRepo {
	private String filepath= "src/resources/Character.txt";

	@Override
	public synchronized PlayableCharacter addCharacter(PlayableCharacter character) {
		List<PlayableCharacter> currentCharacters = this.getAllCharacters();
		try {
			ObjectOutputStream objectOutputStream = 
					new ObjectOutputStream(new FileOutputStream(filepath));
			currentCharacters.add(character);
			objectOutputStream.writeObject(currentCharacters);
			objectOutputStream.close();
			return character;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PlayableCharacter> getAllCharacters() {
		try {
			ObjectInputStream inputStream = 
					new ObjectInputStream(new FileInputStream(filepath));
			List<PlayableCharacter> retrievedCharacters = (ArrayList<PlayableCharacter>) inputStream.readObject();
			inputStream.close();
			return retrievedCharacters;
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			//Just in case Character class is not found
			e.printStackTrace();
		} 
		
		return new ArrayList<PlayableCharacter>();
	}

	@Override
	public List<PlayableCharacter> getSomeCharacters(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCharacters(String name) {
		// TODO Auto-generated method stub
		
	}

}
