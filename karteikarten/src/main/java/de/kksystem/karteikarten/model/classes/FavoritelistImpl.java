package de.kksystem.karteikarten.model.classes;

import de.kksystem.karteikarten.model.interfaces.Favoritelist;

public class FavoritelistImpl implements Favoritelist {
	private int favoritelistId;
	private String name;
	private int userId;
	
	public FavoritelistImpl() {
		super();
	}
	
	public FavoritelistImpl(int favoritelistId, String name, int userId) {
		this.favoritelistId = favoritelistId;
		this.name = name;
		this.userId = userId;
	}

	public FavoritelistImpl(String name){
		this.name = name;
	}
	
	@Override
	public int getFavoritelistId() {
		return favoritelistId;
	}

	@Override
	public void setFavoritelistId(int favoritelistId) {
		this.favoritelistId = favoritelistId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
