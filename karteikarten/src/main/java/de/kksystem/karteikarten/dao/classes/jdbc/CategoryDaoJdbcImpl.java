package de.kksystem.karteikarten.dao.classes.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.CategoryDao;
import de.kksystem.karteikarten.factories.ModelFactory;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.utils.JdbcUtils;

public class CategoryDaoJdbcImpl implements CategoryDao {

    @Override
    public void addCategory(Category category){
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sqlString = "INSERT INTO Kategorie(Name, Beschreibung, BenutzerID) VALUES (?,?,?)";
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(sqlString);
            pstatement.setString(1, category.getName());
            pstatement.setString(2, category.getDescription());
            pstatement.setInt(3, category.getUserId());
            pstatement.executeUpdate();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }finally{
            if(pstatement != null) {
                try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        	
            if(connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public void deleteCategory(Category category) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String deleteCategorieString = "DELETE FROM Kategorie WHERE KategorieID = ? AND BenutzerID = ?";
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(deleteCategorieString);
            pstatement.setInt(1, getCategoryIdFromDatabase(category));
            pstatement.setInt(2, category.getUserId());
            pstatement.executeUpdate();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }finally{
            if(pstatement != null) {
                try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        	
            if(connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public Category findCategory(Category category) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet rs = null;
        String sqlString = "SELECT * FROM Kategorie WHERE KategorieID = ?";
        try {
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(sqlString);
            pstatement.setInt(1, getCategoryIdFromDatabase(category));
            rs = pstatement.executeQuery();
            
            if(rs.next()) {
                final Category category1 = createCategoryFromResultSet(rs);
                return category1;
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
    public List<Category> findAllCategories() {
        List<Category> allCategoriesList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet rs = null;
        String allCategoriesString = "SELECT * FROM Kategorie";
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(allCategoriesString);
            rs = pstatement.executeQuery();
            while(rs.next()){
                Category category = createCategoryFromResultSet(rs);
                allCategoriesList.add(category);
            }
            for (Category category : allCategoriesList){
                System.out.println(category);
            }
            return allCategoriesList;
        }catch(SQLException se){
            System.out.println(se.getMessage());
            return null;
        }finally{
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
    public void updateCategorieDescription(Category category, String neueBeschreibung){
        Connection connection = null;
        PreparedStatement pstatement = null;
        String updateCategorieString = "UPDATE Kategorie SET Beschreibung = ? WHERE KategorieID = ? and BenutzerID = ?";
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(updateCategorieString);
            pstatement.setString(1, neueBeschreibung);
            pstatement.setInt(2, getCategoryIdFromDatabase(category));
            //pstatement.setInt(3, userImplDao.getUserIdFromDatabase(user));
            pstatement.executeUpdate();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }finally{
            if(pstatement != null) {
                try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        	
            if(connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public int countCategories(){
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet rs = null;
        String countString = "SELECT COUNT(*) FROM Kategorie";
        int numberOfCategories;
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(countString);
            rs = pstatement.executeQuery();
            
            if(rs.next()) {
            	numberOfCategories = rs.getInt(1);
            	return numberOfCategories; 
			}
			return -1;
        }catch(SQLException se){
            System.out.println(se.getMessage());
            return -1;
        }finally{
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

    private Category createCategoryFromResultSet(final ResultSet resultSet) throws SQLException {
        final int categoryId = resultSet.getInt(1);
        final String name = resultSet.getString(2);
        final String description = resultSet.getString(3);
        final int userId = resultSet.getInt(4);

        final Category category = ModelFactory.createCategory();
        category.setCategoryId(categoryId);
        category.setName(name);
        category.setDescription(description);
        category.setUserId(userId);

        return category;
    }


    public int getCategoryIdFromDatabase(Category category){
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet rs = null;
        String catIdString = "SELECT KategorieID from Kategorie where Name = ?";
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(catIdString);
            pstatement.setString(1, category.getName());
            rs = pstatement.executeQuery();
            
            if(rs.next()) {
                int catId = rs.getInt(1);
                return catId;
			}
			return 0;
        }catch(SQLException se){
            System.out.println(se.getMessage());
            return 0;
        }finally{
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
	public void updateCategorieName(Category category, String neuerCatName) {
		Connection connection = null;
        PreparedStatement pstatement = null;
        String updateCategorieString = "UPDATE Kategorie SET Name = ? WHERE KategorieID = ? and BenutzerID = ?";
        try{
            connection = JdbcUtils.getConnection();
            pstatement = connection.prepareStatement(updateCategorieString);
            pstatement.setString(1, neuerCatName);
            pstatement.setInt(2, category.getCategoryId());
            pstatement.setInt(3, category.getUserId());
            pstatement.executeUpdate();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }finally{
            if(pstatement != null) {
                try { pstatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        	
            if(connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
	}
	
	@Override
	public List<Category> findCategoriesByUserId(int userId) {
		List<Category> allCategoriesList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sqlString = "SELECT * FROM Kategorie WHERE BenutzerID = ?";

		try {
			connection = JdbcUtils.getConnection();
			pstatement = connection.prepareStatement(sqlString);
			pstatement.setInt(1, userId);

			rs = pstatement.executeQuery();
			
			while(rs.next()) {
				Category category = createCategoryFromResultSet(rs);
				allCategoriesList.add(category);
			}
			return allCategoriesList;
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

//    public static void main(String[] args) {
//       CategoryDaoJdbcImpl cat = new CategoryDaoJdbcImpl();
//       //UserImpl user1 = new UserImpl("Destroyer", "flow3@random.com", "pw123456", "Vl", "FL");
//       //UserImpl user2 = new UserImpl("Guentha2", "gunt@random.com", "pw23", "Guenther", "Mueller");
//       //CategoryImpl category3 = new CategoryImpl("Sport", null);
//       //new UserDaoJdbcImpl().addUser(user2);
//       //cat.updateCategorieDescription(category3, user2, "Unnoetig");
//       List<Category> list = cat.findCategoriesByUserId(4);
//       for (int i = 0; i < list.size(); i++) {
//    	   System.out.println(list.get(i).getCategoryId());
//       }
//    }

}
