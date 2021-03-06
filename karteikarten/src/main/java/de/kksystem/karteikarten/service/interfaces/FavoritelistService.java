package de.kksystem.karteikarten.service.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.classes.FavoritelistImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;

public interface FavoritelistService {
	int addFavoritelist(String name , int userId);
	void delete(int favoritId);
	void updateFavoritelist(int favListId, String name, int userId);
	void findFavoritelistById(int favoritelistId);
	Favoritelist findFavoritelist(int favoritelistId);
	List<Favoritelist> findAllFavoritelist();
	List<Favoritelist> findFavoritesByUserId(int userId);
	Favoritelist findFavoritelistIdByUserId(int userId);
}
