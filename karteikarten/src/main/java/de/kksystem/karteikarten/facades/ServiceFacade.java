package de.kksystem.karteikarten.facades;

import java.sql.Timestamp;
import java.util.List;

import de.kksystem.karteikarten.factories.ServiceFactory;
import de.kksystem.karteikarten.model.classes.FavoritelistImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.Picture;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.service.interfaces.CategoryService;
import de.kksystem.karteikarten.service.interfaces.FavoritelistService;
import de.kksystem.karteikarten.service.interfaces.IndexCardService;
import de.kksystem.karteikarten.service.interfaces.LectionService;
import de.kksystem.karteikarten.service.interfaces.PictureService;
import de.kksystem.karteikarten.service.interfaces.UserService;

public class ServiceFacade {
	private static ServiceFacade serviceFacade;
	private static UserService userService;
	private static CategoryService categoryService;
	private static LectionService lectionService;
	private static IndexCardService indexCardService;
	private static PictureService pictureService;
	private static FavoritelistService favoritelistService;
	
	private ServiceFacade() {
		userService = ServiceFactory.createUserService();
		categoryService = ServiceFactory.createCategoryService();
		lectionService = ServiceFactory.createLectionService();
		indexCardService = ServiceFactory.createIndexCardService();
		pictureService = ServiceFactory.createPicture();
		favoritelistService = ServiceFactory.createFavoritelist();
	}

	
	public static ServiceFacade getInstance() {
		if (serviceFacade == null) {
			return new ServiceFacade();
		}
		
		return serviceFacade;
	}
	
	/*
	 * 
	 *User
	 *
	 * 
	 */
	public int addUser(User user) {
		return userService.addUser(user);
	}
	
	public void deleteUser(User user) {
		userService.deleteUser(user);
	}
	
	public void updateUser(User user) {
		userService.updateUser(user);
	}
	
	public void updatePassword(String newPassword, int userId) {
		userService.updatePassword(newPassword, userId);
	}
	
	public void updateForename(String newForename, int userId) {
		userService.updateForename(newForename, userId);
	}
	
	public void updateSurname(String newSurname, int userId) {
		userService.updateSurname(newSurname, userId);
	}
	
	public User findUserByUsername(String username) {
		return userService.findUserByUsername(username);
	}
	
	public User findUserById(int userId) {
		return userService.findUserById(userId);
	}
	
	public User findUserByEMail(String email) {
		return userService.findUserByEMail(email);
	}
	
	public boolean checkLogIn(String username, String password) {
		return userService.checkLogIn(username, password);
	}

	public int checkRegister(String username, String email){
		return userService.checkRegister(username, email);
	}
	
	/*
	 * 
	 *Category
	 *
	 * 
	 */
	public void addCategory(Category category){
		categoryService.addCategory(category);
	}
	
	public void deleteCategory(Category category) {
		categoryService.deleteCategory(category);
	}
	
	public void updateCategoryName(Category category, String neuerCatName){
		categoryService.updateCategorieName(category, neuerCatName);
	}
	public void updateCategoryDescription(Category category, String neueBeschreibung){
		categoryService.updateCategorieDescription(category, neueBeschreibung);
	}
	public Category findCategory(Category category) {
		return categoryService.findCategory(category);
	}
	
	public List<Category> findAllCategories() {
		return categoryService.findAllCategories();
	}

	public int countCategories() {
		return categoryService.countCategories();
	}
	
	public List<Category> findCategoriesByUserId(int userId) {
		return categoryService.findCategoriesByUserId(userId);
	}
	
	/*
	 * 
	 *Lection
	 *
	 * 
	 */
	public void addLection(Lection lection) {
		lectionService.addLection(lection);
	}
	
	public void deleteLection(Lection lection) {
		lectionService.deleteLection(lection);
	}
	
	public void updateLection(Lection lection) {
		lectionService.updateLection(lection);
	}
	
	public void updateName(String name, int lectionId) {
		lectionService.updateName(name, lectionId);
	}
	
	public void updateDescription(String description, int lectionId) {
		lectionService.updateDescription(description, lectionId);
	}
	
	public void updateLastExcercise(Timestamp timestamp, int lectionId) {
		lectionService.updateLastExcercise(timestamp, lectionId);
	}
	
	public Lection findLectionById(int lectionId) {
		return lectionService.findLectionById(lectionId);
	}
	
	public List<Lection> findLectionByCategoryId(int categoryId) {
		return lectionService.findLectionByCategoryId(categoryId);
	}
	
	public List<Lection> findLectionByFavoritelistId(int favoritelisteId) {
		return lectionService.findLectionByFavoritelistId(favoritelisteId);
	}
	/*
	 * 
	 *IndexCard
	 *
	 * 
	 */
	public int addIndexCard(IndexCard indexCard) {
		return indexCardService.addIndexCard(indexCard);
	}
	
	public void deleteIndexCard(IndexCard indexCard) {
		indexCardService.deleteIndexCard(indexCard);
	}
	
	public void updateIndexCard(IndexCard indexCard) {
		indexCardService.updateIndexCard(indexCard);
	}
	
	public void updateQuestion(String newQuestion, int indexCardId) {
		indexCardService.updateQuestion(newQuestion, indexCardId);
	}
	
	public void updateAnswer(String newAnswer, int indexCardId) {
		indexCardService.updateAnswer(newAnswer, indexCardId);
	}
	
	public void updatePictureId(int indexCardId, int pictureId) {
		indexCardService.updatePictureId(indexCardId, pictureId);
	}
	
	public IndexCard findIndexCardById(int indexCardId) {
		return indexCardService.findIndexCardById(indexCardId);
	}
	
	public List<IndexCard> findAllIndexCardsByLectionId(int lectionId) {
		return indexCardService.findAllIndexCardsByLectionId(lectionId);
	}

	public List<IndexCard> findAllIndexCardsByLectionIdWithoutHTML(int lectionId) {
		return indexCardService.findAllIndexCardsByLectionIdWithoutHTML(lectionId);
	}
	
	/*
	 * 
	 *Picture
	 *
	 * 
	 */
	public void deletePicture(int pictureId) {
		pictureService.deletePicture(pictureId);
	}
	
	public void updatePicture(int pictureId, Picture newPicture) {
		pictureService.updatePicture(pictureId, newPicture);
	}
	
	public Picture findPicture(int pictureId) {
		return pictureService.findPicture(pictureId);
	}
	
	public int addPicture(Picture picture) {
		return pictureService.addPicture(picture);
	}
	
	/*
	 * 
	 *Favoritelist
	 *
	 * 
	 */
	public int addFavoritelist(String name, int userId) {
		return favoritelistService.addFavoritelist(name, userId);
	}
	
	public void delete(int favoritId) {
		favoritelistService.delete(favoritId);
	}
	
	public void updateFavoritelist(int favListId, String name, int userId) {
		favoritelistService.updateFavoritelist(favListId, name, userId);
	}
	
	public void findFavoritelistById(int favoritelistId) {
		favoritelistService.findFavoritelistById(favoritelistId);
	}
	
	public Favoritelist findFavoritelist(int favoritelistId) {
		return favoritelistService.findFavoritelist(favoritelistId);
	}
	
	public List<Favoritelist> findAllFavoritelist() {
		return favoritelistService.findAllFavoritelist();
	}
	
	public List<Favoritelist> findFavoritesByUserId(int userId){
		return favoritelistService.findFavoritesByUserId(userId);
	}
	
	public Favoritelist findFavoritelistIdByUserId(int userId) {
		return favoritelistService.findFavoritelistIdByUserId(userId);
	}
}
