package de.kksystem.karteikarten.dao.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Favoritelist;

public interface FavoritelistDao {
	void addFavoritelist(String name , int userId);
	void delete(int favoritId);
	void updateFavoritelist(int favListId, String name, int userId);
	void findFavoritelistById(int favoritelistId);
	Favoritelist findFavoritelist(int favoritelistId);
	List<Favoritelist> findAllFavoritelist();
}
