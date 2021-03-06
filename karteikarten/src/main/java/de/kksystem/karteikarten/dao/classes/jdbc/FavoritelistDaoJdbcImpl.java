package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.FavoritelistDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.model.classes.FavoritelistImpl;
import de.kksystem.karteikarten.utils.JdbcUtils;

/**
 * 
 * @author engin
 *
 */
public class FavoritelistDaoJdbcImpl  implements FavoritelistDao {
	

	@Override
	/**
	 * Eine Methode die als parameter eine ID uebergeben bekommt und dieses Tupel dann loescht.
	 * @param favoritId - type Integer 
	 */
	public void delete(int favoritId) {
		String sql ="DELETE FROM FavoritenListe WHERE FavoritenListeID = ?";
		try (Connection conn = JdbcUtils.getConnection()){
			PreparedStatement query = conn.prepareStatement(sql); 
			query.setInt(1,favoritId);
			query.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Favoritelist> findAllFavoritelist() {
		
		List<Favoritelist> listeAllerFavoritenListen = new ArrayList<>();
		String sql ="SELECT * FROM FavoritenListe";
		try (Connection conn = JdbcUtils.getConnection()){
			PreparedStatement query = conn.prepareStatement(sql); 
			ResultSet rs = query.executeQuery();
			
			while (rs.next()) { 
				FavoritelistImpl favList = new FavoritelistImpl(); 
				favList.setFavoritelistId(rs.getInt("FavoritenListeID"));
				favList.setName(rs.getString("Name"));
				favList.setUserId(rs.getInt("BenutzerID")); 
				listeAllerFavoritenListen.add(favList);
		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return listeAllerFavoritenListen;
	}

	@Override
	public void updateFavoritelist(int favListId , String name , int userId) {
		String sql = "UPDATE FavoritenListe SET Name = ? , "
				+ "BenutzerID = ?"
				+ "WHERE FavoritenListeID = ? " ;
		try (Connection conn = JdbcUtils.getConnection()){
			PreparedStatement query = conn.prepareStatement(sql); 
			query.setString(1,name);
			query.setInt(2,userId);
	        query.executeUpdate();
		}catch(SQLException e ) { 
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public Favoritelist findFavoritelistIdByUserId(int userId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Favoritenliste WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, userId);

			rs = pstatement.executeQuery();
			
			if(rs.next()) {
				final Favoritelist favoritelist = createFavoritelistFromResultSet(rs);
				return favoritelist;
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
	public void findFavoritelistById(int favoritelistId) {
		String sql ="SELECT * FROM FavoritenListe WHERE FavoritenListeID = ?";
		try (Connection conn = JdbcUtils.getConnection()){
			PreparedStatement query = conn.prepareStatement(sql); 
			query.setInt(1,favoritelistId);
			ResultSet rs = query.executeQuery();
			
			while (rs.next()) { 
				// die id ausgeben
				System.out.println(rs.getInt("FavoritenListeID"));
				// den namen ausgeben 
				System.out.println(rs.getString("Name"));
				// die Benutzer ID ausgeben 
				System.out.println(rs.getInt("BenutzerID"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
	}

	@Override
	public int addFavoritelist(String name, int userId) {
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		
		String sqlString = "INSERT INTO Favoritenliste (Name, BenutzerID) VALUES (NULL, ?)";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);

			pstatement.setInt(1, userId);

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
	public Favoritelist findFavoritelist(int favoritelistId) {
		FavoritelistImpl favList = new FavoritelistImpl();
		String sql ="SELECT * FROM FavoritenListe WHERE FavoritenListeID = ?";
		try (Connection conn = JdbcUtils.getConnection()){
			PreparedStatement query = conn.prepareStatement(sql); 
			query.setInt(1,favoritelistId);
			ResultSet rs = query.executeQuery();
			
			while (rs.next()) { 
				// die id in unserem Favoriten Objekt setzen
				favList.setFavoritelistId(rs.getInt("FavoritenListeID"));
				// den namen in unserem Favoriten Objekt setzen 
				favList.setName(rs.getString("Name"));
				// die Benutzer ID in unserem Objekt setzen
				favList.setUserId(rs.getInt("BenutzerID"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return favList ;	
	}
	
	@Override
	public List<Favoritelist> findFavoritesByUserId(int userId) {
		FavoritelistImpl favList = new FavoritelistImpl();
		List<Favoritelist> allFavoritesList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Favoritenliste WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, userId);

			rs = pstatement.executeQuery();
			
			while(rs.next()) {
				// die id in unserem Favoriten Objekt setzen
				favList.setFavoritelistId(rs.getInt("FavoritenListeID"));
				// den namen in unserem Favoriten Objekt setzen 
				favList.setName(rs.getString("Name"));
				// die Benutzer ID in unserem Objekt setzen
				favList.setUserId(rs.getInt("BenutzerID"));
				allFavoritesList.add(favList);
			}
			return allFavoritesList;
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
	
	private Favoritelist createFavoritelistFromResultSet(final ResultSet resultSet) throws SQLException {
		final int favoritelistId = resultSet.getInt(1);
		final String favoritelistName = resultSet.getString(2);
		final int favoritelistUserId = resultSet.getInt(3);

		final Favoritelist favoritelist = ModelFactory.createFavoritelist();
		favoritelist.setFavoritelistId(favoritelistId);
		favoritelist.setName(favoritelistName);
		favoritelist.setUserId(favoritelistUserId);

		return favoritelist;
	}
	
//	public static void main(String[] args) {
//		FavoritelistDaoJdbcImpl cat = new FavoritelistDaoJdbcImpl();
//		List<Favoritelist> list = cat.findFavoritesByUserId(4);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getFavoritelistId());
//		}
//	}

}
