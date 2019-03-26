package de.kksystem.karteikarten.dao.interfaces;


import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.User;

public interface UserDao {
	void addUser(User user);
	void deleteUser(User user); // userId mitzugeben wuerde ausreichen
	void updateUser(User user);
	void updatePassword(String newPassword, int userId);
	void updateForename(String newForename, int userId);
	void updateSurname(String newSurname, int userId);
	User findUserByUsername(String username);
	User findUserById(int userId);
	User findUserByEMail(String email);
}
