package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.FavoritelistDao;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
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
	public void addFavoritelist(String name , int userId) {
		
	String sql = "INSERT INTO FavoritenListe (Name, BenutzerID) VALUES (?,?)";
		try {
			Connection conn = JdbcUtils.getConnection();
			PreparedStatement query = conn.prepareStatement(sql); 
			query.setString(1,name); 
			query.setInt(2,userId);
			query.executeUpdate();
		}catch(SQLException e ) { 
			System.out.println(e.getMessage());
		}	
	}

	@Override
	public FavoritelistImpl findFavoritelist(int favoritelistId) {
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

}
