package de.kksystem.karteikarten.service.interfaces;

import de.kksystem.karteikarten.model.interfaces.IndexCard;

public interface IndexCardService {
	void addIndexCard(IndexCard indexCard);
	void deleteIndexCard(IndexCard indexCard); // indexCardId mitzugeben wuerde ausreichen
	void updateIndexCard(IndexCard indexCard);
	void updateQuestion(String newQuestion, int indexCardId);
	void updateAnswer(String newAnswer, int indexCardId);
	IndexCard findIndexCardById(int indexCardId);
	// muss in die Lection -> List<IndexCard> findAllIndexCardsByLectionId(int lectionId);
	// muss in die Lection -> int countIndexCardsByLectionId(int lectionId);
}
