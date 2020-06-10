package com.revature.mariokartfighter.models;

import java.io.Serializable;

public class PlayableCharacter implements Serializable {
	private static final long serialVersionUID = 1L;
	private String characterID;
	private String type;
	private String characterName;
	private int maxHealth;
	private double attackStat;
	private double defenseStat;
	private int unlockAtLevel;
	
	public PlayableCharacter(String type, String name, int maxHealth, double attackStat, double defenseStat, int unlockAtLevel) {
		this.characterID = "";
		this.type = type;
		this.characterName = name;
		this.maxHealth = maxHealth;
		this.attackStat = attackStat;
		this.defenseStat = defenseStat;
		this.unlockAtLevel = unlockAtLevel;
	}
	
	public PlayableCharacter(String id, String type, String name, int maxHealth, double attackStat, double defenseStat, int unlockAtLevel) {
		this.characterID = id;
		this.type = type;
		this.characterName = name;
		this.maxHealth = maxHealth;
		this.attackStat = attackStat;
		this.defenseStat = defenseStat;
		this.unlockAtLevel = unlockAtLevel;
	}

	
	@Override
	public String toString() {
		return "PlayableCharacter [characterID=" + characterID + ", type=" + type + ", characterName=" + characterName
				+ ", maxHealth=" + maxHealth + ", attackStat=" + attackStat + ", defenseStat=" + defenseStat
				+ ", unlockAtLevel=" + unlockAtLevel + "]";
	}
	
	public String getInfoString() {
		String printString = "";
		printString += "\tCharacter ID: " + this.characterID + "\n";
		printString += "\tName: " + this.characterName + "\n";
		printString += "\tType: " + this.type + "\n";
		printString += "\tAttack Stat: " + this.attackStat + "\n";
		printString += "\tDefense Stat: " + this.defenseStat + "\n";
		printString += "\tMax Health: " + this.maxHealth + "\n";
		printString += "\tUnlock at level: " + this.unlockAtLevel + "\n";
		return printString;
	}


	public String getCharacterID() {
		return characterID;
	}

	public void setCharacterID(String characterID) {
		this.characterID = characterID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String name) {
		this.characterName = name;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getAttackStat() {
		return attackStat;
	}

	public void setAttackStat(double attackStat) {
		this.attackStat = attackStat;
	}

	public double getDefenseStat() {
		return defenseStat;
	}

	public void setDefenseStat(double defenseStat) {
		this.defenseStat = defenseStat;
	}

	public int getUnlockAtLevel() {
		return unlockAtLevel;
	}

	public void setUnlockAtLevel(int unlockAtLevel) {
		this.unlockAtLevel = unlockAtLevel;
	}
}
