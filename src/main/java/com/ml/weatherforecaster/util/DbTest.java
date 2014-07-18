package com.ml.weatherforecaster.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbTest {
	
	 
		public void connectionTest() {
	 
			System.out.println("-------- PostgreSQL "
					+ "JDBC Connection Testing ------------");
	 
			try {
	 
				Class.forName("org.postgresql.Driver");
	 
			} catch (ClassNotFoundException e) {
	 
				System.out.println("Where is your PostgreSQL JDBC Driver? "
						+ "Include in your library path!");
				e.printStackTrace();
				return;
	 
			}
	 
			System.out.println("PostgreSQL JDBC Driver Registered!");
	 
			Connection connection = null;
	 
			try {
	 
				connection = DriverManager.getConnection(
						"jdbc:postgresql://ec2-54-204-45-196.compute-1.amazonaws.com:5432/db9p9ve344t53i?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", 
						"mwndnollyngkju","SHTKlTOVK-jeUZuKl5B0L4yXgU");
	 
			} catch (SQLException e) {
	 
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
	 
			}
	 
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		}
	 
}
