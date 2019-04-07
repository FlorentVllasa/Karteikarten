package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.service.interfaces.UserService;

public class UserServiceImpl implements UserService {
	private DaoFacade daoFacade;
	
	public UserServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public int addUser(User user) {
		return daoFacade.addUser(user);
	}

	@Override
	public void deleteUser(User user) {
		daoFacade.deleteUser(user);
	}

	@Override
	public void updateUser(User user) {
		daoFacade.updateUser(user);
	}

	@Override
	public void updateUsername(String newUsername, int userId){
		daoFacade.updateUsername(newUsername, userId);
	}

	@Override
	public void updatePassword(String newPassword, int userId) {
		daoFacade.updatePassword(newPassword, userId);
	}

	@Override
	public void updateEmail(String newEmail, int userId){
		daoFacade.updateEmail(newEmail, userId);
	}

	@Override
	public void updateForename(String newForename, int userId) {
		daoFacade.updateForename(newForename, userId);
	}

	@Override
	public void updateSurname(String newSurname, int userId) {
		daoFacade.updateSurname(newSurname, userId);
	}

	@Override
	public User findUserByUsername(String username) {
		return daoFacade.findUserByUsername(username);
	}

	@Override
	public User findUserById(int userId) {
		return daoFacade.findUserById(userId);
	}

	@Override
	public User findUserByEMail(String email) {
		return daoFacade.findUserByEMail(email);
	}

	@Override
	public List<String> findAllUserEmails(){
		return daoFacade.findAllUserEmails();
	}

	@Override
	public List<String> findAllUserNames(){
		return daoFacade.findAllUserNames();
	}

	@Override
	public List<Category> findCategoriesByUserId(int userId) {
		return daoFacade.findCategoriesByUserId(userId);
	}
	
	@Override
	public boolean checkLogIn(String username, String password) {
		User user = daoFacade.findUserByUsername(username);
		//Favoritelist favoritelist = daoFacade.findF
		
		if(user != null && user.getPassword().equals(password)) {
			Favoritelist favoritelist = daoFacade.findFavoritelistIdByUserId(user.getUserId());
			UserData.getInstance().setUsername(user.getUsername());
			UserData.getInstance().setUserId(user.getUserId());
			UserData.getInstance().setFavoritelistId(favoritelist.getFavoritelistId());
			UserData.getInstance().setEmail(user.getEmail());
			UserData.getInstance().setPassword(user.getPassword());
			UserData.getInstance().setForeName(user.getForename());
			UserData.getInstance().setSurname(user.getSurname());
			
			return true;
		}
		return false;
	}

	@Override
	public int checkRegister(String username, String email){
		User searchUserEmail = daoFacade.findUserByEMail(email);
		User searchUserName = daoFacade.findUserByUsername(username);

		if(searchUserEmail != null && searchUserEmail.getEmail().equals(email)){
			return 1;
		}else if(searchUserName != null && searchUserName.getUsername().equals(username)){
			return 0;
		}
		return -1;
	}

}
