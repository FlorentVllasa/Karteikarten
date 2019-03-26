package de.kksystem.karteikarten.model.classes;

import de.kksystem.karteikarten.model.interfaces.IndexCard;

public class IndexCardImpl implements IndexCard{
	private int indexCardId;
	private String question;
	private String answer;
	private String color;
	private int lectionId;
	private Integer pictureId; // Integer genommen, um auf null überprüfen zu können
	
	public IndexCardImpl() {
		super();
	}
	
	// mit ID
	public IndexCardImpl(int indexCardId, String question, String answer, String color, int lectionId, Integer pictureId) {
		this.indexCardId = indexCardId;
		this.question = question;
		this.answer = answer;
		this.color = color;
		this.lectionId = lectionId;
		this.pictureId = pictureId;
	}
	
	// ohne ID 
	public IndexCardImpl(String question, String answer, String color, int lectionId, Integer pictureId) {
		this.question = question;
		this.answer = answer;
		this.color = color;
		this.lectionId = lectionId;
		this.pictureId = pictureId;
	}

	@Override
	public int getIndexCardId() {
		return indexCardId;
	}

	@Override
	public void setIndexCardId(int indexCardId) {
		this.indexCardId = indexCardId;
	}

	@Override
	public String getQuestion() {
		return question;
	}

	@Override
	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int getLectionId() {
		return lectionId;
	}

	@Override
	public void setLectionId(int lectionId) {
		this.lectionId = lectionId;
	}

	@Override
	public Integer getPictureId() {
		return pictureId;
	}

	@Override
	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

}
