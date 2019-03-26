package de.kksystem.karteikarten.service.classes;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.service.interfaces.UserService;

public class UserServiceImpl implements UserService {
	private DaoFacade daoFacade;
	
	public UserServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void addUser(User user) {
		daoFacade.addUser(user);
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
	public void updatePassword(String newPassword, int userId) {
		daoFacade.updatePassword(newPassword, userId);
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

}
