package de.kksystem.karteikarten.model.classes;

import de.kksystem.karteikarten.model.interfaces.Picture;

public class PictureImpl implements Picture{
	private int pictureId;
	private String fileLocation;
	private String description;
	
	public PictureImpl() {
		super();
	}
	
	public PictureImpl(int pictureId, String fileLocation, String description) {
		this.pictureId = pictureId;
		this.fileLocation = fileLocation;
		this.description = description;
	}

	@Override
	public int getPictureId() {
		return pictureId;
	}

	@Override
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}

	@Override
	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

}
