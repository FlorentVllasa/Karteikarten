
package de.kksystem.karteikarten.factories;

import de.kksystem.karteikarten.dao.classes.jdbc.CategoryDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.FavoritelistDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.IndexCardDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.IndexCardStatDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.LectionDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.PictureDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.CategoryDao;
import de.kksystem.karteikarten.dao.interfaces.FavoritelistDao;
import de.kksystem.karteikarten.dao.interfaces.IndexCardDao;
import de.kksystem.karteikarten.dao.interfaces.IndexCardStatDao;
import de.kksystem.karteikarten.dao.interfaces.LectionDao;
import de.kksystem.karteikarten.dao.interfaces.PictureDao;
import de.kksystem.karteikarten.dao.interfaces.UserDao;

public class DaoFactory {
	public static UserDao createUserDao() {
		return new UserDaoJdbcImpl();
	}

	public static CategoryDao createCategoryDao() {
		return new CategoryDaoJdbcImpl();
	}
	
	public static LectionDao createLectionDao() {
		return new LectionDaoJdbcImpl();
	}
	
	public static IndexCardDao createIndexCardDao() {
		return new IndexCardDaoJdbcImpl();
	}
	
	public static PictureDao createPictureDao() {
		return new PictureDaoJdbcImpl();
	}
	
	public static FavoritelistDao createFavoritelistDao() {
		return new FavoritelistDaoJdbcImpl();
	}
	
	public static IndexCardStatDao createIndexCardStatDao() {
		return new IndexCardStatDaoJdbcImpl();
	}
}