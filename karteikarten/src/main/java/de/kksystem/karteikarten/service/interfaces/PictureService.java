package de.kksystem.karteikarten.service.interfaces;

import de.kksystem.karteikarten.model.interfaces.Picture;

public interface PictureService {
	void deletePicture(int pictureId);
	void updatePicture(int pictureId, Picture newPicture);
	Picture findPicture(int pictureId);
	int addPicture(Picture picture);
}
