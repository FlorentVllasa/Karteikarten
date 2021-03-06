
package de.kksystem.karteikarten.facades;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import de.kksystem.karteikarten.dao.interfaces.CategoryDao;
import de.kksystem.karteikarten.dao.interfaces.FavoritelistDao;
import de.kksystem.karteikarten.dao.interfaces.IndexCardDao;
import de.kksystem.karteikarten.dao.interfaces.IndexCardStatDao;
import de.kksystem.karteikarten.dao.interfaces.LectionDao;
import de.kksystem.karteikarten.dao.interfaces.PictureDao;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.factories.DaoFactory;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.IndexCardStat;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.Picture;
import de.kksystem.karteikarten.model.interfaces.User;

public class DaoFacade {
	private static DaoFacade daoFacade;
	private static UserDao userDao;
	private static CategoryDao categoryDao;
	private static LectionDao lectionDao;
	private static IndexCardDao indexCardDao;
	private static IndexCardStatDao indexCardStatDao;
	private static PictureDao pictureDao;
	private static FavoritelistDao favoritelistDao;
	
	private DaoFacade() {
		userDao = DaoFactory.createUserDao();
		//userDao = new UserDaoJdbcImpl();
		categoryDao = DaoFactory.createCategoryDao();
		lectionDao = DaoFactory.createLectionDao();
		indexCardDao = DaoFactory.createIndexCardDao();
		indexCardStatDao = DaoFactory.createIndexCardStatDao();
		favoritelistDao = DaoFactory.createFavoritelistDao();
		pictureDao = DaoFactory.createPictureDao();
	}
	
	public static DaoFacade getInstance() {
		if (daoFacade == null) {
			return new DaoFacade();
		}
		
		return daoFacade;
	}
	
	/*
	 * 
	 *User
	 *
	 * 
	 */
	public int addUser(User user) {
		return userDao.addUser(user);
	}
	
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}
	
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
	
	public void updatePassword(String newPassword, int userId) {
		userDao.updatePassword(newPassword, userId);
	}

	public void updateEmail(String newEmail, int userId){
		userDao.updateEmail(newEmail, userId);
	}
	
	public void updateForename(String newForename, int userId) {
		userDao.updateForename(newForename, userId);
	}
	
	public void updateSurname(String newSurname, int userId) {
		userDao.updateSurname(newSurname, userId);
	}
	
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}
	
	public User findUserById(int userId) {
		return userDao.findUserById(userId);
	}
	
	public User findUserByEMail(String email) {
		return userDao.findUserByEMail(email);
	}

	public void updateUsername(String newUsername, int userId){
		userDao.updateUsername(newUsername, userId);
	}

	public List<String> findAllUserEmails(){
		return userDao.findAllUserEmails();
	}

	public List<String> findAllUserNames(){
		return userDao.findAllUserNames();
	}
	
	/*
	 * 
	 *Category
	 *
	 * 
	 */
	public void addCategory(Category category){
		categoryDao.addCategory(category);
	}
	
	public void deleteCategory(Category category) {
		categoryDao.deleteCategory(category);
	}
	
	public void updateCategoryName(Category category, String neuerCatName){
		categoryDao.updateCategorieName(category, neuerCatName);
	}
	
	public void updateCategoryDescription(Category category, String neueBeschreibung){
		categoryDao.updateCategorieDescription(category, neueBeschreibung);
	}
	
	public Category findCategory(Category category) {
		return categoryDao.findCategory(category);
	}
	
	public List<Category> findAllCategories() {
		return categoryDao.findAllCategories();
	}

	public int countCategories() {
		return categoryDao.countCategories();
	}
	
	public List<Category> findCategoriesByUserId(int userId) {
		return categoryDao.findCategoriesByUserId(userId);
	}
	
	/*
	 * 
	 *Lection
	 *
	 * 
	 */
	public void addLection(Lection lection) {
		lectionDao.addLection(lection);
	}
	
	public void deleteLection(Lection lection) {
		lectionDao.deleteLection(lection);
	}
	
	public void updateLection(Lection lection) {
		lectionDao.updateLection(lection);
	}
	
	public void updateName(String name, int lectionId) {
		lectionDao.updateName(name, lectionId);
	}
	
	public void updateDescription(String description, int lectionId) {
		lectionDao.updateDescription(description, lectionId);
	}
	
	public void updateLastExcercise(Timestamp timestamp, int lectionId) {
		lectionDao.updateLastExcercise(timestamp, lectionId);
	}
	
	public Lection findLectionById(int lectionId) {
		return lectionDao.findLectionById(lectionId);
	}

	public List<Lection> findLectionByUserId(int userId) {
		return lectionDao.findLectionByUserId(userId);
	}
	
	public List<Lection> findLectionByCategoryId(int categoryId) {
		return lectionDao.findLectionByCategoryId(categoryId);
	}
	
	public List<Lection> findLectionByFavoritelistId(int favoritelisteId) {
		return lectionDao.findLectionByFavoritelistId(favoritelisteId);
	}

	public void updateFavoriteListId(Lection lection, int favoriteId){
		lectionDao.updateFavoriteListId(lection, favoriteId);
	}


	
	/*
	 * 
	 *IndexCard
	 *
	 * 
	 */
	public int addIndexCard(IndexCard indexCard) {
		return indexCardDao.addIndexCard(indexCard);
	}
	
	public void deleteIndexCard(IndexCard indexCard) {
		indexCardDao.deleteIndexCard(indexCard);
	}
	
	public void updateIndexCard(IndexCard indexCard) {
		indexCardDao.updateIndexCard(indexCard);
	}
	
	public void updateQuestion(String newQuestion, int indexCardId) {
		indexCardDao.updateQuestion(newQuestion, indexCardId);
	}
	
	public void updateAnswer(String newAnswer, int indexCardId) {
		indexCardDao.updateAnswer(newAnswer, indexCardId);
	}
	
	public void updatePictureId(int indexCardId, int pictureId) {
		indexCardDao.updatePictureId(indexCardId, pictureId);
	}
	
	public IndexCard findIndexCardById(int indexCardId) {
		return indexCardDao.findIndexCardById(indexCardId);
	}
	
	public List<IndexCard> findAllIndexCardsByLectionId(int lectionId) {
		return indexCardDao.findAllIndexCardsByLectionId(lectionId);
	}
	
	/*
	 * 
	 *Picture
	 *
	 * 
	 */
	public void deletePicture(int pictureId) {
		pictureDao.deletePicture(pictureId);
	}
	
	public void updatePicture(Picture newPicture) {
		pictureDao.updatePicture(newPicture);
	}
	
	public Picture findPicture(int pictureId) {
		return pictureDao.findPicture(pictureId);
	}
	
	public int addPicture(Picture picture) {
		return pictureDao.addPicture(picture);
	}
	
	/*
	 * 
	 *Favoritelist
	 *
	 * 
	 */
	public int addFavoritelist(String name, int userId) {
		return favoritelistDao.addFavoritelist(name, userId);
	}
	
	public void delete(int favoritId) {
		favoritelistDao.delete(favoritId);
	}
	
	public void updateFavoritelist(int favListId, String name, int userId) {
		favoritelistDao.updateFavoritelist(favListId, name, userId);
	}
	
	public void findFavoritelistById(int favoritelistId) {
		favoritelistDao.findFavoritelistById(favoritelistId);
	}
	
	public Favoritelist findFavoritelist(int favoritelistId) {
		return favoritelistDao.findFavoritelist(favoritelistId);
	}
	
	public List<Favoritelist> findAllFavoritelist() {
		return favoritelistDao.findAllFavoritelist();
	}
	
	public List<Favoritelist> findFavoritesByUserId(int userId) {
		return favoritelistDao.findFavoritesByUserId(userId);
	}
	
	public Favoritelist findFavoritelistIdByUserId(int userId) {
		return favoritelistDao.findFavoritelistIdByUserId(userId);
	}
	
	/*
	 * 
	 *IndexCardStat
	 *
	 * 
	 */
	public int addIndexCardStat(IndexCardStat indexCardStat) {
		return indexCardStatDao.addIndexCardStat(indexCardStat);
	}
	
	public void updateRight(int indexCardId) {
		indexCardStatDao.updateRight(indexCardId);
	}
	
	public void updateWrong(int indexCardId) {
		indexCardStatDao.updateWrong(indexCardId);
	}

	public boolean isIndexCardStatAvailable(int indexCardId) {
		return indexCardStatDao.isIndexCardStatAvailable(indexCardId);
	}
	
	public List<IndexCardStat> findAllStatsByIndexCardId(int indexCardId, int numberOfLastDays) {
		return indexCardStatDao.findAllStatsByIndexCardId(indexCardId, numberOfLastDays);
	}
	
	public List<IndexCardStat> findAllStatsByLectionId(int lectionId, int numberOfLastDays) {
		return indexCardStatDao.findAllStatsByLectionId(lectionId, numberOfLastDays);
	}
	
}

