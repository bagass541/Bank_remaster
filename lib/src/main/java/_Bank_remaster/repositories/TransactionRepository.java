package _Bank_remaster.repositories;

import java.sql.SQLException;
import java.util.List;

import _Bank_remaster.exceptions.TransactionNotFoundException;
import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.util.TimePeriod;

public interface TransactionRepository {
	
	Transaction findById(long id) throws TransactionNotFoundException;
	
	List<Transaction> findTransactionsByAccount(Account account) throws SQLException;
	
	List<Transaction> findAllTransactions();
	
	List<Transaction> findTransactionsByPeriodAccount(Account account, TimePeriod period) throws SQLException;
	
	void createTransaction(Transaction transaction);
	
	void updateTransaction(Transaction transaction);
	
	void deleteTransaction(long id);
}
