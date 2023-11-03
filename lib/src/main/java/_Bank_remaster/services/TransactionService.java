package _Bank_remaster.services;

import java.math.BigDecimal;

import _Bank_remaster.models.Account;
import _Bank_remaster.models.Transaction;
import _Bank_remaster.models.TransactionType;

/*
 * The interface which is needed for convenient creation transactions.  
 */
public interface TransactionService {
	
	/*
	 * Create a transaction with the specified properties.
	 * If the operation is deposit or withdraw, senderAcc and recipAcc will be the same.
	 * 
	 * @param type The type of transaction (e. g. DEPOSIT, WITHDRAW, TRANSFER). 
	 * @param senderAcc The account from which the amount of money is transfered.
	 * @param recipAcc The account to which the amount of money is transfered.
	 * @param amount The amount to be transfered.
	 */
	public Transaction createTransaction(TransactionType type, Account senderAcc, Account recipAcc, BigDecimal amount);

}
