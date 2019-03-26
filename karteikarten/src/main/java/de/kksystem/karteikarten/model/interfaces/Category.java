package de.kksystem.karteikarten.model.interfaces;

public interface Category {
	public int getCategoryId();
	public void setCategoryId(int categoryId);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public int getUserId();
	public void setUserId(int userId);
}
