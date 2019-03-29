package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.service.interfaces.IndexCardService;

public class IndexCardServiceImpl implements IndexCardService{

	private DaoFacade daoFacade;
	
	public IndexCardServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void addIndexCard(IndexCard indexCard) {
		daoFacade.addIndexCard(indexCard);
	}

	@Override
	public void deleteIndexCard(IndexCard indexCard) {
		daoFacade.deleteIndexCard(indexCard);
		
	}

	@Override
	public void updateIndexCard(IndexCard indexCard) {
		daoFacade.updateIndexCard(indexCard);
	}

	@Override
	public void updateQuestion(String newQuestion, int indexCardId) {
		daoFacade.updateQuestion(newQuestion, indexCardId);
	}

	@Override
	public void updateAnswer(String newAnswer, int indexCardId) {
		daoFacade.updateAnswer(newAnswer, indexCardId);
	}

	@Override
	public IndexCard findIndexCardById(int indexCardId) {
		return daoFacade.findIndexCardById(indexCardId);
	}

	@Override
	public List<IndexCard> findAllIndexCardsByLectionId(int lectionId) {
		return daoFacade.findAllIndexCardsByLectionId(lectionId);
	}
}
