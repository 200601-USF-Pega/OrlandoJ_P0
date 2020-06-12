package com.revature.mariokartfighter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.models.Item;

public class ItemService {
	ValidationService validation;
	IItemRepo repo;
	
	public ItemService (IItemRepo repo) {
		this.repo = repo;
		this.validation = new ValidationService();
	}
	
	public String createNewItem() {
		String name;
		String typeThatCanUse;
		int unlockAtLevel;
		int bonusToHealth; 
		double bonusToAttack;
		double bonusToDefense;
		
		System.out.println("Name: ");
		name = validation.getValidString();
		
		//check type is allowed
		boolean gotAllowedType = false;
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add("skill");
		allowedTypes.add("all-around");
		allowedTypes.add("speed");
		allowedTypes.add("power");
		
		System.out.println("Type that can use (choose from skill, all-around, speed, power):");
		do {
			typeThatCanUse = validation.getValidString();
			if (allowedTypes.contains(typeThatCanUse.toLowerCase())) {
				gotAllowedType = true;
				break;
			} else {
				System.out.println("Not a valid character type...try again");
			}
		} while (gotAllowedType);
		
		System.out.println("Bonus to Health: ");
		bonusToHealth = validation.getValidInt();
		
		System.out.println("Bonus to Attack: ");
		bonusToAttack = validation.getValidDouble();
		
		System.out.println("Bonus to Defense: ");
		bonusToDefense = validation.getValidDouble();
		
		//choose unlock level based on stats
		unlockAtLevel = (int)(bonusToHealth + bonusToAttack + bonusToDefense)/3;
		String itemID = generateItemID();
		Item newItem = new Item(itemID, name, typeThatCanUse, unlockAtLevel, bonusToHealth, bonusToAttack, bonusToDefense);
		repo.addItem(newItem);
		return itemID;
	}
	
	public String generateItemID() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
		
		//choose a random length up to 64 characters 
		Random random = new Random();
		int n = random.nextInt(60) + 3;
				
		StringBuilder sb;
		do {
			sb = new StringBuilder(n); 
	        for (int i = 0; i < n; i++) { 
	            int index = (int)(alphaNumericString.length()  * Math.random()); 
	            sb.append(alphaNumericString.charAt(index)); 
	        } 
		} while(checkItemExists(sb.toString()));
		
		return sb.toString();
	}
	
	public void getAllItems() {
		System.out.println(String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s\n", 
				"itemID", "itemName", "typeThatCanUse", "unlockAtLevel", "bonusToHealth", 
				"bonusToAttack", "bonusToDefense"));
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			System.out.println(i);
		}
	}
	
	public void getSomeItems(int level) {
		System.out.println(String.format("%-20s|%-20s|%-20s|%-15s|%-15s|%-15s|%-15s\n", 
				"itemID", "itemName", "typeThatCanUse", "unlockAtLevel", "bonusToHealth", 
				"bonusToAttack", "bonusToDefense"));
		List<Item> retrievedItems = repo.getSomeItems(level);
		for(Item i : retrievedItems) {
			System.out.println(i);
		}
	}
	
	public boolean checkItemExists(String itemID) {
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			if (i.getItemID().equals(itemID)) {
				return true;
			}
		}
		return false;
	}
	
	public void getItemInfo(String itemName) {
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			if (i.getItemName().equals(itemName)) {
				System.out.println(i.getInfoString());
				return;
			}
		}
	}
	
	public void removeTestItems(String testName) {
		repo.removeItems(testName);
	}
}
