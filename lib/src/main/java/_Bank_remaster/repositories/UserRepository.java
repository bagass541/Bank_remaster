package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.models.User;

public interface UserRepository {
	
	User findByNamePatronymicSurname(String name, String patronymic, String surname); 
	
	User findById(long id);
	
	List<User> findAllUsers();
	
	void createUser(User user);
	
	void updateUser();
	
	void deleteUser();
}
