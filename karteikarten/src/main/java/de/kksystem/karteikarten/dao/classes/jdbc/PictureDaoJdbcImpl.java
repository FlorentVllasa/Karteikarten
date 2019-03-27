package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.kksystem.karteikarten.dao.interfaces.PictureDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.Picture;
import de.kksystem.karteikarten.utils.JdbcUtils;

public class PictureDaoJdbcImpl implements PictureDao {

	//nurkommentar
	@Override
	public void deletePicture(int pictureId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = 	"DELETE FROM Bild" +
							"WHERE BildID = ?";
		
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, pictureId);
			
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
	public void updatePicture(int pictureId, Picture newPicture) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = 	"UPDATE Bild" +
							"SET SpeicherOrt = ?, Beschreibung = ?" +
							"WHERE BildID = ?";
		
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setString(1, newPicture.getFileLocation());
			pstatement.setString(2, newPicture.getDescription());
			pstatement.setInt(3, pictureId);
			
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
	public Picture findPicture(int pictureId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Bild WHERE BildID = ?";
		
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, pictureId);
			 
			rs = pstatement.executeQuery();
			final Picture picture = createPictureFromResultSet(rs);
			return picture;
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
	public int addPicture(Picture picture) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		String sqlString = 	"INSERT INTO Bild (SpeicherOrt, Beschreibung)" + 
							"VALUES (?, ?)";
		
		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString,Statement.RETURN_GENERATED_KEYS);
			pstatement.setString(1, picture.getFileLocation());
			pstatement.setString(2, picture.getDescription());
			pstatement.executeUpdate();
			ResultSet rs = pstatement.getGeneratedKeys();
			int generated_key = rs.getInt(1);
			return generated_key;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if(pstatement != null) {
				try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
			
			if(connection != null) {
				try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}	
	
	private Picture createPictureFromResultSet(final ResultSet resultSet) throws SQLException {
		final int pictureId = resultSet.getInt(1);
		final String fileLocation = resultSet.getString(2);
		final String description = resultSet.getString(3);
		
		final Picture picture = ModelFactory.createPicture();
		picture.setPictureId(pictureId);
		picture.setFileLocation(fileLocation);
		picture.setDescription(description);

		return picture;
	}
}
