package _Bank_remaster.repositories;

import java.sql.SQLException;
import java.util.List;

import _Bank_remaster.exceptions.TransactionNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.util.TimePeriod;

/*
 * The interface that interacts with transaction data.
 */
public interface TransactionRepository {
	
	/*
	 * Finds a transaction by its id.
	 * 
	 * @param id The transaction id to search for.
	 * @return The transaction with the specified id.
	 * @throws TransactionNotFoundException if no transaction is found with the specified id.
	 */
	Transaction findById(long id) throws TransactionNotFoundException;
	
	/*
	 * Retrieves a list of transactions by account.
	 * 
	 * @param user The account for which transactions are to be retrived.
	 * @return A list of account's transactions. 
	 */
	List<Transaction> findTransactionsByAccount(Account account) throws SQLException;
	
	/*
	 * Retrieves a list of transactions from database.
	 * 
	 * @return A list of transactions.
	 */
	List<Transaction> findAllTransactions();
	
	/*
	 * Retrieves a list of transactions by time period and account.
	 * 
	 * @param account The account for which transactions are to be retrived.
	 * @param period The period for which transactions are to be retrived.
	 * @return A list of transactions. 
	 */
	List<Transaction> findTransactionsByPeriodAccount(Account account, TimePeriod period) throws SQLException;
	
	/*
	 * Create a new transaction in the database.
	 * 
	 * @param transaction The transaction to be created.
	 */
	void createTransaction(Transaction transaction);
	
	/*
	 * Update an existing transaction in the database.
	 * 
	 * @param account The transaction which exitsts in the database with updated properties.
	 */
	void updateTransaction(Transaction transaction);
	
	/*
	 * Delete a transaction from the database by id.
	 * 
	 * @param id The id of the transaction to be deleted.
	 */
	void deleteTransaction(long id);
}
