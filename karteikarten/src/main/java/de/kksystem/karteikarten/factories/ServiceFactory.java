package de.kksystem.karteikarten.factories;

import de.kksystem.karteikarten.service.classes.CategoryServiceImpl;
import de.kksystem.karteikarten.service.classes.FavoritelistServiceImpl;
import de.kksystem.karteikarten.service.classes.IndexCardServiceImpl;
import de.kksystem.karteikarten.service.classes.LectionServiceImpl;
import de.kksystem.karteikarten.service.classes.PictureServiceImpl;
import de.kksystem.karteikarten.service.classes.UserServiceImpl;
import de.kksystem.karteikarten.service.interfaces.CategoryService;
import de.kksystem.karteikarten.service.interfaces.FavoritelistService;
import de.kksystem.karteikarten.service.interfaces.IndexCardService;
import de.kksystem.karteikarten.service.interfaces.LectionService;
import de.kksystem.karteikarten.service.interfaces.PictureService;
import de.kksystem.karteikarten.service.interfaces.UserService;

public class ServiceFactory {
	public static UserService createUserService() {
		return new UserServiceImpl();
	}
	
	public static CategoryService createCategoryService() {
		return new CategoryServiceImpl();
	}
	
	public static LectionService createLectionService() {
		return new LectionServiceImpl();
	}
	
	public static IndexCardService createIndexCardService() {
		return new IndexCardServiceImpl();
	}
	
	public static PictureService createPicture() {
		return new PictureServiceImpl();
	}
	
	public static FavoritelistService createFavoritelist() {
		return new FavoritelistServiceImpl();
	}
}
