package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
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

	public List<IndexCardStat> findAllStatsByIndexCardId(int indexCardId, int numberOfLastDays) {
		return daoFacade.findAllStatsByIndexCardId(indexCardId, numberOfLastDays);
	}
	
	public List<IndexCardStat> findAllStatsByLectionId(int lectionId, int numberOfLastDays) {
		return daoFacade.findAllStatsByLectionId(lectionId, numberOfLastDays);
	}
}