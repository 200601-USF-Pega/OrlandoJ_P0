package com.revature.mariokartfighter.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
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
	
	public Item(String id, String name, String typeThatCanUse, int unlockAtLevel, int bonusToHealth, double bonusToAttack, double bonusToDefense) {
		this.itemID = id;
		this.name = name;
		this.setTypeThatCanUse(typeThatCanUse);
		this.unlockAtLevel = unlockAtLevel;
		this.bonusToHealth = bonusToHealth;
		this.bonusToAttack = bonusToAttack;
		this.bonusToDefense = bonusToDefense;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bonusToAttack);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bonusToDefense);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + bonusToHealth;
		result = prime * result + ((itemID == null) ? 0 : itemID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((typeThatCanUse == null) ? 0 : typeThatCanUse.hashCode());
		result = prime * result + unlockAtLevel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (Double.doubleToLongBits(bonusToAttack) != Double.doubleToLongBits(other.bonusToAttack))
			return false;
		if (Double.doubleToLongBits(bonusToDefense) != Double.doubleToLongBits(other.bonusToDefense))
			return false;
		if (bonusToHealth != other.bonusToHealth)
			return false;
		if (itemID == null) {
			if (other.itemID != null)
				return false;
		} else if (!itemID.equals(other.itemID))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (typeThatCanUse == null) {
			if (other.typeThatCanUse != null)
				return false;
		} else if (!typeThatCanUse.equals(other.typeThatCanUse))
			return false;
		if (unlockAtLevel != other.unlockAtLevel)
			return false;
		return true;
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

	public String getItemName() {
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
