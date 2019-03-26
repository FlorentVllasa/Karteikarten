package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.service.interfaces.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	private DaoFacade daoFacade;
	
	public CategoryServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void addCategory(Category category) {
		daoFacade.addCategory(category);
	}

	@Override
	public void deleteCategory(Category category) {
		daoFacade.deleteCategory(category);
	}

	@Override
	public Category findCategory(Category category) {
		return daoFacade.findCategory(category);
	}

	@Override
	public List<Category> findAllCategories() {
		return daoFacade.findAllCategories();
	}

	@Override
	public void updateCategorieName(Category category, String neuerCatName) {
		daoFacade.updateCategoryName(category, neuerCatName);
	}

	@Override
	public void updateCategorieDescription(Category category, String neueBeschreibung) {
		daoFacade.updateCategoryDescription(category, neueBeschreibung);
	}

	@Override
	public int countCategories() {
		return daoFacade.countCategories();
	}

	@Override
	public List<Category> findCategoriesByUserId(int userId) {
		return daoFacade.findCategoriesByUserId(userId);
	}
}
