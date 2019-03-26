package de.kksystem.karteikarten.model.interfaces;

import java.sql.Timestamp;

public interface User {
	public int getUserId();
	public void setUserId(int userId);
	public String getUsername();
	public void setUsername(String username);
	public String getEmail();
	public void setEmail(String userEmail);
	public String getPassword();
	public void setPassword(String userPassword);
	public String getSurname();
	public void setSurname(String userSurname);
	public String getForename();
	public void setForename(String userForename);
	public Timestamp getLastLogin();
	public void setLastLogin(Timestamp userLastLogin);
}
