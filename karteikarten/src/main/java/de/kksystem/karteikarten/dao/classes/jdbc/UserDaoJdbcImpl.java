package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
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
	public int addUser(User user) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		
		String sqlString = "INSERT INTO Benutzer (EmailAdresse, Benutzername, Passwort, Nachname, Vorname, LetzteAnmeldung) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);

			pstatement.setString(1, user.getEmail());
			pstatement.setString(2, user.getUsername());
			pstatement.setString(3, user.getPassword());
			pstatement.setString(4, user.getSurname());
			pstatement.setString(5, user.getForename());
			pstatement.setTimestamp(6, user.getLastLogin());

			pstatement.executeUpdate();
			
			rs = pstatement.getGeneratedKeys();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
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
			
			if(rs.next()) {
				final User user = createUserFromResultSet(rs);
				return user;
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
			if(rs.next()) {
				final User user = createUserFromResultSet(rs);
				return user;
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
			
			if(rs.next()) {
				final User user = createUserFromResultSet(rs);
				return user;
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

	// Eventuell entfernen; nicht sicher
	@Override
	public void updateUser(User user) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET Benutzername = ?, Passwort = ?, Nachname = ?, Vorname = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, user.getUsername());
			pstatement.setString(2, user.getPassword());
			pstatement.setString(3, user.getSurname());
			pstatement.setString(4, user.getForename());
			pstatement.setInt(5, user.getUserId());

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

	@Override
	public void updateUsername(String newUsername, int userId){
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET Benutzername = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newUsername);
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
	public void updateEmail(String newEmail, int userId){
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = "UPDATE Benutzer SET EmailAdresse = ? WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			pstatement.setString(1, newEmail);
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
	public List<String> findAllUserEmails() {
		List<String> allUserEmails = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT EmailAdresse FROM Benutzer";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			rs = pstatement.executeQuery();

			while(rs.next()) {
				String email = rs.getString(1);
				allUserEmails.add(email);
			}
			return allUserEmails;
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
	public List<String> findAllUserNames() {
		List<String> allUserNames = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT Benutzername FROM Benutzer";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);

			rs = pstatement.executeQuery();

			while(rs.next()) {
				String userName = rs.getString(1);
				allUserNames.add(userName);
			}
			return allUserNames;
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
		List<String> allUserNames = ServiceFacade.getInstance().findAllUserNames();
		for (int i = 0; i < allUserNames.size(); i++) {
			System.out.println(allUserNames.get(i));
		}
	}
}
