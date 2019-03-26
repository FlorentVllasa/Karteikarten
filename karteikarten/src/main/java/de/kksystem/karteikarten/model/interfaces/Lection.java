package de.kksystem.karteikarten.model.interfaces;

import java.sql.Timestamp;

public interface Lection {
	public int getLectionId();
	public void setLectionId(int lectionId);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public Timestamp getLastExcercise();
	public void setLastExcercise(Timestamp lastExcercise);
	public int getFavoritelistId();
	public void setFavoritelistId(int favoritelistId);
	public int getCategoryId();
	public void setCategoryId(int categoryId);
}
