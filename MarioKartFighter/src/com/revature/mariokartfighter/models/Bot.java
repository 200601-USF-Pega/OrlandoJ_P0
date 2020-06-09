package com.revature.mariokartfighter.models;

public class Bot {
	private PlayableCharacter selectedCharacter;
	private Item selectedItem;
	private int level;
	private String id;
	private int health;
	
	public Bot(int level) {
		//choose random character and item for bot
//		Character selectedCharacter;
//		Item selectedItem;
		this.level = level;
		this.id = this.generateBotID();
		this.health = 100;
	}

	@Override
	public String toString() {
		return "Bot [selectedCharacter=" + selectedCharacter + ", selectedItem=" + selectedItem + ", level=" + level
				+ "]";
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String generateBotID() {
		String botID = Integer.toString(this.getLevel()) + "_" + System.currentTimeMillis();
		return botID;
	}

	public PlayableCharacter getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(PlayableCharacter selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}
	
	public String getID() {
		return this.id;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
