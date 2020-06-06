package com.revature.mariokartfighter.dao;

import java.util.List;

import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public interface IPlayerRepo {
	public Player addPlayer(Player player);
	public List<Player> getAllPlayers();
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID);
}
