package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.classes.FavoritelistImpl;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.service.interfaces.FavoritelistService;


public class FavoritelistServiceImpl implements FavoritelistService {
	private DaoFacade daoFacade;
	
	public FavoritelistServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void addFavoritelist(String name, int userId) {
		daoFacade.addFavoritelist(name, userId);
	}

	@Override
	public void delete(int favoritId) {
		daoFacade.delete(favoritId);
	}

	@Override
	public void updateFavoritelist(int favListId, String name, int userId) {
		daoFacade.updateFavoritelist(favListId, name, userId);
	}

	@Override
	public void findFavoritelistById(int favoritelistId) {
		daoFacade.findFavoritelistById(favoritelistId);
	}

	@Override
	public Favoritelist findFavoritelist(int favoritelistId) {
		return daoFacade.findFavoritelist(favoritelistId);
	}

	@Override
	public List<Favoritelist> findAllFavoritelist() {
		return daoFacade.findAllFavoritelist();
	}


}
