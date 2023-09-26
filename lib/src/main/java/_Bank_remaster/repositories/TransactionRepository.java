package _Bank_remaster.repositories;

import java.util.List;

import _Bank_remaster.models.Transaction;

public interface TransactionRepository {
	
	Transaction findById(long id);
	
	List<Transaction> findAllTransactions();
	
	void createTransaction(Transaction transaction);
	
	void updateTransaction();
	
	void deleteTransaction();
}
