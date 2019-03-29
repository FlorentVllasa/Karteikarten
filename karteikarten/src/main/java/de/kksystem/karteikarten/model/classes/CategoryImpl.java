package de.kksystem.karteikarten.model.classes;

import de.kksystem.karteikarten.model.interfaces.Category;

public class CategoryImpl implements Category {
	private int categoryId;
	private String name;
	private String description;
	private int userId;
	
	public CategoryImpl() {
		super();
	}
	
	public CategoryImpl(int categoryId, String name, String description, int userId) {
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.userId = userId;
	}

	public CategoryImpl(String name, String description, int userId){
	    this.name = name;
	    this.description = description;
	    this.userId = userId;
    }
	
	@Override
	public int getCategoryId() {
		// TODO Auto-generated method stub
		return categoryId;
	}

	@Override
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	public int getUserId() {
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString(){
		return name;
	}
	
}
