package de.kksystem.karteikarten.service.interfaces;

import java.sql.Timestamp;
import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Lection;

public interface LectionService {
	void addLection(Lection lection);
	void deleteLection(Lection lection);
	void updateLection(Lection lection);
	void updateName(String name, int lectionId);
	void updateDescription(String description, int lectionId);
	void updateLastExcercise(Timestamp timestamp, int lectionId);
	Lection findLectionById(int lectionId);
	public List<Lection> findLectionByCategoryId(int categoryId);
	public List<Lection> findLectionByFavoritelistId(int favoritelisteId);
}
