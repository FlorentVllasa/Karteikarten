package de.kksystem.karteikarten.data;

import java.util.List;

import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.interfaces.*;

/*
 * Eventuell weitere Daten während Laufzeit speichern; nicht sicher, welche noch
 */
public class UserData {
	private String username;
	private int userId;
	private int favoritelistId;
	private List<Category> categoryList;
	private List<Favoritelist> favoriteList;
	/*Choosen Lection to learn, the value is determined after selecting lection from category->lection or favoritelist*/
	private Lection choosenLection;
	
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
	
	public void setLection(Lection lection) {
		choosenLection = lection;
	}
	
	public void setFavoritelistId(int favoritelistId) {
		this.favoritelistId = favoritelistId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public int getFavoritelistId() {
		return this.favoritelistId;
	}
	
	public Lection getLection() {
		return choosenLection;
	}
	
	public List<Category> getCategoryList(){
		categoryList = ServiceFacade.getInstance().findCategoriesByUserId(getUserId());
		return categoryList;
	}
	
	public List<Favoritelist> getFavoriteList(){
		favoriteList = ServiceFacade.getInstance().findFavoritesByUserId(getUserId());
		return favoriteList;
	}
	
}
