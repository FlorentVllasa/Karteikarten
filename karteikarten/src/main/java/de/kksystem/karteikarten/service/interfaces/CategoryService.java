package de.kksystem.karteikarten.service.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.User;

public interface CategoryService {
	void addCategory(Category category);
	void deleteCategory(Category category);
	Category findCategory(Category category);
	List<Category> findAllCategories();
	void updateCategorieName(Category category, String neuerCatName);
	void updateCategorieDescription(Category category, String neueBeschreibung);
	int countCategories();
	List<Category> findCategoriesByUserId(int userId);
}
