package de.kksystem.karteikarten.model.classes;

import java.sql.Date;

import de.kksystem.karteikarten.model.interfaces.IndexCardStat;

public class IndexCardStatImpl implements IndexCardStat{
	private int statisticId;
	private int totalNumberRight;
	private int totalNumberWrong;
	private Date date;
	private int indexCardId;
	
	public IndexCardStatImpl() {
		super();
	}
	
	// ohne ID
	public IndexCardStatImpl(int statisticId, int totalNumberRight, int totalNumberWrong, Date date, int indexCardId) {
		this.statisticId = statisticId;
		this.totalNumberRight = totalNumberRight;
		this.totalNumberWrong = totalNumberWrong;
		this.date = date;
		this.indexCardId = indexCardId;
	}
	
	// ohne ID
	public IndexCardStatImpl(int totalNumberRight, int totalNumberWrong, Date date, int indexCardId) {
		this.totalNumberRight = totalNumberRight;
		this.totalNumberWrong = totalNumberWrong;
		this.date = date;
		this.indexCardId = indexCardId;
	}
	
	// ohne ID und Date
	public IndexCardStatImpl(int totalNumberRight, int totalNumberWrong, int indexCardId) {
		this.totalNumberRight = totalNumberRight;
		this.totalNumberWrong = totalNumberWrong;
		this.indexCardId = indexCardId;
	}
	
	@Override
	public int getStatisticId() {
		return statisticId;
	}

	@Override
	public void setStatisticId(int statisticId) {
		this.statisticId = statisticId;
	}
	
	@Override
	public int getTotalNumberRight() {
		return totalNumberRight;
	}

	@Override
	public void setTotalNumberRight(int totalNumberRight) {
		this.totalNumberRight = totalNumberRight;
	}

	@Override
	public int getTotalNumberWrong() {
		return totalNumberWrong;
	}

	@Override
	public void setTotalNumberWrong(int totalNumberWrong) {
		this.totalNumberWrong = totalNumberWrong;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int getIndexCardId() {
		return indexCardId;
	}

	@Override
	public void setIndexCardId(int indexCardId) {
		this.indexCardId = indexCardId;
	}
}