package de.kksystem.karteikarten.model.interfaces;

public interface Picture {
	public int getPictureId();
	public void setPictureId(int pictureId);
	public String getFileLocation();
	public void setFileLocation(String fileLocation);
	public String getDescription();
	public void setDescription(String description);
}
