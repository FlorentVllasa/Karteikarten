package de.kksystem.karteikarten.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class JdbcUtils {
	private static final String URL = "jdbc:sqlite:src/main/resources/database/Projekt.db";
	
	public static Connection getConnection() throws SQLException {
		// wird benötigt um Foreign-Keys zu erlauben ... (luca)
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true);
		return DriverManager.getConnection(URL, config.toProperties());
	}
}
