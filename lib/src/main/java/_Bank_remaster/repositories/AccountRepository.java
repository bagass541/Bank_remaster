package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.AccNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.User;

/*
 * The interface that interacts with account data.
 */
public interface AccountRepository {
	
	/*
	 * Finds an account by its number.
	 * 
	 * @param number The account number to search for.
	 * @return The account with the specified number.
	 * @throws AccNotFoundException if no account is found with the specified number.
	 */
	Account findByNumber(String number) throws AccNotFoundException;
	
	
	/*
	 * Finds an account by its id.
	 * 
	 * @param id The account id to search for.
	 * @return The account with the specified id.
	 * @throws AccNotFoundException if no account is found with the specified id.
	 */
	Account findById(long id) throws AccNotFoundException;
	
	/*
	 * Retrieves a list of accounts from database.
	 * 
	 * @return A list of accounts.
	 */
	List<Account> findAllAccounts();
	
	
	/*
	 * Retrieves a list of account by user.
	 * 
	 * @param user The user for whom accounts are to be retrived.
	 * @return A list of user's accounts. 
	 */
	List<Account> findAccountsByUser(User user);
	
	/*
	 * Create a new account in the database.
	 * 
	 * @param account The account to be created.
	 * @throws PSQLException If a request has troubles with the properties.
	 */
	void createAccount(Account account);
	
	/*
	 * Update an existing account in the database.
	 * 
	 * @param account The account which exitsts in the database with updated properties.
	 * @throws PSQLException If a request has troubles with the properties.
	 */
	void updateAccount(Account account);
	
	/*
	 * Delete an account from the database by id.
	 * 
	 * @param id The id of the account to be deleted.
	 */
	void deleteAccount(long id);
}
