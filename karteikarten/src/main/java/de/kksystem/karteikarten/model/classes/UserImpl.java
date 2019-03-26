package de.kksystem.karteikarten.model.classes;

import java.sql.Timestamp;

import de.kksystem.karteikarten.model.interfaces.User;

public class UserImpl implements User {
	private int userId;
	private String username;
	private String userEmail;
	private String userPassword;
	private String userSurname;
	private String userForename;
	private Timestamp userLastLogin;
	
	public UserImpl() {
		super();
	}
	
	// mit ID und LetzteAnmeldung
	// Timestamp kann auch null sein
	public UserImpl(int userId, String username, String userEmail,
			String userPassword, String userSurname, String userForename, Timestamp userLastLogin) {
		this.userId = userId;
		this.username = username;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userSurname = userSurname;
		this.userForename = userForename;
		this.userLastLogin = userLastLogin;
	}

	// ohne ID 
	// das hier ist wichtig, wenn AUTOINCREMENT in DB gesetzt -> dadurch keine ID nötig beim Einfügen
	// Timestamp kann auch null sein
	public UserImpl(String username, String userEmail, String userPassword, String userSurname, String userForename, Timestamp userLastLogin){
		super();
		
		this.username = username;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userSurname = userSurname;
		this.userForename = userForename;
		this.userLastLogin = userLastLogin;
	}

	@Override
	public int getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
		
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getEmail() {
		return userEmail;
	}

	@Override
	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

	@Override
	public void setPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String getSurname() {
		return userSurname;
	}

	@Override
	public void setSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	@Override
	public String getForename() {
		return userForename;
	}

	@Override
	public void setForename(String userForename) {
		this.userForename = userForename;
	}

	@Override
	public Timestamp getLastLogin() {
		return userLastLogin;
	}

	@Override
	public void setLastLogin(Timestamp userLastLogin) {
		this.userLastLogin = userLastLogin;
	}

}
