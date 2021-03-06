package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.LectionDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.utils.JdbcUtils;

public class LectionDaoJdbcImpl implements LectionDao {

	@Override
	public void addLection(Lection lection) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "INSERT INTO Lektion (Name, Beschreibung, LetzteUebung, KategorieID, FavoritenlisteID) VALUES (?, ?, NULL, ?, NULL)";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, lection.getName());
			pstatement.setString(2, lection.getDescription());
			pstatement.setInt(3, lection.getCategoryId());

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
	public void updateFavoriteListId(Lection lection, int favoriteId){
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Lektion SET FavoritenlisteID = ? WHERE LektionID = ?";
		try{
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			if(favoriteId > 0) {
				pstatement.setInt(1, favoriteId);
			} else {
				pstatement.setNull(1, Types.INTEGER);
			}

			pstatement.setInt(2, lection.getLectionId());
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
			
            if(rs.next()) {
    			final Lection lection = createLectionFromResultSet(rs);
    			return lection;
			}
			return null;
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

	@Override
	public List<Lection> findLectionByUserId(int userId){
		List<Lection> allLectionsList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Lektion WHERE UserId = ?";
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, userId);

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
	
	@Override
	public List<Lection> findLectionByFavoritelistId(int favoritelisteId) {
		List<Lection> allLectionsList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Lektion WHERE FavoritenlisteID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, favoritelisteId);

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
	
	public static void main(String[] args) {
		LectionDaoJdbcImpl cat = new LectionDaoJdbcImpl();
		List<Lection> list = cat.findLectionByFavoritelistId(1);
		//List<Lection> list = cat.findLectionByCategoryId(6);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getLectionId());
		}
	}

}
