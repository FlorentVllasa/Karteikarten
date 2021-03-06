package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.service.interfaces.IndexCardService;
import de.kksystem.karteikarten.utils.StringUtils;

public class IndexCardServiceImpl implements IndexCardService {

	private DaoFacade daoFacade;
	
	public IndexCardServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public int addIndexCard(IndexCard indexCard) {
		return daoFacade.addIndexCard(indexCard);
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
	
	public void updatePictureId(int indexCardId, int pictureId) {
		daoFacade.updatePictureId(indexCardId, pictureId);
	}

	@Override
	public IndexCard findIndexCardById(int indexCardId) {
		return daoFacade.findIndexCardById(indexCardId);
	}
	@Override
	public IndexCard findIndexCardByIdWithoutHTML(int indexCardId) {
		IndexCard indexCardWithoutHTML = daoFacade.findIndexCardById(indexCardId);
		
		String question = StringUtils.getText(indexCardWithoutHTML.getQuestion());
		String answer = StringUtils.getText(indexCardWithoutHTML.getAnswer());

		indexCardWithoutHTML.setQuestion(question);
		indexCardWithoutHTML.setAnswer(answer);
		
		return indexCardWithoutHTML;
	}

	@Override
	public List<IndexCard> findAllIndexCardsByLectionId(int lectionId) {
		return daoFacade.findAllIndexCardsByLectionId(lectionId);
	}

	@Override
	public List<IndexCard> findAllIndexCardsByLectionIdWithoutHTML(int lectionId) {
		List<IndexCard> listWithoutHTML = daoFacade.findAllIndexCardsByLectionId(lectionId);
		for (int i = 0; i < listWithoutHTML.size(); i++) {
			String question = StringUtils.getText(listWithoutHTML.get(i).getQuestion());
			String answer = StringUtils.getText(listWithoutHTML.get(i).getAnswer());

			listWithoutHTML.get(i).setQuestion(question);
			listWithoutHTML.get(i).setAnswer(answer);

		}
		return listWithoutHTML;
	}
}