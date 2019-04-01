package de.kksystem.karteikarten.service.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.interfaces.IndexCard;

public interface IndexCardService {
	int addIndexCard(IndexCard indexCard);
	void deleteIndexCard(IndexCard indexCard); // indexCardId mitzugeben wuerde ausreichen
	void updateIndexCard(IndexCard indexCard);
	void updateQuestion(String newQuestion, int indexCardId);
	void updateAnswer(String newAnswer, int indexCardId);
	void updatePictureId(int indexCardId, int pictureId);
	IndexCard findIndexCardById(int indexCardId);
	List<IndexCard> findAllIndexCardsByLectionId(int lectionId);
}
