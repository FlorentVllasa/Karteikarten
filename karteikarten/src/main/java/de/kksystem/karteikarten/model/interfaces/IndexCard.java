package de.kksystem.karteikarten.model.interfaces;

public interface IndexCard {
	public int getIndexCardId();
	public void setIndexCardId(int indexCardId);
	public String getQuestion();
	public void setQuestion(String question);
	public String getAnswer();
	public void setAnswer(String answer);
	public String getColor();
	public void setColor(String color);
	public int getLectionId();
	public void setLectionId(int lectionId);
	public Integer getPictureId();
	public void setPictureId(Integer pictureId);
}
