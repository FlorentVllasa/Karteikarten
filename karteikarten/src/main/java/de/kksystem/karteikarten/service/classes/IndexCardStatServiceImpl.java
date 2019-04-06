package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.IndexCardStatImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCardStat;
import de.kksystem.karteikarten.service.interfaces.IndexCardStatService;

public class IndexCardStatServiceImpl implements IndexCardStatService {
	
	private DaoFacade daoFacade;
	
	public IndexCardStatServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public int addIndexCardStat(IndexCardStat indexCardStat) {
		return daoFacade.addIndexCardStat(indexCardStat);
	}

	@Override
	public void updateRight(int indexCardId) {
		daoFacade.updateRight(indexCardId);
	}

	@Override
	public void updateWrong(int indexCardId) {
		daoFacade.updateWrong(indexCardId);
	}
	
	@Override
	public boolean isIndexCardStatAvailable(int indexCardId) {
		return daoFacade.isIndexCardStatAvailable(indexCardId);
	}
	
	@Override
	public void updateOrAddNumberOfRight(int indexCardId) {
		boolean checkIfIndexCardExistsInDB = daoFacade.isIndexCardStatAvailable(indexCardId);
		
		if(checkIfIndexCardExistsInDB) {
			daoFacade.updateRight(indexCardId);
		} else {
			IndexCardStat indexCardStat = new IndexCardStatImpl(1, 0, indexCardId);
			daoFacade.addIndexCardStat(indexCardStat);
		}
	}
	
	@Override
	public void updateOrAddNumberOfWrong(int indexCardId) {
		boolean checkIfIndexCardExistsInDB = daoFacade.isIndexCardStatAvailable(indexCardId);
		
		if(checkIfIndexCardExistsInDB) {
			daoFacade.updateWrong(indexCardId);
		} else {
			IndexCardStat indexCardStat = new IndexCardStatImpl(0, 1, indexCardId);
			daoFacade.addIndexCardStat(indexCardStat);
		}
	}
	
	@Override
	public List<IndexCardStat> findAllStatsByIndexCardId(int indexCardId, int numberOfLastDays) {
		return daoFacade.findAllStatsByIndexCardId(indexCardId, numberOfLastDays);
	}
	
	@Override
	public List<IndexCardStat> findAllStatsByLectionId(int lectionId, int numberOfLastDays) {
		return daoFacade.findAllStatsByLectionId(lectionId, numberOfLastDays);
	}
}