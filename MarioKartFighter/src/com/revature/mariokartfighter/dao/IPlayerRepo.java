package com.revature.mariokartfighter.dao;

import java.util.List;

import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public interface IPlayerRepo {
	public Player addPlayer(Player player);
	public List<Player> getAllPlayers();
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID);
	public void assignItemToPlayer(Item item, String playerID);
	public void updateAfterFight(boolean wonMatch, String playerID);
}
