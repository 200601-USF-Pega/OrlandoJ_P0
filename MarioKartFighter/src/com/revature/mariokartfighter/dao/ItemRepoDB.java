package com.revature.mariokartfighter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.mariokartfighter.models.Item;
import com.revature.mariokartfighter.models.PlayableCharacter;
import com.revature.mariokartfighter.models.Player;

public class ItemRepoDB implements IItemRepo {
	Connection connection;
	
	public ItemRepoDB() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://ruby.db.elephantsql.com:5432", 
					"brdzdjzb", "l7Lh2FHoFuFdz4Gf1h5j0-9LSj78BeJ8");
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Item addItem(Item item) {
		try {			
			PreparedStatement itemInsert = connection.prepareStatement(
					"INSERT INTO item VALUES (?, ?, ?, ?, ?, ?, ?)");
			itemInsert.setString(1, item.getItemID());
			itemInsert.setString(2, item.getItemName());
			itemInsert.setString(3, item.getTypeThatCanUse());
			itemInsert.setInt(4, item.getBonusToHealth());
			itemInsert.setDouble(5, item.getBonusToAttack());
			itemInsert.setDouble(6, item.getBonusToDefense());
			itemInsert.setInt(7, item.getUnlockAtLevel());
			
			itemInsert.executeUpdate();
			
			return item;
			
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		try {			
			PreparedStatement getItems = connection.prepareStatement(
					"SELECT * FROM item;");
			ResultSet itemsRS = getItems.executeQuery();
			
			List<Item> retrievedItems = new ArrayList<Item>();
			
			while(itemsRS.next()) {				
				Item newItem = new Item(
					itemsRS.getString("itemID"),
					itemsRS.getString("name"),
					itemsRS.getString("typeThatCanUse"),
					itemsRS.getInt("unlockAtLevel"),
					itemsRS.getInt("bonusToHealth"),
					itemsRS.getDouble("bonusToAttack"), 
					itemsRS.getDouble("bonusToDefense"));	
				
				retrievedItems.add(newItem);
				return retrievedItems;
			}	
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Item>();
	}

	@Override
	public List<Item> getSomeItems(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
