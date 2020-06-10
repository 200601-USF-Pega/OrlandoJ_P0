package com.revature.mariokartfighter.dao;

import java.util.List;

import com.revature.mariokartfighter.models.PlayableCharacter;

public interface ICharacterRepo {
	public PlayableCharacter addCharacter(PlayableCharacter character);
	public List<PlayableCharacter> getAllCharacters();
	public List<PlayableCharacter> getSomeCharacters(int level);
}
