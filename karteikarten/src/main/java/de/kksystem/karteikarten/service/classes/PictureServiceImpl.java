package de.kksystem.karteikarten.service.classes;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.Picture;
import de.kksystem.karteikarten.service.interfaces.PictureService;

public class PictureServiceImpl implements PictureService {
	private DaoFacade daoFacade;
	
	public PictureServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void deletePicture(int pictureId) {
		daoFacade.deletePicture(pictureId);
	}

	@Override
	public void updatePicture(Picture newPicture) {
		daoFacade.updatePicture(newPicture);
	}

	@Override
	public Picture findPicture(int pictureId) {
		return daoFacade.findPicture(pictureId);
	}

	@Override
	public int addPicture(Picture picture) {
		return daoFacade.addPicture(picture);
	}
}

