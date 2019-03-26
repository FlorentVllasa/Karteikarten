package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.utils.JdbcUtils;
import de.kksystem.karteikarten.factories.ModelFactory;

/**
 * 
 * @author gian-luca
 *
 */
public class UserDaoJdbcImpl implements UserDao {

	@Override
	public void addUser(User user) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "INSERT INTO Benutzer (EmailAdresse, Benutzername, Passwort, Nachname, Vorname, LetzteAnmeldung) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, user.getEmail());
			pstatement.setString(2, user.getUsername());
			pstatement.setString(3, user.getPassword());
			pstatement.setString(4, user.getSurname());
			pstatement.setString(5, user.getForename());
			pstatement.setTimestamp(6, timestamp);

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
	public void deleteUser(User user) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "DELETE FROM Benutzer WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, user.getUserId());

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
	public User findUserByEMail(String email) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Benutzer WHERE EmailAdresse = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setString(1, email);

			rs = pstatement.executeQuery();
			final User user = createUserFromResultSet(rs);
			return user;
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
	public User findUserByUsername(String username) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Benutzer WHERE Benutzername = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setString(1, username);

			rs = pstatement.executeQuery();
			final User user = createUserFromResultSet(rs);
			return user;
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
	public User findUserById(int userId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Benutzer WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, userId);

			rs = pstatement.executeQuery();
			final User user = createUserFromResultSet(rs);
			return user;
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

	// Eventuell entfernen; nicht sicher
	@Override
	public void updateUser(User user) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET Password = ?, Nachname = ?, Vorname = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, user.getPassword());
			pstatement.setString(2, user.getSurname());
			pstatement.setString(3, user.getForename());

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
	public void updatePassword(String newPassword, int userId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET Passwort = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newPassword);
			pstatement.setInt(2, userId);

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
	public void updateForename(String newForename, int userId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET Vorname = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newForename);
			pstatement.setInt(2, userId);

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
	public void updateSurname(String newSurname, int userId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET Nachname = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newSurname);
			pstatement.setInt(2, userId);

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

	private User createUserFromResultSet(final ResultSet resultSet) throws SQLException {
		final int userId = resultSet.getInt(1);
		final String userEmail = resultSet.getString(2);
		final String username = resultSet.getString(3);
		final String userPassword = resultSet.getString(4);
		final String userSurname = resultSet.getString(5);
		final String userForename = resultSet.getString(6);
		final Timestamp userLastLogin = resultSet.getTimestamp(7);

		final User user = ModelFactory.createUser();
		user.setUserId(userId);
		user.setEmail(userEmail);
		user.setUsername(username);
		user.setPassword(userPassword);
		user.setSurname(userSurname);
		user.setForename(userForename);
		user.setLastLogin(userLastLogin);

		return user;
	}
	
	public static void main(String[] args) {
		User user = new UserImpl("benutzername", "email@example.com", "meinpw", "buschi", "giorgio", null);
		new UserDaoJdbcImpl().addUser(user);
	}
}
