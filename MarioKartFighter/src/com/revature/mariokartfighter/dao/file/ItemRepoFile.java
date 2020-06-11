package com.revature.mariokartfighter.dao.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.dao.IItemRepo;
import com.revature.mariokartfighter.models.Item;

public class ItemRepoFile implements IItemRepo {
	private String filepath= "src/resources/Item.txt";

	@Override
	public synchronized Item addItem(Item item) {
		List<Item> currentItems = this.getAllItems();
		try {
			ObjectOutputStream objectOutputStream = 
					new ObjectOutputStream(new FileOutputStream(filepath));
			currentItems.add(item);
			objectOutputStream.writeObject(currentItems);
			objectOutputStream.close();
			return item;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		try {
			ObjectInputStream inputStream = 
					new ObjectInputStream(new FileInputStream(filepath));
			List<Item> retrievedItems = (ArrayList<Item>) inputStream.readObject();
			inputStream.close();
			return retrievedItems;
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			//Just in case Character class is not found
			e.printStackTrace();
		} 
		
		return new ArrayList<Item>();
	}

	@Override
	public List<Item> getSomeItems(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeItems(String name) {
		// TODO Auto-generated method stub
		
	}

}
