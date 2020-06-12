package com.revature.mariokartfighter.dao;

import java.util.List;

import com.revature.mariokartfighter.models.Bot;
import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public interface IPlayerRepo {
	public Player addPlayer(Player player);
	public Bot addBot(Bot bot);
	public List<Player> getAllPlayers();
	public void assignCharacterToPlayer(PlayableCharacter character, String playerID);
	public void assignItemToPlayer(Item item, String playerID);
	public void updateAfterFight(boolean wonMatch, String playerID);
	public int getPlayerRank(String playerID);
	public void removePlayers(String name);
}
