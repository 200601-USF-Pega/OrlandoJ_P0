package com.revature.mariokartfighter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.revature.mariokartfighter.models.Item;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getSomeItems(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
