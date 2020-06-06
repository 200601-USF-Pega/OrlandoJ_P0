package com.revature.mariokartfighter.service;

import java.util.List;
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
		
		System.out.println("Type that can use: ");
		typeThatCanUse = input.nextLine();
		
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
	
	public void getAllItems() {
		List<Item> retrievedItems = repo.getAllItems();
		for(Item i : retrievedItems) {
			System.out.println(i);
		}
	}
}
