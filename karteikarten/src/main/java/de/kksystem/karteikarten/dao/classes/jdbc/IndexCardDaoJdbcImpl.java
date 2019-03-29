package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.IndexCardDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.utils.JdbcUtils;

/**
 * 
 * @author gian-luca
 *
 */
public class IndexCardDaoJdbcImpl implements IndexCardDao {

	@Override
	public IndexCard findIndexCardById(int indexCardId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Karteikarte WHERE KarteikarteID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, indexCardId);

			rs = pstatement.executeQuery();

			if (rs.next()) {
				final IndexCard indexCard = createIndexCardFromResultSet(rs);
				return indexCard;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	public void addIndexCard(IndexCard indexCard) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "INSERT INTO Karteikarte (Frage, Antwort, Farbe, LektionID, BildID) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, indexCard.getQuestion());
			pstatement.setString(2, indexCard.getAnswer());

			if (indexCard.getColor() == null) {
				pstatement.setNull(3, Types.VARCHAR);
			} else {
				pstatement.setString(3, indexCard.getColor());
			}

			pstatement.setInt(4, indexCard.getLectionId());

			// wenn pictureId null, dann übergebe null (d.h. pictureId kann in der DB null
			// sein)
			if (indexCard.getPictureId() == null) {
				pstatement.setNull(5, Types.INTEGER);
			} else {
				pstatement.setInt(5, indexCard.getPictureId());
			}

			pstatement.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
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
	public void deleteIndexCard(IndexCard indexCard) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "DELETE FROM Karteikarte WHERE KarteikarteID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, indexCard.getIndexCardId());

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
	public void updateIndexCard(IndexCard indexCard) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Karteikarte SET Frage = ?, Antwort = ? WHERE KarteikarteID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, indexCard.getQuestion());
			pstatement.setString(2, indexCard.getAnswer());
			pstatement.setInt(3, indexCard.getIndexCardId());

			pstatement.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
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
	public void updateQuestion(String newQuestion, int indexCardId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Karteikarte SET Frage = ? WHERE KarteikarteID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newQuestion);
			pstatement.setInt(2, indexCardId);

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
	public void updateAnswer(String newAnswer, int indexCardId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Karteikarte SET Antwort = ? WHERE KarteikarteID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newAnswer);
			pstatement.setInt(2, indexCardId);

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
	public List<IndexCard> findAllIndexCardsByLectionId(int lectionId) {
		List<IndexCard> allIndexCardsList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Karteikarte WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, lectionId);

			rs = pstatement.executeQuery();
			
			while(rs.next()) {
				IndexCard indexCard = createIndexCardFromResultSet(rs);
				allIndexCardsList.add(indexCard);
			}
			return allIndexCardsList;
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

	private IndexCard createIndexCardFromResultSet(final ResultSet resultSet) throws SQLException {
		final int indexCardId = resultSet.getInt(1);
		final String question = resultSet.getString(2);
		final String answer = resultSet.getString(3);
		final String color = resultSet.getString(4);
		final int lectionId = resultSet.getInt(5);
		final int pictureId = resultSet.getInt(6);

		final IndexCard indexCard = ModelFactory.createIndexCard();
		indexCard.setIndexCardId(indexCardId);
		indexCard.setQuestion(question);
		indexCard.setAnswer(answer);
		indexCard.setColor(color);
		indexCard.setLectionId(lectionId);
		indexCard.setPictureId(pictureId);

		return indexCard;
	}
}
