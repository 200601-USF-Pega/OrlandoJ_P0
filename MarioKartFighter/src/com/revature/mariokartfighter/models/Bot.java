package com.revature.mariokartfighter.models;

public class Bot {
	private PlayableCharacter selectedCharacter;
	private Item selectedItem;
	private int level;
	private String id;
	
	public Bot(int level, PlayableCharacter selectedCharacter, Item selectedItem) {
		//choose random character and item for bot
		this.selectedCharacter = selectedCharacter;
		this.selectedItem = selectedItem;
		this.level = level;
		this.id = this.generateBotID();
	}

	@Override
	public String toString() {
		return "Bot [selectedCharacter=" + selectedCharacter + ", selectedItem=" + selectedItem + ", level=" + level
				+ "]";
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
