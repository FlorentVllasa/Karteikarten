package de.kksystem.karteikarten.service.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.classes.FavoritelistImpl;

public interface FavoritelistService {
	void addFavoritelist(String name , int userId);
	void delete(int favoritId);
	void updateFavoritelist(int favListId, String name, int userId);
	void findFavoritelistById(int favoritelistId);
	FavoritelistImpl findFavoritelist(int favoritelistId);
	List<FavoritelistImpl> findAllFavoritelist();
}
