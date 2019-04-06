package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.IndexCardStatDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.IndexCardStat;
import de.kksystem.karteikarten.utils.JdbcUtils;

/**
 * 
 * @author gian-luca
 *
 */
public class IndexCardStatDaoJdbcImpl implements IndexCardStatDao {

	@Override
	public int addIndexCardStat(IndexCardStat indexCardStat) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		
		LocalDate currentLocalDate = LocalDate.now();
		Date currentSqlDate = Date.valueOf(currentLocalDate);
		
		String sqlString = "INSERT INTO StatistikKarteikarte (AnzahlRichtig, AnzahlFalsch, Datum, KarteikarteID) VALUES (?, ?, ?, ?)";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);

			pstatement.setInt(1, indexCardStat.getTotalNumberRight());
			pstatement.setInt(2, indexCardStat.getTotalNumberWrong());
			if(indexCardStat.getDate() == null) {
				pstatement.setDate(3, currentSqlDate);
			} else {
				pstatement.setDate(3, indexCardStat.getDate());
			}
			pstatement.setInt(4, indexCardStat.getIndexCardId());
			
			pstatement.executeUpdate();
			
			rs = pstatement.getGeneratedKeys();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return -1;
		} finally {
			if(rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
			
			if (pstatement != null) {
				try {
					pstatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	public void updateRight(int indexCardId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE StatistikKarteikarte SET AnzahlRichtig = AnzahlRichtig + 1 WHERE KarteikarteID = ? AND Datum = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setInt(1, indexCardId);
			
			LocalDate currentLocalDate = LocalDate.now();
			Date currentSqlDate = Date.valueOf(currentLocalDate);	
			pstatement.setDate(2, currentSqlDate);

			pstatement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (pstatement != null) {
				try {
					pstatement.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
	}

	@Override
	public void updateWrong(int indexCardId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE StatistikKarteikarte SET AnzahlFalsch = AnzahlFalsch + 1 WHERE KarteikarteID = ? AND Datum = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setInt(1, indexCardId);
			
			LocalDate currentLocalDate = LocalDate.now();
			Date currentSqlDate = Date.valueOf(currentLocalDate);	
			pstatement.setDate(2, currentSqlDate);

			pstatement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (pstatement != null) {
				try {
					pstatement.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public boolean isIndexCardStatAvailable(int indexCardId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		
		LocalDate currentLocalDate = LocalDate.now();
		Date currentSqlDate = Date.valueOf(currentLocalDate);
		
		String sqlString = "SELECT StatistikID FROM StatistikKarteikarte"
				+ " WHERE KarteikarteID = ? AND Datum = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setInt(1, indexCardId);
			pstatement.setDate(2, currentSqlDate);
			
			rs = pstatement.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}  finally {
			if(rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(connection != null) {
				try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}

	@Override
	public List<IndexCardStat> findAllStatsByIndexCardId(int indexCardId, int numberOfLastDays) {
		List<IndexCardStat> allIndexCardStats = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		
		LocalDate currentLocalDate = LocalDate.now();
		Date currentSqlDate = Date.valueOf(currentLocalDate);
		
		// letzten 7 Tage
		LocalDate minusXDaysLocalDate = currentLocalDate.minusDays(numberOfLastDays);
		Date minusXDaysSqlDate = Date.valueOf(minusXDaysLocalDate);
		
		String sqlString = "SELECT * FROM StatistikKarteikarte WHERE KarteikarteID = ? AND Datum BETWEEN ? AND ? ORDER BY Datum ASC";
		
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, indexCardId);
			pstatement.setDate(2, minusXDaysSqlDate);
			pstatement.setDate(3, currentSqlDate);

			rs = pstatement.executeQuery();
			
			while(rs.next()) {
				IndexCardStat indexCardStat = createIndexCardStatFromResultSet(rs);
				allIndexCardStats.add(indexCardStat);
			}
			return allIndexCardStats;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(connection != null) {
				try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}
	
	@Override
	public List<IndexCardStat> findAllStatsByLectionId(int lectionId, int numberOfLastDays) {
		List<IndexCardStat> allIndexCardStats = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		
		LocalDate currentLocalDate = LocalDate.now();
		Date currentSqlDate = Date.valueOf(currentLocalDate);
		
		// letzten 7 Tage
		LocalDate minusXDaysLocalDate = currentLocalDate.minusDays(numberOfLastDays);
		Date minusXDaysSqlDate = Date.valueOf(minusXDaysLocalDate);
		
		String sqlString = "SELECT s.KarteikarteID, SUM(AnzahlRichtig), SUM(AnzahlFalsch)"
				+ " FROM StatistikKarteikarte s JOIN Karteikarte k"
				+ " ON s.KarteikarteID = k.KarteikarteID"
				+ " WHERE LektionID = ? AND Datum BETWEEN ? AND ?"
				+ " GROUP BY s.KarteikarteID";
		
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, lectionId);
			pstatement.setDate(2, minusXDaysSqlDate);
			pstatement.setDate(3, currentSqlDate);

			rs = pstatement.executeQuery();
			
			while(rs.next()) {
				IndexCardStat indexCardStat = createIndexCardStatWithoutIdAndDateFromResultSet(rs);
				allIndexCardStats.add(indexCardStat);
			}
			return allIndexCardStats;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(connection != null) {
				try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}
	
	private IndexCardStat createIndexCardStatFromResultSet(final ResultSet resultSet) throws SQLException {		
		final int statisticId = resultSet.getInt(1);
		final int totalNumberRight = resultSet.getInt(2);
		final int totalNumberWrong = resultSet.getInt(3);
		final Date date = resultSet.getDate(4);
		final int indexCardId = resultSet.getInt(5);

		final IndexCardStat indexCardStat = ModelFactory.createIndexCardStat();
		indexCardStat.setStatisticId(statisticId);
		indexCardStat.setTotalNumberRight(totalNumberRight);
		indexCardStat.setTotalNumberWrong(totalNumberWrong);
		indexCardStat.setDate(date);
		indexCardStat.setIndexCardId(indexCardId);

		return indexCardStat;
	}
	
	private IndexCardStat createIndexCardStatWithoutIdAndDateFromResultSet(final ResultSet resultSet) throws SQLException {
		final int indexCardId = resultSet.getInt(1);
		final int totalNumberRight = resultSet.getInt(2);
		final int totalNumberWrong = resultSet.getInt(3);

		final IndexCardStat indexCardStat = ModelFactory.createIndexCardStat();
		indexCardStat.setStatisticId(0);
		indexCardStat.setTotalNumberRight(totalNumberRight);
		indexCardStat.setTotalNumberWrong(totalNumberWrong);
		indexCardStat.setIndexCardId(indexCardId);
		indexCardStat.setDate(null);

		return indexCardStat;
	}

}
