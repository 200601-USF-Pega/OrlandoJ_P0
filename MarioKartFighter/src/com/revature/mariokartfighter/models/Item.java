package com.revature.mariokartfighter.models;

import java.util.ArrayList;

public class Item {
	private String itemID;
	private String name;
	private String typeThatCanUse;
	private int unlockAtLevel;
	private int bonusToHealth;
	private double bonusToAttack;
	private double bonusToDefense;
	
	public Item(String name, String typeThatCanUse, int unlockAtLevel, int bonusToHealth, double bonusToAttack, double bonusToDefense) {
		this.itemID = "";
		this.name = name;
		this.setTypeThatCanUse(typeThatCanUse);
		this.unlockAtLevel = unlockAtLevel;
		this.bonusToHealth = bonusToHealth;
		this.bonusToAttack = bonusToAttack;
		this.bonusToDefense = bonusToDefense;
	}

	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", name=" + name + ", typeThatCanUse=" + typeThatCanUse + ", unlockAtLevel="
				+ unlockAtLevel + ", bonusToHealth=" + bonusToHealth + ", bonusToAttack=" + bonusToAttack
				+ ", bonusToDefense=" + bonusToDefense + "]";
	}
	
	public String getInfoString() {
		String printString = "";
		printString += "\tItem ID: " + this.itemID + "\n";
		printString += "\tName: " + this.name + "\n";
		printString += "\tType That Can Use: " + this.typeThatCanUse + "\n";
		printString += "\tBonus to Attack: " + this.bonusToAttack + "\n";
		printString += "\tBonus to Defense: " + this.bonusToDefense + "\n";
		printString += "\tBonus to Health: " + this.bonusToHealth + "\n";
		printString += "\tUnlock at level: " + this.unlockAtLevel + "\n";
		return printString;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeThatCanUse() {
		return typeThatCanUse;
	}

	public void setTypeThatCanUse(String typeThatCanUse) {
		ArrayList<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add("speed");
		for (String type : allowedTypes) {
			if (typeThatCanUse.equals(type)) {
				this.typeThatCanUse = typeThatCanUse;
			}
		}
		//TODO check that type is allowed
	}

	public int getUnlockAtLevel() {
		return unlockAtLevel;
	}

	public void setUnlockAtLevel(int unlockAtLevel) {
		this.unlockAtLevel = unlockAtLevel;
	}

	public int getBonusToHealth() {
		return bonusToHealth;
	}

	public void setBonusToHealth(int bonusToHealth) {
		this.bonusToHealth = bonusToHealth;
	}

	public double getBonusToAttack() {
		return bonusToAttack;
	}

	public void setBonusToAttack(double bonusToAttack) {
		this.bonusToAttack = bonusToAttack;
	}

	public double getBonusToDefense() {
		return bonusToDefense;
	}

	public void setBonusToDefense(double bonusToDefense) {
		this.bonusToDefense = bonusToDefense;
	}
}
