package de.kksystem.karteikarten.dao.interfaces;

import de.kksystem.karteikarten.model.interfaces.Picture;

public interface PictureDao {
	void deletePicture(int pictureId);
	void updatePicture(int pictureId, Picture newPicture);
	Picture findPicture(int pictureId);
	int addPicture(Picture picture);
}
