package de.kksystem.karteikarten.service.interfaces;

import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.User;

public interface UserService {
	int addUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	void updatePassword(String newPassword, int userId);
	void updateForename(String newForename, int userId);
	void updateSurname(String newSurname, int userId);
	User findUserByUsername(String username);
	User findUserById(int userId);
	User findUserByEMail(String email);
	List<Category> findCategoriesByUserId(int userId);
	boolean checkLogIn(String username, String password);
	int checkRegister(String username, String email);
}
