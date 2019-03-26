package de.kksystem.karteikarten.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
	private static final String URL = "jdbc:sqlite:C:/Users/gianl/git/karteikartensystem_neu/karteikarten/src/main/resources/database/Projekt.db";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL);
	}
}
