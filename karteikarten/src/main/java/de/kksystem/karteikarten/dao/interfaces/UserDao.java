package de.kksystem.karteikarten.dao.interfaces;


import java.util.List;

import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.User;

public interface UserDao {
	int addUser(User user);
	void deleteUser(User user); // userId mitzugeben wuerde ausreichen
	void updateUser(User user);
	void updateUsername(String newUsername, int userId);
	void updatePassword(String newPassword, int userId);
	void updateEmail(String newEmail, int userId);
	void updateForename(String newForename, int userId);
	void updateSurname(String newSurname, int userId);
	User findUserByUsername(String username);
	User findUserById(int userId);
	User findUserByEMail(String email);
	List<String> findAllUserNames();
	List<String> findAllUserEmails();
}
