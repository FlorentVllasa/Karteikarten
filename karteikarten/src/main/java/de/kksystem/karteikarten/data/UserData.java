package de.kksystem.karteikarten.data;

/*
 * Eventuell weitere Daten während Laufzeit speichern; nicht sicher, welche noch
 */
public class UserData {
	private String username;
	private int userId;
	
	private static UserData instance;
	
	private UserData() {}
	
	public static UserData getInstance() {
		if(instance == null){
            instance = new UserData();
        }
		return instance;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}	
	
	public String getUsername() {
		return this.username;
	}
	
	public int getUserId() {
		return this.userId;
	}
}
