package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.UserNotFoundException;
import _Bank_remaster.models.User;

/*
 * The interface that interacts with user data.
 */
public interface UserRepository {
	
	/*
	 * Finds a user by his name, patronymic, surname.
	 * 
	 * @param name The user name to search for.
	 * @param patronymic The user patronymic to search for.
	 * @param surname The surname id to search for.
	 * @return The user with the specified name, patronymic, surname.
	 * @throws UserNotFoundException if no user is found with the specified name, patronymic, surname..
	 */
	User findByNamePatronymicSurname(String name, String patronymic, String surname) throws UserNotFoundException; 
	
	/*
	 * Finds a user by its id.
	 * 
	 * @param id The user id to search for.
	 * @return The user with the specified id.
	 * @throws UserNotFoundException if no user is found with the specified id.
	 */
	User findById(long id) throws UserNotFoundException;
	
	/*
	 * Retrieves a list of users from database.
	 * 
	 * @return A list of users.
	 */
	List<User> findAllUsers();
	
	/*
	 * Create a new user in the database.
	 * 
	 * @param account The user to be created.
	 */
	void createUser(User user);
	
	/*
	 * Update an existing user in the database.
	 * 
	 * @param account The user which exitsts in the database with updated properties.
	 */
	void updateUser(User user);
	
	/*
	 * Delete a user from the database by id.
	 * 
	 * @param id The id of the user to be deleted.
	 */
	void deleteUser(long id);
}
