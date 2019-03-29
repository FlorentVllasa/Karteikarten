package de.kksystem.karteikarten.model.classes;

import java.sql.Timestamp;

import de.kksystem.karteikarten.model.interfaces.Lection;

public class LectionImpl implements Lection{
	private int lectionId;
	private String name;
	private String description;
	private Timestamp lastExcercise;
	private int categoryId;
	private int favoritelistId;
	
	public LectionImpl() {
		super();
	}
	
	public LectionImpl(int lectionId, String name, String description, Timestamp lastExcercise, int categoryId, int favoritelistId) {
		this.lectionId = lectionId;
		this.name = name;
		this.description = description;
		this.lastExcercise = lastExcercise;
		this.categoryId = categoryId;
		this.favoritelistId = favoritelistId;
	}
	
	@Override
	public int getLectionId() {
		return lectionId;
	}

	@Override
	public void setLectionId(int lectionId) {
		this.lectionId = lectionId;
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Timestamp getLastExcercise() {
		return lastExcercise;
	}

	@Override
	public void setLastExcercise(Timestamp lastExcercise) {
		this.lastExcercise = lastExcercise;
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
	public int getCategoryId() {
		return categoryId;
	}

	@Override
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
