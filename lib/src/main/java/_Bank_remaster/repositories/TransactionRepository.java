package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.exceptions.TransactionNotFoundException;
import _Bank_remaster.models.Transaction;

public interface TransactionRepository {
	
	Transaction findById(long id) throws TransactionNotFoundException;
	
	List<Transaction> findAllTransactions();
	
	void createTransaction(Transaction transaction);
	
	void updateTransaction(Transaction transaction);
	
	void deleteTransaction(long id);
}
