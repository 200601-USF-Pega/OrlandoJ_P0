package com.revature.mariokartfighter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.models.Item;

public class ItemService {
	Scanner input = new Scanner(System.in);
	IItemRepo repo;
	
	public ItemService (IItemRepo repo) {
		this.repo = repo;
	}
	
	public void createNewItem() {
		String name;
		String typeThatCanUse;
		int unlockAtLevel;
		int bonusToHealth; 
		double bonusToAttack;
		double bonusToDefense;
		
		//TODO validate input
		System.out.println("Name: ");
		name = input.nextLine();
		
		//TODO check type is allowed
		boolean gotAllowedType = false;
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add("skill");
		allowedTypes.add("all-around");
		allowedTypes.add("speed");
		allowedTypes.add("power");
		
		System.out.println("Type that can use (choose from skill, all-around, speed, power):");
		do {
			typeThatCanUse = input.nextLine();
			if (allowedTypes.contains(typeThatCanUse.toLowerCase())) {
				gotAllowedType = true;
				break;
			} else {
				System.out.println("Not a valid character type...try again");
			}
		} while (gotAllowedType);
		
		System.out.println("Bonus to Health: ");
		bonusToHealth = input.nextInt();
		input.nextLine();
		
		System.out.println("Bonus to Attack: ");
		bonusToAttack = input.nextDouble();
		input.nextLine();
		
		System.out.println("Bonus to Defense: ");
		bonusToDefense = input.nextDouble();
		input.nextLine();
		
		//choose unlock level based on stats
		unlockAtLevel = (int)(bonusToHealth + bonusToAttack + bonusToDefense)/3;
		
		Item newItem = new Item(name, typeThatCanUse, unlockAtLevel, bonusToHealth, bonusToAttack, bonusToDefense);
		repo.addItem(newItem);
	}
	
	public String generateItemID() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
		
		//choose a random length up to 64 characters 
		Random random = new Random();
		int n = random.nextInt(63) + 1;
				
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
		List<Item> retrievedItems = repo.getAllItems();
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
			if (i.getName().equals(itemName)) {
				System.out.println(i.getInfoString());
				return;
			}
		}
	}
}
