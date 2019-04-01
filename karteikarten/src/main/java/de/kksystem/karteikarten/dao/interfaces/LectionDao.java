package de.kksystem.karteikarten.dao.interfaces;

import java.sql.Timestamp;
import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Lection;

public interface LectionDao {
	void addLection(Lection lection);
	void deleteLection(Lection lection);
	void updateLection(Lection lection);
	void updateName(String name, int lectionId);
	void updateDescription(String description, int lectionId);
	void updateLastExcercise(Timestamp timestamp, int lectionId);
	Lection findLectionById(int lectionId);
	List<Lection> findLectionByUserId(int userId);
	List<Lection> findLectionByCategoryId(int categoryId);
	List<Lection> findLectionByFavoritelistId(int favoritelisteId);
}
