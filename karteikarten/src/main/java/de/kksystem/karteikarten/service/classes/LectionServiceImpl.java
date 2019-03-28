package de.kksystem.karteikarten.service.classes;

import java.sql.Timestamp;
import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.service.interfaces.LectionService;

public class LectionServiceImpl implements LectionService {
	private DaoFacade daoFacade;
	
	public LectionServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void addLection(Lection lection) {
		daoFacade.addLection(lection);
	}

	@Override
	public void deleteLection(Lection lection) {
		daoFacade.deleteLection(lection);
	}

	@Override
	public void updateLection(Lection lection) {
		daoFacade.updateLection(lection);
	}

	@Override
	public void updateName(String name, int lectionId) {
		daoFacade.updateName(name, lectionId);
	}

	@Override
	public void updateDescription(String description, int lectionId) {
		daoFacade.updateDescription(description, lectionId);
	}

	@Override
	public void updateLastExcercise(Timestamp timestamp, int lectionId) {
		daoFacade.updateLastExcercise(timestamp, lectionId);
	}

	@Override
	public Lection findLectionById(int lectionId) {
		return daoFacade.findLectionById(lectionId);
	}
	
	@Override
	public List<Lection> findLectionByCategoryId(int categoryId) {
		return daoFacade.findLectionByCategoryId(categoryId);
	}

	@Override
	public List<Lection> findLectionByFavoritelistId(int favoritelisteId) {
		return daoFacade.findLectionByFavoritelistId(favoritelisteId);
	}
	
}
