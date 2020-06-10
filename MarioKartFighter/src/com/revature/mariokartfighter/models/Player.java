package com.revature.mariokartfighter.models;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String playerID;
	private int level;
	private int xpEarned;
	private int numberOfWins;
	private int numberOfMatches;
	private PlayableCharacter selectedCharacter;
	private Item selectedItem;
	
	public Player(String playerID) {
		this.playerID = playerID;
		this.level = 1;
		this.xpEarned = 0;
		this.numberOfWins = 0;
		this.numberOfMatches = 0;
	}

	public Player(String playerID, int level, int xpEarned, int numberOfWins, int numberOfMatches,
			PlayableCharacter selectedCharacter, Item selectedItem) {
		super();
		this.playerID = playerID;
		this.level = level;
		this.xpEarned = xpEarned;
		this.numberOfWins = numberOfWins;
		this.numberOfMatches = numberOfMatches;
		this.selectedCharacter = selectedCharacter;
		this.selectedItem = selectedItem;
	}

	public String getPlayerID() {
		return playerID;
	}

	@Override
	public String toString() {
		return "Player [playerID=" + playerID + ", level=" + level + ", xpEarned=" + xpEarned + ", numberOfWins="
				+ numberOfWins + ", numberOfMatches=" + numberOfMatches + ", selectedCharacter=" + selectedCharacter
				+ ", selectedItem=" + selectedItem + "]";
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXpEarned() {
		return xpEarned;
	}

	public void setXpEarned(int xpEarned) {
		this.xpEarned = xpEarned;
	}

	public int getNumberOfWins() {
		return numberOfWins;
	}

	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	public int getNumberOfMatches() {
		return numberOfMatches;
	}

	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}

	public PlayableCharacter getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(PlayableCharacter selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}
}
