package de.kksystem.karteikarten.model.interfaces;

import java.sql.Date;

public interface IndexCardStat {
	public int getStatisticId();
	public void setStatisticId(int statisticId);
	public int getTotalNumberRight();
	public void setTotalNumberRight(int totalNumberRight);
	public int getTotalNumberWrong();
	public void setTotalNumberWrong(int totalNumberWrong);
	public Date getDate();
	public void setDate(Date date);
	public int getIndexCardId();
	public void setIndexCardId(int indexCardId);
}