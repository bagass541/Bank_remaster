package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.BankNotFoundException;
import _Bank_remaster.models.Bank;

/*
 * The interface that interacts with bank data.
 */
public interface BankRepository {
	
	/*
	 * Finds a bank by its id.
	 * 
	 * @param id The bank id to search for.
	 * @return The bank with the specified id.
	 * @throws BankNotFoundException if no bank is found with the specified id.
	 */
	Bank findById(long id) throws BankNotFoundException;
	
	/*
	 * Retrieves a list of banks from database.
	 * 
	 * @return A list of banks.
	 */
	List<Bank> findAllBanks();
	
	/*
	 * Create a new bank in the database.
	 * 
	 * @param account The bank to be created.
	 */
	void createBank(Bank bank);
	
	/*
	 * Update an existing bank in the database.
	 * 
	 * @param account The bank which exitsts in the database with updated properties.
	 */
	void updateBank(Bank bank);
	
	/*
	 * Delete a bank from the database by id.
	 * 
	 * @param id The id of the bank to be deleted.
	 */
	void deleteBank(long id);
}
