package de.kksystem.karteikarten.service.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.interfaces.IndexCardStat;

public interface IndexCardStatService {
	int addIndexCardStat(IndexCardStat indexCardStat);
	void updateRight(int indexCardId);
	void updateWrong(int indexCardId);
	boolean isIndexCardStatAvailable(int indexCardId);
	void updateOrAddNumberOfRight(int indexCardId);
	void updateOrAddNumberOfWrong(int indexCardId);
	List<IndexCardStat> findAllStatsByIndexCardId(int indexCardId, int numberOfLastDays);
	List<IndexCardStat> findAllStatsByLectionId(int lectionId, int numberOfLastDays);
}

