package de.kksystem.karteikarten.data;

import java.sql.ResultSet;
import java.util.List;

import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.interfaces.*;
import de.kksystem.karteikarten.view.javafx.controllers.CreateWindowController;

/*
 * Eventuell weitere Daten während Laufzeit speichern; nicht sicher, welche noch
 */
public class UserData {
	private String username;
	private String email;
	private int userId;
	private int favoritelistId;
	private List<Category> categoryList;
	private List<Lection> lectionList;
	private List<Favoritelist> favoriteList;
	private CreateWindowController controller;
	private String password;
	private String foreName;
	private String surname;
	/*Choosen Lection to learn, the value is determined after selecting lection from category->lection or favoritelist*/
	private List<IndexCard> choosenLection;
	
	private static UserData instance;
	
	public UserData() {}
	
	public static UserData getInstance() {
		if(instance == null){
            instance = new UserData();
        }
		return instance;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email){
		this.email = email;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setForeName(String foreName){
		this.foreName = foreName;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}
	public void setLection(List<IndexCard> lection) {
		choosenLection = lection;
	}
	
	public void setFavoritelistId(int favoritelistId) {
		this.favoritelistId = favoritelistId;
	}

	public void setCreateWindowController(CreateWindowController controller){
		this.controller = controller;
	}
	
	public String getUsername() {
		return this.username;
	}

	public String getEmail(){
		return this.email;
	}

	public String getPassword(){
		return this.password;
	}

	public String getForeName(){
		return this.foreName;
	}

	public String getSurname(){
		return this.surname;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public int getFavoritelistId() {
		return this.favoritelistId;
	}

	public List<IndexCard> getLection() {
		return choosenLection;
	}

	public CreateWindowController getC(){
		return controller;
	}
	
	public List<Category> getCategoryList(){
		categoryList = ServiceFacade.getInstance().findCategoriesByUserId(getUserId());
		return categoryList;
	}
	
	public List<Favoritelist> getFavoriteList(){
		favoriteList = ServiceFacade.getInstance().findFavoritesByUserId(getUserId());
		return favoriteList;
	}

    public List<Lection> getLectionList() {
		lectionList = ServiceFacade.getInstance().findLectionByCategoryId(getUserId());
		return lectionList;
    }

}
