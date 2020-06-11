package com.revature.mariokartfighter.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionService {
	private Connection connection;
	
	public ConnectionService() {
		try {
			FileInputStream fis = new FileInputStream("connection.prop");
			Properties p = new Properties();
			p.load(fis);
			
			connection = DriverManager.getConnection(p.getProperty("hostname"), 
					p.getProperty("username"), p.getProperty("password"));
		} catch(SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void finalize() {
		try {
			connection.close();
		} catch (Exception e) {
			
		}
	}
}
