package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.LectionDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.utils.JdbcUtils;

public class LectionDaoJdbcImpl implements LectionDao {

	@Override
	public void addLection(Lection lection) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "INSERT INTO Lektion (Name, Beschreibung, LetzteUebung, KategorieID, FavoritenlisteID) VALUES (?, ?, NULL, ?, ?)";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, lection.getName());
			pstatement.setString(2, lection.getDescription());
			pstatement.setInt(3, lection.getCategoryId());
			pstatement.setInt(4, lection.getFavoritelistId());

			pstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(connection != null) {
				try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}

	@Override
	public void deleteLection(Lection lection) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "DELETE FROM Lektion WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, lection.getLectionId());

			pstatement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}

			if(connection != null) {
				try { connection.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}
		}
	}

	@Override
	public void updateLection(Lection lection) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Lektion SET Name = ?, Beschreibung = ? WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, lection.getName());
			pstatement.setString(2, lection.getDescription());
			pstatement.setInt(3, lection.getLectionId());

			pstatement.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			if(connection != null) {
				try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}

	@Override
	public void updateName(String name, int lectionId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Lektion SET Name = ? WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, name);
			pstatement.setInt(2, lectionId);

			pstatement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstatement != null) {
				try {  pstatement.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}

			if(connection != null) {
				try {  connection.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}
		}
	}

	@Override
	public void updateDescription(String description, int lectionId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Lektion SET Beschreibung = ? WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, description);
			pstatement.setInt(2, lectionId);

			pstatement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstatement != null) {
				try {  pstatement.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}

			if(connection != null) {
				try {  connection.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}
		}
	}

	@Override
	public void updateLastExcercise(Timestamp timestamp, int lectionId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Lektion SET LetzteUebung = ? WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setTimestamp(1, timestamp);
			pstatement.setInt(2, lectionId);

			pstatement.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if(pstatement != null) {
				try {  pstatement.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}

			if(connection != null) {
				try {  connection.close(); } catch (SQLException sqle) { sqle.printStackTrace(); }
			}
		}
	}

	@Override
	public Lection findLectionById(int lectionId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Lektion WHERE LektionID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, lectionId);

			rs = pstatement.executeQuery();
			final Lection lection = createLectionFromResultSet(rs);
			return lection;
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
	
	public List<Lection> findLectionByCategoryId(int categoryId) {
		List<Lection> allLectionsList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Lektion WHERE KategorieID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, categoryId);

			rs = pstatement.executeQuery();
			
			while(rs.next()) {
				Lection lection = createLectionFromResultSet(rs);
				allLectionsList.add(lection);
			}
			return allLectionsList;
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
	
	private Lection createLectionFromResultSet(final ResultSet resultSet) throws SQLException {
		final int lectionId = resultSet.getInt(1);
		final String name = resultSet.getString(2);
		final String description = resultSet.getString(3);
		final Timestamp lastExcercise = resultSet.getTimestamp(4);
		final int categoryId = resultSet.getInt(5);
		final int favoritelistId = resultSet.getInt(6);
		
		final Lection lection = ModelFactory.createLection();
		lection.setLectionId(lectionId);
		lection.setName(name);
		lection.setDescription(description);
		lection.setLastExcercise(lastExcercise);
		lection.setCategoryId(categoryId);
		lection.setFavoritelistId(favoritelistId);

		return lection;
	}

}
