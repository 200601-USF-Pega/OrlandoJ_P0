package com.revature.mariokartfighter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.revature.mariokartfighter.models.PlayableCharacter;

public class CharacterRepoDB implements ICharacterRepo {
	Connection connection;
	
	public CharacterRepoDB() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://ruby.db.elephantsql.com:5432", 
					"brdzdjzb", "l7Lh2FHoFuFdz4Gf1h5j0-9LSj78BeJ8");
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public PlayableCharacter addCharacter(PlayableCharacter character) {
		try {			
			PreparedStatement characterInsert = connection.prepareStatement(
					"INSERT INTO character VALUES (?, ?, ?, ?, ?, ?, ?)");
			characterInsert.setString(1, character.getCharacterID());
			characterInsert.setString(2, character.getCharacterName());
			characterInsert.setString(3, character.getType());
			characterInsert.setInt(4, character.getMaxHealth());
			characterInsert.setDouble(5, character.getAttackStat());
			characterInsert.setDouble(6, character.getDefenseStat());
			characterInsert.setInt(7, character.getUnlockAtLevel());
			
			characterInsert.executeUpdate();
			
			return character;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PlayableCharacter> getAllCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayableCharacter> getSomeCharacters(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
