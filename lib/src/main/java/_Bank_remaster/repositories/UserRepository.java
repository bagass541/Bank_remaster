package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.UserNotFoundException;
import _Bank_remaster.models.User;

public interface UserRepository {
	
	User findByNamePatronymicSurname(String name, String patronymic, String surname) throws UserNotFoundException; 
	
	User findById(long id) throws UserNotFoundException;
	
	List<User> findAllUsers();
	
	void createUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(long id);
}
